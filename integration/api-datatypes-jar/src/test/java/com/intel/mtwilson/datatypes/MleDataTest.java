package com.intel.mtwilson.datatypes;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

public class MleDataTest {
    MleData mledata = null;
    
    @Before
    public void setUp() {
        mledata = new MleData("MLE1", "V1", MleData.MleType.BIOS,
                MleData.AttestationType.PCR, new ArrayList<ManifestData>(Arrays.asList(new ManifestData("MODULE","A0J2"),new ManifestData("PCR","FFF"),new ManifestData("PCR","EY78"))),
                "Description", "RHEL", "6.5", "OEM1");
    }
    
    @Test
    public void testToString() {
        final String a = mledata.getName() + " " + mledata.getVersion() + " " + "("
                + mledata.getMleType() + " " + mledata.getAttestationType()
                + ")" + " " + "-" + " " + mledata.getDescription();
        assertEquals(a, mledata.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetOemNameNullExceptionIsThrown() {
        mledata.setOemName(null);
        mledata.getOemName();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetOemNameIsEmptyIsThrown() {
        mledata.setOemName("");
        mledata.getOemName();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetOsNameNullExceptionIsThrown() {
        MleData mledata = new MleData("MLE1", "V1", MleData.MleType.VMM,
                MleData.AttestationType.PCR, new ArrayList<ManifestData>(),
                "Description", "RHEL", "6.5", "OEM1");
        mledata.setOsName(null);
        mledata.getOsName();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetOsNameIsEmptyIsThrown() {
        MleData mledata = new MleData("MLE1", "V1", MleData.MleType.VMM,
                MleData.AttestationType.PCR, new ArrayList<ManifestData>(),
                "Description", "RHEL", "6.5", "OEM1");
        mledata.setOsName("");
        mledata.getOsName();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetOsVersionNullExceptionIsThrown() {
        MleData mledata = new MleData("MLE1", "V1", MleData.MleType.VMM,
                MleData.AttestationType.PCR, new ArrayList<ManifestData>(),
                "Description", "RHEL", "6.5", "OEM1");
        mledata.setOsVersion(null);
        mledata.getOsVersion();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetOsVersionIsEmptyIsThrown() {
        MleData mledata = new MleData("MLE1", "V1", MleData.MleType.VMM,
                MleData.AttestationType.PCR, new ArrayList<ManifestData>(),
                "Description", "RHEL", "6.5", "OEM1");
        mledata.setOsVersion("");
        mledata.getOsVersion();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNameNullExceptionIsThrown() {
        MleData mledata = new MleData(null, "V1", MleData.MleType.VMM,
                MleData.AttestationType.PCR, new ArrayList<ManifestData>(),
                "Description", "RHEL", "6.5", "OEM1");
        mledata.getName();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetVersionNullExceptionIsThrown() {
        MleData mledata = new MleData("MLE1", null, MleData.MleType.VMM,
                MleData.AttestationType.PCR, new ArrayList<ManifestData>(),
                "Description", "RHEL", "6.5", "OEM1");
        mledata.getVersion();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetAttestationTypeNullExceptionIsThrown() {
        MleData mledata = new MleData("MLE1", null, MleData.MleType.VMM,
                MleData.AttestationType.PCR, new ArrayList<ManifestData>(),
                "Description", "RHEL", "6.5", "OEM1");
        mledata.setAttestationType(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetInvalidAttestationTypeIsThrown() {
        mledata.setAttestationType("ABC"); 
    }
      
    @Test(expected = IllegalArgumentException.class)
    public void testSetMleTypeNullExceptionIsThrown() {
        MleData mledata = new MleData("MLE1", null, MleData.MleType.VMM,
                MleData.AttestationType.PCR, new ArrayList<ManifestData>(),
                "Description", "RHEL", "6.5", "OEM1");
        mledata.setMleType(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetInvalidMleTypeIsThrown() {
        mledata.setMleType("DEF");
    }
    
    @Test
    public void testGetManifestList(){      
        assertEquals("MODULE",mledata.getManifestList().get(0).getName());
        assertEquals("A0J2",mledata.getManifestList().get(0).getValue());
        assertEquals("PCR",mledata.getManifestList().get(1).getName());
        assertEquals("FFF",mledata.getManifestList().get(1).getValue());
        assertEquals("PCR",mledata.getManifestList().get(2).getName());
        assertEquals("EY78",mledata.getManifestList().get(2).getValue());
    }
}
