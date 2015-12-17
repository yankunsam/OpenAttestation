package com.intel.mtwilson;
import org.junit.Test;  
import static org.junit.Assert.assertEquals;
public class HtmlErrorParserTest {

    HtmlErrorParser htmlerrorparser=new HtmlErrorParser("<h3>GlassFish Server");
    
    @Test
    public void testGetServerName(){
        assertEquals("GlassFish",htmlerrorparser.getServerName());
    }
    
    @Test
    public void testGetServerNameNull(){
        HtmlErrorParser htmlerrorparser=new HtmlErrorParser(null);
        assertEquals(null,htmlerrorparser.getServerName());
    }
    
    @Test
    public void testGetServerNameNotContainGlassfish(){
        HtmlErrorParser htmlerrorparser=new HtmlErrorParser("<h3>Tomcat Server");
        assertEquals(null,htmlerrorparser.getServerName());
    }
    
    @Test
    public void testGetRootCause(){
        assertEquals(null,htmlerrorparser.getRootCause());
    }
}
