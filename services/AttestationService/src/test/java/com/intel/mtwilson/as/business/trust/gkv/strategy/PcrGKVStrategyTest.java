package com.intel.mtwilson.as.business.trust.gkv.strategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.persistence.EntityManagerFactory;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.intel.mountwilson.manifest.data.IManifest;
import com.intel.mountwilson.manifest.data.PcrManifest;
import com.intel.mtwilson.as.controller.TblMleJpaController;
import com.intel.mtwilson.as.controller.TblPcrManifestJpaController;
import com.intel.mtwilson.as.data.TblMle;
import com.intel.mtwilson.as.data.TblOem;
import com.intel.mtwilson.as.data.TblOs;
import com.intel.mtwilson.as.data.TblPcrManifest;

@RunWith(MockitoJUnitRunner.class)
public class PcrGKVStrategyTest {
	@Spy
	PcrGKVStrategy gkvstrategy = new PcrGKVStrategy();
	
	@Mock
	TblMleJpaController mleJpaController;
	
	@Mock
	TblPcrManifestJpaController  pcrManifestJpaController;
	
	@Mock
	private EntityManagerFactory emf;

	@Before
	public void setUp() throws Exception {
		doReturn(emf).when(gkvstrategy).getEntityManagerFactory();
		doReturn(mleJpaController).when(gkvstrategy).getMleJpaController();
		doReturn(pcrManifestJpaController).when(gkvstrategy).getPcrManifestJpaController();
	}
	public TblMle mockFindBiosMle() {
		String biosName = "DELL";
		String biosVersion = "A08";
		String oemName = "DELL";
	    TblMle biosMle = new TblMle();
	    biosMle.setId(1);
	    biosMle.setName(biosName);
	    biosMle.setVersion(biosVersion);
	    
	    TblOem oem = new TblOem();
	    oem.setId(1);
	    oem.setName(oemName);
	    biosMle.setOemId(oem);
	    
	    Collection<TblPcrManifest> tblPcrManifestCollection = new ArrayList<TblPcrManifest>();
	    TblPcrManifest tblPcrManifest = new TblPcrManifest();
	    tblPcrManifest.setId(1);
	    tblPcrManifest.setName("0");
	    tblPcrManifestCollection.add(tblPcrManifest);
	    biosMle.setTblPcrManifestCollection(tblPcrManifestCollection);
	    return biosMle;
	}
	
	public TblMle mockFindVmmMle() {
		String vmmName = "XEN";
		String vmmVersion = "4.3";
		String osName = "Fedora";
		String osVersion = "20";
	    TblMle vmmMle = new TblMle();
	    vmmMle.setId(1);
	    vmmMle.setName(vmmName);
	    vmmMle.setVersion(vmmVersion);
	    
	    TblOs os = new TblOs();
	    os.setId(1);
	    os.setName(osName);
	    os.setVersion(osVersion);
	    vmmMle.setOsId(os);
	    
	    Collection<TblPcrManifest> tblPcrManifestCollection = new ArrayList<TblPcrManifest>();
        TblPcrManifest tblPcrManifest = new TblPcrManifest();
        tblPcrManifest.setId(2);
        tblPcrManifest.setName("18");
        tblPcrManifestCollection.add(tblPcrManifest);
        vmmMle.setTblPcrManifestCollection(tblPcrManifestCollection);
	    return vmmMle;
	}
	
	@Test 
	public void testGetBiosGoodKnownManifest() {
	    when(mleJpaController.findBiosMle(anyString(),anyString(),anyString())).thenReturn(mockFindBiosMle());
	    TblPcrManifest pcr = new TblPcrManifest(1,"0","31B97D97B4679917EC3C1D943635693FFBAB4143");
	    when(pcrManifestJpaController.findPcrManifestById(any(Integer.class))).thenReturn(pcr);
	   
		HashMap<String, IManifest> pcrManifests = (HashMap<String, IManifest>) gkvstrategy.getBiosGoodKnownManifest("DELL", "A08", "DELL");
		assertNotNull(pcrManifests);
		assertEquals(pcrManifests.size(), 1);
		PcrManifest pcrMf = (PcrManifest) pcrManifests.get("0");
		assertNotNull(pcrMf);
		assertEquals(pcrMf.getPcrNumber(), 0);
		assertEquals(pcrMf.getPcrValue(), "31B97D97B4679917EC3C1D943635693FFBAB4143");
	}

	@Test
	public void testGetVmmGoodKnownManifest() {
		when(mleJpaController.findVmmMle(anyString(), anyString(), anyString(), anyString())).thenReturn(mockFindVmmMle());
		TblPcrManifest pcr = new TblPcrManifest(1,"18","FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
		when(pcrManifestJpaController.findPcrManifestById(any(Integer.class))).thenReturn(pcr);
		
		HashMap<String, IManifest> pcrManifests = (HashMap<String, IManifest>) gkvstrategy
				.getVmmGoodKnownManifest("XEN", "4.3", "Fedora", "20", 1);
		assertNotNull(pcrManifests);
		assertEquals(pcrManifests.size(), 1);
		PcrManifest pcrMf = (PcrManifest) pcrManifests.get("18");
		assertNotNull(pcrMf);
		assertEquals(pcrMf.getPcrNumber(), 18);
		assertEquals(pcrMf.getPcrValue(), "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
	}
	
	private HashMap<String, ? extends IManifest> getPcrManifestMap(TblMle mle) {
		HashMap<String, IManifest> pcrManifests = new HashMap<String, IManifest>();

		for (TblPcrManifest pcrMf : mle.getTblPcrManifestCollection()) {			
			pcrMf = pcrManifestJpaController.findPcrManifestById(pcrMf.getId());
			 pcrManifests.put(pcrMf.getName().trim(), new
			 PcrManifest(Integer.valueOf(pcrMf.getName()),
			 pcrMf.getValue().trim()));
		}
		return pcrManifests;
	}
}
