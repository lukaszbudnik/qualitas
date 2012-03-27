package com.googlecode.qualitas.engines.api.component;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.googlecode.qualitas.engines.api.configuration.QualitasConfiguration;
import com.googlecode.qualitas.engines.api.core.Bundle;
import com.googlecode.qualitas.engines.api.core.Entry;

public class AbstractComponentTest {

    private AbstractComponent abstractComponent;
    private Bundle bundle;

    @Before
    public void setUp() throws Exception {
        bundle = Mockito.mock(Bundle.class);
        byte[] qualitasConfiguration = IOUtils.toByteArray(this.getClass().getResourceAsStream(
                "/qualitas.xml"));
        Mockito.when(bundle.getQualitasConfiguration()).thenReturn(
                new Entry(Bundle.QUALITAS_CONFIGURATION_NAME, qualitasConfiguration));

        abstractComponent = Mockito.mock(AbstractComponent.class);
        Mockito.when(abstractComponent.getQualitasConfiguration(bundle)).thenCallRealMethod();
    }

    @Test
    public void testGetQualitasConfiguration() throws IOException {
        QualitasConfiguration qualitasConfiguration = abstractComponent
                .getQualitasConfiguration(bundle);

        Assert.assertNotNull(qualitasConfiguration);
    }

}
