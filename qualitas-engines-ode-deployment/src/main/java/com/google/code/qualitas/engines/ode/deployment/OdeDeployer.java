package com.google.code.qualitas.engines.ode.deployment;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.code.qualitas.engines.api.core.Bundle;
import com.google.code.qualitas.engines.api.deployment.Deployer;
import com.google.code.qualitas.engines.api.deployment.DeploymentException;
import com.google.code.qualitas.engines.ode.component.AbstractOdeComponent;
import com.google.code.qualitas.engines.ode.core.OdeBundle;
import com.google.code.qualitas.engines.ode.deployment.manager.OdeDeploymentManager;

/**
 * The Class OdeProcessBundleDeployer.
 */
public class OdeDeployer extends AbstractOdeComponent implements Deployer {

    /** The log. */
    private static final Log LOG = LogFactory.getLog(OdeDeployer.class);

    /** The default deployment service endpoint. */
    private String defaultDeploymentServiceEndpoint;

    /** The deployment service endpoint. */
    private String deploymentServiceEndpoint;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.code.qualitas.engines.api.deployment.ProcessBundleDeployer
     * #deploy(com.google.code.qualitas.engines.api.core.ProcessBundle)
     */
    @Override
    public void deploy(Bundle bundle) throws DeploymentException {

        checkBundle(bundle);

        OdeBundle odeBundle = (OdeBundle) bundle;

        String odeUrl;
        if (deploymentServiceEndpoint != null) {
            odeUrl = deploymentServiceEndpoint;
        } else {
            odeUrl = defaultDeploymentServiceEndpoint;
        }

        OdeDeploymentManager manager = new OdeDeploymentManager(odeUrl);

        try {
            manager.deploy(odeBundle);
        } catch (Exception e) {
            String msg = "Caught exception while trying to deploy bundle "
                    + odeBundle.getMainProcessQName();
            LOG.error(msg, e);
            throw new DeploymentException(msg, e);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.code.qualitas.engines.api.deployment.ProcessBundleDeployer
     * #setDefaultDeploymentServiceEndpoint(java.lang.String)
     */
    @Override
    public void setDefaultDeploymentServiceEndpoint(String defaultDeploymentServiceEndpoint) {
        this.defaultDeploymentServiceEndpoint = defaultDeploymentServiceEndpoint;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.google.code.qualitas.engines.api.deployment.Deployer#
     * getDefaultDeploymentServiceEndpoint()
     */
    @Override
    public String getDefaultDeploymentServiceEndpoint() {
        return defaultDeploymentServiceEndpoint;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.code.qualitas.engines.api.deployment.ProcessBundleDeployer
     * #setDeploymentServiceEndpoint(java.lang.String)
     */
    @Override
    public void setDeploymentServiceEndpoint(String deploymentServiceEndpoint) {
        this.deploymentServiceEndpoint = deploymentServiceEndpoint;
    }

    @Override
    public String getDeploymentServiceEndpoint() {
        return deploymentServiceEndpoint;
    }

}
