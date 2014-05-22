package com.intel.mountwilson.manifest.data;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class PcrModuleManifestTest {
    PcrModuleManifest pcrModuleManifestInst, goodKnownZeroValue, goodKnownEmptyValue, goodKnownnullValue;
    PcrManifest superGoodKnownValue, superEmptyValue;

    @Before
    public void initialize() {
        pcrModuleManifestInst = new PcrModuleManifest(0, "E13C765292BB474A9E69B56C477CA375DD0BBB6C");
        superGoodKnownValue = new PcrManifest(0, "E13C765292BB474A9E69B56C477CA375DD0BBB6C");
        superEmptyValue = new PcrManifest(0, "");
        goodKnownZeroValue = new PcrModuleManifest(0, "0000000000000000000000000000000000000000");
        goodKnownEmptyValue = new PcrModuleManifest(0, "");
        goodKnownnullValue = new PcrModuleManifest(0, null);
    }

    @Test
    public void testByteArrayToHexString() {
        Assert.assertEquals("6162", pcrModuleManifestInst.byteArrayToHexString("ab".getBytes()));
    }

    @Test
    public void testVerify() {
        Assert.assertFalse(pcrModuleManifestInst.verify(goodKnownEmptyValue));
        Assert.assertFalse(pcrModuleManifestInst.verify(goodKnownnullValue));
        Assert.assertTrue(goodKnownZeroValue.verify(goodKnownEmptyValue));
        Assert.assertTrue(goodKnownZeroValue.verify(goodKnownnullValue));
        Assert.assertTrue(pcrModuleManifestInst.verify(superGoodKnownValue));
        Assert.assertFalse(superEmptyValue.verify(superGoodKnownValue));
    }
}
