/*
 * Copyright (c) 2013, Intel Corporation. 
 * All rights reserved.
 * 
 * The contents of this file are released under the BSD license, you may not use this file except in compliance with the License.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * Neither the name of Intel Corporation nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.intel.mtwilson.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.EnvironmentConfiguration;
import org.apache.commons.configuration.MapConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Attempts to use commons-configuration to load Mt Wilson environment settings.
 *
 * @since 0.5.2
 * @author jbuhacoff
 */
public final class ConfigurationUtil {
    private static final Logger log = LoggerFactory.getLogger(ConfigurationUtil.class);
    private static String DEFAULT_WINDOWS_HOME = "C:\\Intel\\CloudSecurity";
    private static String DEFAULT_LINUX_HOME = "/etc/intel/cloudsecurity";
    private static final Configuration[] sources = new Configuration[] { new SystemConfiguration(), new EnvironmentConfiguration(), new AllCapsEnvironmentConfiguration() }; // in priority order: check system (JVM args) first, then environment (as given and in ALL_CAPS format)
    private static final CompositeConfiguration systemConfiguration = new CompositeConfiguration();
    private static final CompositeConfiguration rc3 = new CompositeConfiguration();
    private static final String homeFolder;
    private static final File configurationFile;

    /**
     * Configuration parameters are obtained from the following sources
     * in priority order:
     * System Configuration (JVM Properties)
     * Environment Variables (Shell)
     * mtwilson.properties  
     * 
     * The mtwilson.properties file is loaded from the location returned by
     * getConfigurationFile(). The containing folder is available as 
     * getConfigurationFolderPath(). To change this location, pass a -Dmtwilson.home=/path/to/dir 
     * argument to the JVM or set the MTWILSON_HOME environment variable. 
     * 
     * If you have a specific file from which to load the configuration,
     * call fromInputStream() or fromResource() instead.
     * 
     * @return Configuration from a composite of sources
     * @since 0.5.4
     */
    public static Configuration getConfiguration() { return rc3; }
    
    /**
     * @return the file representing mtwilson.properties
     */
    public static File getConfigurationFile() { return configurationFile; }
    
    /**
     * To change the return value of this method, set -Dmtwilson.home=/path/to/dir
     * in the JVM args or set the MTWILSON_HOME environment variable.
     * 
     * @return the path to the folder containing the mtwilson.properties file
     */
    public static String getConfigurationFolderPath() { return homeFolder; }

    
    static {
        // system configuration (JVM args and environment variables)
        for( Configuration source : sources ) {
            systemConfiguration.addConfiguration(source);
        }
        // mt wilson configuration folder
        homeFolder = systemConfiguration.getString("mtwilson.home", getDefaultMtWilsonHome());
        // mt wilson RC3 configuration file
        configurationFile = new File(homeFolder + File.separator + "mtwilson.properties");
        // mt wilson RC3 configuration
        rc3.addConfiguration(systemConfiguration);
        try {
            if( configurationFile.exists() && configurationFile.canRead() ) {                
                rc3.addConfiguration(new PropertiesConfiguration(configurationFile));
            }
            else {
                log.warn("Missing mtwilson.properties file: "+configurationFile.getAbsolutePath());
            }
        } catch (ConfigurationException ex) {
            log.error("Cannot load configuration file: "+configurationFile.getAbsolutePath(), ex);
        }
    }
            
    private static String getDefaultMtWilsonHome() {
        String flavorSpecificDefault = null;
        if (System.getProperty("os.name", "").toLowerCase().contains("win")) {
            flavorSpecificDefault = DEFAULT_WINDOWS_HOME;
        }
        if (System.getProperty("os.name", "").toLowerCase().contains("linux")
            || System.getProperty("os.name", "").toLowerCase().contains("unix")) {
            flavorSpecificDefault = DEFAULT_LINUX_HOME;
        }
        File folder = new File(flavorSpecificDefault);
        if( folder.exists() ) {
            return flavorSpecificDefault;
        }
        // last resort is whatever the current operating system defines as a "mtwilson" folder in the user's home directory
        return System.getProperty("user.home") + File.separator + "mtwilson";
    }
    
    

    /**
     * Does NOT close the InputStream
     * 
     * @param properties
     * @return
     * @throws IOException 
     */
    public static Configuration fromInputStream(InputStream properties) throws IOException {
        Properties p = new Properties();
        p.load(properties);
        return new MapConfiguration(p);        
    }
    
    // resource must be visible to classloader of THIS class
    public static Configuration fromResource(String resourceName) throws IOException {
        InputStream in = ConfigurationUtil.class.getResourceAsStream(resourceName);
        try {
            return fromInputStream(in);
        }
        finally {
            in.close();
        }
    }
    
    /**
     * The Apache Commons Configuration PropertiesConfiguration class is very lax about what it
     * allows in a properties file. Keys can have equal signs, colons, or spaces to separate them
     * from values. This convenience method loads the named file using a Java Properties object
     * and then creates a Configuration object from that. This enforces the Java properties format.
     * @param propertiesFile
     * @return
     * @throws ConfigurationException 
     */
    public static Configuration fromPropertiesFile(File propertiesFile) throws IOException {
        FileInputStream in = new FileInputStream(propertiesFile);
        try {
            return fromInputStream(in);
        }
        finally {
            in.close();
        }
    }
}
