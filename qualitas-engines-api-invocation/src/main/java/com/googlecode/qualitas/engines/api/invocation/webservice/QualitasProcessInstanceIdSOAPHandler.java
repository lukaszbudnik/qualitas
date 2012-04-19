package com.googlecode.qualitas.engines.api.invocation.webservice;

import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import com.googlecode.qualitas.utils.dom.DOMUtils;

public class QualitasProcessInstanceIdSOAPHandler implements SOAPHandler<SOAPMessageContext> {

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

                String string = DOMUtils.toString(envelope);
                System.out.println(string);
                
            } catch (Exception e) {
                
            }
        }
        
        return true;
    }

    @Override
    public Set<QName> getHeaders() {
        return null;
    }

}
