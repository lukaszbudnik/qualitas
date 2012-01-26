package com.google.code.qualitas.engines.api.deployment;

import com.google.code.qualitas.engines.api.component.Component;
import com.google.code.qualitas.engines.api.core.Bundle;

/**
 * The Interface ProcessBundleDeployer.
 * 
 */
public interface Deployer extends Component {

    /**
     * Sets remote default deployment service endpoint.
     * 
     * @param defaultDeploymentServiceEndpoint
     *            the new default deployment service endpoint
     */
    void setDefaultDeploymentServiceEndpoint(String defaultDeploymentServiceEndpoint);

    /**
     * Sets remote deployment service endpoint.
     * 
     * @param deploymentServiceEndpoint
     *            the new deployment service endpoint
     */
    void setDeploymentServiceEndpoint(String deploymentServiceEndpoint);

    /**
     * Deploys process bundle to a remote host.
     * 
     * @param processBundle
     *            the process bundle
     * @throws DeploymentException
     *             the deployment exception
     */
    void deploy(Bundle processBundle) throws DeploymentException;

}
