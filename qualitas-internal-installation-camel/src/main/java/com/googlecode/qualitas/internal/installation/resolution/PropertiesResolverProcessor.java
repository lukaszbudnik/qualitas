package com.googlecode.qualitas.internal.installation.resolution;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.googlecode.qualitas.engines.api.core.Bundle;
import com.googlecode.qualitas.engines.api.resolution.Properties;
import com.googlecode.qualitas.engines.api.resolution.PropertiesResolver;
import com.googlecode.qualitas.internal.installation.core.AbstractProcessor;
import com.googlecode.qualitas.internal.installation.core.QualitasHeadersNames;
import com.googlecode.qualitas.internal.services.ProcessManager;

/**
 * The Class PropertiesResolverProcessor.
 */
@Component
public class PropertiesResolverProcessor extends AbstractProcessor {

    @Autowired
    private ProcessManager processManager;
    
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
        
        long qualitasProcessId = getQualitasProcessIdHeader(exchange);
        
        processManager.updateProcessProperties(qualitasProcessId, properties.getProcessQName(), properties.getProcessEPR(), properties.getQualitasConfiguration());
    }

    protected long getQualitasProcessIdHeader(Exchange exchange) {
        Message in = exchange.getIn();
        Long qualitasProcessIdHeader = (Long) in.getHeader(QualitasHeadersNames.QUALITAS_PROCESS_ID_HEADER);
        return qualitasProcessIdHeader;
    }
    
}
