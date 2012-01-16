package com.google.qualitas.engines.api.validation;

/**
 * The Class ValidationException.
 */
public class ValidationException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -4899161992550881629L;

    /**
     * Instantiates a new validation exception.
     */
    public ValidationException() {
    }

    /**
     * Instantiates a new validation exception.
     * 
     * @param message
     *            the message
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * Instantiates a new validation exception.
     * 
     * @param cause
     *            the cause
     */
    public ValidationException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new validation exception.
     * 
     * @param message
     *            the message
     * @param cause
     *            the cause
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

}
