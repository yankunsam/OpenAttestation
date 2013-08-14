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

import java.security.cert.X509Certificate;
import java.util.List;

/**
 * There are two models for using a certificate repository. first,
 * the model in which a large repository is used to store certificates trusted
 * for many different servers, with the methods getCertificatesForAddress() and
 * getCertificatesForSubject(). second, the model in which an address-specific
 * or subject-specific view is created on top of such a large repository such 
 * that those certificates are selected and then they appear to be the only ones
 * in the new view, with the methods getTrustedCertificates() and getTrustedAuthorities(),
 * or even a simpler getCertificates() to return both (caller can determine authorities
 * by checking for the CA flag).  in mt wilson, all tls connections have per-server
 * trusted repositories:  api clients either have all the self-signed certs of the
 * mt wilson servers or they have a CA they trust for all of them; mt wilson has a 
 * per-host repository for every monitored host or its vcenter. mt wilson can also
 * have a global trusted root ca that is automatically added to every repository
 * instance that is created.
 * This interface assumes that implementations are using a per-server storage,
 * so that getCertificates() returns only trusted certificates for the server to
 * which the connection is being made. 
 * @author jbuhacoff
 */
public interface CertificateRepository {
    List<X509Certificate> getCertificates();
    // from previous draft:
//    X509Certificate getCertificateForAddress(InternetAddress dnsHostnameOrIpAddress);
//    List<X509Certificate> getCertificateForSubject(String subjectDN); // XXX maybe ask to pass a DN object to be clear about the input format??
//    List<X509Certificate> getCertificateAuthorities(); // return all certificates with the CA flag set
}
