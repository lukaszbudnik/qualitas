package com.google.code.qualitas.engines.ode.deployment;

import com.google.code.qualitas.engines.api.core.OdeProcessBundle;
import com.google.code.qualitas.engines.api.core.ProcessBundle;
import com.google.code.qualitas.engines.api.deployment.ProcessBundleDeploymentResult;
import com.google.code.qualitas.engines.api.deployment.ProcessBundleUndeployer;

/**
 * The Class OdeProcessBundleUndeployer.
 */
public class OdeProcessBundleUndeployer implements
        ProcessBundleUndeployer<OdeProcessBundle> {

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
    public ProcessBundleDeploymentResult undeploy(OdeProcessBundle processBundle) {

        String odeUrl;
        if (this.deploymentServiceEndpoint == null) {
            odeUrl = this.deploymentServiceEndpoint;
        } else {
            odeUrl = this.defaultDeploymentServiceEndpoint;
        }

        OdeDeploymentManager manager = new OdeDeploymentManager(odeUrl);

        ProcessBundleDeploymentResult bundleDeploymentResult = new ProcessBundleDeploymentResult();

        try {
            manager.undeploy(processBundle.getMainProcessName());
        } catch (RemoteDeploymentException e) {
            bundleDeploymentResult.setSuccess(false);
            bundleDeploymentResult.setErrorMessage(e.getMessage());
        }

        bundleDeploymentResult.setSuccess(true);

        return bundleDeploymentResult;
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
    public void setDefaultDeploymentServiceEndpoint(
            String defaultDeploymentServiceEndpoint) {
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
