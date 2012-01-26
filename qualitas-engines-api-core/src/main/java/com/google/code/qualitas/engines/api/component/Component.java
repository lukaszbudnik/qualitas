package com.google.code.qualitas.engines.api.component;

import com.google.code.qualitas.engines.api.core.ProcessType;

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
