package com.intel.mtwilson.datatypes;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.Assert;
import org.junit.Test;  
import static org.junit.Assert.assertEquals;
public class IPAddressTest {

@Test
public void testToString(){
	String ip;
	try {
		InetAddress addr = InetAddress.getLocalHost();
		ip=addr.getHostAddress().toString();
		IPAddress ipaddress=new IPAddress(ip);
		assertEquals(ip,ipaddress.toString());
	} catch (UnknownHostException e) {
		e.printStackTrace();
	}	
}

@Test(expected=IllegalArgumentException.class)
public void testSetAddressNullExceptionIsThrown(){
	IPAddress ipaddress=new IPAddress("1.2.3.4");
	ipaddress.setAddress("1:2,3.4");
}

@Test
public void testInvalidAddressExceptionThrown(){
	IPAddress ipaddress=new IPAddress("1.2.3.4");
	ipaddress.isValid("1,2,3,4");
	Assert.assertFalse(ipaddress.isValid("1,2,3,4"));
}

@Test
public void testParse(){
	IPAddress ipaddress=new IPAddress("1.2.3.4");
	assertEquals("5.6.7.8",ipaddress.parse("5.6.7.8").toString());
}

@Test(expected=IllegalArgumentException.class)
public void testParseExceptionThrown(){
	IPAddress ipaddress=new IPAddress("1.2.3.4");
	ipaddress.parse("jfkdslafdsa");
}

@Test
public void testEquals(){
	IPAddress ipaddress1=new IPAddress("1.2.3.4");
	IPAddress ipaddress2 =new IPAddress("5.6.7.8");
	Assert.assertTrue(ipaddress1.equals(ipaddress1));
	assertEquals(new IPAddress(),new IPAddress());
	Assert.assertFalse(new IPAddress().equals("foo"));
	Assert.assertFalse(new IPAddress().equals(null));
	Assert.assertFalse(ipaddress1.equals(ipaddress2));
}
}