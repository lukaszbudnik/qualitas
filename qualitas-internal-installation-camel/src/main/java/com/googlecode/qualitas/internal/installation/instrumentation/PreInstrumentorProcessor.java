package com.googlecode.qualitas.internal.installation.instrumentation;

import org.springframework.stereotype.Component;

import com.googlecode.qualitas.engines.api.configuration.ProcessStatus;
import com.googlecode.qualitas.internal.installation.core.FailureStatus;
import com.googlecode.qualitas.internal.installation.core.SuccessfulStatus;

@Component
@SuccessfulStatus(ProcessStatus.PRE_INSTRUMENTATION_OK)
@FailureStatus(ProcessStatus.PRE_INSTRUMENTATION_ERROR)
public class PreInstrumentorProcessor extends BaseInstrumentorProcessor {

}
