package com.googlecode.qualitas.internal.installation.core;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.googlecode.qualitas.engines.api.configuration.ProcessStatus;
import com.googlecode.qualitas.internal.services.ProcessManager;

@Aspect
@Component
public class ProcessStatusUpdaterAspect {
    
    @Autowired
    protected ProcessManager processManager;
    
    /**
     * Update status.
     *
     * @param pjp the pjp
     * @param exchange the exchange
     * @return the object
     * @throws Throwable the throwable
     */
    @Around("execution(* com.googlecode.qualitas.internal.installation..*.process(org.apache.camel.Exchange)) && args(exchange) && target(org.apache.camel.Processor)")
    public Object updateStatus(ProceedingJoinPoint pjp, Exchange exchange) throws Throwable {
        
        Object retValue = null;
        
        try {
            retValue = pjp.proceed();
        } catch (ComponentNotFoundException e) {
            updateStatus(exchange, ProcessStatus.COMPONENT_NOT_FOUND, e.getMessage());
            throw e;
        } catch (PhaseSkippedException e) {
            Object target = pjp.getTarget();
            if (target instanceof AbstractProcessor) {
                AbstractProcessor processor = (AbstractProcessor) target;
                SkippedStatus skippedStatus = processor.getClass().getAnnotation(SkippedStatus.class);
                if (skippedStatus != null) {
                    ProcessStatus processStatus = skippedStatus.value();
                    updateStatus(exchange, processStatus);
                }
            }
            throw e;
        } catch (Exception e) {
            Object target = pjp.getTarget();
            boolean unknownError = true;
            if (target instanceof AbstractProcessor) {
                AbstractProcessor processor = (AbstractProcessor) target;
                FailureStatus failureStatus = processor.getClass().getAnnotation(FailureStatus.class);
                if (failureStatus != null) {
                    ProcessStatus processStatus = failureStatus.value();
                    updateStatus(exchange, processStatus, e.getMessage());
                    unknownError = false;
                }
            }
            if (unknownError) {
                updateStatus(exchange, ProcessStatus.UNKNOWN_ERROR, e.getMessage());
            }
            throw e;
        }
        
        // no exceptions thrown
        Object target = pjp.getTarget();
        if (target instanceof AbstractProcessor) {
            AbstractProcessor processor = (AbstractProcessor) target;
            SuccessfulStatus successfulStatus = processor.getClass().getAnnotation(SuccessfulStatus.class);
            if (successfulStatus != null) {
                ProcessStatus processStatus = successfulStatus.value();
                updateStatus(exchange, processStatus);
            }
        }
        
        return retValue;
    }
    
    protected long getQualitasProcessIdHeader(Exchange exchange) {
        Message in = exchange.getIn();
        Long qualitasProcessIdHeader = (Long) in.getHeader(QualitasHeadersNames.QUALITAS_PROCESS_ID_HEADER);
        return qualitasProcessIdHeader;
    }

    protected void updateStatus(Exchange exchange, ProcessStatus processStatus, String errorMessage) {
        processManager.updateProcessStatus(getQualitasProcessIdHeader(exchange), processStatus, errorMessage);
    }

    protected void updateStatus(Exchange exchange, ProcessStatus processStatus) {
        processManager.updateProcessStatus(getQualitasProcessIdHeader(exchange), processStatus);
    }

}
