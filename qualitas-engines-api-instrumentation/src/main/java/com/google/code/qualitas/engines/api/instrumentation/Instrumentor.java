package com.google.code.qualitas.engines.api.instrumentation;

import com.google.code.qualitas.engines.api.component.Component;
import com.google.code.qualitas.engines.api.core.Bundle;

/**
 * The Interface ProcessBundleInstrumentor.
 *
 */
public interface Instrumentor extends Component {

    /**
     * Instrument.
     *
     * @param processBundle the process bundle
     * @throws InstrumentationException the instrumentation exception
     */
    void instrument(Bundle processBundle) throws InstrumentationException;
    
}
