package com.google.code.qualitas.engines.api.core;

import java.io.IOException;

/**
 * The Class AbstractProcessBundle.
 */
public abstract class AbstractProcessBundle implements ProcessBundle {

    /** The main process name. */
    private String mainProcessName;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.code.qualitas.engines.api.core.ProcessBundle#setMainProcessName
     * (java.lang.String)
     */
    @Override
    public void setMainProcessName(String mainProcessName) {
        this.mainProcessName = mainProcessName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.code.qualitas.engines.api.core.ProcessBundle#getMainProcessName
     * ()
     */
    @Override
    public String getMainProcessName() {
        return mainProcessName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.code.qualitas.engines.api.core.ProcessBundle#getQualitasDescriptor
     * ()
     */
    @Override
    public Entry getQualitasDescriptor() throws IOException {
        return getEntry(QUALITAS_DESCRIPTOR_NAME);
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
        return getQualitasDescriptor() != null;
    }

}
