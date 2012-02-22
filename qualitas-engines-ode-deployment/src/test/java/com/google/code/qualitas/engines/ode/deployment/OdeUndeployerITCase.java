package com.google.code.qualitas.engines.ode.deployment;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.xml.namespace.QName;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.code.qualitas.engines.ode.core.OdeBundle;

public class OdeUndeployerITCase {

    private static OdeBundle odeProcessBundle;
    private static OdeUndeployer odeUndeployer;
    private static String defaultDeploymentServiceEndpoint;

    @BeforeClass
    public static void setUpClass() throws IOException {
        odeProcessBundle = new OdeBundle();
        odeProcessBundle.setMainProcessQName(new QName("XhHelloWorld2"));

        Properties properties = new Properties();
        InputStream is = OdeDeployerITCase.class
                .getResourceAsStream("/qualitas-engines-ode-deployment.properties");
        properties.load(is);
        defaultDeploymentServiceEndpoint = properties
                .getProperty("ode.defaultDeploymentServiceEndpoint");

        odeUndeployer = new OdeUndeployer();
        odeUndeployer.setDeploymentServiceEndpoint(defaultDeploymentServiceEndpoint);
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        odeProcessBundle.cleanUp();
    }

    @Test
    public void testSetDeploymentServiceEndpoint() {
        odeUndeployer.setDeploymentServiceEndpoint(defaultDeploymentServiceEndpoint);
    }

    @Test
    public void testSetDefaultDeploymentServiceEndpoint() {
        odeUndeployer.setDefaultDeploymentServiceEndpoint(defaultDeploymentServiceEndpoint);
    }

    @Test
    public void testUndeploy() throws Exception {
        odeUndeployer.undeploy(odeProcessBundle);
    }

}
