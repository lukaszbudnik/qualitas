package com.google.code.qualitas.engines.ode.factory;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.code.qualitas.engines.api.core.Bundle;
import com.google.code.qualitas.engines.api.factory.BundleCreationException;
import com.google.code.qualitas.engines.api.factory.BundleFactory;
import com.google.code.qualitas.engines.ode.core.AbstractOdeComponent;
import com.google.code.qualitas.engines.ode.core.OdeBundle;

/**
 * The Class OdeBundleFactory.
 */
public class OdeBundleFactory extends AbstractOdeComponent implements BundleFactory {

    /** The Constant LOG. */
    private static final Log LOG = LogFactory.getLog(OdeBundleFactory.class);
    
    /* (non-Javadoc)
     * @see com.google.code.qualitas.engines.api.factory.BundleFactory#createProcessBundle(byte[])
     */
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
