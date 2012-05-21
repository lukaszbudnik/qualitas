package com.googlecode.qualitas.internal.installation.core;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.googlecode.qualitas.engines.api.configuration.ProcessStatus;

@Target(value={java.lang.annotation.ElementType.TYPE})
@Retention(value=java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface SkippedStatus {

    ProcessStatus value();

}
