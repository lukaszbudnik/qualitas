package com.google.code.qualitas.engines.api.deployment;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.code.qualitas.engines.ode.core.OdeProcessBundle;
import com.google.code.qualitas.engines.ode.deployment.OdeDeployer;

public class OdeDeployerITCase {

    private static OdeProcessBundle odeProcessBundle;
    private static OdeDeployer odeDeployer;

    @BeforeClass
    public static void setUpClass() throws IOException {
        odeProcessBundle = new OdeProcessBundle();
        byte[] zippedArchive = FileUtils.readFileToByteArray(new File(
                "src/test/resources/XhGPWWhile.zip"));
        odeProcessBundle.setBundle(zippedArchive);
        odeProcessBundle.setMainProcessName("XhGPWWhile");
        odeDeployer = new OdeDeployer();
        odeDeployer
                .setDeploymentServiceEndpoint("http://localhost:9090/ode/processes/DeploymentService");
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        odeProcessBundle.cleanUp();
    }

    @Test
    public void testSetDeploymentServiceEndpoint() {
        odeDeployer
                .setDeploymentServiceEndpoint("http://localhost:9090/ode/processes/DeploymentService");
    }

    @Test
    public void testSetDefaultDeploymentServiceEndpoint() {
        odeDeployer
                .setDefaultDeploymentServiceEndpoint("http://localhost:9090/ode/processes/DeploymentService");
    }

    @Test
    public void testDeploy() throws DeploymentException {
        odeDeployer.deploy(odeProcessBundle);
    }

}
