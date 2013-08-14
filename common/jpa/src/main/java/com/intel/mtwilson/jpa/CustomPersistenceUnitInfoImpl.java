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

package com.intel.mtwilson.jpa;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.DataSourceConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.dbcp.managed.BasicManagedDataSource;
import org.apache.commons.dbcp.managed.ManagedDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.objectweb.jotm.Current;
//import org.apache.commons.dbcp.PoolingDataSource;

/**
 * This class represents the contents of persistence.xml
 * @author jbuhacoff
 */
public class CustomPersistenceUnitInfoImpl implements PersistenceUnitInfo {
    protected URL url;
    protected DataSource ds;
    protected Properties jpaProperties;
    protected String persistenceUnitName; // ex: ASDataPU
    protected String persistenceUnitProvider; // ex: org.eclipse.persistence.jpa.PersistenceProvider
    protected String transactionType; // ex: RESOURCE_LOCAL, JTA (enum PersistenceUnitTransactionType)
    protected List<String> classList; // ex: com.mtwilson.as.data.MwCertificate, com.mtwilson.as.data.MwOem
//    protected String jdbcDriver; // ex: com.mysql.jdbc.Driver
//    protected String jdbcUrl; // ex: jdbc:mysql://127.0.0.1:3306/mw_as
//    protected String jdbcUsername; // ex: root
//    protected String jdbcPassword; // ex: password
            
    @Override
    public String getPersistenceUnitName() {
        return persistenceUnitName;
    }

    @Override
    public String getPersistenceProviderClassName() {
        return persistenceUnitProvider;
    }

    @Override
    public PersistenceUnitTransactionType getTransactionType() {
        return PersistenceUnitTransactionType.valueOf(transactionType);
    }
    
    @Override
    public DataSource getJtaDataSource() {
//        throw new UnsupportedOperationException("Not supported yet.");
//        return mds; // XXX TODO need to create the transaction-managed jta/jpa data source
        return ds;
    }

    @Override
    public DataSource getNonJtaDataSource() {
//        throw new UnsupportedOperationException("Not supported yet.");
        return ds;
    }

    @Override
    public List<String> getMappingFileNames() {
//        throw new UnsupportedOperationException("Not supported yet.");
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<URL> getJarFileUrls() {
//        throw new UnsupportedOperationException("Not supported yet.");
        return Collections.EMPTY_LIST;
    }

    @Override
    public URL getPersistenceUnitRootUrl() {
        if( url != null ) { return url; }
        try {
            return new URL("http://localhost");
        }
        catch(MalformedURLException e) {
            throw new IllegalArgumentException("Invalid persistence unit root url: "+e.getLocalizedMessage());
        }
    }

    @Override
    public List<String> getManagedClassNames() {
        return classList;
    }

    @Override
    public boolean excludeUnlistedClasses() {
//        throw new UnsupportedOperationException("Not supported yet.");
        return true;
    }

    @Override
    public SharedCacheMode getSharedCacheMode() {
//        throw new UnsupportedOperationException("Not supported yet.");
        return SharedCacheMode.NONE;
    }

    @Override
    public ValidationMode getValidationMode() {
//        throw new UnsupportedOperationException("Not supported yet.");
        return ValidationMode.NONE;
    }

    @Override
    public Properties getProperties() {
//        throw new UnsupportedOperationException("Not supported yet.");
        return jpaProperties;
    }

    @Override
    public String getPersistenceXMLSchemaVersion() {
//        throw new UnsupportedOperationException("Not supported yet.");
        return "2.0";
    }

    @Override
    public ClassLoader getClassLoader() {
//        throw new UnsupportedOperationException("Not supported yet.");
//        return ClassLoader.getSystemClassLoader();
        return getClass().getClassLoader();
    }

    /**
     * XXX currently we do not support this feature; our usage of EclipseLink
     * appears to be working well without it. 
     * @param ct 
     */
    @Override
    public void addTransformer(ClassTransformer ct) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ClassLoader getNewTempClassLoader() {
//        throw new UnsupportedOperationException("Not supported yet.");
//        return ClassLoader.getSystemClassLoader();
        return getClass().getClassLoader();
    }
    
}
