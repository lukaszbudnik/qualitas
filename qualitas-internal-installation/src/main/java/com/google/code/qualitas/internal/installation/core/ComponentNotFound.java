package com.google.code.qualitas.internal.installation.core;

/**
 * The Class ComponentNotFound.
 */
public class ComponentNotFound extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -3963304386395695107L;

    /**
     * The Constructor.
     */
    public ComponentNotFound() {
    }

    /**
     * The Constructor.
     * 
     * @param message
     *            the message
     */
    public ComponentNotFound(String message) {
        super(message);
    }

    /**
     * The Constructor.
     * 
     * @param cause
     *            the cause
     */
    public ComponentNotFound(Throwable cause) {
        super(cause);
    }

    /**
     * The Constructor.
     * 
     * @param message
     *            the message
     * @param cause
     *            the cause
     */
    public ComponentNotFound(String message, Throwable cause) {
        super(message, cause);
    }

}
