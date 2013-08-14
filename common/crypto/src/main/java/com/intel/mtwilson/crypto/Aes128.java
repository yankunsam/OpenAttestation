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
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 * This utility class includes methods for encrypting/decrypting fields in our
 * database tables using AES-128. This class assumes the use of CBC mode and
 * PKCS#5 padding.
 * 
 * If you need to generate a new secret key from a password, try something like this:
 * SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
 * KeySpec spec = new PBEKeySpec("password".toCharArray(), salt_byteArray, ITERATIONS(65536), KEYLEN_BITS(128));
 * SecretKey tmp = factory.generateSecret (spec);
 * SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
 * 
 * Or if you want to generate a new random key, call the generateKey() method.
 * SecretKey skey = Aes128.generateKey();
 * 
 * To simplify usage, this class wraps all cryptographic exceptions with a single CryptographyException class.
 * The original exceptions are available via the getCause() method of CryptographyException.
 * 
 * The encryptString and decryptString methods use the UTF-8 character set and Base64 encoding.
 * 
 * @author jbuhacoff
 */
public class Aes128 {
    private SecretKey secretKey;
    private static int KEY_LENGTH = 128;
    private static int BLOCK_SIZE = 16; // KEY_LENGTH / 8
    private Cipher cipher;
    
    public Aes128(byte[] secretKeyAes128) throws CryptographyException {
        try {
            cipher = Cipher.getInstance ("AES/CBC/PKCS5Padding");
            secretKey = new SecretKeySpec(secretKeyAes128, "AES");
        }
        catch(NoSuchAlgorithmException e) {
            throw new CryptographyException(e);
        }
        catch(NoSuchPaddingException e) {
            throw new CryptographyException(e);
        }
    }

    public Aes128(SecretKey secretKeyAes128) throws CryptographyException {
        try {
            cipher = Cipher.getInstance ("AES/CBC/PKCS5Padding");
            secretKey = secretKeyAes128;
        }
        catch(NoSuchAlgorithmException e) {
            throw new CryptographyException(e);
        }
        catch(NoSuchPaddingException e) {
            throw new CryptographyException(e);
        }
    }
    
    public byte[] encrypt(byte[] plaintext) throws CryptographyException {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] iv = cipher.getIV();
            byte[] ciphertext = cipher.doFinal(plaintext);
            return concat(iv, ciphertext);
        }
        catch(InvalidKeyException e) {
            throw new CryptographyException(e);
        }
        catch(IllegalBlockSizeException e) {
            throw new CryptographyException(e);
        }
        catch(BadPaddingException e) {
            throw new CryptographyException(e);
        }
    }
    
    public String encryptString(String plaintext) throws CryptographyException {
        try {
            return Base64.encodeBase64String(encrypt(plaintext.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            throw new CryptographyException(e);
        }
    }
        
    public byte[] decrypt(byte[] ciphertext) throws CryptographyException {
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(ciphertext, 0, BLOCK_SIZE));
            return cipher.doFinal(ciphertext, BLOCK_SIZE, ciphertext.length - BLOCK_SIZE); // skip the first 16 bytes (IV)
        }
        catch(InvalidKeyException e) {
            throw new CryptographyException(e);
        }
        catch(IllegalBlockSizeException e) {
            throw new CryptographyException(e);
        }
        catch(BadPaddingException e) {
            throw new CryptographyException(e);
        }
        catch(InvalidAlgorithmParameterException e) {
            throw new CryptographyException(e);
        }
    }

    public String decryptString(String ciphertext) throws CryptographyException {
        try {
            return new String(decrypt(Base64.decodeBase64(ciphertext)), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new CryptographyException(e);
        }
    }
    
    
    private byte[] concat(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
    
    public static SecretKey generateKey() throws CryptographyException {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(KEY_LENGTH);
            SecretKey skey = kgen.generateKey();
            return skey;
        }
        catch(NoSuchAlgorithmException e) {
            throw new CryptographyException(e);
        }
    }
}
