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

import org.codehaus.jackson.annotate.JsonGetter;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonSetter;

/**
 *
 * @author dsmagadx
 */
public final class OsData {


    private String name;
    private String version;
    private String description;

    public OsData(){
        
    }
    
    public OsData(String name, String version, String description) {
        setName(name); 
        setVersion(version);
        setDescription(description);
    }
    
    
    

    @JsonGetter("Description")
    public String getDescription() {

        return description;
    }

    @JsonSetter("Description")
    public void setDescription(String value) {


        this.description = value;
    }

    @JsonGetter("Name")
    public String getName() {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("OS Name is missing");
        }

        return this.name;
    }

    @JsonSetter("Name")
    public void setName(String value) {
        this.name = value;

    }

    @JsonGetter("Version")
    public String getVersion() {
        if (version == null || version.isEmpty()) {
            throw new IllegalArgumentException("OS Version is missing");
        }
        return version;
    }

    @JsonSetter("Version")
    public void setVersion(String value) {
        this.version = value;
    }
}
