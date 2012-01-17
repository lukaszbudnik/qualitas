package com.google.code.qualitas.engines.api.invocation.webservice;

import java.io.Serializable;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.addressing.EndpointReference;

/**
 * The Class ServiceClientInvocationOrder.
 */
public class ServiceClientInvocationOrder implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -2323423404823458241L;

    /** The endpoint reference. */
    private EndpointReference endpointReference;

    /** The payload. */
    private OMElement payload;

    /** The header. */
    private OMElement header;

    /**
     * The Constructor.
     */
    public ServiceClientInvocationOrder() {
    }

    /**
     * The Constructor.
     * 
     * @param endpointReference
     *            the endpoint reference
     * @param payload
     *            the payload
     */
    public ServiceClientInvocationOrder(EndpointReference endpointReference, OMElement payload) {
        this.endpointReference = endpointReference;
        this.payload = payload;
    }

    /**
     * Gets the payload.
     * 
     * @return the payload
     */
    public OMElement getPayload() {
        return payload;
    }

    /**
     * Sets the payload.
     * 
     * @param payload
     *            the payload
     */
    public void setPayload(OMElement payload) {
        this.payload = payload;
    }

    /**
     * Gets the endpoint reference.
     * 
     * @return the endpoint reference
     */
    public EndpointReference getEndpointReference() {
        return endpointReference;
    }

    /**
     * Sets the endpoint reference.
     * 
     * @param endpointReference
     *            the endpoint reference
     */
    public void setEndpointReference(EndpointReference endpointReference) {
        this.endpointReference = endpointReference;
    }

    /**
     * Gets the header.
     * 
     * @return the header
     */
    public OMElement getHeader() {
        return header;
    }

    /**
     * Sets the header.
     * 
     * @param header
     *            the header
     */
    public void setHeader(OMElement header) {
        this.header = header;
    }

}
