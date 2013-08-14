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

package com.intel.mtwilson.crypto;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @since 0.5.2
 * @author jbuhacoff
 */
public class HmacCredential implements Credential {
    private static Logger log = LoggerFactory.getLogger(HmacCredential.class);
    private String username;
    private String password;
    private final String signatureAlgorithm = "HmacSHA256";
    private byte[] identity;
    
    public HmacCredential(String clientId, String secretKey) {
        username = clientId;
        password = secretKey;
        identity = getIdentity(clientId);
    }
    
    private byte[] getIdentity(String name) {
        try {
            return name.getBytes("UTF-8");
        } catch (UnsupportedEncodingException ex) {
            log.error(ex.toString(), ex);
        }
        return name.getBytes();
    }
    
    /**
     * If UTF-8 encoding is not available, returns the bytes in the default platform encoding
     * @return UTF-8 encoded username API such as "cloudsecurity@intel"
     */
    @Override
    public byte[] identity() {
        return identity;
    }
    
    /**
     * If document is text, you should pass it in the encoding you want to sign,
     * such as document.getBytes("UTF-8").
     * 
     * The Credential interface indicates that signature() may throw a SignatureException,
     * but this implementation does not throw SignatureException.
     * 
     * @param document
     * @return 
     */
    @Override
    public byte[] signature(byte[] document) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec key = new SecretKeySpec(password.getBytes(), signatureAlgorithm);
        Mac mac = Mac.getInstance(signatureAlgorithm); // a string like "HmacSHA256"
        mac.init(key);
        return mac.doFinal(document);
    }
    
    /**
     * 
     * @return the signature algorithm "HmacSHA256"
     */
    @Override
    public String algorithm() {
        return signatureAlgorithm;
    }
}
