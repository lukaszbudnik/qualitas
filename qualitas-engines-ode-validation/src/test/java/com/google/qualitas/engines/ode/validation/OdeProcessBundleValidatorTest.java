package com.google.qualitas.engines.ode.validation;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.code.qualitas.engines.api.core.OdeProcessBundle;
import com.google.qualitas.engines.api.validation.ProcessBundleValidationResult;

public class OdeProcessBundleValidatorTest {

    private static final String APACHE_ODE_PLATFORM = "win";
    private static final String APACHE_ODE_PATH = "C:/Studies/Programs/apache-ode-war-1.3.2";
    private static OdeProcessBundle odeProcessBundle;
    private static OdeProcessBundle odeProcessBundleError;
    private static OdeProcessBundleValidator odeProcessBundleValidator;

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

        odeProcessBundleValidator = new OdeProcessBundleValidator();
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
    public void testValidateODEArchive() throws IOException {
        ProcessBundleValidationResult result = odeProcessBundleValidator.validate(odeProcessBundle);
        Assert.assertTrue(result.isSuccess());
    }

    @Test
    public void testValidateODEArchiveString() throws IOException {
        ProcessBundleValidationResult result = odeProcessBundleValidator.validate(odeProcessBundle,
                "XhGPWWhile");
        Assert.assertTrue(result.isSuccess());
    }

    @Test
    public void testValidateODEArchiveError() throws IOException {
        ProcessBundleValidationResult result = odeProcessBundleValidator.validate(odeProcessBundleError);
        Assert.assertFalse(result.isSuccess());
        System.out.println(result.getErrorMessage());
    }

}
