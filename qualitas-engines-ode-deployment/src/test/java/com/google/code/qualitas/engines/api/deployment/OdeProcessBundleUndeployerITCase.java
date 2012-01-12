package com.google.code.qualitas.engines.api.deployment;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.code.qualitas.engines.api.core.OdeProcessBundle;
import com.google.code.qualitas.engines.ode.deployment.OdeProcessBundleUndeployer;

public class OdeProcessBundleUndeployerITCase {

	private static OdeProcessBundle odeProcessBundle;
	private static OdeProcessBundleUndeployer odeUndeployer;

	@BeforeClass
	public static void setUpClass() throws IOException {
		odeProcessBundle = new OdeProcessBundle();
		odeProcessBundle.setMainProcessName("XhHelloWorld2");
		// undeployer
		odeUndeployer = new OdeProcessBundleUndeployer();
		odeUndeployer
				.setDeploymentServiceEndpoint("http://localhost:9090/ode/processes/DeploymentService");
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		odeProcessBundle.cleanUp();
	}

	@Test
	public void testSetDeploymentServiceEndpoint() {
		odeUndeployer
				.setDeploymentServiceEndpoint("http://localhost:9090/ode/processes/DeploymentService");
	}

	@Test
	public void testSetDefaultDeploymentServiceEndpoint() {
		odeUndeployer
				.setDefaultDeploymentServiceEndpoint("http://localhost:9090/ode/processes/DeploymentService");
	}

	@Test
	public void testUndeploy() {
		ProcessBundleDeploymentResult result = odeUndeployer.undeploy(odeProcessBundle);
		Assert.assertTrue(result.isSuccess());
	}

}
