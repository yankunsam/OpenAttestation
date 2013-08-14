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

package com.intel.mountwilson.trustagent.datatype;

import java.net.URI;

/**
 * Same as the IPAddress class in AttestationService package
 * com.intel.mountwilson.as.datatype.IPAddress, but with
 * the JSON annotations disabled and without the parse() method.
 * @author jbuhacoff
 */
public class IPAddress {

    private String address = null;

    public IPAddress() {
    }

    public IPAddress(String address) {
        setAddress(address);
    }


    public String getAddress() {
        return address;
    }

    public final void setAddress(String address) {
        if (isValid(address)) {
            this.address = address;
        } else {
            throw new IllegalArgumentException("Invalid IPAddress: " + address);
        }
    }

    /**
     * Returns the address so that you can easily concatenate to a string.
     * Example: assert new IPAddress("1.2.3.4").toString().equals("1.2.3.4");
     *
     * @see java.lang.Object#toString()
     */
//    @JsonValue
    @Override
    public String toString() {
        return address;
    }

    /**
     * This method does NOT check the network for the existence of the given
     * address, it only checks its format for validity and, if an IPv4 or IPv6
     * address is given, checks that it is within the allowed range.
     *
     * @param address to check for validity, such as 1.2.3.4
     * @return true if the address appears to be a valid IPv4 or IPv6 address,
     * false if the address is null or otherwise invalid
     */
    public static boolean isValid(String address) {
        try {
            /*
             * because URI format for host is hostname ; but problem is that
             * ipv4 is valid and [ipv6] is valid but [ipv4] is not valid and
             * ipv6 is not valid so we need to know in advance which it is or it
             * won't validate properly.. .which defeats the purpose of this
             * check... so we look for ":" to distinguish ipv4 from ipv6
             */
            if( address.contains(":") ) {
                // IPv6 format
                URI valid = new URI(String.format("//[%s]", address));
                return valid.getHost() != null;
            }
            else {
                // IPv4 format
                URI valid = new URI(String.format("//%s", address));
                // also make sure that there are only digits and dots
                // because URI also accepts valid hostnames, which are not addresses
                return valid.getHost() != null && address.matches("[\\d\\.]+");
            }
        } catch (Throwable e) {
            return false; 
            // happens when IP address is invalid format like
            // 1b.2.3i.4
        } 
    }
}
