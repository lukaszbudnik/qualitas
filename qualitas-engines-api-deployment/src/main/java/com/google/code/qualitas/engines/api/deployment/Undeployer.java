package com.google.code.qualitas.engines.api.deployment;

import com.google.code.qualitas.engines.api.component.Component;
import com.google.code.qualitas.engines.api.core.Bundle;

/**
 * The Interface ProcessBundleUndeployer.
 * 
 */
public interface Undeployer extends Component {

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
     * Undeploys process bundle from a remote host.
     * 
     * @param processName
     *            the process name
     * @throws DeploymentException
     *             the deployment exception
     */
    void undeploy(String processName) throws DeploymentException;

    /**
     * Undeploys bundle from a remote host.
     * 
     * @param bundle
     *            the bundle
     * @throws DeploymentException
     *             the deployment exception
     */
    void undeploy(Bundle bundle) throws DeploymentException;

}
