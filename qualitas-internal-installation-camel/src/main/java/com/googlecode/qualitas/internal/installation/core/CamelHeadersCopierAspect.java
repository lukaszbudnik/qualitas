package com.googlecode.qualitas.internal.installation.core;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class CamelHeadersCopierAspect {
    
    @Around("execution(* com.googlecode.qualitas.internal.installation..*.process(org.apache.camel.Exchange)) && args(exchange) && target(org.apache.camel.Processor)")
    public Object copyHeaders(ProceedingJoinPoint pjp, Exchange exchange) throws Throwable {
        Object retValue = pjp.proceed();
        
        System.out.println(pjp.getTarget());
        
        Message in = exchange.getIn();
        Message out = exchange.getOut();
        
        out.setHeaders(in.getHeaders());
        
        if (out.getBody() == null) {
            out.setBody(in.getBody());
        }
        
        return retValue;
    }

}
