package com.google.code.qualitas.engines.ode.instrumentation;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.code.qualitas.engines.api.core.Entry;
import com.google.code.qualitas.engines.api.instrumentation.InstrumentationException;
import com.google.code.qualitas.engines.ode.core.OdeProcessBundle;

public class OdeInstrumentorTest {

    private static final String EXPECTED_INSTRUMENTED_DESCRIPTOR_FILE =
        "src/test/resources/expected_instrumented_descriptor.xml";
    
    private static final String EXPECTED_INSTRUMENTED_PROCESS_FILE =
        "src/test/resources/expected_instrumented_process.xml";

    private static OdeProcessBundle processBundle;

    private static OdeInstrumentor instrumentor;

    @BeforeClass
    public static void setUpClass() throws IOException {
        processBundle = new OdeProcessBundle();
        byte[] zippedArchive = FileUtils.readFileToByteArray(new File(
                "src/test/resources/XhGPWWhile.zip"));
        processBundle.setBundle(zippedArchive);
        processBundle.setMainProcessName("XhGPWWhile");

        instrumentor = new OdeInstrumentor();
    }

    @AfterClass
    public static void tearDown() throws IOException {
        processBundle.cleanUp();
    }

    @Test
    public void testIsSupported() {
        boolean supported = instrumentor.isSupported(processBundle);
        Assert.assertTrue(supported);
    }

    @Test
    public void testInstrument() throws InstrumentationException, IOException {
        instrumentor.instrument(processBundle);

        Entry descriptor = processBundle.getOdeDescriptor();
        String instrumentedDescriptor = new String(descriptor.getContent());
        String expectedInstrumentedDescriptor = FileUtils.readFileToString(new File(
                EXPECTED_INSTRUMENTED_DESCRIPTOR_FILE));
        Assert.assertEquals(expectedInstrumentedDescriptor, instrumentedDescriptor);
        
        Entry process = processBundle.getMainProcessDefinition();
        String instrumentedProcess = new String(process.getContent());
        String expectedInstrumentedProcess = FileUtils.readFileToString(new File(
                EXPECTED_INSTRUMENTED_PROCESS_FILE));
        Assert.assertEquals(expectedInstrumentedProcess, instrumentedProcess);
    }

}
