package com.googlecode.qualitas.engines.api.core;

import java.io.IOException;

import javax.xml.namespace.QName;

/**
 * The Class AbstractProcessBundle.
 */
public abstract class AbstractBundle implements Bundle {

    /** The main process name. */
    private QName mainProcessQName;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.code.qualitas.engines.api.core.Bundle#setMainProcessQName(
     * javax.xml.namespace.QName)
     */
    @Override
    public void setMainProcessQName(QName mainProcessQName) {
        this.mainProcessQName = mainProcessQName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.code.qualitas.engines.api.core.Bundle#getMainProcessQName()
     */
    @Override
    public QName getMainProcessQName() {
        return mainProcessQName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.code.qualitas.engines.api.core.ProcessBundle#getQualitasDescriptor
     * ()
     */
    @Override
    public Entry getQualitasConfiguration() throws IOException {
        return getEntry(QUALITAS_CONFIGURATION_NAME);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.code.qualitas.engines.api.core.ProcessBundle#isInstrumentable
     * ()
     */
    @Override
    public boolean isInstrumentable() throws IOException {
        return getQualitasConfiguration() != null;
    }

}
