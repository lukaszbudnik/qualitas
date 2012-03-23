package com.googlecode.qualitas.engines.ode.deployment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.xml.namespace.QName;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.googlecode.qualitas.engines.ode.core.OdeBundle;

public class OdeDeployerITCase {

    private static OdeBundle odeProcessBundle;
    private static OdeDeployer odeDeployer;
    private static String defaultDeploymentServiceEndpoint;

    @BeforeClass
    public static void setUpClass() throws IOException {
        odeProcessBundle = new OdeBundle();
        byte[] zippedArchive = FileUtils.readFileToByteArray(new File(
                "src/test/resources/XhGPWWhile.zip"));
        odeProcessBundle.setBundle(zippedArchive);
        odeProcessBundle.setMainProcessQName(new QName("XhGPWWhile"));

        Properties properties = new Properties();
        InputStream is = OdeDeployerITCase.class
                .getResourceAsStream("/qualitas-engines-ode-deployment.properties");
        properties.load(is);
        defaultDeploymentServiceEndpoint = properties
                .getProperty("ode.defaultDeploymentServiceEndpoint");

        odeDeployer = new OdeDeployer();
        odeDeployer.setDefaultDeploymentServiceEndpoint(defaultDeploymentServiceEndpoint);
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        odeProcessBundle.cleanUp();
    }

    @Test
    public void testSetDeploymentServiceEndpoint() {
        odeDeployer.setDeploymentServiceEndpoint(defaultDeploymentServiceEndpoint);
    }

    @Test
    public void testSetDefaultDeploymentServiceEndpoint() {
        odeDeployer.setDefaultDeploymentServiceEndpoint(defaultDeploymentServiceEndpoint);
    }

    @Test
    public void testDeploy() throws Exception {
        odeDeployer.deploy(odeProcessBundle);
    }

}
