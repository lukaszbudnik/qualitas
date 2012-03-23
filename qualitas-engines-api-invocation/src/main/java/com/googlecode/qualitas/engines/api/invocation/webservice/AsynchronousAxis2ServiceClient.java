package com.googlecode.qualitas.engines.api.invocation.webservice;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Class AsynchronousAxis2ServiceClient.
 */
public class AsynchronousAxis2ServiceClient {

    // 10 minutes = 6000 seconds
    /** The Constant TIMEOUT. */
    private static final int TIMEOUT = 600 * 1000;

    /** The Constant log. */
    private static final Log LOG = LogFactory.getLog(AsynchronousAxis2ServiceClient.class);

    /**
     * Call.
     * 
     * @param order
     *            the order
     * @return the service client invocation result
     */
    public ServiceClientInvocationResult call(ServiceClientInvocationOrder order) {
        ServiceClientInvocationResult result = new ServiceClientInvocationResult();

        // Create an instance of service client
        ServiceClient serviceClient = null;

        try {
            serviceClient = new ServiceClient();
        } catch (AxisFault e) {
            LOG.debug("Caught AxisFault while creating ServiceClient object", e);
            result.setError(true);
            result.setErrorMessage(e.getMessage());
            return result;
        }

        // Create an options object to pass parameters to service client.
        Options options = new Options();
        // Set the location of the Web service
        options.setTo(order.getEndpointReference());
        options.setTimeOutInMilliSeconds(TIMEOUT);

        serviceClient.setOptions(options);

        // Create async callback
        AsynchronousAxis2Callback callback = new AsynchronousAxis2Callback();

        OMElement payload = order.getPayload();
        OMElement header = order.getHeader();

        LOG.debug("invoking " + order.getEndpointReference() + " with payload ==> " + payload
                + " and headers ==> " + header);

        // add SOAP Header
        serviceClient.addHeader(header);
        // do an async invocation
        try {
            serviceClient.sendReceiveNonBlocking(payload, callback);
        } catch (AxisFault e) {
            LOG.debug("Caught AxisFault while invoking WS-BPEL", e);
            result.setError(true);
            result.setErrorMessage(e.getMessage());
            return result;
        }

        synchronized (callback) {
            try {
                callback.wait();
            } catch (InterruptedException e) {
                LOG.error("Caught interrupted exception while waiting for callback", e);
            }
        }

        LOG.debug(order.getEndpointReference() + " returned response ==> " + callback.getResult());

        // copy values
        result.setResult(callback.getResult());
        result.setError(callback.isError());
        result.setTimeout(callback.isTimeout());
        result.setCompleted(callback.isCompleted());
        result.setErrorMessage(callback.getErrorMessage());

        return result;
    }

}
