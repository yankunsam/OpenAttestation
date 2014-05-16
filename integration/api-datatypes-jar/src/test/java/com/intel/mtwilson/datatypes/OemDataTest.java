package com.intel.mtwilson.datatypes;
import org.junit.Assert;
import org.junit.Test;  
import static org.junit.Assert.assertEquals;
public class OemDataTest {

@Test(expected=IllegalArgumentException.class)
public void testGetNameIllegalArgumentExceptionIsThrown(){
    OemData oemdata=new OemData(null,"Description"); 
    oemdata.getName();
}
}
