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
     * @param bundle
     *            the bundle
     * @throws InstrumentationException
     *             the instrumentation exception
     */
    void instrument(Bundle bundle) throws InstrumentationException;

}
