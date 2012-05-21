package com.googlecode.qualitas.internal.installation.instrumentation;

import java.lang.annotation.Annotation;

import org.apache.camel.Exchange;
import org.apache.camel.Message;

import com.googlecode.qualitas.engines.api.core.Bundle;
import com.googlecode.qualitas.engines.api.instrumentation.Instrumentor;
import com.googlecode.qualitas.internal.installation.core.AbstractProcessor;
import com.googlecode.qualitas.internal.installation.core.QualitasHeadersNames;

/**
 * The Class BaseInstrumentorProcessor.
 */
public class BaseInstrumentorProcessor extends AbstractProcessor {

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
     */
    @Override
    public void process(Exchange exchange) throws Exception {
        Message in = exchange.getIn();

        @SuppressWarnings("unchecked")
        Class<Annotation> annotationType = (Class<Annotation>) in
                .getHeader(QualitasHeadersNames.QUALITAS_INSTRUMENTATION_PHASE);

        Bundle bundle = exchange.getIn().getBody(Bundle.class);

        Instrumentor instrumentor = findQualitasComponent(Instrumentor.class,
                bundle.getProcessType(), annotationType);

        instrumentor.instrument(bundle);

        Message out = exchange.getOut();
        out.setBody(bundle);
    }
}
