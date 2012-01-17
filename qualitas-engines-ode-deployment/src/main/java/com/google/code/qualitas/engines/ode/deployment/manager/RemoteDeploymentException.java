package com.google.code.qualitas.engines.ode.deployment.manager;

/**
 * The Class RemoteDeploymentException.
 */
public class RemoteDeploymentException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 8620620514403305480L;

    /**
     * Instantiates a new remote deployment exception.
     */
    public RemoteDeploymentException() {
    }

    /**
     * Instantiates a new remote deployment exception.
     * 
     * @param message
     *            the message
     */
    public RemoteDeploymentException(String message) {
        super(message);
    }

    /**
     * Instantiates a new remote deployment exception.
     * 
     * @param cause
     *            the cause
     */
    public RemoteDeploymentException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new remote deployment exception.
     * 
     * @param message
     *            the message
     * @param cause
     *            the cause
     */
    public RemoteDeploymentException(String message, Throwable cause) {
        super(message, cause);
    }

}
