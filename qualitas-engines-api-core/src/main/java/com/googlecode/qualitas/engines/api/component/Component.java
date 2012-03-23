package com.googlecode.qualitas.engines.api.component;

import com.googlecode.qualitas.engines.api.configuration.ProcessType;

/**
 * The Interface Component.
 */
public interface Component {

    /**
     * Checks if is supported.
     * 
     * @param processType
     *            the process type
     * @return true, if checks if is supported
     */
    boolean isSupported(ProcessType processType);

}
