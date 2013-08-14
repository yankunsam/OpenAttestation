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

import org.apache.commons.lang3.Validate;

/**
 * Representation of a Vmm comprised of Name, Version, OS Name, and OS Version.
 * 
 * @since 0.5.1
 * @author jbuhacoff
 */
public final class Vmm {

    private String name = null;
    private String version = null;
    private String osName = null;
    private String osVersion = null;

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public Vmm(String name, String version,String osName, String osVersion ) {
        if (name == null || name.isEmpty() || version == null || version.isEmpty()) {
            throw new IllegalArgumentException("VMM name or version is missing");
        }
        if (osName == null || osName.isEmpty() || osVersion == null || osVersion.isEmpty()) {
            throw new IllegalArgumentException("VMM OS name or OS version is missing");
        }
        setName(name);
        setVersion(version);
        setOsName(osName);
        setOsVersion(osVersion);
    }

    public final void setName(String value) {
        Validate.notNull(value);
        name = value;
    }

    public final void setVersion(String value) {
        Validate.notNull(value);
        version = value;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String toString() {
        return String.format("%s:%s", name, version);
    }
}
