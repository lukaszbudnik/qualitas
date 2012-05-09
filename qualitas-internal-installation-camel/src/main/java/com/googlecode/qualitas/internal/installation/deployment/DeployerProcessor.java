package com.googlecode.qualitas.internal.installation.deployment;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.springframework.stereotype.Component;

import com.googlecode.qualitas.engines.api.core.Bundle;
import com.googlecode.qualitas.engines.api.deployment.Deployer;
import com.googlecode.qualitas.internal.installation.core.AbstractProcessor;

/**
 * The Class DeployerProcessor.
 */
@Component
public class DeployerProcessor extends AbstractProcessor {

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
     */
    @Override
    public void process(Exchange exchange) throws Exception {
        Message in = exchange.getIn();
        
        Bundle bundle = in.getBody(Bundle.class);

        Deployer deployer = findQualitasComponent(Deployer.class, bundle.getProcessType());
        deployer.deploy(bundle);
    }

}
