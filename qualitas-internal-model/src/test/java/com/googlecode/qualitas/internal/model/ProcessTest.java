package com.googlecode.qualitas.internal.model;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import junit.framework.Assert;

import org.hsqldb.util.DatabaseManagerSwing;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.googlecode.qualitas.engines.api.configuration.ProcessStatus;
import com.googlecode.qualitas.engines.api.configuration.ProcessType;

@Ignore
public class ProcessTest {

    private static EntityManager em;

    @BeforeClass
    public static void setUpClass() {
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("qualitas-pu-test");
        em = emf.createEntityManager();
        
        DatabaseManagerSwing.main(new String[] { "--url","jdbc:hsqldb:mem:test", "--noexit" });
    }
    
    @Before
    public void   setUp() {
        em.getTransaction().begin();
    }
    
    @After
    public void tearDown() {
        em.getTransaction().commit();
    }
    
    @Test
    public void testCreateNewProcess() {
        User user = new User();
        user.setOpenIDUsername("openidusername");

        em.persist(user);
        
        Assert.assertTrue(user.getUserId() > 0);

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder
                .createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.where(criteriaBuilder.equal(
                root.get(User_.openIDUsername), "openidusername"));
        user = em.createQuery(criteriaQuery).getSingleResult();

        Process process = new Process();
        process.setUser(user);
        process.setProcessType(ProcessType.WS_BPEL_2_0_APACHE_ODE);
        process.setProcessStatus(ProcessStatus.UPLOADED);
        process.setInstallationDate(new Date());
        
        ProcessBundle processBundle = new ProcessBundle();
        processBundle.setContents("contents".getBytes());
        process.setOriginalProcessBundle(processBundle);

        em.persist(process);
        
        Assert.assertTrue(process.getProcessId() > 0);
        Assert.assertTrue(processBundle.getProcessBundleId() > 0);
    }
    
    @Test
    public void testUpdateProcess() {
        Process process = em.find(Process.class, new Long(1));
        process.setProcessName("processName");
        ProcessBundle instrumentedProcessBundle = new ProcessBundle();
        instrumentedProcessBundle.setContents("contents".getBytes());
        process.setInstrumentedProcessBundle(instrumentedProcessBundle);
        
        em.merge(process);
    }

}
