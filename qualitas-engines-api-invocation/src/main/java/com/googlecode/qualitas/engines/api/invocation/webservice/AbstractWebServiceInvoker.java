package com.googlecode.qualitas.engines.api.invocation.webservice;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;

import com.googlecode.qualitas.engines.api.invocation.AbstractInvoker;
import com.googlecode.qualitas.engines.api.invocation.InvocationException;
import com.googlecode.qualitas.utils.dom.DOMUtils;
import com.googlecode.qualitas.utils.xslt.XSLTUtils;

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
     * com.googlecode.qualitas.engines.api.invocation.Invoker#invoke(java.lang
     * .String, java.lang.String, long, java.util.Map)
     */
    @Override
    public String invoke(String endpointAddress, String payload,
            final long qualitasProcessInstanceId, Map<String, Object> parameters)
            throws InvocationException {
        QName serviceName = (QName) parameters.get(SERVICE_NAME_QNAME);

        if (serviceName == null) {
            throw new IllegalArgumentException(SERVICE_NAME_QNAME + " parameter cannot be null");
        }

        QName portName = (QName) parameters.get(PORT_NAME_QNAME);

        if (portName == null) {
            throw new IllegalArgumentException(PORT_NAME_QNAME + " parameter cannot be null");
        }

        URL url = null;

        try {
            url = new URL(endpointAddress);
        } catch (MalformedURLException e) {
            String msg = "Passed endpoint address " + endpointAddress + " is not a valid URL";
            LOG.debug(msg, e);
            throw new InvocationException(msg, e);
        }

        Service service = Service.create(url, serviceName);
        service.setHandlerResolver(new HandlerResolver() {
            public List<Handler> getHandlerChain(PortInfo portInfo) {
                List<Handler> handlerList = new ArrayList<Handler>();
                handlerList
                        .add(new QualitasProcessInstanceIdSOAPHandler(qualitasProcessInstanceId));
                return handlerList;
            }
        });

        Dispatch<Source> dispatch = service.createDispatch(portName, Source.class,
                Service.Mode.PAYLOAD);

        Document document = null;
        try {
            document = DOMUtils.parse(payload, "UTF-8");
        } catch (Exception e) {
            String msg = "Could not parse payload";
            LOG.debug(msg, e);
            throw new InvocationException(msg, e);
        }

        DOMSource source = new DOMSource(document);

        Source result = dispatch.invoke(source);

        DOMResult domResult = new DOMResult();
        try {
            XSLTUtils.transformDocument(result, domResult);
        } catch (TransformerException e) {
            String msg = "Could not transform result to DOM";
            LOG.debug(msg, e);
            throw new InvocationException(msg, e);
        }

        String response = null;

        try {
            response = DOMUtils.toString(domResult.getNode());
        } catch (TransformerException e) {
            String msg = "Could not transform result to String";
            LOG.debug(msg, e);
            throw new InvocationException(msg, e);
        }

        return response;

    }

}
