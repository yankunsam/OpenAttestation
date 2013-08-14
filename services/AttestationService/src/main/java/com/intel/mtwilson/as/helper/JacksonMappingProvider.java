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

package com.intel.mtwilson.as.helper;

import java.net.URL;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Allows the OpenStack JSON format where a single-element array is serialized
 * as a string instead of as a single-element array.
 * 
 * Glassfish comes with its own copy of jackson-jaxrs in
 * glassfish/modules/jackson-mapper-asl.jar.
 * 
 * Glassfish 3.1.1 includes an older version of jackson mapper that does not
 * support the feature ACCEPT_SINGLE_VALUE_AS_ARRAY. 
 * 
 * Glassfish 3.1.2.2 includes a newer version jackson that does support this
 * feature. 
 * 
 * @author jbuhacoff
 * @since 1.1 
 */
@Provider
public class JacksonMappingProvider implements ContextResolver<ObjectMapper> {

    private final ObjectMapper mapper = new ObjectMapper();
    
    public JacksonMappingProvider() {
        /*
        DeserializationConfig.Feature[] values = DeserializationConfig.Feature.values();
        for(DeserializationConfig.Feature value : values) {
            System.out.println("[JACKSON DeserializationConfig.Feature] "+value.name());
        }
        URL jackson = getClass().getResource("org/codehaus/jackson/map/ObjectMapper.class");
        if( jackson != null ) {
            System.out.println("[JACKSON JAR] "+jackson.toExternalForm());
        }
        */
        mapper.configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }
    
    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }
    
}
