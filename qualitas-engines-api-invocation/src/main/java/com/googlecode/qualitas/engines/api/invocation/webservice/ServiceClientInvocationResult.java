package com.googlecode.qualitas.engines.api.invocation.webservice;

import java.io.Serializable;

import org.apache.axiom.om.OMElement;

/**
 * The Class ServiceClientInvocationResult.
 */
public class ServiceClientInvocationResult implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -2323423404823458241L;

    /** The result. */
    private OMElement result;

    /** The completed. */
    private boolean completed;

    /** The error. */
    private boolean error;

    /** The timeout. */
    private boolean timeout;

    /** The error message. */
    private String errorMessage;

    /**
     * The Constructor.
     */
    public ServiceClientInvocationResult() {
    }

    /**
     * Gets the result.
     * 
     * @return the result
     */
    public OMElement getResult() {
        return result;
    }

    /**
     * Sets the result.
     * 
     * @param result
     *            the result
     */
    public void setResult(OMElement result) {
        this.result = result;
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

    /**
     * Checks if is error.
     * 
     * @return true, if checks if is error
     */
    public boolean isError() {
        return error;
    }

    /**
     * Sets the error.
     * 
     * @param error
     *            the error
     */
    public void setError(boolean error) {
        this.error = error;
    }

    /**
     * Checks if is timeout.
     * 
     * @return true, if checks if is timeout
     */
    public boolean isTimeout() {
        return timeout;
    }

    /**
     * Sets the timeout.
     * 
     * @param timeout
     *            the timeout
     */
    public void setTimeout(boolean timeout) {
        this.timeout = timeout;
    }

    /**
     * Checks if is completed.
     * 
     * @return true, if checks if is completed
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Sets the completed.
     * 
     * @param completed
     *            the completed
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

}
