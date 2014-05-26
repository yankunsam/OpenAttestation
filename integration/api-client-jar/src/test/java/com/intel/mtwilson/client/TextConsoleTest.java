package com.intel.mtwilson.client;
import org.junit.Assert;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;  
public class TextConsoleTest {
    String[] args1;
    File file=new File("system_err.txt");
    
    @Before
    public void setUp(){
        args1 = new String[]{"oat_os","oat_host","oat_whitelist"};
    }
    
    @After
    public void tearDown(){
        try {
            File file2 = new File("system_err.txt");
            file2.delete();
        } catch (Exception e){
            System.out.println("error message " + e.toString());
        }

    }
    
    @Test
    public void testMain1(){
        FileOutputStream fos = null;
        BufferedInputStream bis = null;
        try {
            fos = new FileOutputStream(file);
            System.setErr(new PrintStream(fos));
            TextConsole.main(args1);
            bis=new BufferedInputStream(new FileInputStream(new File(file.toString())));
            byte b[]=new byte[1024];
            int length=bis.read(b);  
            String temp=new String(b,0,length).trim();
            StringBuffer sb = new StringBuffer();
            sb.append("Unrecognized command:").append(" oat_os:").append(" com.intel.mtwilson.client.cmd.oat_os");
            Assert.assertEquals(sb.toString(), temp);
        } catch (IOException e){
            System.out.println("error message " + e.toString()); 
        } catch (Exception e){
            System.out.println("error message " + e.toString()); 
        } finally {
            fos.close();
            bis.close();
        }
    }
}
