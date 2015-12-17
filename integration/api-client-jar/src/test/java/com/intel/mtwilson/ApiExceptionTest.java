package com.intel.mtwilson;
import javax.ws.rs.core.MediaType;
import org.junit.Test;  
import static org.junit.Assert.assertEquals;
public class ApiExceptionTest {

    @Test
    public void testGetErrorCode(){
        ApiException apiexception=new ApiException("error!",502);
        assertEquals(502,apiexception.getErrorCode());
    }
    
    @Test
    public void testGetHttpReasonPhrase(){
        MediaType contentType=new MediaType();
        byte[] content=new byte[]{0x30, 0x31};
        ApiResponse response=new ApiResponse(404,"Not Found",contentType,content);
        ApiException apiexception=new ApiException(response,"error!");
        assertEquals("Not Found",apiexception.getHttpReasonPhrase());
    }
    
    @Test
    public void testGetHttpReasonPhraseNull(){
        ApiException apiexception=new ApiException("error!");
        assertEquals(null,apiexception.getHttpReasonPhrase());
    }
    
    @Test
    public void testGetHttpStatusCode(){
        MediaType contentType=new MediaType();
        byte[] content=new byte[]{0x30, 0x31};
        ApiResponse response=new ApiResponse(400,"Invalid Request!",contentType,content);
        ApiException apiexception=new ApiException(response,"error!");
        assertEquals("400",apiexception.getHttpStatusCode().toString());
    }
    
    @Test
    public void testGetHttpStatusCodeNull(){
        ApiException apiexception=new ApiException("error!");
        assertEquals(null,apiexception.getHttpStatusCode());
	}
}
