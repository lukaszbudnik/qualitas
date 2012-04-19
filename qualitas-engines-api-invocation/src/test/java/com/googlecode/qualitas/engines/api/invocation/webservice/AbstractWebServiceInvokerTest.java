package com.googlecode.qualitas.engines.api.invocation.webservice;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import com.googlecode.qualitas.engines.api.invocation.InvocationException;
import com.googlecode.qualitas.engines.api.invocation.Invoker;

@Ignore
public class AbstractWebServiceInvokerTest {

    @Test
    public void testInvoke() throws InvocationException {

        AbstractWebServiceInvoker invoker = Mockito.mock(AbstractWebServiceInvoker.class);

        long qualitasProcessInstanceId = 123l;
        Map<String, Object> parameters = new HashMap<String, Object>();
        String payload = "<getStockQuote xmlns=\"http://examples.ws.nuntius.xh.org\"><company>Oracle</company></getStockQuote>";
        String endpointAddress = "http://localhost:9191/ode/processes/StockQuotesService?wsdl";

        QName serviceName = new QName("http://examples.ws.nuntius.xh.org", "StockQuotesService");
        parameters.put(Invoker.SERVICE_NAME_QNAME, serviceName);

        QName portName = new QName("http://examples.ws.nuntius.xh.org",
                "StockQuotesServiceSOAP12port_http");
        parameters.put(Invoker.PORT_NAME_QNAME, portName);

        Mockito.doCallRealMethod().when(invoker)
                .invoke(endpointAddress, payload, qualitasProcessInstanceId, parameters);

        String response = invoker.invoke(endpointAddress, payload, qualitasProcessInstanceId,
                parameters);

        Assert.assertNotNull(response);

    }

}
