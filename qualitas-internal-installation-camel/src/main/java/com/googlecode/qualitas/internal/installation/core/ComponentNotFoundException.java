package com.googlecode.qualitas.internal.installation.core;

/**
 * The Class ComponentNotFound.
 */
public class ComponentNotFoundException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -3963304386395695107L;

    /**
     * The Constructor.
     */
    public ComponentNotFoundException() {
    }

    /**
     * The Constructor.
     * 
     * @param message
     *            the message
     */
    public ComponentNotFoundException(String message) {
        super(message);
    }

    /**
     * The Constructor.
     * 
     * @param cause
     *            the cause
     */
    public ComponentNotFoundException(Throwable cause) {
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
    public ComponentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
