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

import java.nio.ByteBuffer;
import java.security.cert.X509Certificate;
import java.util.Comparator;

/**
 * This class supports sorting of certificates in the hashCode() method of a
 * CertificateRepository. Because there isn't really a meaning to having a 
 * sorted set of certificates, the only aim of this class is to always produce the
 * same order given the same certificates. If the certificate list changes then
 * the comparison of the "new order" to the "old order" is meaningless, and the
 * only thing that can be said is they are necessarily different because the list contents are different.
 * @author jbuhacoff
 */
public class X509CertificateComparator implements Comparator<X509Certificate> {

    /**
     * This method compares serial number, then subject name, then the entire
     * encoded certificate.
     * @param o1
     * @param o2
     * @return 
     */
    @Override
    public int compare(X509Certificate o1, X509Certificate o2) {
        int serialNumber = o1.getSerialNumber().compareTo(o2.getSerialNumber());
        if( serialNumber != 0 ) { return serialNumber; }
        int subjectName = o1.getSubjectX500Principal().getName().compareTo(o2.getSubjectX500Principal().getName());
        if( subjectName != 0 ) { return subjectName; }
        try {
            return ByteBuffer.wrap(o1.getEncoded()).compareTo(ByteBuffer.wrap(o2.getEncoded())); // CertificateEncodingException
        }
        catch(Exception e) {
            return 0;
        }
    }
    
}
