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

package com.intel.mountwilson.trustagent;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.provider.JDKDigestSignature;
import org.bouncycastle.jce.provider.JDKDigestSignature.SHA1WithRSAEncryption;
/**
 *
 * @author jbuhacoff
 */
public class Diagnostic {
    public static void main(String[] args) {
        checkBouncycastlePresent();
        Security.addProvider(new BouncyCastleProvider());        
        checkBouncycastleAlgorithms();
    }
    
    public static void checkBouncycastlePresent() {
        tryLoadingClass("org.bouncycastle.jce.provider.JDKDigestSignature");
        tryLoadingClass("org.bouncycastle.jce.provider.JDKDigestSignature$SHA1WithRSAEncryption");
    }
    
    private static void tryLoadingClass(String className) {
        try {
            Class.forName(className);
            System.out.println("Found class: "+className);
        }
        catch(ClassNotFoundException e) {
            System.err.println("Cannot find class: "+className+": "+e.toString());
        }
        catch(Exception e) {
            System.err.println("Cannot load class: "+className+": "+e.toString());
        }
    }
    
    public static void checkBouncycastleAlgorithms() {
        printAvailableAlgorithms();
        tryMacWithPassword("HmacSHA1", "hello world", "xyzzy");        
        trySignature();
    }
    
    private static void printAvailableAlgorithms() {
        for (Provider provider: Security.getProviders()) {
          System.out.println(provider.getName());
          for (String key: provider.stringPropertyNames()) {
            System.out.println("\t" + key + "\t" + provider.getProperty(key));
          }
        }        
    }
    
    private static void tryMacWithPassword(String algorithmName, String message, String password) {
        try {
            SecretKeySpec key = new SecretKeySpec(password.getBytes(), algorithmName);
            Mac mac = Mac.getInstance(algorithmName, "BC"); // a string like "HmacSHA256"
            mac.init(key);
            byte[] digest = mac.doFinal(message.getBytes());
            System.out.println("Created "+algorithmName+" digest of length "+digest.length);
        }
        catch(NoSuchProviderException e) {
            System.err.println("Cannot use provider: BC: "+e.toString());
        }
        catch(NoSuchAlgorithmException e) {
            System.err.println("Cannot use algorithm: "+algorithmName+": "+e.toString());
        }
        catch(InvalidKeyException e) {
            System.err.println("Cannot use key: "+password+": "+e.toString());
        }
    }
    
    private static void trySignature() {
        String algorithmName = "SHA1withRSA";
        try {
            // generate keypair
            KeyPair keyPair = KeyPairGenerator.getInstance("RSA", "BC").generateKeyPair(); // NoSuchAlgorithmException, NoSuchProviderException
            PrivateKey privateKey = keyPair.getPrivate();
            String plaintext = "This is the message being signed";

            // generate signature
            Signature instance = Signature.getInstance("SHA1withRSAEncryption", "BC"); // NoSuchAlgorithmException, NoSuchProviderException
            instance.initSign(privateKey); // InvalidKeyException
            instance.update((plaintext).getBytes()); // SignatureException
            byte[] signature = instance.sign();

            System.out.println("Generated SHA1 with RSA signature of length: "+signature.length);
        }
        catch(NoSuchProviderException e) {
            System.err.println("Cannot use provider: BC: "+e.toString());
        }
        catch(NoSuchAlgorithmException e) {
            System.err.println("Cannot use algorithm: "+algorithmName+": "+e.toString());            
        }
        catch(InvalidKeyException e) {
            System.err.println("Cannot use key: "+e.toString());
        }
        catch(SignatureException e) {
            System.err.println("Cannot generate signature: "+e.toString());
        }
    }
}
