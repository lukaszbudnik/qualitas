package com.google.code.qualitas.engines.api.resolution;

import java.util.List;

import javax.xml.namespace.QName;

import com.google.code.p.qualitas.engines.api.configuration.QualitasConfiguration;

/**
 * The Class Properties.
 */
public class Properties {

    /** The process q name. */
    private QName processQName;
    
    /** The services q names. */
    private List<QName> servicesQNames;
    
    /** The qualitas configuration. */
    private QualitasConfiguration qualitasConfiguration;

    /**
     * Gets the process q name.
     *
     * @return the process q name
     */
    public QName getProcessQName() {
        return processQName;
    }

    /**
     * Sets the process q name.
     *
     * @param processQName the process q name
     */
    public void setProcessQName(QName processQName) {
        this.processQName = processQName;
    }

    /**
     * Gets the services q names.
     *
     * @return the services q names
     */
    public List<QName> getServicesQNames() {
        return servicesQNames;
    }

    /**
     * Sets the services q names.
     *
     * @param servicesQNames the services q names
     */
    public void setServicesQNames(List<QName> servicesQNames) {
        this.servicesQNames = servicesQNames;
    }

    /**
     * Gets the qualitas configuration.
     *
     * @return the qualitas configuration
     */
    public QualitasConfiguration getQualitasConfiguration() {
        return qualitasConfiguration;
    }

    /**
     * Sets the qualitas configuration.
     *
     * @param qualitasConfiguration the qualitas configuration
     */
    public void setQualitasConfiguration(QualitasConfiguration qualitasConfiguration) {
        this.qualitasConfiguration = qualitasConfiguration;
    }

}
