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

/**
 * @author yuvrajsx
 *
 */
public class VmmHostDataVo {

		private String hostOS;
		private String hostVersion;
		private List<String> vmmNames;
		private String attestationType;
		/**
		 * @return the hostOS
		 */
		public String getHostOS() {
			return hostOS;
		}
		/**
		 * @return the hostVersion
		 */
		public String getHostVersion() {
			return hostVersion;
		}
		/**
		 * @param hostVersion the hostVersion to set
		 */
		public void setHostVersion(String hostVersion) {
			this.hostVersion = hostVersion;
		}
		/**
		 * @return the vmmNames
		 */
		public List<String> getVmmNames() {
			return vmmNames;
		}
		/**
		 * @return the isModuleBased
		 */
		public String getAttestationType() {
			return attestationType;
		}
		/**
		 * @param hostOS the hostOS to set
		 */
		public void setHostOS(String hostOS) {
			this.hostOS = hostOS;
		}
		/**
		 * @param vmmNames the vmmNames to set
		 */
		public void setVmmNames(List<String> vmmNames) {
			this.vmmNames = vmmNames;
		}
		/**
		 * @param isModuleBased the isModuleBased to set
		 */
		public void setAttestationType(String isModuleBased) {
			this.attestationType = isModuleBased;
		}
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "VmmHostDataVo [hostOS=" + hostOS + ", hostVersion="
					+ hostVersion + ", vmmNames=" + vmmNames
					+ ", attestationType=" + attestationType + "]";
		}
		
		
		
}
