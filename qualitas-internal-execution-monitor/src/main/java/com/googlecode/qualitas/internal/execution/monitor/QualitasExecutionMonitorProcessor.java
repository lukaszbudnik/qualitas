package com.googlecode.qualitas.internal.execution.monitor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.cxf.message.MessageContentsList;
import org.springframework.stereotype.Component;

/**
 * The Class QualitasExecutionMonitorProcessor.
 */
@Component
public class QualitasExecutionMonitorProcessor implements Processor {

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
     */
    @Override
    public void process(Exchange exchange) throws Exception {

        MessageContentsList messageContentsList = exchange.getIn().getBody(
                MessageContentsList.class);

        for (Object o : messageContentsList) {
            System.out.print(o.getClass() + ": ");
            System.out.println(o);
        }
    }
}
