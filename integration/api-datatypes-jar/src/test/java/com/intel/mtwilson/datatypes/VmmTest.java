package com.intel.mtwilson.datatypes;
import org.junit.Assert;
import org.junit.Test;  
import static org.junit.Assert.assertEquals;
public class VmmTest {

@Test
public void testToString(){
	Vmm vmm=new Vmm("xen","2.0","RHEL","6.5");
	assertEquals("xen:2.0",vmm.toString());	
}

@Test(expected=IllegalArgumentException.class)
public void testNameOrVersionNullExceptionIsThrown(){
	Vmm vmm=new Vmm(null,null,"RHEL","6.5");
}

@Test(expected=IllegalArgumentException.class)
public void testNameOrVersionIsEmptyIsThrown(){
    Vmm vmm=new Vmm("","","RHEL","6.5");
}

@Test(expected=IllegalArgumentException.class)
public void testOsNameOrOsVersionNullExceptionIsThrown(){
	Vmm vmm=new Vmm("xen","2.0",null,null);
}

@Test(expected=IllegalArgumentException.class)
public void testOsNameOrOsVersionIsEmptyIsThrown(){
    Vmm vmm=new Vmm("xen","2.0","","");
}
}
