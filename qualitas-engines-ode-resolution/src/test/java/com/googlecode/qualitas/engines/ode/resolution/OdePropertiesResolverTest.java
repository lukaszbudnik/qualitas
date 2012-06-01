package com.googlecode.qualitas.engines.ode.resolution;

import java.util.Arrays;

import javax.xml.namespace.QName;

import junit.framework.Assert;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.googlecode.qualitas.engines.api.configuration.ProcessType;
import com.googlecode.qualitas.engines.api.configuration.QualitasConfiguration;
import com.googlecode.qualitas.engines.api.core.Entry;
import com.googlecode.qualitas.engines.api.resolution.Properties;
import com.googlecode.qualitas.engines.api.resolution.ResolutionException;
import com.googlecode.qualitas.engines.ode.core.OdeBundle;

@RunWith(MockitoJUnitRunner.class)
public class OdePropertiesResolverTest {

    @Mock
    private OdePropertiesResolver odePropertiesResolver;

    @Mock
    private OdeBundle bundle;

    @Before
    public void setUp() throws Exception {
        byte[] deployContents = IOUtils.toByteArray(this.getClass().getResourceAsStream(
                "/deploy.xml"));
        byte[] wsdlContents = IOUtils
                .toByteArray(this.getClass().getResourceAsStream("/test.wsdl"));
        Entry wsdl = new Entry("XhGPWWhile.wsdl", wsdlContents);
        Mockito.when(bundle.getProcessType()).thenReturn(ProcessType.WS_BPEL_2_0_APACHE_ODE);
        Mockito.when(bundle.getOdeDescriptor()).thenReturn(new Entry("deploy.xml", deployContents));
        Mockito.when(bundle.getEntry("XhGPWWhile.wsdl")).thenReturn(wsdl);
        Mockito.when(bundle.getMainProcessQName()).thenReturn(
                new QName("http://examples.bpel.nuntius.xh.org/xhGPWWhile", "XhGPWWhile"));

        Mockito.when(odePropertiesResolver.getQualitasConfiguration(bundle)).thenReturn(
                new QualitasConfiguration());
        Mockito.when(bundle.getEntries("*.wsdl")).thenReturn(Arrays.asList(wsdl));

        Mockito.when(odePropertiesResolver.resolve(bundle)).thenCallRealMethod();
        Mockito.when(bundle.getWSDL("XhGPWWhile")).thenCallRealMethod();
    }

    @Test
    public void testResolve() throws ResolutionException {
        Properties properties = odePropertiesResolver.resolve(bundle);

        String expectedEPR = "http://localhost:8181/ode/processes/xhGPWWhile";
        Assert.assertEquals(expectedEPR, properties.getProcessEPR());
    }

}
