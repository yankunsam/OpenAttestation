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

package com.intel.mtwilson.datatypes;

import java.util.List;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author jbuhacoff
 */
public class MleData {

    public enum MleType {

        BIOS,
        VMM;
    }

    public enum AttestationType {

        PCR,
        MODULE;
    }
    private String name;
    private String version;
    private AttestationType attestationType;
    private MleType mleType;
    private String description;
    private List<ManifestData> mleManifests;
    /* Start attributes added for phase 2*/
    private String osName; // This will be set for MleType.VMM
    private String osVersion; // This will be set for MleType.VMM
    private String oemName; // This will be set for MleType.BIOS
    /* End attributes added for phase 2*/

    public MleData() {
    }

    public MleData(String name, String version, MleType mleType, AttestationType attestationType,
            List<ManifestData> manifestList, String description,
            String osName, String osVersion, String oemName) {
        setName(name);
        setVersion(version);
        setMleType(mleType.toString());
        setAttestationType(attestationType.toString());
        setManifestList(manifestList);
        setDescription(description);
        setOsName(osName);
        setOsVersion(osVersion);
        setOemName(oemName);
    }

    @JsonProperty("OemName")
    public String getOemName() {
        if (getMleType().equals(MleType.BIOS.toString()) && (oemName == null || oemName.isEmpty())) {
            throw new IllegalArgumentException("OEM name is missing for BIOS MLE");
        }

        return oemName;
    }

    @JsonProperty("OemName")
    public final void setOemName(String oemName) {
        this.oemName = oemName;
    }

    @JsonProperty("OsName")
    public String getOsName() {
        if (getMleType().equals(MleType.VMM.toString()) && (osName == null || osName.isEmpty())) {
            throw new IllegalArgumentException("OS name is missing for VMM MLE");
        }

        return osName;
    }

    @JsonProperty("OsName")
    public final void setOsName(String osName) {
        this.osName = osName;
    }

    @JsonProperty("OsVersion")
    public String getOsVersion() {
        if (getMleType().equals(MleType.VMM.toString()) && (osVersion == null || osVersion.isEmpty())) {
            throw new IllegalArgumentException("OS version is missing for VMM MLE");
        }
        return osVersion;
    }

    @JsonProperty("OsVersion")
    public final void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    @JsonProperty("Name")
    public final void setName(String value) {
        name = value;
    }

    @JsonProperty("Name")
    public String getName() {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("MLE Name is missing");
        }
        return name;
    }

    @JsonProperty("Version")
    public final void setVersion(String value) {
        version = value;
    }

    @JsonProperty("Version")
    public String getVersion() {
        if (version == null || version.isEmpty()) {
            throw new IllegalArgumentException("MLE Version is missing");
        }
        return version;
    }

    @JsonProperty("Attestation_Type")
    public final void setAttestationType(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("MLE Attestation Type is missing");
        }
        try {
            attestationType = AttestationType.valueOf(value.toUpperCase()); //Enum.valueOf(AttestationType.class, value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Attestation Type is invalid");
        }
    }

    @JsonProperty("Attestation_Type")
    public String getAttestationType() {
        if (attestationType == null) {
            throw new IllegalArgumentException("MLE Attestation Type is missing");
        }

        return attestationType.toString();
    }

    @JsonProperty("MLE_Type")
    public final void setMleType(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("MLE Type is missing");
        }
        try {
            mleType = MleType.valueOf(value); //Enum.valueOf(MleType.class, value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("MLE Type is invalid");
        }
    }

    @JsonProperty("MLE_Type")
    public String getMleType() {
        if (mleType == null) {
            throw new IllegalArgumentException("MLE Type is missing");
        }
        return mleType.toString();
    }

    @JsonProperty("Description")
    public final void setDescription(String value) {
        description = value;
    }

    @JsonProperty("Description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("MLE_Manifests")
    public final void setManifestList(List<ManifestData> list) {
        mleManifests = list;
    }

    @JsonProperty("MLE_Manifests")
    public final List<ManifestData> getManifestList() {
        //return Arrays.asList(mleManifests);
        //validate data before returning the list
        if (mleManifests != null) {
            for (ManifestData manifestData : mleManifests) {
                manifestData.getName();
                manifestData.getValue();
            }
        }
        return mleManifests;
    }

    @Override
    public String toString() {
        return String.format("%s %s (%s %s) - %s", name, version, mleType.toString(), attestationType.toString(), description);
    }
}
