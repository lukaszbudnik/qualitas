package com.google.code.qualitas.internal.installation.instrumentation;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import com.google.code.qualitas.engines.api.core.Bundle;
import com.google.code.qualitas.engines.api.instrumentation.Instrumentor;
import com.google.code.qualitas.internal.installation.core.AbstractProcessor;

/**
 * The Class InstrumentorProcessor.
 */
@Component
public class InstrumentorProcessor extends AbstractProcessor {

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
     */
    @Override
    public void process(Exchange exchange) throws Exception {

        Map<String, Object> headers = exchange.getIn().getHeaders();

        String instrumentationPhase = (String) headers.get("qualitasinstrumentationphase");

        @SuppressWarnings("unchecked")
        Class<Annotation> annotationType = (Class<Annotation>) Class.forName(Instrumentor.class
                .getPackage().getName() + "." + instrumentationPhase);

        Bundle bundle = exchange.getIn().getBody(Bundle.class);

        Instrumentor instrumentor = findQualitasComponent(Instrumentor.class,
                bundle.getProcessType(), annotationType);

        instrumentor.instrument(bundle);
    }
}
