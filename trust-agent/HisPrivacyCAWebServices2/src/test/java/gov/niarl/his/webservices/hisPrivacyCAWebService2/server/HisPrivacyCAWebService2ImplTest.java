package gov.niarl.his.webservices.hisPrivacyCAWebService2.server;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import gov.niarl.his.privacyca.TpmIdentityProof;
import gov.niarl.his.privacyca.TpmUtils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.codehaus.plexus.util.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HisPrivacyCAWebService2ImplTest {
	String homeFolder = "./config/";
	String PrivacyCaSubjectName = "HIS_PRIVACY_CA";
	String PrivacyCaPassword = "abc123";
	String PrivacyCaFileName = "PrivacyCA.p12";
	int ValidityDays = 3652;
	String EndorsementCaSubjectName = "Endorsement_CA_Rev_1";
	String EndorsementCaPassword = "abc123";
	String EndorsementCaFileName = "endorsement.p12";
	String PrivacyCaCertFileName = "PrivacyCA.cer";
	String ecCaPath = "cacerts";
	String EndorsementCaCertFileName = "EndorsementCA.cer";
	byte[] ekCert = null;
	X509Certificate pcaCert = null;
	TpmIdentityProof idProof = null;
	
	File homeFolderDir;
	HisPrivacyCAWebService2Impl hisPrivacyCAWebService2Impl;
	
	@Before
	public void setUp() throws Exception {
		//create homeFolder
		homeFolderDir = new File(homeFolder);
		if (!homeFolderDir.exists()) {
			homeFolderDir.mkdir();
		}
		//generate certs and props
		generateCertsAndProps();
		//create hisPrivacyCAWebService2Impl object
		hisPrivacyCAWebService2Impl = new HisPrivacyCAWebService2Impl();
		hisPrivacyCAWebService2Impl.setHomeFolder(homeFolder);
	}

	@After
	public void tearDown() throws Exception {
		//remove homeFolder
		FileUtils.deleteDirectory(homeFolderDir);
	}

	@Test
	public void testRequestGetEC() throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		X509Certificate pcaCert = TpmUtils.certFromP12(homeFolder + PrivacyCaFileName, PrivacyCaPassword);
		String EkMod = "12ef45";
		SecretKey deskey = generateSessionKey();
		byte[] encryptedEkMod = hisPrivacyCAWebService2Impl.encryptDES(TpmUtils.hexStringToByteArray(EkMod), deskey);
		byte[] encryptedSessionKey = encryptRSA(deskey.getEncoded(), (RSAPublicKey)pcaCert.getPublicKey());
		byte[] encryptEkCert = hisPrivacyCAWebService2Impl.requestGetEC(encryptedEkMod, encryptedSessionKey, ValidityDays);
		assertNotNull(encryptEkCert);
	}
	
	// Generate security key via 3DES algorithm
	public static SecretKey generateSessionKey() {
		SecretKey deskey = null;
		try {
			KeyGenerator keygen = KeyGenerator.getInstance("DESede", "BC"); 
			keygen.init(new SecureRandom()); 
			deskey = keygen.generateKey();  
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deskey; 
	}
	
	public static byte[] encryptRSA(byte[] text, PublicKey pubRSA) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA", "BC");
        cipher.init(Cipher.ENCRYPT_MODE, pubRSA);
        return cipher.doFinal(text);
    }
	
	private void generateCertsAndProps() throws Exception{
	    //generate PrivacyCA.p12 and endorsement.p12
        TpmUtils.createCaP12(2048, PrivacyCaSubjectName, PrivacyCaPassword, homeFolder+ PrivacyCaFileName, ValidityDays);
        TpmUtils.createCaP12(2048, EndorsementCaSubjectName, EndorsementCaPassword, homeFolder + EndorsementCaFileName, ValidityDays);
		// Create the Privacy CA certificate file
		pcaCert = TpmUtils.certFromP12(homeFolder + "/" + PrivacyCaFileName, PrivacyCaPassword);
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
		//generate properties:  PrivacyCA.properties, EndorsementCA.properties and privacyca-client.properties
		String PrivacyCaPropertiesFile = "PrivacyCA.properties";
		String EndorsementCaPropertiesFile = "EndorsementCA.properties";
		String PrivacycaClientPropertiesFile = "privacyca-client.properties";
		FileOutputStream fos = new FileOutputStream(homeFolder + "/" + PrivacyCaPropertiesFile);
		/*
		 * #Privacy CA Operation
		 * P12filename = PrivacyCA.p12
		 * P12password = ***replace***
		 * PrivCaCertValiditydays = 3652
		 */
		String toWrite = 
			"#Privacy CA Operation\r\n" + 
			"P12filename = " + PrivacyCaFileName + "\r\n" + 
			"P12password = " + PrivacyCaPassword + "\r\n" + 
			"PrivCaCertValiditydays = " +ValidityDays +"\r\n";
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
		/*
		 * Endorsement CA Data
		 *  TpmEndorsmentP12 = endorsement.p12
		 *  EndorsementP12Pass =  ***replace***
		 */
		fos = new FileOutputStream(homeFolder +"/" + EndorsementCaPropertiesFile);
		toWrite = 
			"#Endorsement CA Operation\r\n" + 
			"TpmEndorsmentP12 = " +EndorsementCaFileName  + "\r\n" + 
			"EndorsementP12Pass = " +EndorsementCaPassword +"\r\n";
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
	    /*
		 * Privacy CA Client Operation
		 *  PrivacyCaSubjectName = HIS_PRIVACY_CA
		 *  PrivacyCaPassword =  ***replace***
		 */
		fos = new FileOutputStream(homeFolder +"/" + PrivacycaClientPropertiesFile);
		toWrite = 
			"PrivacyCaSubjectName = " +PrivacyCaSubjectName  + "\r\n" + 
			"PrivacyCaPassword =" +PrivacyCaPassword  +"\r\n";
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
