package com.googlecode.qualitas.engines.api.invocation;

/**
 * The Class ExecutionException.
 */
public class InvocationException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -2212539527358907556L;

    /**
     * The Constructor.
     */
    public InvocationException() {
    }

    /**
     * The Constructor.
     * 
     * @param message
     *            the message
     */
    public InvocationException(String message) {
        super(message);
    }

    /**
     * The Constructor.
     * 
     * @param cause
     *            the cause
     */
    public InvocationException(Throwable cause) {
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
    public InvocationException(String message, Throwable cause) {
        super(message, cause);
    }

}
