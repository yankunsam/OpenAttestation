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

/**
 * 
 */
package com.intel.mountwilson.datamodel;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author yuvrajsx
 *
 */
public final class MLEDataVO {
	
	@JsonProperty("Name")
	private String mleName;
	
	@JsonProperty("Version")
    private String mleVersion;
	
	@JsonProperty("Description")
    private String mleDescription;
	
	@JsonProperty("OsName")
    private String osName;
	
	@JsonProperty("OsVersion")
    private String osVersion;
    
    @JsonProperty("Attestation_Type")
    private String attestation_Type;
    
    @JsonProperty("OemName")
    private String oemName;
    
    @JsonProperty("MLE_Type")
    private String mleType;
    
    @JsonProperty("MLE_Manifests")
    private List<Map<String, String>> manifestList;
    
    
    /**
	 * @return the mleName
	 */
	public String getMleName() {
		return mleName;
	}



	/**
	 * @return the mleVersion
	 */
	public String getMleVersion() {
		return mleVersion;
	}



	/**
	 * @return the mleDescription
	 */
	public String getMleDescription() {
		return mleDescription;
	}



	/**
	 * @return the osName
	 */
	public String getOsName() {
		return osName;
	}



	/**
	 * @return the osVersion
	 */
	public String getOsVersion() {
		return osVersion;
	}



	/**
	 * @return the attestation_Type
	 */
	public String getAttestation_Type() {
		return attestation_Type;
	}



	/**
	 * @return the oemName
	 */
	public String getOemName() {
		return oemName;
	}



	/**
	 * @return the mleType
	 */
	public String getMleType() {
		return mleType;
	}



	/**
	 * @return the manifestList
	 */
	public List<Map<String, String>> getManifestList() {
		return manifestList;
	}



	/**
	 * @param mleName the mleName to set
	 */
	public void setMleName(String mleName) {
		this.mleName = mleName;
	}



	/**
	 * @param mleVersion the mleVersion to set
	 */
	public void setMleVersion(String mleVersion) {
		this.mleVersion = mleVersion;
	}



	/**
	 * @param mleDescription the mleDescription to set
	 */
	public void setMleDescription(String mleDescription) {
		this.mleDescription = mleDescription;
	}



	/**
	 * @param osName the osName to set
	 */
	public void setOsName(String osName) {
		this.osName = osName;
	}



	/**
	 * @param osVersion the osVersion to set
	 */
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}



	/**
	 * @param attestation_Type the attestation_Type to set
	 */
	public void setAttestation_Type(String attestation_Type) {
		this.attestation_Type = attestation_Type;
	}



	/**
	 * @param oemName the oemName to set
	 */
	public void setOemName(String oemName) {
		this.oemName = oemName;
	}



	/**
	 * @param mleType the mleType to set
	 */
	public void setMleType(String mleType) {
		this.mleType = mleType;
	}



	/**
	 * @param manifestList the manifestList to set
	 */
	public void setManifestList(List<Map<String, String>> manifestList) {
		this.manifestList = manifestList;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MLEDataVO [mleName=" + mleName + ", mleVersion=" + mleVersion
				+ ", mleDescription=" + mleDescription + ", osName=" + osName
				+ ", osVersion=" + osVersion + ", attestation_Type="
				+ attestation_Type + ", oemName=" + oemName + ", mleType="
				+ mleType + ", manifestList=" + manifestList + "]";
	}

}
