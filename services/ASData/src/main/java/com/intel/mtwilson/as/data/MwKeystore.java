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

package com.intel.mtwilson.as.data;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * When using the named queries for findByNameLike, findByProviderLike, and
 * findByCommentLike, it's the caller's responsibility to add "%" to the
 * value being searched as necessary (before and/or after the value) for
 * wildcard results.
 * 
 * @author jbuhacoff
 */
@Entity
@Table(name = "mw_keystore")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MwKeystore.findAll", query = "SELECT a FROM MwKeystore a"),
    @NamedQuery(name = "MwKeystore.findById", query = "SELECT a FROM MwKeystore a WHERE a.id = :id"),
    @NamedQuery(name = "MwKeystore.findByName", query = "SELECT a FROM MwKeystore a WHERE a.name = :name"),
    @NamedQuery(name = "MwKeystore.findByNameLike", query = "SELECT a FROM MwKeystore a WHERE a.name LIKE :name"),
    @NamedQuery(name = "MwKeystore.findByProvider", query = "SELECT a FROM MwKeystore a WHERE a.provider = :provider"),
    @NamedQuery(name = "MwKeystore.findByProviderLike", query = "SELECT a FROM MwKeystore a WHERE a.provider LIKE :provider"),
    @NamedQuery(name = "MwKeystore.findByCommentLike", query = "SELECT a FROM MwKeystore a WHERE a.comment LIKE :comment")})
public class MwKeystore implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @Lob
    @Column(name = "keystore")
    private byte[] keystore;

    @Basic(optional = true)
    @Column(name = "provider")
    private String provider;

    @Basic(optional = true)
    @Column(name = "comment")
    private String comment;

    public MwKeystore() {
    }

    public MwKeystore(Integer id) {
        this.id = id;
    }

    public MwKeystore(Integer id, String name, byte[] keystore, String provider) {
        this.id = id;
        this.name = name;
        this.keystore = keystore;
        this.provider = provider;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getKeystore() {
        return keystore;
    }

    public void setKeystore(byte[] keystore) {
        this.keystore = keystore;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MwKeystore)) {
            return false;
        }
        MwKeystore other = (MwKeystore) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intel.mountwilson.ms.data.MwKeystore[ id=" + id + " ]";
    }
        
}
