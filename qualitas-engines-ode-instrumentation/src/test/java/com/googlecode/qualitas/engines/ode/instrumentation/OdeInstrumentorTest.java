package com.googlecode.qualitas.engines.ode.instrumentation;

import java.io.File;
import java.io.IOException;

import javax.xml.namespace.QName;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.googlecode.qualitas.engines.api.core.Entry;
import com.googlecode.qualitas.engines.api.instrumentation.InstrumentationException;
import com.googlecode.qualitas.engines.ode.core.OdeBundle;
import com.googlecode.qualitas.engines.ode.instrumentation.OdeInstrumentor;

public class OdeInstrumentorTest {

    private static final String EXPECTED_INSTRUMENTED_DESCRIPTOR_FILE =
        "src/test/resources/expected_instrumented_descriptor.xml";
    
    private static final String EXPECTED_INSTRUMENTED_PROCESS_FILE =
        "src/test/resources/expected_instrumented_process.xml";

    private static OdeBundle processBundle;

    private static OdeInstrumentor instrumentor;

    @BeforeClass
    public static void setUpClass() throws IOException {
        processBundle = new OdeBundle();
        byte[] zippedArchive = FileUtils.readFileToByteArray(new File(
                "src/test/resources/XhGPWWhile.zip"));
        processBundle.setBundle(zippedArchive);
        processBundle.setMainProcessQName(new QName("XhGPWWhile"));

        instrumentor = new OdeInstrumentor();
    }

    @AfterClass
    public static void tearDown() throws IOException {
        processBundle.cleanUp();
    }

    @Test
    public void testIsSupported() {
        boolean supported = instrumentor.isSupported(processBundle.getProcessType());
        Assert.assertTrue(supported);
    }

    @Test
    public void testInstrument() throws InstrumentationException, IOException {
        instrumentor.instrument(processBundle);

        Entry descriptor = processBundle.getOdeDescriptor();
        String instrumentedDescriptor = new String(descriptor.getContent());
        String expectedInstrumentedDescriptor = FileUtils.readFileToString(new File(
                EXPECTED_INSTRUMENTED_DESCRIPTOR_FILE));
        
        // remove \r and \n otherwise test fails on Linux
        expectedInstrumentedDescriptor = expectedInstrumentedDescriptor.replaceAll("\n", "").replaceAll("\r", "");
        instrumentedDescriptor = instrumentedDescriptor.replaceAll("\n", "").replaceAll("\r", "");
        
        Assert.assertEquals(expectedInstrumentedDescriptor, instrumentedDescriptor);
        
        Entry process = processBundle.getEntries("*.bpel").get(0);
        String instrumentedProcess = new String(process.getContent());
        String expectedInstrumentedProcess = FileUtils.readFileToString(new File(
                EXPECTED_INSTRUMENTED_PROCESS_FILE));
        
        // remove \r and \n otherwise test fails on Linux
        expectedInstrumentedProcess = expectedInstrumentedProcess.replaceAll("\n", "").replaceAll("\r", "");
        instrumentedProcess = instrumentedProcess.replaceAll("\n", "").replaceAll("\r", "");
        
        Assert.assertEquals(expectedInstrumentedProcess, instrumentedProcess);
    }

}
