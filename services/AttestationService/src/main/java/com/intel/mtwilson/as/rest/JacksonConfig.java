package com.intel.mtwilson.as.rest;

import javax.ws.rs.Produces;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.MediaType;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JacksonConfig implements ContextResolver<ObjectMapper>

{

    private final ObjectMapper objectMapper;

    public JacksonConfig()

    {

        objectMapper = new ObjectMapper();

        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);

    }

    @Override

    public ObjectMapper getContext(Class<?> objectType)

    {

        return objectMapper;

    }
}

