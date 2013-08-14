/*
 * Copyright (c) 2013, Intel Corporation. 
 * All rights reserved.
 * 
 * The contents of this file are released under the BSD license, you may not use this file except in compliance with the License.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * Neither the name of Intel Corporation nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.intel.mtwilson.tls;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.X509TrustManager;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This trust policy must be initialized with the server that is being checked
 * and its associated server-specific keystore. 
 * 
 * If the keystore does not already have a trusted certificate for this server,
 * the server's certificate is saved and considered trusted. User should verify
 * the fingerprint before sending any data. 
 * This class is suitable for use when a keystore only contains certificates
 * relevant to a single server - using this class with the same keystore for
 * all connections is insecure because all new certificates will be accepted.
 * 
 * It checks the repository for the trusted certificate every time it is used - 
 * if you want to cache the repository information (eg. to avoid a database hit on 
 * each SSL connection), then you need to provide
 * an in-memory repository that contains just the cached certificates (query 
 * the database once and put the results in an ArrayCertificateRepository and
 * provide that to the policy object).
 * 
 * @author jbuhacoff
 */
public class TrustFirstCertificateTlsPolicy implements TlsPolicy, ApacheTlsPolicy, X509TrustManager {
    private Logger log = LoggerFactory.getLogger(getClass());
    private transient final MutableCertificateRepository repository;

    public TrustFirstCertificateTlsPolicy(MutableCertificateRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public X509TrustManager getTrustManager() { return this; }

    @Override
    public HostnameVerifier getHostnameVerifier() {
        return SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
    }

    @Override
    public X509HostnameVerifier getApacheHostnameVerifier() {
        return SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
    }
    
    
    @Override
    public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
        if( xcs == null || xcs.length == 0 ) { throw new IllegalArgumentException("Server did not present any certificates"); }
        List<X509Certificate> trustedCertificates = repository.getCertificates(); //repository.getCertificateForSubject(xcs[0].getSubjectX500Principal().getName());  
        if( trustedCertificates.isEmpty() ) {
            addServerCertificatesToRepository(xcs);
            return;
        }
        for(int i=0; i<xcs.length; i++) {
            for(X509Certificate trustedCert : trustedCertificates) {
                if( Arrays.equals(trustedCert.getEncoded(), xcs[i].getEncoded())) {
                    try {
                        xcs[i].checkValidity(); // CertificateExpiredException, CertificateNotYetValidEception
                        return; // XXX TODO   we need to check the entire chain... we can't just accept any ca  , we need t omake sure tehre is a PATH from the server cert to the trusted cert.
                    }
                    catch(Exception e) {
                        log.trace("TrustFirstCertificateTlsPolicy checkServerTrusted cert was not valid. checking next cert");
                        // don't throw an exception because we need to check the next certificate... throw new CertificateException("Invalid server certificate", e);
                    }
                }
            }
        }
        throw new CertificateException("Server certificate is not trusted");
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0]; // XXX TODO should we be returning the CA-enabled certificates in the repository, if there are any?
    }
    
    private void addServerCertificatesToRepository(X509Certificate[] xcs) {
        for(X509Certificate cert : xcs) {
            System.out.println("server certificate: "+cert.getSubjectX500Principal().getName());
        }
        for(int i=0; i<xcs.length; i++) {
            try {
                xcs[i].checkValidity(); // CertificateExpiredException, CertificateNotYetValidEception
                log.info("Saving certificate {}", xcs[i].getSubjectX500Principal().getName());
                repository.addCertificate(xcs[i]); // KeyManagementException
            }
            catch(Exception e) {
                log.trace("TrustFirstCertificateTlsPolicy addServerCertificateToRepository cert was not valid. trying to save next cert");
                // don't throw an exception because we may be able to save other certificates? throw new CertificateException("Unable to save server certificate", e);
            }
        }        
    }

    @Override
    public CertificateRepository getCertificateRepository() {
        return repository;
    }
    

}
