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

import java.util.HashMap;
import java.util.Map;

public enum HostWhiteListTarget {
	
        BIOS_OEM("OEM"),
        BIOS_HOST("Host"),
        VMM_GLOBAL("Global"),
        VMM_OEM("OEM"),
        VMM_HOST("Host");


        private String value;

        private HostWhiteListTarget(String value){
                this.setValue(value);
        }

        public String getValue() {
                return value;
        }

        public void setValue(String value) {
                this.value = value;
        }
        
        // Used for reverse mapping and retrieving the name given the value. We have created separte mapping classes for both BIOS and VMM since the value for 2 of the items are same.
        private static class BIOSTargetWLCache {
                private static Map<String, HostWhiteListTarget> targetWLCache = new HashMap<String,HostWhiteListTarget>();

                static {
                        targetWLCache.put(HostWhiteListTarget.BIOS_OEM.getValue(), HostWhiteListTarget.BIOS_OEM);
                        targetWLCache.put(HostWhiteListTarget.BIOS_HOST.getValue(), HostWhiteListTarget.BIOS_HOST);
                }          
        }
    
        // Used for reverse mapping and retrieving the name given the value. We have created separte mapping classes for both BIOS and VMM since the value for 2 of the items are same.        
        private static class VMMTargetWLCache {
                private static Map<String, HostWhiteListTarget> targetWLCache = new HashMap<String,HostWhiteListTarget>();

                static {
                        targetWLCache.put(HostWhiteListTarget.VMM_OEM.getValue(), HostWhiteListTarget.VMM_OEM);
                        targetWLCache.put(HostWhiteListTarget.VMM_HOST.getValue(), HostWhiteListTarget.VMM_HOST);
                        targetWLCache.put(HostWhiteListTarget.VMM_GLOBAL.getValue(), HostWhiteListTarget.VMM_GLOBAL);
                }          
        }
        
        public static HostWhiteListTarget getBIOSWhiteListTarget(String wlt) {
                return BIOSTargetWLCache.targetWLCache.get(wlt);
        }
        
        public static HostWhiteListTarget getVMMWhiteListTarget(String wlt) {
                return VMMTargetWLCache.targetWLCache.get(wlt);
        }        
}
