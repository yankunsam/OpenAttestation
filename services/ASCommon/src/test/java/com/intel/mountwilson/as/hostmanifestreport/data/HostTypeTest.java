package com.intel.mountwilson.as.hostmanifestreport.data;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class HostTypeTest {

    ManifestType manifesttype1 = new ManifestType();
    ManifestType manifesttype2 = new ManifestType();
    HostType hosttype=new HostType();
    
    @Test
    public void testManifestIsNullAndGetManifest(){
        hosttype.manifest=null;
        assertEquals(0,hosttype.getManifest().size());
    }
    
    @Test
    public void testGetManifest(){
        hosttype.manifest = new ArrayList<ManifestType>();
        hosttype.manifest.add(manifesttype1);
        hosttype.manifest.add(manifesttype2);
        assertEquals(2, hosttype.getManifest().size());
    }  
}
