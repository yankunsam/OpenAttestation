package com.intel.mountwilson.manifest.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PcrManifestTest {
    PcrManifest goodKnownValue, wrongValue, manifestPcrValue;

    @Before
    public void initialize() {
        goodKnownValue = new PcrManifest(1, "00000000000000000000");
        wrongValue = new PcrManifest(1, "FFFFFFFFFFFFFFFFFFFF");
        manifestPcrValue = new PcrManifest(1, "00000000000000000000");
    }

    @Test
    public void testVerify() {
        Assert.assertTrue(manifestPcrValue.verify(goodKnownValue));
        Assert.assertTrue(manifestPcrValue.verifyStatus);
        manifestPcrValue.verifyStatus = false;
        Assert.assertFalse(manifestPcrValue.verifyStatus);
        Assert.assertFalse(manifestPcrValue.verify(wrongValue));
    }

    @Test
    public void testGetVerifyStatus() {
        manifestPcrValue = new PcrManifest(1, "00000000000000000000");
        Assert.assertFalse(manifestPcrValue.getVerifyStatus());
        manifestPcrValue.verifyStatus = true;
        Assert.assertTrue(manifestPcrValue.getVerifyStatus());
    }
}
