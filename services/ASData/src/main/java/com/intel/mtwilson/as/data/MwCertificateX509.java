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
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jbuhacoff
 */
@Entity
@Table(name = "mw_certificate_x509")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MwCertificateX509.findAll", query = "SELECT a FROM MwCertificateX509 a"),
    @NamedQuery(name = "MwCertificateX509.findById", query = "SELECT a FROM MwCertificateX509 a WHERE a.id = :id"),
    @NamedQuery(name = "MwCertificateX509.findByName", query = "SELECT a FROM MwCertificateX509 a WHERE a.name = :name"),
    @NamedQuery(name = "MwCertificateX509.findByNameLike", query = "SELECT a FROM MwCertificateX509 a WHERE a.name LIKE :name"), // it's the caller's responsibility to add "%" before and/or after the name value
    @NamedQuery(name = "MwCertificateX509.findByMD5", query = "SELECT a FROM MwCertificateX509 a WHERE a.md5_hash = :md5_hash"), // added to facilitate authentication filter -jabuhacx 20120621
    @NamedQuery(name = "MwCertificateX509.findByMD5Enabled", query = "SELECT a FROM MwCertificateX509 a WHERE a.md5_hash = :md5_hash AND a.enabled = 1"), // added to facilitate authentication filter -jabuhacx 20120621
    @NamedQuery(name = "MwCertificateX509.findBySHA1", query = "SELECT a FROM MwCertificateX509 a WHERE a.sha1_hash = :sha1_hash"), // added to facilitate authentication filter -jabuhacx 20120621
    @NamedQuery(name = "MwCertificateX509.findBySHA1Enabled", query = "SELECT a FROM MwCertificateX509 a WHERE a.sha1_hash = :sha1_hash AND a.enabled = 1"), // added to facilitate authentication filter -jabuhacx 20120621
    @NamedQuery(name = "MwCertificateX509.findByIssuer", query = "SELECT a FROM MwCertificateX509 a WHERE a.issuer = :issuer"),
    @NamedQuery(name = "MwCertificateX509.findByCommentLike", query = "SELECT a FROM MwCertificateX509 a WHERE a.comment LIKE :comment"), // it's the caller's responsibility to add "%" before and/or after the name value
    @NamedQuery(name = "MwCertificateX509.findByExpires", query = "SELECT a FROM MwCertificateX509 a WHERE a.expires = :expires"),
    @NamedQuery(name = "MwCertificateX509.findByExpiresAfter", query = "SELECT a FROM MwCertificateX509 a WHERE a.expires > :expires"),
    @NamedQuery(name = "MwCertificateX509.findByExpiresBefore", query = "SELECT a FROM MwCertificateX509 a WHERE a.expires < :expires"),
    @NamedQuery(name = "MwCertificateX509.findByEnabled", query = "SELECT a FROM MwCertificateX509 a WHERE a.enabled = :enabled"),
    @NamedQuery(name = "MwCertificateX509.findByStatus", query = "SELECT a FROM MwCertificateX509 a WHERE a.status = :status"),
    @NamedQuery(name = "MwCertificateX509.findByEnabledStatus", query = "SELECT a FROM MwCertificateX509 a WHERE a.enabled = :enabled AND a.status = :status")})
public class MwCertificateX509 implements Serializable {
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
    @Column(name = "certificate")
    private byte[] certificate;
    @Basic(optional = false)
    @Lob
    @Column(name = "md5_hash")
    private byte[] md5_hash;
    @Basic(optional = false)
    @Lob
    @Column(name = "sha1_hash")
    private byte[] sha1_hash;
    @Column(name = "issuer")
    private String issuer;
    @Column(name = "expires")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expires;
    @Basic(optional = false)
    @Column(name = "enabled")
    private boolean enabled;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Basic(optional = true)
    @Column(name = "comment")
    private String comment;

    public MwCertificateX509() {
    }

    public MwCertificateX509(Integer id) {
        this.id = id;
    }

    public MwCertificateX509(Integer id, String name, byte[] certificate, boolean enabled, String status) {
        this.id = id;
        this.name = name;
        this.certificate = certificate;
//        this.md5_hash = md5_hash;
//        this.sha1_hash = sha1_hash;
        this.enabled = enabled;
        this.status = status;
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

    public byte[] getCertificate() {
        return certificate;
    }

    public void setCertificate(byte[] certificate) {
        this.certificate = certificate;
    }

    public byte[] getMd5Hash() {
        return md5_hash;
    }

    public void setMd5Hash(byte[] md5_hash) {
        this.md5_hash = md5_hash;
    }

    public byte[] getSha1Hash() {
        return sha1_hash;
    }

    public void setSha1Hash(byte[] sha1_hash) {
        this.sha1_hash = sha1_hash;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        if (!(object instanceof MwCertificateX509)) {
            return false;
        }
        MwCertificateX509 other = (MwCertificateX509) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intel.mountwilson.ms.data.MwCertificateX509[ id=" + id + " ]";
    }
        
}
