package com.intel.mtwilson.datatypes;
import org.junit.Assert;
import org.junit.Test;  
import static org.junit.Assert.assertEquals;
public class HostLocationRequestTest {

@Test(expected=IllegalArgumentException.class)
    public void testSetHostnameNullExceptionIsThrown(){
    HostLocationRequest hostlocationrequest=new HostLocationRequest("node1","spark","abc123","host");
    hostlocationrequest.setHostName(null);
    }    
}
