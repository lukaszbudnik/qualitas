package com.googlecode.qualitas.internal.installation.core;

import javax.annotation.PostConstruct;
import javax.jms.ConnectionFactory;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jndi.JndiTemplate;
import org.springframework.stereotype.Component;

@Component
public class JndiBinder {

    @Autowired
    private JndiTemplate jndiTemplate;
    
    @Autowired
    private ConnectionFactory eclipseLinkCacheConnectionFactory;
    
    @Autowired
    private Topic eclipseLinkCacheTopic;
    
    @PostConstruct
    protected void bindEclipseLinkCacheResources() throws NamingException {
        jndiTemplate.bind("jms", new InitialContext());
        jndiTemplate.bind("jms/Qualitas.EclipseLinkCacheConnectionFactory", eclipseLinkCacheConnectionFactory);
        jndiTemplate.bind("jms/Qualitas.EclipseLinkCacheTopic", eclipseLinkCacheTopic);
    }
    
}
