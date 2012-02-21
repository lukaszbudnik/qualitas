package com.google.code.qualitas.internal.installation.factory;

import java.util.Map;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import com.google.code.qualitas.engines.api.core.Bundle;
import com.google.code.qualitas.engines.api.core.ProcessType;
import com.google.code.qualitas.engines.api.factory.BundleFactory;
import com.google.code.qualitas.internal.installation.core.AbstractProcessor;

/**
 * The Class DeployerProcessor.
 */
@Component
public class BundleFactoryProcessor extends AbstractProcessor {

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
     */
    @Override
    public void process(Exchange exchange) throws Exception {
        
        Map<String, Object> headers = exchange.getIn().getHeaders();
        String qualitasProcessTypeString = (String) headers.get("qualitasprocesstype");
        ProcessType processType = ProcessType.valueOf(qualitasProcessTypeString);
        
        byte[] contents = exchange.getIn().getBody(byte[].class);
        
        BundleFactory factory = findQualitasComponent(BundleFactory.class, processType);

        Bundle bundle = factory.createProcessBundle(contents);
        
        exchange.getOut().setBody(bundle);
    }

}
