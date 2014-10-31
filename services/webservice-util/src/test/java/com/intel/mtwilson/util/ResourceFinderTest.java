package com.intel.mtwilson.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ResourceFinderTest {

	private File file;
	
	@Before
	public void setUp() throws Exception {
		 file = new File("config/test1.txt");
		 file.getParentFile().mkdir();
		 file.createNewFile();
	}

	@After
	public void tearDown() throws Exception {
		file.delete();
		file.getParentFile().delete();
	}

	@Test 
	public void testGetFile() throws FileNotFoundException {
		File file2 = ResourceFinder.getFile("config/test1.txt");
		assertNotNull(file2);
	}

	@Test(expected = FileNotFoundException.class)
	public void testGetFileNotFound() throws FileNotFoundException {
		ResourceFinder.getFile("test2.txt");
	}
	
	@Test
	public void testGetURL() throws FileNotFoundException {
		URL url = ResourceFinder.getURL("config/test1.txt");
		assertNotNull(url);
	}

	@Test
	public void testGetLocation() throws FileNotFoundException {
		String location = ResourceFinder.getLocation("config/test1.txt");
		assertNotNull(location);
	}

}
