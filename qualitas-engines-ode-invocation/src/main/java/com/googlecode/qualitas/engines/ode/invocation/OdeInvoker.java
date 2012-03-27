package com.googlecode.qualitas.engines.ode.invocation;

import com.googlecode.qualitas.engines.api.configuration.ProcessType;
import com.googlecode.qualitas.engines.api.invocation.webservice.AbstractWebServiceInvoker;

/**
 * The Class OdeExecutor.
 */
public class OdeInvoker extends AbstractWebServiceInvoker {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.code.qualitas.engines.api.invocation.Invoker#isSupported(com
     * .google.code.qualitas.engines.api.core.ProcessType)
     */
    public boolean isSupported(ProcessType processType) {
        return processType == ProcessType.WS_BPEL_2_0_APACHE_ODE;
    }

}
