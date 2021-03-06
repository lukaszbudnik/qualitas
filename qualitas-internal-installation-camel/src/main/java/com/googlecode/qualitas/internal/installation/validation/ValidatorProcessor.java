package com.googlecode.qualitas.internal.installation.validation;

import org.springframework.stereotype.Component;

import com.googlecode.qualitas.engines.api.configuration.ProcessStatus;
import com.googlecode.qualitas.internal.installation.core.FailureStatus;
import com.googlecode.qualitas.internal.installation.core.SuccessfulStatus;

@Component
@SuccessfulStatus(ProcessStatus.VALIDATION_OK)
@FailureStatus(ProcessStatus.VALIDATION_ERROR)
public class ValidatorProcessor extends BaseValidatorProcessor {

}
