package com.intel.mountwilson.manifest.helper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TAHelperTest {
    byte[] test, qinfo, dest;
    
    @Before
    public void initialize(){
        test = new byte[]{(byte)0x0f, (byte)0x3a, (byte)0x03, (byte)0x2a};
        qinfo = new byte[256];
        dest = new byte[256];
    }

    @Test
    public void testHexString(){
        Assert.assertEquals("0F", TAHelper.hexString(test[0]));
        Assert.assertEquals("3A", TAHelper.hexString(test[1]));
    }
   
    @Test
    public void testNtohs(){
        Assert.assertEquals(3898, TAHelper.ntohs(test, 0));
    }
        
    @Test
    public void testNtohl(){
        Assert.assertEquals(255460138, TAHelper.ntohl(test, 0));
    }

}





