package com.googlecode.qualitas.internal.installation.instrumentation;

import org.springframework.stereotype.Component;

import com.googlecode.qualitas.engines.api.configuration.ProcessStatus;
import com.googlecode.qualitas.internal.installation.core.FailureStatus;
import com.googlecode.qualitas.internal.installation.core.SuccessfulStatus;

@Component
@SuccessfulStatus(ProcessStatus.INSTRUMENTATION_OK)
@FailureStatus(ProcessStatus.INSTRUMENTATION_ERROR)
public class InstrumentorProcessor extends BaseInstrumentorProcessor {

}
