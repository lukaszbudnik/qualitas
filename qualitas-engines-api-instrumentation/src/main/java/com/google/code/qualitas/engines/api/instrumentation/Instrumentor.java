package com.google.code.qualitas.engines.api.instrumentation;

import com.google.code.qualitas.engines.api.core.ProcessBundle;

/**
 * The Interface ProcessBundleInstrumentor.
 *
 * @param <T> the generic type
 */
public interface Instrumentor<T extends ProcessBundle> {

    /**
     * Checks if is supported.
     *
     * @param processBundle the process bundle
     * @return true, if is supported
     */
    boolean isSupported(ProcessBundle processBundle);
    
    /**
     * Instrument.
     *
     * @param processBundle the process bundle
     * @throws InstrumentationException the instrumentation exception
     */
    void instrument(T processBundle) throws InstrumentationException;
    
}
