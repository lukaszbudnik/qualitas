package com.google.code.p.qualitas.engines.api.configuration;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXB;

import junit.framework.Assert;

import org.junit.Test;

import com.google.code.p.qualitas.engines.api.configuration.AnalystConfiguration;
import com.google.code.p.qualitas.engines.api.configuration.AnalystGlobalParameters;
import com.google.code.p.qualitas.engines.api.configuration.AnalystPartner;
import com.google.code.p.qualitas.engines.api.configuration.AnalystPartners;
import com.google.code.p.qualitas.engines.api.configuration.MonitorConfiguration;
import com.google.code.p.qualitas.engines.api.configuration.MonitorPartner;
import com.google.code.p.qualitas.engines.api.configuration.MonitorPartners;
import com.google.code.p.qualitas.engines.api.configuration.QualitasConfiguration;

public class QualitasConfigurationTest {

    @Test
    public void testGetQualitasConfiguration() {
        AnalystConfiguration analystConfiguration = new AnalystConfiguration();
        analystConfiguration.setAnalysisEnabled(true);
        
        AnalystGlobalParameters analystGlobalParameters = new AnalystGlobalParameters();
        analystGlobalParameters.setCost(12.12);
        analystConfiguration.setGlobalParameters(analystGlobalParameters);
        
        AnalystPartners analystPartners = new AnalystPartners();
        AnalystPartner analystPartner = new AnalystPartner();
        analystPartner.setAnlysisEnabled(true);
        analystPartner.setName("stockQuotesServicePartnerLink");
        AnalystParameters analystParameters = new AnalystParameters();
        analystParameters.setEffort(12.12);
        analystPartner.setParameters(analystParameters);
        AnalystService analystService = new AnalystService();
        analystService.setName("getStockQuote");
        analystService.setAnlysisEnabled(true);
        AnalystParameters serviceAnalystParameters = new AnalystParameters();
        serviceAnalystParameters.setReputation(12.12);
        analystService.setParameters(serviceAnalystParameters);
        AnalystServices analystServices = new AnalystServices();
        analystPartner.setServices(analystServices);
        analystPartner.getServices().getService().add(analystService);
        analystPartners.getPartner().add(analystPartner);
        analystConfiguration.setPartners(analystPartners);
        
        MonitorConfiguration monitorConfiguration = new MonitorConfiguration();
        MonitorPartners monitorPartners = new MonitorPartners();
        MonitorPartner monitorPartner = new MonitorPartner();
        monitorPartner.setName("stockQuotesServicePartnerLink");
        MonitorServices monitorServices = new MonitorServices();
        MonitorService monitorService1 = new MonitorService();
        monitorService1.setMep("in-only");
        monitorService1.setName("setStockQuote");
        MonitorService monitorService2 = new MonitorService();
        monitorService2.setMep("out-only");
        monitorService2.setName("isStockOpen");
        monitorPartner.setServices(monitorServices);
        monitorPartner.getServices().getService().add(monitorService1);
        monitorPartner.getServices().getService().add(monitorService2);
        monitorPartners.getPartner().add(monitorPartner);
        monitorConfiguration.setPartners(monitorPartners);
        
        QualitasConfiguration qualitasConfiguration = new QualitasConfiguration();
        qualitasConfiguration.setAnalyst(analystConfiguration);
        qualitasConfiguration.setMonitor(monitorConfiguration);
        
        OutputStream xml = new ByteArrayOutputStream();
        JAXB.marshal(qualitasConfiguration, xml);
        
        String document = xml.toString();
        
        Assert.assertNotNull(document);
        
        System.out.println(document);
    }

}
