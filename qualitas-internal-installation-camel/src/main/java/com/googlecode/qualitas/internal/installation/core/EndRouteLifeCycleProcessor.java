package com.googlecode.qualitas.internal.installation.core;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import com.googlecode.qualitas.engines.api.configuration.ProcessStatus;

@Component
public class EndRouteLifeCycleProcessor extends AbstractRouteLifeCycleProcessor {

    @Override
    public void process(Exchange exchange) throws Exception {
        ProcessStatus processStatus = ProcessStatus.INSTALLED;
        updateProcessStatus(exchange, processStatus);
    }

}
