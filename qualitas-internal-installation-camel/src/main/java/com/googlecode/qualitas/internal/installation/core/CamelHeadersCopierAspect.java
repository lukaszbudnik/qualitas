package com.googlecode.qualitas.internal.installation.core;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * The Class CamelHeadersCopierAspect.
 */
@Aspect
public class CamelHeadersCopierAspect {
    
    /**
     * Copy headers and body.
     *
     * @param pjp the pjp
     * @param exchange the exchange
     * @return the object
     * @throws Throwable the throwable
     */
    @Around("execution(* com.googlecode.qualitas.internal.installation..*.process(org.apache.camel.Exchange)) && args(exchange) && target(org.apache.camel.Processor)")
    public Object copyHeadersAndBody(ProceedingJoinPoint pjp, Exchange exchange) throws Throwable {
        Object retValue = pjp.proceed();
        
        Message in = exchange.getIn();
        Message out = exchange.getOut();
        // always copy headers
        out.setHeaders(in.getHeaders());
        
        // if output body is empty copy it from input
        if (out.getBody() == null) {
            out.setBody(in.getBody());
        }
        
        return retValue;
    }

}
