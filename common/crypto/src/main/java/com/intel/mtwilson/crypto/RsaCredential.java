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

import java.security.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @since 0.5.2
 * @author jbuhacoff
 */
public class RsaCredential implements Credential {
    private static Logger log = LoggerFactory.getLogger(RsaCredential.class);
    private final PrivateKey privateKey;
    private PublicKey publicKey;
    private final String digestAlgorithm = "SHA-256";
    private final String signatureAlgorithm = "SHA256withRSA";
    private final byte[] identity;
    
    protected RsaCredential(PrivateKey privateKey, byte[] credential) throws NoSuchAlgorithmException {
        if( !"RSA".equals(privateKey.getAlgorithm()) ) {
            throw new IllegalArgumentException("Key must be RSA");
        }
        this.privateKey = privateKey;
        this.identity = createIdentity(credential);
    }
    
    
    /**
     * Initializes the RsaCredential using the private and public keys from the
     * provided key pair. The digest of the public key will be used as the identity.
     * @param keyPair
     * @throws NoSuchAlgorithmException 
     */
    public RsaCredential(KeyPair keyPair) throws NoSuchAlgorithmException {
        this(keyPair.getPrivate(), keyPair.getPublic());
    }

    /**
     * Initializes the RsaCredential using the provided private and public keys.
     * The digest of the public key will be used as the identity.
     * @param privateKey
     * @param publicKey
     * @throws NoSuchAlgorithmException 
     */
    public RsaCredential(PrivateKey privateKey, PublicKey publicKey) throws NoSuchAlgorithmException {
        this(privateKey, publicKey.getEncoded());
        this.publicKey = publicKey;
    }


    private byte[] createIdentity(byte[] publicKeyOrCertificate) throws NoSuchAlgorithmException {
        MessageDigest hash = MessageDigest.getInstance(digestAlgorithm);
        byte[] digest = hash.digest(publicKeyOrCertificate);
        return digest;
    }
    
    public PublicKey getPublicKey() {
        return publicKey;
    }
    
    public PrivateKey getPrivateKey() {
        return privateKey;
    }
    
    /**
     * 
     * @return SHA-256 fingerprint of the Certificate containing the RSA Public Key
     */
    @Override
    public byte[] identity() {
        return identity;
    }
    
    @Override
    public byte[] signature(byte[] document) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature rsa = Signature.getInstance(signatureAlgorithm); 
        rsa.initSign(privateKey);
        rsa.update(document);
        return rsa.sign();
    }
    
    /**
     * 
     * @return the signature algorithm "SHA256withRSA"
     */
    @Override
    public String algorithm() {
        return signatureAlgorithm;
    }
}
