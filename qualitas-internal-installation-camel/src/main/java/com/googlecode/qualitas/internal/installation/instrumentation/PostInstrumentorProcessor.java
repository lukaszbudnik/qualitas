package com.googlecode.qualitas.internal.installation.instrumentation;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import com.googlecode.qualitas.engines.api.configuration.ProcessStatus;
import com.googlecode.qualitas.internal.installation.core.ComponentNotFoundException;
import com.googlecode.qualitas.internal.installation.core.FailureStatus;
import com.googlecode.qualitas.internal.installation.core.PhaseSkippedException;
import com.googlecode.qualitas.internal.installation.core.SkippedStatus;
import com.googlecode.qualitas.internal.installation.core.SuccessfulStatus;

@Component
@SuccessfulStatus(ProcessStatus.POST_INSTRUMENTATION_OK)
@FailureStatus(ProcessStatus.POST_INSTRUMENTATION_ERROR)
@SkippedStatus(ProcessStatus.POST_INSTRUMENTATION_SKIPPED)
public class PostInstrumentorProcessor extends BaseInstrumentorProcessor {

    @Override
    public void process(Exchange exchange) throws Exception {
        try {
            super.process(exchange);
        } catch (ComponentNotFoundException e) {
            throw new PhaseSkippedException(e);
        }
    }
    
}
