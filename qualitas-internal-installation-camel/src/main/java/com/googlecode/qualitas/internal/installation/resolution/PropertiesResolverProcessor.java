package com.googlecode.qualitas.internal.installation.resolution;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.springframework.stereotype.Component;

import com.googlecode.qualitas.engines.api.core.Bundle;
import com.googlecode.qualitas.engines.api.resolution.Properties;
import com.googlecode.qualitas.engines.api.resolution.PropertiesResolver;
import com.googlecode.qualitas.internal.installation.core.AbstractProcessor;

/**
 * The Class PropertiesResolverProcessor.
 */
@Component
public class PropertiesResolverProcessor extends AbstractProcessor {

    /* (non-Javadoc)
     * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
     */
    @Override
    public void process(Exchange exchange) throws Exception {
        Message in = exchange.getIn();
        
        Bundle bundle = in.getBody(Bundle.class);

        PropertiesResolver resolver = findQualitasComponent(PropertiesResolver.class,
                bundle.getProcessType());

        Properties properties = resolver.resolve(bundle);
        
        Message out = exchange.getOut();
        out.setBody(bundle);
        out.setHeaders(in.getHeaders());
    }

}
