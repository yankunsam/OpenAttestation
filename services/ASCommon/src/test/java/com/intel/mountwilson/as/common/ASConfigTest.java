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
    public void testGetTrustAgentTimeOutinMilliSecs(){
        Assert.assertEquals(ASConfig.getTrustAgentTimeOutinMilliSecs(), 3000);
    }

    @Test
    public void testGetTrustAgentSleepTimeinMilliSecs(){
        Assert.assertEquals(ASConfig.getTrustAgentSleepTimeinMilliSecs(), 2000);
    }
}
