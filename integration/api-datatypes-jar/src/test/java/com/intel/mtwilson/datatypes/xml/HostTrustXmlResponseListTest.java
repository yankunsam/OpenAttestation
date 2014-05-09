package com.intel.mtwilson.datatypes.xml;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.Assert;


public class HostTrustXmlResponseListTest {
    @Test
    public void testGetHost(){
        Assert.assertEquals(new ArrayList<HostTrustXmlResponse>(), new HostTrustXmlResponseList().getHost());
    }
    
}
