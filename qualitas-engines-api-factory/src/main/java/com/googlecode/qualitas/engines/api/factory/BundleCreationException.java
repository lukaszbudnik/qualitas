package com.googlecode.qualitas.engines.api.factory;

/**
 * The Class BundleCreationException.
 */
public class BundleCreationException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 4069212295054260425L;

    /**
     * The Constructor.
     */
    public BundleCreationException() {
        super();
    }

    /**
     * The Constructor.
     *
     * @param message the message
     * @param cause the cause
     */
    public BundleCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * The Constructor.
     *
     * @param message the message
     */
    public BundleCreationException(String message) {
        super(message);
    }

    /**
     * The Constructor.
     *
     * @param cause the cause
     */
    public BundleCreationException(Throwable cause) {
        super(cause);
    }

}
