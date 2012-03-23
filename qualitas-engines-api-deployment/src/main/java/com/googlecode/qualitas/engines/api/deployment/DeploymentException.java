package com.googlecode.qualitas.engines.api.deployment;

/**
 * The Class DeploymentException.
 */
public class DeploymentException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 8026208902563795214L;

    /**
     * Instantiates a new deployment exception.
     */
    public DeploymentException() {
    }

    /**
     * Instantiates a new deployment exception.
     *
     * @param message the message
     */
    public DeploymentException(String message) {
        super(message);
    }

    /**
     * Instantiates a new deployment exception.
     *
     * @param cause the cause
     */
    public DeploymentException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new deployment exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public DeploymentException(String message, Throwable cause) {
        super(message, cause);
    }

}
