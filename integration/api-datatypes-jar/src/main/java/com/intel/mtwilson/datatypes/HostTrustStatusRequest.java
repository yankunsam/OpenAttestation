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

import java.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonSetter;

/**
 *
 * @author dsmagadx
 */
public class HostTrustStatusRequest /*extends AuthRequest*/ {

    @JsonProperty
    private String[] hostAddresses;
    
    @JsonProperty("force_verify")
    private Boolean forceVerify = false;

    
    public HostTrustStatusRequest(String clientId, String userName, String password, String hosts, Boolean forceVerify) {
       // super(clientId, userName, password);
        if(hosts != null)
        	setHostAddressList(hosts);
        setForceVerify(forceVerify);
    }

    public HostTrustStatusRequest() {
        super();
    }

    @JsonIgnore
    public Collection<String> getHostAddresses() {
        return Arrays.asList(hostAddresses);
    }
    /*
    @JsonGetter("hosts")
    public String[] getHostAddresses() {
        return hostAddresses;
    }
    */

    public final void setHostAddressList(String hosts) {
        
    	Validate.notNull(hosts);
    	 // for klocwork review tool
    		hostAddresses = StringUtils.split(hosts,",");
    }

    @JsonIgnore
    public Boolean getForceVerify() {
        return forceVerify;
    }

    @JsonSetter("force_verify")
    public final void setForceVerify(Boolean forceVerify) {
        if( forceVerify == null ) {
            this.forceVerify = Boolean.FALSE;
        }
        else {
            this.forceVerify = forceVerify;        
        }
    }

}
