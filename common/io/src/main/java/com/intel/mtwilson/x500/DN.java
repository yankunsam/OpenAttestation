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

package com.intel.mtwilson.x500;

import java.util.HashMap;
import java.util.Map;
import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is only for convenience and is not a complete implementation.
 * It would have been convenient to use X500Name directly but it was causing an
 * exception when parsing the DN on some platforms. The LdapName class
 * is not convenient to use, so this class was written as a simple wrapper.
 * 
 * See also sun.security.x509.X500Name and javax.naming.ldap.LdapName
 * 
 * @author jbuhacoff
 */
public class DN {
    private Logger log = LoggerFactory.getLogger(getClass());
    private String dn;
    private Map<String,String> map = new HashMap<String,String>();
    
    public DN(String dn) {
        this.dn = dn;
        parseLdapName(dn);
    }
    
    private void parseLdapName(String distinguishedName) {
        try {
            LdapName dn = new LdapName(distinguishedName);
            for(int i=0; i<dn.size(); i++) {
                Rdn rdn = dn.getRdn(i);
                map.put(rdn.getType(), rdn.getValue().toString());
            }
        }
        catch(InvalidNameException e) {
            log.error("Cannot extract Common Name from Distinguished Name", e);
        }
    }
    
    /**
     * 
     * @return the original string version of the distinguished name
     */
    @Override
    public String toString() { return dn; }
    
    /**
     * Retrieve any Relative Distinguished Name
     * @param rdn name such as "CN", "OU", "L", etc.
     * @return 
     */
    public String get(String rdn) { return map.get(rdn); }
    
    public String getCommonName() { return map.get("CN"); }
    
    public String getOrg() { return map.get("O"); }
    
    public String getOrgUnit() { return map.get("OU"); }
    
    public String getLocality() { return map.get("L"); }
    
    public String getState() { return map.get("S"); }
    
    public String getCountry() { return map.get("C"); }
    
}
