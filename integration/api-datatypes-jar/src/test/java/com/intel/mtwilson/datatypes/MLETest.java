package com.intel.mtwilson.datatypes;
import org.junit.Assert;
import org.junit.Test;  
import static org.junit.Assert.assertEquals;
public class MLETest {
	Bios bios=new Bios("intel","1.0","OEM1");
	Vmm  vmm= new Vmm("kvm","2.0","RHEL","6.5");
	MLE mle=new MLE(bios,vmm);
@Test
public void testToString(){
	final String a = "BIOS:" + bios.toString() + "," + "VMM:" + vmm.toString();
	assertEquals(a,mle.toString());	
}
}
