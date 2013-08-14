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

package com.intel.mtwilson;

import com.intel.mtwilson.datatypes.*;
import java.io.IOException;
import java.security.SignatureException;
import java.util.List;

/**
 *
 * @author jbuhacoff
 */
public interface WhitelistService {

    boolean addMLE(MleData mle) throws IOException, ApiException, SignatureException;

    boolean updateMLE(MleData mle) throws IOException, ApiException, SignatureException;

    List<MleData> searchMLE(String name) throws IOException, ApiException, SignatureException;

    MleData getMLEManifest(MLESearchCriteria criteria) throws IOException, ApiException, SignatureException;

    boolean deleteMLE(MLESearchCriteria criteria) throws IOException, ApiException, SignatureException;

    List<OemData> listAllOEM() throws IOException, ApiException, SignatureException;

    boolean addOEM(OemData oem) throws IOException, ApiException, SignatureException;

    boolean updateOEM(OemData oem) throws IOException, ApiException, SignatureException;

    boolean deleteOEM(String name) throws IOException, ApiException, SignatureException;

    List<OsData> listAllOS() throws IOException, ApiException, SignatureException;

    boolean updateOS(OsData os) throws IOException, ApiException, SignatureException;

    boolean addOS(OsData os) throws IOException, ApiException, SignatureException;

    boolean deleteOS(OsData os) throws IOException, ApiException, SignatureException;

    boolean addPCRWhiteList(PCRWhiteList pcrObj) throws IOException, ApiException, SignatureException;
    
    boolean updatePCRWhiteList(PCRWhiteList pcrObj) throws IOException, ApiException, SignatureException;
    
    boolean deletePCRWhiteList(PCRWhiteList pcrObj) throws IOException, ApiException, SignatureException;
        
    boolean addMleSource(MleSource mleSourceObj) throws IOException, ApiException, SignatureException;
    
    boolean updateMleSource(MleSource mleSourceObj) throws IOException, ApiException, SignatureException;
    
    boolean deleteMleSource(MleData mleDataObj) throws IOException, ApiException, SignatureException;
    
    String getMleSource(MleData mleDataObj) throws IOException, ApiException, SignatureException;
}
