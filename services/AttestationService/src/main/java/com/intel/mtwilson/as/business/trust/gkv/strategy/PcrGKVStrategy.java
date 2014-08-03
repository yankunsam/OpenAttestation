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

package com.intel.mtwilson.as.business.trust.gkv.strategy;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.intel.mtwilson.as.business.trust.gkv.IGKVStrategy;
import com.intel.mtwilson.as.controller.TblMleJpaController;
import com.intel.mtwilson.as.controller.TblPcrManifestJpaController;
import com.intel.mtwilson.as.data.TblMle;
import com.intel.mtwilson.as.data.TblPcrManifest;
import com.intel.mtwilson.as.helper.BaseBO;
import com.intel.mountwilson.manifest.data.IManifest;
import com.intel.mountwilson.manifest.data.PcrManifest;
import com.intel.mountwilson.manifest.data.PcrModuleManifest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PcrGKVStrategy extends BaseBO implements IGKVStrategy {
        private Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public HashMap<String, ? extends IManifest> getBiosGoodKnownManifest(
			String mleName, String mleVersion, String oemName) {
		// Call query method to avoid the objects from the cache
		TblMle biosMle = getMleJpaController().findBiosMle(mleName, mleVersion, oemName);
		HashMap<String, ? extends IManifest> pcrManifestMap = getPcrManifestMap(biosMle);
		return pcrManifestMap;
	}

	@Override
	public HashMap<String, ? extends IManifest> getVmmGoodKnownManifest(
			String mleName, String mleVersion, String osName, String osVersion, Integer hostId) {
		HashMap<String, ? extends IManifest> pcrManifestMap;
		// Call query method to avoid the objects from the cache
		TblMle vmmMle = getMleJpaController().findVmmMle(mleName, mleVersion, osName, osVersion);
		pcrManifestMap = getPcrManifestMap(vmmMle);

		return pcrManifestMap;
	}

	private HashMap<String, ? extends IManifest> getPcrManifestMap(TblMle mle) {
		HashMap<String, IManifest> pcrManifests = new HashMap<String, IManifest>();

		for (TblPcrManifest pcrMf : mle.getTblPcrManifestCollection()) {
			// Call query method to avoid the objects from the cache
			pcrMf = getPcrManifestJpaController().findPcrManifestById(pcrMf.getId());
			 pcrManifests.put(pcrMf.getName().trim(), new
			 PcrManifest(Integer.valueOf(pcrMf.getName()),
			 pcrMf.getValue().trim()));
			

			log.info( "{} - {}", new Object[] { pcrMf.getName(),
					pcrMf.getValue() });
		}

		return pcrManifests;
	}

	public TblMleJpaController getMleJpaController() {
		return new TblMleJpaController(getEntityManagerFactory());
		
	}

	public TblPcrManifestJpaController getPcrManifestJpaController() {
		return new TblPcrManifestJpaController(getEntityManagerFactory());
		
	}

}
