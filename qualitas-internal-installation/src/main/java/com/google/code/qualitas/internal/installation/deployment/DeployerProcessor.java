package com.google.code.qualitas.internal.installation.deployment;

import org.apache.camel.Exchange;

import com.google.code.qualitas.engines.api.core.Bundle;
import com.google.code.qualitas.engines.api.deployment.Deployer;
import com.google.code.qualitas.internal.installation.core.AbstractProcessor;

/**
 * The Class DeployerProcessor.
 */
public class DeployerProcessor extends AbstractProcessor {

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
     */
    @Override
    public void process(Exchange exchange) throws Exception {
        Bundle bundle = exchange.getIn().getBody(Bundle.class);

        Deployer deployer = findQualitasComponent(Deployer.class, bundle.getProcessType());

        deployer.deploy(bundle);
    }

}
