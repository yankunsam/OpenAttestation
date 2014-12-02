package com.intel.mountwilson.his.helper;

import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.io.File;
import java.io.FileOutputStream;

import javax.crypto.SecretKey;
import org.codehaus.plexus.util.FileUtils;

import gov.niarl.his.privacyca.TpmModule;
import gov.niarl.his.privacyca.TpmUtils;
import gov.niarl.his.webservices.hisPrivacyCAWebService2.IHisPrivacyCAWebService2;
import gov.niarl.his.webservices.hisPrivacyCAWebService2.client.HisPrivacyCAWebServices2ClientInvoker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TpmModule.class, HisPrivacyCAWebServices2ClientInvoker.class, TpmUtils.class})
@PowerMockIgnore({"javax.crypto.SecretKey", "javax.crypto.KeyGenerator" })
public class ProvisionTPMTest {
	
	ProvisionTPM provisionTPM = new ProvisionTPM();

    @Mock
    IHisPrivacyCAWebService2 hisPrivacyCAWebService2;
    
    File homeFolderDir;
    
    @Before
    public void setUp() throws Exception {
    	//create homeFolder
    	String homeFolder = "./config";
    	homeFolderDir = new File(homeFolder);
    	if (!homeFolderDir.exists()) {
    	  homeFolderDir.mkdir();
    	}
    	generateCertsAndProps();
    	mockStatic(TpmModule.class);
        mockStatic(HisPrivacyCAWebServices2ClientInvoker.class);
        PowerMockito.spy(TpmUtils.class);
    }

    @After
    public void tearDown() throws Exception {
    	//remove homeFolder
    	FileUtils.deleteDirectory(homeFolderDir);
    }

    @Test
    public void testTakeOwnership() throws Exception {
    	X509Certificate testEC = TpmUtils.certFromFile("./config/testEC.cer");
    	byte[] encryptEC ="".getBytes();
        PowerMockito.doNothing().when(TpmModule.class, "takeOwnership", any(byte[].class), any(byte[].class));
        PowerMockito.doReturn("test".getBytes()).when(TpmModule.class, "getEndorsementKeyModulus",any(byte[].class), any(byte[].class));
        PowerMockito.doReturn(hisPrivacyCAWebService2).when(HisPrivacyCAWebServices2ClientInvoker.class, "getHisPrivacyCAWebService2", Mockito.anyString());
        doReturn(encryptEC).when(hisPrivacyCAWebService2).requestGetEC(any(byte[].class), any(byte[].class), anyInt());
        PowerMockito.doReturn("test".getBytes()).when(TpmUtils.class, "encryptDES", any(byte[].class), any(SecretKey.class));
        PowerMockito.doReturn("test".getBytes()).when(TpmUtils.class, "encryptRSA", any(byte[].class), any(PublicKey.class));
        PowerMockito.doReturn("test".getBytes()).when(TpmUtils.class, "decryptDES", any(byte[].class), any(SecretKey.class));
        PowerMockito.doReturn(testEC).when(TpmUtils.class, "certFromBytes", any(byte[].class));
        PowerMockito.doNothing().when(TpmModule.class, "setCredential", any(byte[].class), anyString(), any(byte[].class));
       
        provisionTPM.takeOwnership();
        
        PowerMockito.verifyStatic();
        TpmModule.takeOwnership(any(byte[].class), any(byte[].class));
        
        PowerMockito.verifyStatic();
        TpmModule.getEndorsementKeyModulus(any(byte[].class), any(byte[].class));
        
        PowerMockito.verifyStatic();
        TpmModule.setCredential(any(byte[].class), anyString(), any(byte[].class));
        
        PowerMockito.verifyStatic();
        HisPrivacyCAWebServices2ClientInvoker.getHisPrivacyCAWebService2(anyString());
    }
    
