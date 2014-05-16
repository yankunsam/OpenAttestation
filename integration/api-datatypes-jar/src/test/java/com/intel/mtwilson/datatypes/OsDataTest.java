package com.intel.mtwilson.datatypes;
import org.junit.Assert;
import org.junit.Test;  
import static org.junit.Assert.assertEquals;
public class OsDataTest {

@Test(expected=IllegalArgumentException.class)
    public void testGetNameIllegalArgumentExceptionIsThrown(){
        OsData osdata=new OsData(null,"1.0","Description"); 
        osdata.getName();
}

@Test(expected=IllegalArgumentException.class)
    public void testGetVersionIllegalArgumentExceptionIsThrown(){
        OsData osdata=new OsData("RHEL",null,"Description"); 
        osdata.getVersion();              
}
}
