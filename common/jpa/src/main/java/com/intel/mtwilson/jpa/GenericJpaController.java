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

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generic JPA Controller with convenience methods that simplify implementation
 * of named queries when customizing generated controller.
 * 
 * This class abstracts common functionality that was previously duplicated
 * across various JPA Controllers.
 * 
 * @author jbuhacoff
 * @since 0.5.4
 */
public abstract class GenericJpaController<T> {
    private Logger log = LoggerFactory.getLogger(getClass());
    private Class entityClass;
    
    protected GenericJpaController(Class<T> entityClass) {
        this.entityClass = entityClass;
        log.debug("JpaController with entityClass {}", entityClass.getName());
    }
    
    public abstract EntityManager getEntityManager();
    
   /**
     * Added to facilitate the management application.
     * Each parameter can be Integer, Boolean, Date, Calendar, or String.
     * Date parameters are set as TemporalType.DATE
     * Calendar parameters are set as TemporalType.TIMESTAMP
     * You can pass java.sql.Date, java.sql.Time, or java.sql.Timestamp to 
     * ensure the correct interpretation of your Date parameter.
     * @param queryName from the entity bean NamedQuery annotations 
     * @param parameters to provide to the query, must be the correct number
     * @return list of entities, or empty list if none were found
     * @since 0.5.4
     */
    protected List<T> searchByNamedQuery(String queryName, Map<String,Object> parameters) {
        log.debug("Named query {} with {} parameters", new String[] { queryName, String.valueOf(parameters.keySet().size()) });
        EntityManager em = getEntityManager();
        try {
            TypedQuery<T> query = em.createNamedQuery(entityClass.getSimpleName()+"."+queryName, entityClass);
            for(String variableName : parameters.keySet()) {
                Object variableValue = parameters.get(variableName);
                log.debug("Named query: {} Variable: {} Value: {}", new String[] { queryName, variableName, variableValue.toString() });
                setQueryParameter(query, variableName, variableValue);
            }
            List<T> list = query.getResultList();
            if( list != null && !list.isEmpty() ) {
                return list;
            }
        } finally {
            em.close();
        }
        return Collections.EMPTY_LIST;        
    }

    /**
     * TODO: support time instants from JodaTime or javax.time and set them as a Date or Calendar with TemporalType.Time 
     * TODO: support a way to specify that a date should be interpreted as TemporalType.DateTime ... maybe by looking for javax.sql.DateTime types??    
     * @param query
     * @param parameterName
     * @param parameterValue 
     */
    private void setQueryParameter(TypedQuery<T> query, String parameterName, Object parameterValue) {
        if( parameterValue instanceof java.util.Date ) {
            if( parameterValue instanceof java.sql.Time  ) {
                query.setParameter(parameterName, (java.util.Date)parameterValue, TemporalType.TIME);                    
            }
            else if( parameterValue instanceof java.sql.Timestamp  ) {
                query.setParameter(parameterName, (java.util.Date)parameterValue, TemporalType.TIMESTAMP);                    
            }
            else if( parameterValue instanceof java.sql.Date ) {
                query.setParameter(parameterName, (java.util.Date)parameterValue, TemporalType.DATE);                    
            }
            else {
                query.setParameter(parameterName, (java.util.Date)parameterValue, TemporalType.DATE);                   
            }
        }
        else if( parameterValue instanceof Calendar ) {
            query.setParameter(parameterName, (Calendar)parameterValue, TemporalType.TIMESTAMP);                    
        }
        else {
            query.setParameter(parameterName, parameterValue);
        }        
    }
    
    /**
     * Convenience method for a 1-parameter named query.
     * Added to facilitate implementation of named queries in generated
     * JPA controllers. 
     * @param queryName
     * @param variableName
     * @param variableValue
     * @return 
     * @since 0.5.4
     */
    protected List<T> searchByNamedQuery(String queryName, String variableName, Object variableValue) {
        HashMap<String,Object> parameters = new HashMap<String,Object>();
        parameters.put(variableName, variableValue);
        return searchByNamedQuery(queryName, parameters);
    }

    /**
     * Convenience method for a 0-parameter named query.
     * Added to facilitate implementation of named queries in generated
     * JPA controllers.
     * @param queryName
     * @return 
     * @since 0.5.4
     */
    protected List<T> searchByNamedQuery(String queryName) {
        HashMap<String,Object> parameters = new HashMap<String,Object>();
        return searchByNamedQuery(queryName, parameters);
    }
    
}
