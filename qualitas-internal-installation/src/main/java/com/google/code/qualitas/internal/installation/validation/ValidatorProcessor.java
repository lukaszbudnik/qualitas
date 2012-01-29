package com.google.code.qualitas.internal.installation.validation;

import java.util.Map;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import com.google.code.qualitas.engines.api.core.ProcessType;
import com.google.code.qualitas.internal.installation.core.AbstractProcessor;

/**
 * The Class ValidatorProcessor.
 */
@Component
public class ValidatorProcessor extends AbstractProcessor {

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
     */
    @Override
    public void process(Exchange exchange) throws Exception {
//        Bundle bundle = exchange.getIn().getBody(Bundle.class);

        Map<String, Object> headers = exchange.getIn().getHeaders();
        for (String headerName : headers.keySet()) {
            System.out.println(headerName + ": " + headers.get(headerName));
        }
        
        String qualitasProcessTypeString = (String) headers.get("qualitasprocesstype");
        System.out.println("Got string: " + qualitasProcessTypeString);
        System.out.println("Enum: " + ProcessType.valueOf(qualitasProcessTypeString));

        // Validator validator = findQualitasComponent(Validator.class,
        // bundle.getProcessType());
        //
        // validator.validate(bundle);
    }

}
