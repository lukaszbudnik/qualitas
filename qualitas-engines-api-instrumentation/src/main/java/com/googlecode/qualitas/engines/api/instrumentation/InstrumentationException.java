package com.googlecode.qualitas.engines.api.instrumentation;

/**
 * The Class InstrumentationException.
 */
public class InstrumentationException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 3466893327367327286L;

    /**
     * Instantiates a new instrumentation exception.
     */
    public InstrumentationException() {
    }

    /**
     * Instantiates a new instrumentation exception.
     *
     * @param message the message
     */
    public InstrumentationException(String message) {
        super(message);
    }

    /**
     * Instantiates a new instrumentation exception.
     *
     * @param cause the cause
     */
    public InstrumentationException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new instrumentation exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public InstrumentationException(String message, Throwable cause) {
        super(message, cause);
    }

}
