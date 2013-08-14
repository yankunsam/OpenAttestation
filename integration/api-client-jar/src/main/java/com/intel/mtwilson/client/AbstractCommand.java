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

package com.intel.mtwilson.client;

import com.intel.mtwilson.ApiClient;
import com.intel.mtwilson.ClientException;
import com.intel.mtwilson.io.ConfigurationUtil;
import java.io.IOException;
import org.apache.commons.configuration.Configuration;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author jbuhacoff
 */
public abstract class AbstractCommand implements Command {
    private ApiClient client;
    private static final ObjectMapper mapper = new ObjectMapper();
    
    protected ApiClient getClient() throws ClientException, IOException { 
        if( client == null ) {
            client = createClient();
        }
        return client;
    }

    private ApiClient createClient() throws ClientException, IOException {
        Configuration conf = ConfigurationUtil.getConfiguration(); // tries jvm properties, environment variables, then mtwilson.properties;  you can set location of mtwilson.properties with -Dmtwilson.home=/path/to/dir
        return new ApiClient(conf);
    }
    
    protected String toJson(Object value) throws ClientException {
        try {
            return mapper.writeValueAsString(value);
        }
        catch(Exception e) {
            throw new ClientException("Cannot serialize object", e);
        }
    }
}
