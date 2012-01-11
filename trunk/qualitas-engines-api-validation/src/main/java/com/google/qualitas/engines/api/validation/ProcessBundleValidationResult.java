package com.google.qualitas.engines.api.validation;

/**
 * The Class ProcessBundleValidationResult.
 */
public class ProcessBundleValidationResult {

    /** The success. */
    private boolean success;

    /** The error message. */
    private String errorMessage;

    /**
     * Checks if is success.
     * 
     * @return true, if checks if is success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the success.
     * 
     * @param success
     *            the success
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
     *            the error message
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
