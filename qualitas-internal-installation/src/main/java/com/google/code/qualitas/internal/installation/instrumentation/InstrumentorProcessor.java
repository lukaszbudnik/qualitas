package com.google.code.qualitas.internal.installation.instrumentation;

import org.apache.camel.Exchange;

import com.google.code.qualitas.engines.api.core.Bundle;
import com.google.code.qualitas.engines.api.instrumentation.Instrumentor;
import com.google.code.qualitas.internal.installation.core.AbstractProcessor;

/**
 * The Class InstrumentorProcessor.
 */
public class InstrumentorProcessor extends AbstractProcessor {

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
     */
    @Override
    public void process(Exchange exchange) throws Exception {
        Bundle bundle = exchange.getIn().getBody(Bundle.class);

        Instrumentor instrumentor = findQualitasComponent(Instrumentor.class,
                bundle.getProcessType());

        instrumentor.instrument(bundle);

    }

}
