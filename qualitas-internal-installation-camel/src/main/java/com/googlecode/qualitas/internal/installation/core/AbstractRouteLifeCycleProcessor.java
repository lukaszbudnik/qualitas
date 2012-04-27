package com.googlecode.qualitas.internal.installation.core;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;

import com.googlecode.qualitas.engines.api.configuration.ProcessStatus;
import com.googlecode.qualitas.internal.services.ProcessManager;

public abstract class AbstractRouteLifeCycleProcessor implements Processor {

    @Autowired
    protected ProcessManager processManager;

    public AbstractRouteLifeCycleProcessor() {
        super();
    }

    protected void updateProcessStatus(Exchange exchange, ProcessStatus processStatus) {
        Message in = exchange.getIn();
        Long processId = (Long) in.getHeader(QualitasHeadersNames.QUALITAS_PROCESS_ID_HEADER);
        
        processManager.updateProcessStatus(processId, processStatus);
        
        Message out = exchange.getOut();
        out.setBody(in.getBody());
        out.setHeaders(in.getHeaders());
    }

}