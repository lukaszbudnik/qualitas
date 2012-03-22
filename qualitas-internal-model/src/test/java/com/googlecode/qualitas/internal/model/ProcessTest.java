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
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.code.p.qualitas.engines.api.configuration.ProcessStatus;
import com.google.code.qualitas.internal.model.User_;
import com.googlecode.qualitas.internal.model.Process;
import com.googlecode.qualitas.internal.model.ProcessBundle;
import com.googlecode.qualitas.internal.model.User;

public class ProcessTest {

    private static EntityManager em;

    @BeforeClass
    public static void setUpClass() {
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("qualitas-pu-test");
        em = emf.createEntityManager();
        
        DatabaseManagerSwing.main(new String[] { "--url","jdbc:hsqldb:mem:test", "--noexit" });
    }
    
    @Test
    public void testCreateNewProcess() {
        em.getTransaction().begin();

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
        process.setProcessType("processType");
        process.setProcessStatus(ProcessStatus.UPLOADED);
        process.setInstallationDate(new Date());
        
        ProcessBundle processBundle = new ProcessBundle();
        processBundle.setContents("contents".getBytes());
        process.setOriginalProcessBundle(processBundle);

        em.persist(process);
        
        Assert.assertTrue(process.getProcessId() > 0);
        Assert.assertTrue(processBundle.getProcessBundleId() > 0);
        
        em.getTransaction().commit();
    }
    
    @Test
    public void testUpdateProcess() {
        em.getTransaction().begin();
        
        Process process = em.find(Process.class, new Long(1));
        process.setProcessName("processName");
        ProcessBundle instrumentedProcessBundle = new ProcessBundle();
        instrumentedProcessBundle.setContents("contents".getBytes());
        process.setInstrumentedProcessBundle(instrumentedProcessBundle);
        
        em.merge(process);
        
        em.getTransaction().commit();
    }

}
