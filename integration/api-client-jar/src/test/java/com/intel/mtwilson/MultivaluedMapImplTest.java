package com.intel.mtwilson;
import java.util.List;
import org.junit.Test;  
import static org.junit.Assert.assertEquals;
public class MultivaluedMapImplTest {

    MultivaluedMapImpl<String,Integer> multivaluedmapimpl=new MultivaluedMapImpl<String,Integer>();;
    
    @Test
    public void testPutSingle(){
        multivaluedmapimpl.putSingle("abc",10);
        List<Integer> array = multivaluedmapimpl.get("abc");
        assertEquals("[10]",array.toString());        
    }
    
    @Test
    public void testAdd(){
        multivaluedmapimpl.add("def",1024);
        List<Integer> array = multivaluedmapimpl.get("def");
        assertEquals("[1024]",array.toString());  
    }
    
    @Test
    public void testGetFrist(){
        multivaluedmapimpl.add("GHI", 30);
        Integer integer= multivaluedmapimpl.getFirst("GHI");
        assertEquals("30",integer.toString());
        
    }
}