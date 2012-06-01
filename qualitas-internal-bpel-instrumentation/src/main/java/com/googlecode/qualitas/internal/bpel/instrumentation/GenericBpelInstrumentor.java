package com.googlecode.qualitas.internal.bpel.instrumentation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.googlecode.qualitas.engines.api.configuration.ProcessType;
import com.googlecode.qualitas.engines.api.core.Bundle;
import com.googlecode.qualitas.engines.api.core.Entry;
import com.googlecode.qualitas.engines.api.instrumentation.InstrumentationException;
import com.googlecode.qualitas.engines.api.instrumentation.InstrumentationPhase;
import com.googlecode.qualitas.engines.api.instrumentation.Instrumentor;
import com.googlecode.qualitas.utils.dom.DOMUtils;
import com.googlecode.qualitas.utils.xslt.XSLTUtils;

/**
 * The Class GenericBpelInstrumentor.
 */
@InstrumentationPhase
public class GenericBpelInstrumentor implements Instrumentor {

    /** The Constant LOG. */
    private static final Log LOG = LogFactory.getLog(GenericBpelInstrumentor.class);

    /** The Constant WS_BPEL_TRANSFORM_XSL. */
    private static final String WS_BPEL_TRANSFORM_XSL = "/com/googlecode/qualitas/internal/bpel/instrumentation/bpel.xsl";

    /** The Constant QUALITAS_EXECUTION_MONITOR_PACKAGE. */
    private static final String QUALITAS_EXECUTION_MONITOR_PACKAGE = "/com/googlecode/qualitas/internal/monitor/webservice/";

    /** The Constant QUALITAS_EXECUTION_MONITOR_WSDL_NAME. */
    private static final String QUALITAS_EXECUTION_MONITOR_WSDL_NAME = "QualitasMonitorService.wsdl";

    /** The Constant QUALITAS_EXECUTION_MONITOR_ARTIFACTS. */
    private static final String QUALITAS_EXECUTION_MONITOR_ARTIFACTS_NAME = "QualitasMonitorServiceArtifacts.wsdl";

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.code.qualitas.engines.api.component.Component#isSupported(
     * com.google.code.qualitas.engines.api.core.ProcessType)
     */
    public boolean isSupported(ProcessType processType) {
        return processType == ProcessType.WS_BPEL_2_0_APACHE_ODE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.code.qualitas.engines.api.instrumentation.Instrumentor#instrument
     * (com.google.code.qualitas.engines.api.core.Bundle)
     */
    public void instrument(Bundle bundle) throws InstrumentationException {
        try {
            doInstrument(bundle);
        } catch (Exception e) {
            String msg = "Could not instrument bundle " + bundle.getMainProcessQName();
            LOG.error(msg, e);
            throw new InstrumentationException(msg, e);
        }
    }

    private void doInstrument(Bundle bundle) throws ParserConfigurationException, SAXException,
            IOException, TransformerException {
        Entry qualitasConfiguration = getQualitasConfiguration(bundle);
        Document configurationDocument = DOMUtils.parse(new ByteArrayInputStream(qualitasConfiguration
                .getContent()));
        
        List<Entry> entries = bundle.getEntries("*.bpel");
        
        for (Entry entry: entries) {
            Document processDocument = DOMUtils.parse(new ByteArrayInputStream(entry
                    .getContent()));

            Node importedConfigurationDocument = processDocument.importNode(
                    configurationDocument.getDocumentElement(), true);
            processDocument.getDocumentElement().appendChild(importedConfigurationDocument);

            byte[] content = enhanceProcessDefinition(processDocument);

            entry.setContent(content);

            // override process definition entry
            bundle.addEntry(entry);
        }
        
        addQualitasFiles(bundle);
    }

    /**
     * Gets the qualitas configuration.
     * 
     * @param bundle
     *            the bundle
     * @return the qualitas configuration
     * @throws InstrumentationException
     *             the instrumentation exception
     * @throws IOException
     */
    private Entry getQualitasConfiguration(Bundle bundle) throws IOException {
        Entry qualitasConfiguration = null;

        if (!bundle.isInstrumentable()) {
            throw new IllegalArgumentException("Could not find qualitas.xml configuration for "
                    + bundle.getMainProcessQName());
        }
        qualitasConfiguration = bundle.getQualitasConfiguration();

        return qualitasConfiguration;
    }

    /**
     * Adds the qualitas files.
     * 
     * @param bundle
     *            the bundle
     * @throws IOException
     *             the IO exception
     */
    private void addQualitasFiles(Bundle bundle) throws IOException {
        InputStream monitorIs = GenericBpelInstrumentor.class
                .getResourceAsStream(QUALITAS_EXECUTION_MONITOR_PACKAGE
                        + QUALITAS_EXECUTION_MONITOR_WSDL_NAME);
        byte[] qualitasExecutionMonitorWSDL;
        try {
            qualitasExecutionMonitorWSDL = IOUtils.toByteArray(monitorIs);
        } finally {
            monitorIs.close();
        }

        Entry qualitasExecutionMonitorWSDLEntry = new Entry(QUALITAS_EXECUTION_MONITOR_WSDL_NAME,
                qualitasExecutionMonitorWSDL);
        bundle.addEntry(qualitasExecutionMonitorWSDLEntry);

        InputStream artifactsIs = GenericBpelInstrumentor.class
                .getResourceAsStream(QUALITAS_EXECUTION_MONITOR_PACKAGE
                        + QUALITAS_EXECUTION_MONITOR_ARTIFACTS_NAME);
        byte[] qualitasExecutionMonitorArtifacts;
        try {
            qualitasExecutionMonitorArtifacts = IOUtils.toByteArray(artifactsIs);
        } finally {
            artifactsIs.close();
        }

        Entry qualitasExecutionMonitorArtifactsEntry = new Entry(
                QUALITAS_EXECUTION_MONITOR_ARTIFACTS_NAME, qualitasExecutionMonitorArtifacts);
        bundle.addEntry(qualitasExecutionMonitorArtifactsEntry);
    }

    /**
     * Enhance process definition.
     * 
     * @param processDefinition
     *            the process definition
     * @return the byte[]
     * @throws IOException
     *             the IO exception
     * @throws TransformerException
     *             the transformer exception
     */
    private byte[] enhanceProcessDefinition(Document processDefinition) throws IOException,
            TransformerException {
        Source stylesheet = new StreamSource(
                GenericBpelInstrumentor.class.getResourceAsStream(WS_BPEL_TRANSFORM_XSL));
        Source source = new DOMSource(processDefinition);
        StreamResult result = new StreamResult();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        result.setOutputStream(outputStream);

        XSLTUtils.transformDocument(stylesheet, source, result, null);

        byte[] content = outputStream.toByteArray();

        return content;
    }

}
