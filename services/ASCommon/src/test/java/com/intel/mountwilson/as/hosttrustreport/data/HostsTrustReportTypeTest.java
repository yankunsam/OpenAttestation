package com.intel.mountwilson.as.hosttrustreport.data;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;

import org.junit.Test;

public class HostsTrustReportTypeTest {

    HostType hosttype1=new HostType();
    HostType hosttype2=new HostType();
    HostsTrustReportType hosttrustreporttype=new HostsTrustReportType();
    
    @Test
    public void testHostIsNullAndGetHost(){
        hosttrustreporttype.host = null;
        assertEquals(0,hosttrustreporttype.getHost().size());
    }
    
    @Test
    public void testGetHost(){
        hosttrustreporttype.host = new ArrayList<HostType>();
        hosttrustreporttype.host.add(hosttype1);
        hosttrustreporttype.host.add(hosttype2);
        assertEquals(2,hosttrustreporttype.getHost().size());
    }
}
