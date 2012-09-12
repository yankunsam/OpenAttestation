/*
Copyright (c) 2012, Intel Corporation
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
    * Neither the name of Intel Corporation nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package com.intel.openAttestation.manifest.hibernate.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Java class linked to the PCR_manifest table.
 * @author  intel
 * @version OpenAttestation
 *
 */

@XmlRootElement

public class OEM {
	Long OEMID;
    String OEMName;
    String OEMDesc;
    
	public OEM(){}
        
    public OEM(Long ID, String name, String desc){
    	this.OEMName = name;
    	this.OEMDesc = desc;
    }
    
	public Long getOEMID() {
		return OEMID;
	}

	public void setOEMID(Long id) {
		OEMID = id;
	}
	
	public String getOEMName() {
		return OEMName;
	}

	public void setOEMName(String name) {
		OEMName = name;
	}
	
	public String getOEMDesc() {
		return OEMDesc;
	}

	public void setOEMDesc(String desc) {
		OEMDesc = desc;
	}

	/**
     * validate 
     * @return
     */
	public String validateDataFormat(){
    	return "";
    }
}
