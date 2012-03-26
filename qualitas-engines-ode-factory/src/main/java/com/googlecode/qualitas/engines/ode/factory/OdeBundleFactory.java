package com.googlecode.qualitas.engines.ode.factory;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.googlecode.qualitas.engines.api.core.Bundle;
import com.googlecode.qualitas.engines.api.factory.BundleCreationException;
import com.googlecode.qualitas.engines.api.factory.BundleFactory;
import com.googlecode.qualitas.engines.ode.component.AbstractOdeComponent;
import com.googlecode.qualitas.engines.ode.core.OdeBundle;

/**
 * The Class OdeBundleFactory.
 */
public class OdeBundleFactory extends AbstractOdeComponent implements BundleFactory {

    /** The Constant LOG. */
    private static final Log LOG = LogFactory.getLog(OdeBundleFactory.class);
    
    /* (non-Javadoc)
     * @see com.google.code.qualitas.engines.api.factory.BundleFactory#createProcessBundle(byte[])
     */
    @Override
    public Bundle createProcessBundle(byte[] contents) throws BundleCreationException {
        OdeBundle odeProcessBundle = new OdeBundle();
        try {
            odeProcessBundle.setBundle(contents);
        } catch (IOException e) {
            String msg = "Could not create OdeBundle";
            LOG.error(msg, e);
            throw new BundleCreationException(msg, e);
        }
        return odeProcessBundle;
    }

}
