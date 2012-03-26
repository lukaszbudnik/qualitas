package com.googlecode.qualitas.internal.integration.api;

/**
 * The Class InstallationException.
 */
public class InstallationException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 4464384036901881761L;

    /**
     * The Constructor.
     */
    public InstallationException() {
    }

    /**
     * The Constructor.
     *
     * @param message the message
     */
    public InstallationException(String message) {
        super(message);
    }

    /**
     * The Constructor.
     *
     * @param cause the cause
     */
    public InstallationException(Throwable cause) {
        super(cause);
    }

    /**
     * The Constructor.
     *
     * @param message the message
     * @param cause the cause
     */
    public InstallationException(String message, Throwable cause) {
        super(message, cause);
    }

}
