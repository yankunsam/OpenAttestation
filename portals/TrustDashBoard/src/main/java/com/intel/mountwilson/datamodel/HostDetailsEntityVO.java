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

package com.intel.mountwilson.datamodel;

import java.io.Serializable;
import java.util.Date;


/**
*
* @author Yuvraj Singh
*/

public class HostDetailsEntityVO implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	private String hostId;
    private String hostName;
	private String hostIPAddress;
	private String hostPort;
	private String hostDescription;
	private String biosName;
	private String biosBuildNo;
	private String vmmName;
	private String vmmBuildNo;
	private Date updatedOn;
	private String emailAddress;
	private String location;
	private String oemName;
	private String vCenterDetails;
	
	/**
	 * @return the vCenterDetails
	 */
	public String getvCenterDetails() {
		return vCenterDetails;
	}

	/**
	 * @param vCenterDetails the vCenterDetails to set
	 */
	public void setvCenterDetails(String vCenterDetails) {
		this.vCenterDetails = vCenterDetails;
	}

	/**
	 * @return the hostId
	 */
	public String getHostId() {
		return hostId;
	}

	/**
	 * @return the hostName
	 */
	public String getHostName() {
		return hostName;
	}

	/**
	 * @return the hostIPAddress
	 */
	public String getHostIPAddress() {
		return hostIPAddress;
	}

	/**
	 * @return the hostPort
	 */
	public String getHostPort() {
		return hostPort;
	}

	/**
	 * @return the hostDescription
	 */
	public String getHostDescription() {
		return hostDescription;
	}

	/**
	 * @return the biosName
	 */
	public String getBiosName() {
		return biosName;
	}

	/**
	 * @return the biosBuildNo
	 */
	public String getBiosBuildNo() {
		return biosBuildNo;
	}

	/**
	 * @return the vmmName
	 */
	public String getVmmName() {
		return vmmName;
	}

	/**
	 * @return the vmmBuildNo
	 */
	public String getVmmBuildNo() {
		return vmmBuildNo;
	}


	/**
	 * @return the updatedOn
	 */
	public Date getUpdatedOn() {
		return updatedOn;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @return the oemName
	 */
	public String getOemName() {
		return oemName;
	}

	/**
	 * @param string the hostId to set
	 */
	public void setHostId(String string) {
		this.hostId = string;
	}

	/**
	 * @param hostName the hostName to set
	 */
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	/**
	 * @param hostIPAddress the hostIPAddress to set
	 */
	public void setHostIPAddress(String hostIPAddress) {
		this.hostIPAddress = hostIPAddress;
	}

	/**
	 * @param hostPort the hostPort to set
	 */
	public void setHostPort(String hostPort) {
		this.hostPort = hostPort;
	}

	/**
	 * @param hostDescription the hostDescription to set
	 */
	public void setHostDescription(String hostDescription) {
		this.hostDescription = hostDescription;
	}

	/**
	 * @param biosName the biosName to set
	 */
	public void setBiosName(String biosName) {
		this.biosName = biosName;
	}

	/**
	 * @param biosBuildNo the biosBuildNo to set
	 */
	public void setBiosBuildNo(String biosBuildNo) {
		this.biosBuildNo = biosBuildNo;
	}

	/**
	 * @param vmmName the vmmName to set
	 */
	public void setVmmName(String vmmName) {
		this.vmmName = vmmName;
	}

	/**
	 * @param vmmBuildNo the vmmBuildNo to set
	 */
	public void setVmmBuildNo(String vmmBuildNo) {
		this.vmmBuildNo = vmmBuildNo;
	}


	/**
	 * @param updatedOn the updatedOn to set
	 */
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @param oemName the oemName to set
	 */
	public void setOemName(String oemName) {
		this.oemName = oemName;
	}

	@Override
	public String toString() {
		return "HostDetailsEntityVO [hostId=" + hostId + ", hostName="
				+ hostName + ", hostIPAddress=" + hostIPAddress + ", hostPort="
				+ hostPort + ", hostDescription=" + hostDescription
				+ ", biosName=" + biosName + ", biosBuildNo=" + biosBuildNo
				+ ", vmmName=" + vmmName + ", vmmBuildNo=" + vmmBuildNo
				+ ", updatedOn=" + updatedOn + ", emailAddress=" + emailAddress
				+ ", location=" + location + ", oemName=" + oemName
				+ ", vCenterDetails=" + vCenterDetails + "]";
	}

}
