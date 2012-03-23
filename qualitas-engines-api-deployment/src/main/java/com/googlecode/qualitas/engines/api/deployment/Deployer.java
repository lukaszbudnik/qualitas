package com.googlecode.qualitas.engines.api.deployment;

import com.googlecode.qualitas.engines.api.component.Component;
import com.googlecode.qualitas.engines.api.core.Bundle;

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
     * Gets the default deployment service endpoint.
     * 
     * @return the default deployment service endpoint
     */
    String getDefaultDeploymentServiceEndpoint();

    /**
     * Sets remote deployment service endpoint.
     * 
     * @param deploymentServiceEndpoint
     *            the new deployment service endpoint
     */
    void setDeploymentServiceEndpoint(String deploymentServiceEndpoint);

    /**
     * Gets the deployment service endpoint.
     * 
     * @return the deployment service endpoint
     */
    String getDeploymentServiceEndpoint();

    /**
     * Deploys bundle to a remote host.
     * 
     * @param bundle
     *            the bundle
     * @throws DeploymentException
     *             the deployment exception
     */
    void deploy(Bundle bundle) throws DeploymentException;

}
