package com.google.code.qualitas.engines.api.instrumentation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The InstrumentationPhase Annotation.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface InstrumentationPhase {
    
    /**
     * Instrumentation phase type.
     */
    InstrumentationPhaseType value() default InstrumentationPhaseType.Instrumentation; 
}
