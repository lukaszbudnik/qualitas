package com.googlecode.qualitas.internal.services.spring;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.googlecode.qualitas.internal.services.ProcessManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/META-INF/spring/qualitas-internal-services-context.xml")
public class IoCTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void testCreateRepository() {
        ProcessManager processManager = context.getBean(ProcessManager.class);
        
        Assert.assertNotNull(processManager);
    }
    
}
