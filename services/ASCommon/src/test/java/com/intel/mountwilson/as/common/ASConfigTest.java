package com.intel.mountwilson.as.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.junit.Assert;
import org.junit.Test;

import com.intel.mountwilson.as.common.ASConfig;


public class ASConfigTest {    
    @Test
    public void testGetDefaults() {
        Assert.assertEquals(ASConfig.getInstance().getDefaults().getProperty("com.intel.mountwilson.as.home"),
                "C:/work/aikverifyhome");
        Assert.assertEquals(ASConfig.getInstance().getDefaults().getProperty("com.intel.mountwilson.as.openssl.cmd"),
                "openssl.bat");
        Assert.assertEquals(ASConfig.getInstance().getDefaults().getProperty("com.intel.mountwilson.as.trustagent.timeout"),
                "3");
        Assert.assertEquals(ASConfig.getInstance().getDefaults().getProperty("com.intel.mountwilson.as.attestation.hostTimeout"),
                "30");
    }
    
    @Test
    public void testGetJpaProperties(){
        Properties prop = ASConfig.getJpaProperties();
        
        //TODO: Not care about the value defined in current ENV, will cover the relevant logic in test suites for ConfigBase.java
        /*
        InputStream inputStream = this.getClass().getResourceAsStream("/" + "attestation-service.properties");
        Properties expectedProp = new Properties();
        try {
            if (inputStream != null) {
                expectedProp.load(inputStream);
                
            } else {
                expectedProp.load(new FileInputStream("/etc/intel/cloudsecurity/" + "attestation-service.properties"));
                expectedProp.load(new FileInputStream("/etc/intel/cloudsecurity/mtwilson.properties"));
            }
        } catch (IOException ex) {
            System.out.println("Encounter an exception, detail message: " + ex.toString());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    System.out.println("Encounter an exception, detail message: " + ex.toString());
                }
            }
        }
        */
        
        //Just compare with default value.
        //It's not surprising to know some test cases listed below will unable to pass verification in the env has been deployed with OAT previously 
        Assert.assertEquals(prop.getProperty("javax.persistence.jdbc.driver"), "com.mysql.jdbc.Driver");
        Assert.assertEquals(prop.getProperty("javax.persistence.jdbc.url"), "jdbc:mysql://127.0.0.1:3306/mw_as?autoReconnect=true");
        Assert.assertEquals(prop.getProperty("javax.persistence.jdbc.user"), "root");
        Assert.assertEquals(prop.getProperty("javax.persistence.jdbc.password"), "password");
    }
    
    @Test
    public void testGetTrustAgentTimeOutinMilliSecs(){
        Assert.assertEquals(ASConfig.getTrustAgentTimeOutinMilliSecs(), 3000);
    }

    @Test
    public void testGetTrustAgentSleepTimeinMilliSecs(){
        Assert.assertEquals(ASConfig.getTrustAgentSleepTimeinMilliSecs(), 2000);
    }
}
