package com.intel.mountwilson.manifest.helper;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class SHA1HashBuilderTest {
    SHA1HashBuilder sha1HashBuilder;
    byte[] bytes, appendBytes;
    
    @Before
    public void initialize(){
        bytes = new byte[20];
        appendBytes = new byte[20];
        sha1HashBuilder = new SHA1HashBuilder();
        Arrays.fill(appendBytes, (byte)1);
    }
    
    @Test
    public void testSHA1HashBuilder(){   
        Assert.assertArrayEquals(bytes, sha1HashBuilder.get_data());
    }
    
    @Test
    public void testGetData(){
        Assert.assertArrayEquals(bytes, sha1HashBuilder.get_data());
    }
    
    @Test
    public void testAppend(){
        sha1HashBuilder.append(appendBytes);
        Assert.assertEquals(20, sha1HashBuilder.get_data().length);
        byte[] _data = sha1HashBuilder.get_data();
        sha1HashBuilder.append(null);
        Assert.assertEquals(_data, sha1HashBuilder.get_data());
    }

}
