package com.googlecode.qualitas.engines.api.invocation;

/**
 * The Class AbstractInvoker.
 */
public abstract class AbstractInvoker implements Invoker {

    /** The service endpoint. */
    private String serviceEndpoint;

    /** The default service endpoint. */
    private String defaultServiceEndpoint;

    /**
     * Gets the service endpoint.
     * 
     * @return the service endpoint
     */
    public String getServiceEndpoint() {
        return serviceEndpoint;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.code.qualitas.engines.api.invocation.Invoker#setServiceEndpoint
     * (java.lang.String)
     */
    @Override
    public void setServiceEndpoint(String serviceEndpoint) {
        this.serviceEndpoint = serviceEndpoint;
    }

    /**
     * Gets the default service endpoint.
     * 
     * @return the default service endpoint
     */
    public String getDefaultServiceEndpoint() {
        return defaultServiceEndpoint;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.google.code.qualitas.engines.api.invocation.Invoker#
     * setDefaultServiceEndpoint(java.lang.String)
     */
    @Override
    public void setDefaultServiceEndpoint(String defaultServiceEndpoint) {
        this.defaultServiceEndpoint = defaultServiceEndpoint;
    }

}
