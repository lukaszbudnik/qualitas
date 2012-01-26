package com.google.code.qualitas.engines.ode.core;

import com.google.code.qualitas.engines.api.component.Component;
import com.google.code.qualitas.engines.api.core.ProcessType;

/**
 * The Class AbstractOdeComponent.
 */
public abstract class AbstractOdeComponent implements Component {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.code.qualitas.engines.api.core.Component#isSupported(com.google
     * .code.qualitas.engines.api.core.ProcessType)
     */
    @Override
    public boolean isSupported(ProcessType processType) {
        return processType == ProcessType.WS_BPEL_1_0_APACHE_ODE
                || processType == ProcessType.WS_BPEL_2_0_APACHE_ODE;
    }

}
