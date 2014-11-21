package com.intel.mtwilson.tls;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.junit.Before;
import org.junit.Test;

public class TrustKnownCertificateTlsPolicyTest {

private CertificateRepository repository;
	
	private TrustKnownCertificateTlsPolicy trustKnownCertificateTlsPolicy;
	
	private X509Certificate[] keystore = new X509Certificate[1] ;
	
	@Before
	public void setUp() throws Exception {
		keystore[0] = CertificateUtils.generateSelfSignedX509Certificate();
		repository = new ArrayCertificateRepository(keystore);
		trustKnownCertificateTlsPolicy = new TrustKnownCertificateTlsPolicy(repository);
	}

	@Test
	public void testCheckServerTrusted() throws CertificateException {
		trustKnownCertificateTlsPolicy.checkServerTrusted(keystore, "test");
	}

}
