package com.google.code.qualitas.internal.installation.deployment;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import com.google.code.qualitas.engines.api.core.Bundle;
import com.google.code.qualitas.engines.api.deployment.Undeployer;
import com.google.code.qualitas.internal.installation.core.AbstractProcessor;

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
        Bundle bundle = exchange.getIn().getBody(Bundle.class);

        Undeployer undeployer = findQualitasComponent(Undeployer.class, bundle.getProcessType());

        undeployer.undeploy(bundle);
    }

}
