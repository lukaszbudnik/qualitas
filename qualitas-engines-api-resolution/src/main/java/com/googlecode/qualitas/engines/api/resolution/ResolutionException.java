package com.googlecode.qualitas.engines.api.resolution;

/**
 * The Class ResolutionException.
 */
public class ResolutionException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8214553945908287276L;

    /**
     * The Constructor.
     */
    public ResolutionException() {
        super();
    }

    /**
     * The Constructor.
     * 
     * @param message
     *            the message
     * @param cause
     *            the cause
     */
    public ResolutionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * The Constructor.
     * 
     * @param message
     *            the message
     */
    public ResolutionException(String message) {
        super(message);
    }

    /**
     * The Constructor.
     * 
     * @param cause
     *            the cause
     */
    public ResolutionException(Throwable cause) {
        super(cause);
    }

}
