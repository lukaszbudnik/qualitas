package com.googlecode.qualitas.internal.monitor.webservice;

import junit.framework.Assert;

import org.junit.Test;

public class SourceGenerationTest {

    @Test
    public void testSourceGeneration() throws ClassNotFoundException {
        
        Class<?> webServiceInterface = Class.forName("com.googlecode.qualitas.internal.monitor.webservice.QualitasMonitorWebService");
        
        Assert.assertNotNull(webServiceInterface);
        
    }
    
}
