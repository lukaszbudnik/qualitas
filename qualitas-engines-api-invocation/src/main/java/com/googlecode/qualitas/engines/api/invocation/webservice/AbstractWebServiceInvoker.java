package com.googlecode.qualitas.engines.api.invocation.webservice;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.googlecode.qualitas.engines.api.invocation.AbstractInvoker;
import com.googlecode.qualitas.engines.api.invocation.InvocationException;

/**
 * The Class AbstractWebServiceExecutor.
 */
public abstract class AbstractWebServiceInvoker extends AbstractInvoker {

    /** The Constant log. */
    private static final Log LOG = LogFactory.getLog(AbstractWebServiceInvoker.class);

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.code.qualitas.engines.api.invocation.Invoker#invoke(java.lang
     * .String, java.lang.String)
     */
    @Override
    public void invoke(String endpointAddress, String payload, String qualitasProcessInstanceId)
            throws InvocationException {
        AsynchronousAxis2ServiceClient client = new AsynchronousAxis2ServiceClient();

        ServiceClientInvocationOrder order = new ServiceClientInvocationOrder();

        OMElement payloadElement = null;
        try {
            payloadElement = AXIOMUtil.stringToOM(payload);
        } catch (XMLStreamException e) {
            String msg = "Could not parse payload for endpoint " + endpointAddress;
            LOG.error(msg, e);
            throw new InvocationException(msg, e);
        }

        QName qname = new QName("http://code.google.com/p/qualitas/execution/monitor",
                "QualitasProcessInstanceId");
        OMElement headerElement = OMAbstractFactory.getOMFactory().createOMElement(qname);
        headerElement.setText(qualitasProcessInstanceId);

        EndpointReference endpointReference = new EndpointReference(endpointAddress);

        order.setPayload(payloadElement);
        order.setHeader(headerElement);
        order.setEndpointReference(endpointReference);

        client.call(order);
    }

}
