package com.googlecode.qualitas.internal.installation.deployment;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.springframework.stereotype.Component;

import com.googlecode.qualitas.engines.api.core.Bundle;
import com.googlecode.qualitas.engines.api.deployment.Undeployer;
import com.googlecode.qualitas.internal.installation.core.AbstractProcessor;

/**
 * The Class UndeployerProcessor.
 */
@Component
public class UndeployerProcessor extends AbstractProcessor {

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
     */
    @Override
    public void process(Exchange exchange) throws Exception {
        Message in = exchange.getIn();
        
        Bundle bundle = in.getBody(Bundle.class);

        Undeployer undeployer = findQualitasComponent(Undeployer.class, bundle.getProcessType());
        undeployer.undeploy(bundle);
        
        Message out = exchange.getOut();
        out.setBody(bundle);
        out.setHeaders(in.getHeaders());
    }

}
