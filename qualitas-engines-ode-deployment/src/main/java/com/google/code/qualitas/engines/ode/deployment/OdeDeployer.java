package com.google.code.qualitas.engines.ode.deployment;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.code.qualitas.engines.api.core.ProcessBundle;
import com.google.code.qualitas.engines.api.deployment.Deployer;
import com.google.code.qualitas.engines.api.deployment.DeploymentException;
import com.google.code.qualitas.engines.ode.core.OdeProcessBundle;
import com.google.code.qualitas.engines.ode.deployment.manager.OdeDeploymentManager;

/**
 * The Class OdeProcessBundleDeployer.
 */
public class OdeDeployer implements Deployer<OdeProcessBundle> {

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
    public void deploy(OdeProcessBundle processBundle) throws DeploymentException {

        String odeUrl;
        if (this.deploymentServiceEndpoint == null) {
            odeUrl = this.deploymentServiceEndpoint;
        } else {
            odeUrl = this.defaultDeploymentServiceEndpoint;
        }

        OdeDeploymentManager manager = new OdeDeploymentManager(odeUrl);

        try {
            manager.deploy(processBundle);
        } catch (Exception e) {
            String msg = "Caught exception while trying to deploy bundle "
                    + processBundle.getMainProcessName();
            LOG.error(msg, e);
            throw new DeploymentException(msg, e);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.code.qualitas.engines.api.deployment.ProcessBundleDeployer
     * #isSupported(com.google.code.qualitas.engines.api.core.ProcessBundle)
     */
    @Override
    public boolean isSupported(ProcessBundle processBundle) {
        return (processBundle instanceof OdeProcessBundle);
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
     * @see
     * com.google.code.qualitas.engines.api.deployment.ProcessBundleDeployer
     * #setDeploymentServiceEndpoint(java.lang.String)
     */
    @Override
    public void setDeploymentServiceEndpoint(String deploymentServiceEndpoint) {
        this.deploymentServiceEndpoint = deploymentServiceEndpoint;
    }

}
