package com.google.code.qualitas.engines.api.invocation;

import com.google.code.qualitas.engines.api.core.ProcessType;

/**
 * The Interface Executor.
 */
public interface Invoker {

    /**
     * Checks if is supported.
     * 
     * @param processType
     *            the process type
     * @return true, if checks if is supported
     */
    boolean isSupported(ProcessType processType);

    /**
     * Sets remote default service endpoint.
     * 
     * @param defaultServiceEndpoint
     *            the new default service endpoint
     */
    void setDefaultServiceEndpoint(String defaultServiceEndpoint);

    /**
     * Sets remote service endpoint.
     * 
     * @param serviceEndpoint
     *            the new service endpoint
     */
    void setServiceEndpoint(String serviceEndpoint);

    /**
     * Invoke.
     * 
     * @param endpointAddress
     *            the endpoint address
     * @param payload
     *            the payload
     * @param qualitasProcessInstanceId
     *            the qualitas process instance id
     * @throws InvocationException
     *             the invocation exception
     */
    void invoke(String endpointAddress, String payload, String qualitasProcessInstanceId)
            throws InvocationException;

}
