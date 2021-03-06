/*
 * Copyright (C) 2014 Intel Corporation
 * All rights reserved.
 */
package com.intel.mtwilson.tag.selection.json;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;
//import com.fasterxml.jackson.annotation.JsonCreator;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonValue;
import com.intel.mtwilson.tag.selection.xml.CacheModeAttribute;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author jbuhacoff
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
public abstract class CacheModeAttributeMixIn {
    @JsonValue
    abstract String value();
    
    // the following static method is never used,  it's only here to 
    // remotely annotate the CacheModeAttribute.fromValue method  as the
    // JsonCreator... cannot be marked abstract because it's static.
    @JsonCreator
    public static CacheModeAttribute fromValue(String v) { return null; }
}
