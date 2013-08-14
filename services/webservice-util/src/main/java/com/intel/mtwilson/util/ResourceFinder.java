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

package com.intel.mtwilson.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Locates a resource such as a configuration file.
 * 
 * The resource search locations, in order:
 * Classpath
 * Standard configuration locations for windows/linux
 * 
 * @author jbuhacoff
 */
public class ResourceFinder {
    private static Logger log = LoggerFactory.getLogger(ResourceFinder.class);
    
    // returns a File from which you can getAbsolutePath or wrap with FileInputStream
    public static File getFile(String filename) throws FileNotFoundException {
        // try standard install locations        
//        System.out.println("ResourceFinder os.name="+System.getProperty("os.name"));
        ArrayList<File> files = new ArrayList<File>();
        // first try an absolute filename or relative to current directory
        files.add(new File(filename));
        // windows-specific location
        if( System.getProperty("os.name", "").toLowerCase().contains("win") ) {
            System.out.println("ResourceFinder user.home="+System.getProperty("user.home"));
            files.add(new File("C:"+File.separator+"Intel"+File.separator+"CloudSecurity"+File.separator+filename));
            files.add(new File(System.getProperty("user.home")+File.separator+filename));
        }
        // linux-specific location
        if( System.getProperty("os.name", "").toLowerCase().contains("linux") || System.getProperty("os.name", "").toLowerCase().contains("unix") ) {
            files.add(new File("./config/"+filename));
            files.add(new File("/etc/intel/cloudsecurity/"+filename));
            files.add(new File(System.getProperty("user.home")+File.separator+filename));
        }
        // try all the files we found
        for(File f : files) {
            if( f.exists() && f.canRead() ) {
                return f;
            }
        }
        
        throw new FileNotFoundException("cannot find "+filename+" [os.name="+System.getProperty("os.name")+"]");        
    }
    
    public static URL getURL(String filename) throws FileNotFoundException {
        // try classpath
        URL relativeClasspathResource = ResourceFinder.class.getResource(filename);
        if( relativeClasspathResource != null ) {
            return relativeClasspathResource;
        }
        URL absoluteClasspathResource = ResourceFinder.class.getResource("/"+filename);
        if( absoluteClasspathResource != null ) {
            return absoluteClasspathResource;
        }

        try {
            File f = getFile(filename);
            return f.toURI().toURL();
        } catch (MalformedURLException ex) {
            log.error("Invalid path or URL: "+filename, ex);
        } // not catching FileNotFoundException because if we don't find it here we're throwing the same exception anyway
        
        throw new FileNotFoundException("Cannot find "+filename);        

    } 
    
    // retained for compatibility with previous version of this class which did not have the getURL() method
    public static String getLocation(String filename) throws FileNotFoundException {
        return getURL(filename).toExternalForm();
    }
}
