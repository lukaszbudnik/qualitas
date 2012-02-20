package com.google.code.qualitas.engines.ode.component;

import com.google.code.qualitas.engines.api.component.AbstractComponent;
import com.google.code.qualitas.engines.api.core.Bundle;
import com.google.code.qualitas.engines.api.core.ProcessType;

/**
 * The Class AbstractOdeComponent.
 */
public abstract class AbstractOdeComponent extends AbstractComponent {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.code.qualitas.engines.api.core.Component#isSupported(com.google
     * .code.qualitas.engines.api.core.ProcessType)
     */
    @Override
    public boolean isSupported(ProcessType processType) {
        return processType == ProcessType.WS_BPEL_2_0_APACHE_ODE;
    }

    /**
     * Check bundle.
     *
     * @param bundle the bundle
     */
    protected void checkBundle(Bundle bundle) {
        boolean isSupported = isSupported(bundle.getProcessType());
        if (!isSupported) {
            throw new IllegalArgumentException("Not supported bundle type. Expected type was "
                    + ProcessType.WS_BPEL_2_0_APACHE_ODE + " but got " + bundle.getProcessType());
        }
    }

}