    private void generateCertsAndProps() throws Exception{
    	String homeFolder = "./config/";
    	int ValidityDays = 3652;
    	String TpmOwnerAuth = "abc123";
    	String HisIdentityLabel = " HIS Identity Key";
    	String HisIdentityAuth = "1111111111111111111111111111111111111111";
    	String PrivacyCaCertFileName = "PrivacyCA.cer";
    	String EndorsementCaCertFileName = "testEC.cer";
    	X509Certificate ekCert = null;
    	X509Certificate pcaCert = null;
    	byte[] certBytes = TpmUtils.hexStringToByteArray("3082032c30820214a00302010202060149ff6e341d300d06092a864886f70d010105050030193117301506035504030c0e484"
    			+ "9535f505249564143595f4341301e170d3134313133303036343035325a170d3234313132393036343035325a30193117301506035504030c0e4849535f505249564143595f4"
    			+ "34130820122300d06092a864886f70d01010105000382010f003082010a0282010100f13aee5d2d0bb568951e1cca199954e969e8aeb802b959d557887ac27abea6e5bbe291a"
    			+ "5c3c7ffd83d12b46d7ac14cf7b2cc5bac9809cb5c240fc906d155d6ec08bff16dbc71bc3e76fc1b1ed042982d55a502c3164d2b3fdbcfc8873a5aec0f2cc34eef085ebbf2152"
    			+ "d19532558e01b5c41c1d24d993ab1391cc05ee84a8c29af44330e2e2b89506142b06bf8fac9a4c9348d3c10ddb86652aedf3331ad61cc0f1917c48d0ced9de7ba90f68e03205"
    			+ "a37bb4763cadc1ce1d1801d6926c429e9dc12046d45bb81118ba17de5653a8169eafa3c4608037d3041cf0c654466d1c9dee793857452809034a3713c457ea56e8982df06ac9"
    			+ "3b57537de44a55cee52bb0203010001a37a3078301d0603551d0e04160414e4e4136fe84fc2063445f223d9adcf685dbf7a3a300f0603551d130101ff040530030101ff30460"
    			+ "603551d23043f303d8014e4e4136fe84fc2063445f223d9adcf685dbf7a3aa11da41b30193117301506035504030c0e4849535f505249564143595f434182060149ff6e341d3"
    			+ "00d06092a864886f70d01010505000382010100171e1f7c1aaeae4d035e819935723c5e859388ccee6faff7a0bb1e8520d6271afdd2ad1c3d9b3adc1e44ddf3292759ae9dadf"
    			+ "6f11cea012c5b9028fac5ab763fcd1fb52fbc7d12901dfc54e36378c1129e4ec13a8b9449cd2482360bfb6af6d92732abc5ce48f7aad03c0009ac793bbdeb57940524c143083"
    			+ "c7dbf645f0957d4752820aa1581d1ef6accfa01faaa16d000d62f73c3ad7e4302a0009cdd09204e889dd8378a1dc74868da3c48c23a1fc88e933756faa6131712cd0b1a4050c"
    			+ "e3cc21281b519903a11972fd089a67a8741908a0b643cbf8689c76708aba5c59c4e838bacdfb053ba20eead8fd13184970ad7a42b9b65c9edc1e3eefeafac0dfcb6");
    	pcaCert = TpmUtils.certFromBytes(certBytes);
    	FileOutputStream pcaFileOut = new FileOutputStream(new File(homeFolder  + "/" + PrivacyCaCertFileName));
		try {
			if (pcaCert != null)
    			pcaFileOut.write(pcaCert.getEncoded());
				pcaFileOut.flush();
				pcaFileOut.close();
		} catch (Exception e) {
   			e.printStackTrace();
		}
		finally{
			if (pcaFileOut != null)
				pcaFileOut.close();
		}
		// Create the testEC certificate file
		ekCert = pcaCert;
		FileOutputStream testECFileOut = new FileOutputStream(new File(homeFolder  + "/" + EndorsementCaCertFileName));
		try {
			if (ekCert != null)
				testECFileOut.write(ekCert.getEncoded());
			    testECFileOut.flush();
			    testECFileOut.close();
		} catch (Exception e) {
   			e.printStackTrace();
		}
		finally{
			if (pcaFileOut != null)
				pcaFileOut.close();
		}
		//generate properties:  hisprovisioner.properties
		String HisprovisionerPropertiesFile = "hisprovisioner.properties";
		FileOutputStream fos = new FileOutputStream(homeFolder + "/" + HisprovisionerPropertiesFile);
		/*
		 * #TPM Provisioning Data
         * EcValidityDays = 3652
         * TpmOwnerAuth = abc123
         * #HIS Identity Provisioning Data
         * HisIdentityLabel = HIS Identity Key
         * HisIdentityIndex = 1
         * HisIdentityAuth = 1111111111111111111111111111111111111111
         * PrivacyCaCertFile = PrivacyCA.cer
         * PrivacyCaUrl = https://***:8181/HisPrivacyCAWebServices2
         * NtruBypass = true
         * ClientPath = cert
         * ecStorage = NVRAM
         * ecSigningKeySize = 2048
         * ecLocation = ***
		 */
		String toWrite = 
			"#TPM Provisioning Data\r\n" + 
			"EcValidityDays = " +ValidityDays + "\r\n" + 
			"TpmOwnerAuth = " + TpmOwnerAuth + "\r\n" + 
			"#HIS Identity Provisioning Data\r\n" +
			"HisIdentityLabel = " +HisIdentityLabel +"\r\n" +
			"HisIdentityIndex = 1" +"\r\n" +
			"HisIdentityAuth = " +HisIdentityAuth +"\r\n" +
			"PrivacyCaCertFile = " +PrivacyCaCertFileName +"\r\n" +
			"PrivacyCaUrl = https://***:8181/HisPrivacyCAWebServices2\r\n" +
			"NtruBypass = true\r\n" +
			"ClientPath = cert\r\n" +
			"ecStorage = NVRAM\r\n" +
			"ecSigningKeySize = 2048\r\n" +
			"ecLocation = ***\r\n";
		try {
			fos.write(toWrite.getBytes("US-ASCII"));
			fos.flush();
			fos.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if (fos != null)
				fos.close();
		}
	}
}
