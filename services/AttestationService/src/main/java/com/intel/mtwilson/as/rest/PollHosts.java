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


package com.intel.mtwilson.as.rest;

import com.intel.mountwilson.as.common.ASException;
import com.intel.mtwilson.as.helper.ASComponentFactory;
import com.intel.mtwilson.datatypes.OpenStackHostTrustLevelReport;
import com.intel.mtwilson.datatypes.OpenStackHostTrustLevelQuery;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * REST Web Service
 * 
 * Currently the "count" and "pcrMask" fields of HostTrustInput are ignored by PollHosts and HostTrustBO,
 * so removed it from the pollHosts method documentation.
 * 
 */

@Stateless
@Path("/PollHosts")
public class PollHosts {
    private static final Logger log = LoggerFactory.getLogger(PollHosts.class);
    
    /**
     * Returns information about the trust status of the specified hosts.
     * 
     * Sample request:
     * Content-Type: application/json
     * POST http://localhost:8080/AttestationService/resources/PollHosts
     * {
     *   "hosts":["host name 1", "host name 2", "host name 3"]
     * }
     * 
     * Sample response (JSON format):
     * {
     *   "count":3,
     *   "hosts":{
     *     "host name 1": {
     *       "trust_lvl": "unknown",
     *       "timestamp": "Tue Feb 14 09:02:48 2012"
     *     },
     *     "host name 2": {
     *       "trust_lvl": "untrusted",
     *       "timestamp": "Tue Feb 14 09:02:48 2012"
     *     },
     *     "host name 3":{
     *       "trust_lvl": "trusted",
     *       "timestamp": "Tue Feb 14 09:02:48 2012"
     *     }
     *   }
     * }
     * 
     * @param input
     * @return the trust status of the specified hosts
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public OpenStackHostTrustLevelReport pollMultipleHosts(OpenStackHostTrustLevelQuery input) {
        try {
            log.info("PCR Mask {}", input.pcrMask);
            return new ASComponentFactory().getHostTrustBO().getPollHosts(input);
        }
        catch(ASException e) {
            throw e;
        }
        catch(Exception e) {
            throw new ASException(e);
        }
    }

    
}
