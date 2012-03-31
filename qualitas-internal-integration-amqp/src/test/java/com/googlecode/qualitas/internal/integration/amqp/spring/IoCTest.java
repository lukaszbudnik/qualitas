package com.googlecode.qualitas.internal.integration.amqp.spring;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.googlecode.qualitas.internal.integration.amqp.InstallationServiceImpl;
import com.googlecode.qualitas.internal.integration.api.InstallationService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/META-INF/spring/qualitas-internal-integration-amqp-context.xml")
public class IoCTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void testCreateRepository() {
        InstallationService installationService = (InstallationService) context.getBean("amqpInstallationService");
        
        Assert.assertNotNull(installationService);
        Assert.assertEquals(InstallationServiceImpl.class, installationService.getClass());
    }
    
}