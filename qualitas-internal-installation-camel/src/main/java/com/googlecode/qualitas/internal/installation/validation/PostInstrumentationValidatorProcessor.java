package com.googlecode.qualitas.internal.installation.validation;

import org.springframework.stereotype.Component;

import com.googlecode.qualitas.engines.api.configuration.ProcessStatus;
import com.googlecode.qualitas.internal.installation.core.FailureStatus;
import com.googlecode.qualitas.internal.installation.core.SuccessfulStatus;

@Component
@SuccessfulStatus(ProcessStatus.POST_INSTRUMENTATION_VALIDATION_OK)
@FailureStatus(ProcessStatus.POST_INSTRUMENTATION_VALIDATION_ERROR)
public class PostInstrumentationValidatorProcessor extends BaseValidatorProcessor {

}
