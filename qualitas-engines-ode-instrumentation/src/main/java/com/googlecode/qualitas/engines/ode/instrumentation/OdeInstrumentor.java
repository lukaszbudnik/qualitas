package com.googlecode.qualitas.engines.ode.instrumentation;

import java.io.IOException;
import java.util.List;

import javax.xml.transform.TransformerException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.googlecode.qualitas.engines.api.core.Bundle;
import com.googlecode.qualitas.engines.api.core.Entry;
import com.googlecode.qualitas.engines.api.instrumentation.InstrumentationException;
import com.googlecode.qualitas.engines.api.instrumentation.Instrumentor;
import com.googlecode.qualitas.engines.api.instrumentation.PreInstrumentationPhase;
import com.googlecode.qualitas.engines.ode.component.AbstractOdeComponent;
import com.googlecode.qualitas.engines.ode.core.OdeBundle;
import com.googlecode.qualitas.utils.xslt.XSLTUtils;

/**
 * The Class OdeProcessBundleInstrumentor.
 */
@PreInstrumentationPhase
public class OdeInstrumentor extends AbstractOdeComponent implements Instrumentor {

    /** The Constant LOG. */
    private static final Log LOG = LogFactory.getLog(OdeInstrumentor.class);

    /** The Constant ODE_DEPLOY_TRANSFORM_XSL. */
    private static final String ODE_DEPLOY_TRANSFORM_XSL = 
        "/com/google/code/qualitas/engines/ode/instrumentation/deploy.xsl";

    /** The Constant WS_BPEL_TRANSFORM_XSL. */
    private static final String WS_BPEL_TRANSFORM_XSL =
        "/com/google/code/qualitas/engines/ode/instrumentation/bpel.xsl";

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.code.qualitas.engines.api.instrumentation.Instrumentor#instrument
     * (com.google.code.qualitas.engines.api.core.ProcessBundle)
     */
    @Override
    public void instrument(Bundle bundle) throws InstrumentationException {

        checkBundle(bundle);

        OdeBundle odeProcessBundle = (OdeBundle) bundle;

        // 1. enhance process
        try {
            enhanceProcessDefinition(odeProcessBundle);
        } catch (Exception e) {
            String msg = "Could not instrument process definition "
                    + odeProcessBundle.getMainProcessQName();
            LOG.debug(msg, e);
            throw new InstrumentationException(msg, e);
        }

        // 2. enhance descriptor
        try {
            enhanceDescriptor(odeProcessBundle);
        } catch (Exception e) {
            String msg = "Could not instrument descriptor "
                    + odeProcessBundle.getMainProcessQName();
            LOG.debug(msg, e);
            throw new InstrumentationException(msg, e);
        }

    }

    /**
     * Enhance process definition.
     * 
     * @param bundle
     *            the process bundle
     * @throws IOException
     *             the IO exception
     * @throws TransformerException
     *             the transformer exception
     */
    private void enhanceProcessDefinition(OdeBundle bundle) throws IOException,
            TransformerException {
        // read the stylesheet
        byte[] stylesheet = IOUtils.toByteArray(OdeInstrumentor.class
                .getResourceAsStream(WS_BPEL_TRANSFORM_XSL));
        
        List<Entry> entries = bundle.getEntries("*.bpel");
        
        for (Entry entry: entries) {
            // in-memory transformation
            byte[] result = XSLTUtils.transformDocument(stylesheet, entry.getContent(),
                    null);
            // copy results
            entry.setContent(result);
            // override existing process definition
            bundle.addEntry(entry);
        }
        
    }

    /**
     * Enhance descriptor.
     * 
     * @param processBundle
     *            the process bundle
     * @throws IOException
     *             the IO exception
     * @throws TransformerException
     *             the transformer exception
     */
    private void enhanceDescriptor(OdeBundle processBundle) throws IOException,
            TransformerException {
        // read the stylesheet
        byte[] stylesheet = IOUtils.toByteArray(OdeInstrumentor.class
                .getResourceAsStream(ODE_DEPLOY_TRANSFORM_XSL));
        // get the descriptor
        Entry descriptor = processBundle.getOdeDescriptor();
        // in-memory transformation
        byte[] result = XSLTUtils.transformDocument(stylesheet, descriptor.getContent(), null);
        // copy results
        descriptor.setContent(result);
        // override existing descriptor
        processBundle.addEntry(descriptor);
    }

}
