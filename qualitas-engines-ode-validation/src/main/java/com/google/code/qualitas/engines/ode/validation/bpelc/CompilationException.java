package com.google.code.qualitas.engines.ode.validation.bpelc;

/**
 * The Class CompilationException.
 */
public class CompilationException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -4659402652651122817L;

    /**
     * The Constructor.
     */
    public CompilationException() {
    }

    /**
     * The Constructor.
     *
     * @param message the message
     */
    public CompilationException(String message) {
        super(message);
    }

    /**
     * The Constructor.
     *
     * @param cause the cause
     */
    public CompilationException(Throwable cause) {
        super(cause);
    }

    /**
     * The Constructor.
     *
     * @param message the message
     * @param cause the cause
     */
    public CompilationException(String message, Throwable cause) {
        super(message, cause);
    }

}
