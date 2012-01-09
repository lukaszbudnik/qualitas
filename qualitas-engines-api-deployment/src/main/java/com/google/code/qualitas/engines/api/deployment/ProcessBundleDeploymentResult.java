package com.google.code.qualitas.engines.api.deployment;

/**
 * The Class ProcessBundleDeploymentResult.
 */
public class ProcessBundleDeploymentResult {

    /** The success. */
    private boolean success;

    /** The error message. */
    private String errorMessage;

    /**
     * Checks if is success.
     * 
     * @return true, if is success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the success.
     * 
     * @param success
     *            the new success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Gets the error message.
     * 
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the error message.
     * 
     * @param errorMessage
     *            the new error message
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
