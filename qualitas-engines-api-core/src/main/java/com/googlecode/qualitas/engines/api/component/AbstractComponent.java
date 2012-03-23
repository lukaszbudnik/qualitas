package com.googlecode.qualitas.engines.api.component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXB;

import com.googlecode.qualitas.engines.api.configuration.QualitasConfiguration;
import com.googlecode.qualitas.engines.api.core.Bundle;

/**
 * The Class AbstractComponent.
 */
public abstract class AbstractComponent implements Component {

    /**
     * Gets the qualitas configuration.
     * 
     * @param bundle
     *            the bundle
     * @return the qualitas configuration
     * @throws IOException
     *             the IO exception
     */
    public QualitasConfiguration getQualitasConfiguration(Bundle bundle) throws IOException {
        InputStream xml = new ByteArrayInputStream(bundle.getQualitasConfiguration().getContent());
        QualitasConfiguration qualitasConfiguration = JAXB.unmarshal(xml,
                QualitasConfiguration.class);

        return qualitasConfiguration;
    }

}
