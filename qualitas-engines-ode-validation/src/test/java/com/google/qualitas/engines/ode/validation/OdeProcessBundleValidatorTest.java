package com.google.qualitas.engines.ode.validation;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.code.qualitas.engines.ode.core.OdeProcessBundle;
import com.google.qualitas.engines.api.validation.ValidationException;

public class OdeProcessBundleValidatorTest {

    private static final String APACHE_ODE_PLATFORM = "win";
    private static final String APACHE_ODE_PATH = "C:\\Studies\\Programs\\apache-ode-war-1.3.4";
    private static OdeProcessBundle odeProcessBundle;
    private static OdeProcessBundle odeProcessBundleError;
    private static OdeValidator odeProcessBundleValidator;

    @BeforeClass
    public static void setUpClass() throws IOException {
        odeProcessBundle = new OdeProcessBundle();
        byte[] zippedArchive = FileUtils.readFileToByteArray(new File(
                "src/test/resources/XhGPWWhile.zip"));
        odeProcessBundle.setBundle(zippedArchive);
        odeProcessBundle.setMainProcessName("XhGPWWhile");

        odeProcessBundleError = new OdeProcessBundle();
        byte[] zippedArchiveError = FileUtils.readFileToByteArray(new File(
                "src/test/resources/XhGPWWhileError.zip"));
        odeProcessBundleError.setBundle(zippedArchiveError);
        odeProcessBundleError.setMainProcessName("XhGPWWhile");

        odeProcessBundleValidator = new OdeValidator();
        odeProcessBundleValidator.setExternalToolHome(APACHE_ODE_PATH);
        odeProcessBundleValidator.setExternalToolPlatform(APACHE_ODE_PLATFORM);
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        odeProcessBundle.cleanUp();
        odeProcessBundleError.cleanUp();
    }

    @Test
    public void testSetExternalToolHome() {
        odeProcessBundleValidator.setExternalToolHome(APACHE_ODE_PATH);
    }

    @Test
    public void testSetExternalToolPlatform() {
        odeProcessBundleValidator.setExternalToolPlatform(APACHE_ODE_PLATFORM);
    }

    @Test
    public void testValidateODEArchive() throws ValidationException {
        odeProcessBundleValidator.validate(odeProcessBundle);
    }

    @Test
    public void testValidateODEArchiveString() throws ValidationException {
        odeProcessBundleValidator.validate(odeProcessBundle,
                "XhGPWWhile");
    }

    @Test(expected = ValidationException.class)
    public void testValidateODEArchiveError() throws ValidationException {
        odeProcessBundleValidator.validate(odeProcessBundleError);
    }

}
