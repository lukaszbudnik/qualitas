package com.google.code.p.qualitas.engines.api.configuration;

import junit.framework.Assert;

import org.junit.Test;

import com.google.code.p.qualitas.engines.api.configuration.QualitasConfigurationHelper.ParameterType;

public class QualitasConfigurationHelperTest {

    @Test
    public void testGetWeight() {

        AnalystConfiguration analystConfiguration = new AnalystConfiguration();
        analystConfiguration.setAnalysisEnabled(true);

        AnalystGlobalParameters analystGlobalParameters = new AnalystGlobalParameters();
        analystGlobalParameters.setCost(1.1);
        analystGlobalParameters.setCostWeight(10);
        analystConfiguration.setGlobalParameters(analystGlobalParameters);

        AnalystPartners analystPartners = new AnalystPartners();
        AnalystPartner analystPartner = new AnalystPartner();
        analystPartner.setAnlysisEnabled(true);
        analystPartner.setName("stockQuotesServicePartnerLink");
        AnalystParameters analystParameters = new AnalystParameters();
        analystParameters.setEffort(2.2);
        analystParameters.setEffortWeight(20d);
        analystPartner.setParameters(analystParameters);
        AnalystService analystService = new AnalystService();
        analystService.setName("getStockQuote");
        analystService.setAnlysisEnabled(true);
        AnalystParameters serviceAnalystParameters = new AnalystParameters();
        serviceAnalystParameters.setReputation(3.3);
        serviceAnalystParameters.setReputationWeight(30d);
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
        qualitasConfiguration.setAnalystConfiguration(analystConfiguration);
        qualitasConfiguration.setMonitorConfiguration(monitorConfiguration);

        Double costWeight = QualitasConfigurationHelper.getAnalystParameter(
                qualitasConfiguration, "stockQuotesServicePartnerLink",
                "getStockQuote", "cost", ParameterType.WEIGHT);
        
        Assert.assertNotNull(costWeight);
        Assert.assertEquals(10d, costWeight);

        Double effortWeight = QualitasConfigurationHelper.getAnalystParameter(
                qualitasConfiguration, "stockQuotesServicePartnerLink",
                "getStockQuote", "effort", ParameterType.WEIGHT);
        
        Assert.assertNotNull(effortWeight);
        Assert.assertEquals(20d, effortWeight);

        Double reputationWeight = QualitasConfigurationHelper.getAnalystParameter(
                qualitasConfiguration, "stockQuotesServicePartnerLink",
                "getStockQuote", "reputation", ParameterType.WEIGHT);

        Assert.assertNotNull(reputationWeight);
        Assert.assertEquals(30d, reputationWeight);
    }

}
