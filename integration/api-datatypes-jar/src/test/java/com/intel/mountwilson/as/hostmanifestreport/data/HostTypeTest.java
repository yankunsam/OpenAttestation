package com.intel.mountwilson.as.hostmanifestreport.data;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

public class HostTypeTest {
    @Test
    public void testGetManifest(){
        HostType hostInst = new HostType();
        //test in case manifest is null;
        Assert.assertEquals(new ArrayList<ManifestType>(), hostInst.getManifest());
        //test in case manifest has been initialized;
        Assert.assertEquals(new ArrayList<ManifestType>(), hostInst.getManifest());
    }
}
