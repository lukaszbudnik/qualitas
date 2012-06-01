package com.googlecode.qualitas.engines.api.resolution;

import java.util.List;

import javax.xml.namespace.QName;

import com.googlecode.qualitas.engines.api.configuration.QualitasConfiguration;

// TODO: Auto-generated Javadoc
/**
 * The Class Properties.
 */
public class Properties {

    /** The process q name. */
    private QName processQName;
    
    /** The service q name. */
    private QName serviceQName;
    
    /** The process epr. */
    private String processEPR;
    
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

    public QName getServiceQName() {
        return serviceQName;
    }

    public void setServiceQName(QName serviceQName) {
        this.serviceQName = serviceQName;
    }

    /**
     * Gets the process epr.
     *
     * @return the process epr
     */
    public String getProcessEPR() {
        return processEPR;
    }

    /**
     * Sets the process epr.
     *
     * @param processEPR the new process epr
     */
    public void setProcessEPR(String processEPR) {
        this.processEPR = processEPR;
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
