package com.intel.mtwilson.tls;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Calendar;

import javax.security.auth.x500.X500Principal;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.X509V1CertificateGenerator;

public class  CertificateUtils {

	/**
	 * Generate a self signed X509 certificate with Bouncy Castle.
	 * @throws SignatureException 
	 * @throws IllegalStateException 
	 * @throws InvalidKeyException 
	 * @throws CertificateEncodingException 
	 */
	public static X509Certificate generateSelfSignedX509Certificate() throws NoSuchAlgorithmException, NoSuchProviderException, CertificateEncodingException, InvalidKeyException, IllegalStateException, SignatureException {
		Security.addProvider(new BouncyCastleProvider());
		int validityDays = 3652;
	    // GENERATE THE PUBLIC/PRIVATE RSA KEY PAIR
	    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
	    keyPairGenerator.initialize(1024, new SecureRandom());

	    KeyPair keyPair = keyPairGenerator.generateKeyPair();

	    // GENERATE THE X509 CERTIFICATE
	    X509V1CertificateGenerator certGen = new X509V1CertificateGenerator();
	    X500Principal dnName = new X500Principal("CN=OATServer");

	    certGen.setSerialNumber(BigInteger.valueOf(System.currentTimeMillis()));
	    certGen.setSubjectDN(dnName);
	    certGen.setIssuerDN(dnName); // use the same
	    certGen.setNotBefore(new java.sql.Time(System.currentTimeMillis()));
	    Calendar expiry = Calendar.getInstance();
		expiry.add(Calendar.DAY_OF_YEAR, validityDays);
		certGen.setNotAfter(expiry.getTime());
	    certGen.setPublicKey(keyPair.getPublic());
	    certGen.setSignatureAlgorithm("SHA256WithRSAEncryption");
	   
	    X509Certificate cert = certGen.generate(keyPair.getPrivate(), "BC");
	    return cert;
	}
}
