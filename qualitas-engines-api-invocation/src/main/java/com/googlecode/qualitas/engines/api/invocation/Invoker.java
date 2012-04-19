package com.googlecode.qualitas.engines.api.invocation;

import java.util.Map;

import com.googlecode.qualitas.engines.api.component.Component;

/**
 * The Interface Executor.
 */
public interface Invoker extends Component {

    /** The service name qname key used in parameters map. */
    String SERVICE_NAME_QNAME = "SERVICE_NAME_QNAME";

    /** The port name qname key used in parameters map. */
    String PORT_NAME_QNAME = "PORT_NAME_QNAME";

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
     * @param parameters
     *            the parameters
     * @return the string
     * @throws InvocationException
     *             the invocation exception
     */
    String invoke(String endpointAddress, String payload, long qualitasProcessInstanceId,
            Map<String, Object> parameters) throws InvocationException;

}
