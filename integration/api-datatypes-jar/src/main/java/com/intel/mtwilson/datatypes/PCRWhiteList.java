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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intel.mtwilson.datatypes;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * TODO: Once we use the same API DataTypes package in the backend service, we can 
 * use the OS and OEM data objects here instead of declaring them separately again.
 * TODO replace pcrName and pcrDigest with the Pcr object.
 * XXX TODO oemName should be moved out of this class (and corresponding database table)
 * and into a separate table that links oem's to mle's. or create a separate class
 * for Mle(Name,Version,Os,Pcr) and BiosMle(Name,Version,Oem,Pcr) with corresponding
 * separate database tables. "either or" optional fields pervade the entire application
 * complicating logic everywhere, and in most cases a piece of code is only interested
 * in one or the other so a streamlined class is much easier to read at first sight.
 * 
 * @author ssbangal
 */
public class PCRWhiteList {
    
    private String pcrName;
    private String pcrDigest;
    private String mleName;
    private String mleVersion;
    private String osName; 
    private String osVersion; 
    private String oemName;


    /**
     * Constructor for the PCRWhiteList object. Note that based on the MLE type only OS or OEM information
     * need to populated.
     */
    public PCRWhiteList() {
    }

    /**
     * Constructor for the PCRWhiteList object. Note that based on the MLE type only OS or OEM information
     * need to populated.
     * 
     * @param pcrName
     * @param pcrDigest
     * @param mleName
     * @param mleVersion
     * @param osName
     * @param osVersion
     * @param oemName 
     */
    public PCRWhiteList(String pcrName, String pcrDigest, String mleName, String mleVersion, String osName, String osVersion, String oemName) {
        this.pcrName = pcrName;
        this.pcrDigest = pcrDigest;
        this.mleName = mleName;
        this.mleVersion = mleVersion;
        this.osName = osName;
        this.osVersion = osVersion;
        this.oemName = oemName;
    }
    
    @JsonProperty("mleName")
    public String getMleName() {
        return mleName;
    }

    @JsonProperty("mleName")
    public void setMleName(String mleName) {
        this.mleName = mleName;
    }

    @JsonProperty("mleVersion")
    public String getMleVersion() {
        return mleVersion;
    }

    @JsonProperty("mleVersion")
    public void setMleVersion(String mleVersion) {
        this.mleVersion = mleVersion;
    }

    @JsonProperty("oemName")
    public String getOemName() {
        return oemName;
    }

    @JsonProperty("oemName")
    public void setOemName(String oemName) {
        this.oemName = oemName;
    }

    @JsonProperty("osName")
    public String getOsName() {
        return osName;
    }

    @JsonProperty("osName")
    public void setOsName(String osName) {
        this.osName = osName;
    }

    @JsonProperty("osVersion")
    public String getOsVersion() {
        return osVersion;
    }

    @JsonProperty("osVersion")
    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    @JsonProperty("pcrDigest")
    public String getPcrDigest() {
        return pcrDigest;
    }

    @JsonProperty("pcrDigest")
    public void setPcrDigest(String pcrDigest) {
        this.pcrDigest = pcrDigest;
    }

    @JsonProperty("pcrName")
    public String getPcrName() {
        return pcrName;
    }

    @JsonProperty("pcrName")
    public void setPcrName(String pcrName) {
        this.pcrName = pcrName;
    }

}
