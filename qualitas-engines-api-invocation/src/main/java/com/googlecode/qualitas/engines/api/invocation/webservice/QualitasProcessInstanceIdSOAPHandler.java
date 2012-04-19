package com.googlecode.qualitas.engines.api.invocation.webservice;

import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class QualitasProcessInstanceIdSOAPHandler implements SOAPHandler<SOAPMessageContext> {
    
    private static final Log LOG = LogFactory.getLog(QualitasProcessInstanceIdSOAPHandler.class);

    private long qualitasProcessInstanceId;

    public QualitasProcessInstanceIdSOAPHandler(long qualitasProcessInstanceId) {
        this.qualitasProcessInstanceId = qualitasProcessInstanceId;
    }

    @Override
    public void close(MessageContext context) {

    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return false;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        
        Boolean outboundProperty = 
                (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        
        if (outboundProperty.booleanValue()) {
            SOAPMessage message = context.getMessage();
            try {
                SOAPEnvelope envelope = message
                        .getSOAPPart().getEnvelope();
                SOAPFactory factory = SOAPFactory.newInstance();
                String prefix = "m";
                String uri = "http://qualitas.googlecode.com/internal/monitor/webservice";
                SOAPElement qualitasMonitorHeaderElement = 
                        factory.createElement("qualitasProcessInstanceId",prefix,uri);
                qualitasMonitorHeaderElement.addTextNode(Long.toString(qualitasProcessInstanceId));
                
                SOAPHeader header = envelope.getHeader();
                
                if (header == null) {
                    header = envelope.addHeader();
                }
                
                header.addChildElement(qualitasMonitorHeaderElement);
            } catch (Exception e) {
                LOG.debug("Could not append Qualitas monitor header element to SOAP message", e);
                return false;
            }
        }
        
        return true;
    }

    @Override
    public Set<QName> getHeaders() {
        return null;
    }

}
