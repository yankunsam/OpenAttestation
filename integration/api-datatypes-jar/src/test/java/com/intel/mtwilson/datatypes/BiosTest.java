package com.intel.mtwilson.datatypes;

import com.intel.mtwilson.datatypes.Bios;
import org.junit.Test;
import org.junit.Assert;

public class BiosTest {
    Bios inst = new Bios("1217", "v1", "oem");
    
    @Test
    public void testToString(){
        Assert.assertEquals("1217:v1:oem", new Bios("1217", "v1", "oem").toString());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testOemNullExceptionIsThrown() {
      inst.setOem(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testOemEmptyExceptionIsThrown() {
      inst.setOem("");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testNameNullExceptionIsThrown() {
      inst.setName(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testNameEmptyExceptionIsThrown() {
      inst.setName("");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testVersionNullExceptionIsThrown() {
      inst.setVersion(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testVersionEmptyExceptionIsThrown() {
      inst.setVersion("");
    }
    
}
