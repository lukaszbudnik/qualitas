package com.google.code.qualitas.internal.installation.instantiation;

import org.apache.camel.Exchange;
import org.apache.camel.Message;

import com.google.code.qualitas.engines.api.core.Bundle;
import com.google.code.qualitas.engines.api.core.ProcessType;
import com.google.code.qualitas.engines.api.factory.BundleFactory;
import com.google.code.qualitas.internal.installation.core.AbstractProcessor;

/**
 * The Class FactoryProcessor.
 */
public class FactoryProcessor extends AbstractProcessor {

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
     */
    @Override
    public void process(Exchange exchange) throws Exception {

        Message in = exchange.getIn();
        ProcessType processType = (ProcessType) in.getHeader("QualitasProcessType");

        BundleFactory bundleFactory = findQualitasComponent(BundleFactory.class, processType);

        Bundle bundle = bundleFactory.createProcessBundle(in.getBody(byte[].class));

        Message out = exchange.getOut();
        out.setBody(bundle);
    }

}
