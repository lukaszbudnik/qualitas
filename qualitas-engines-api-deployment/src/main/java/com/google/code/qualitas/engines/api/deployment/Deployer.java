package com.google.code.qualitas.engines.api.deployment;

import com.google.code.qualitas.engines.api.core.ProcessBundle;

/**
 * The Interface ProcessBundleDeployer.
 * 
 * @param <T>
 *            the generic type
 */
public interface Deployer<T extends ProcessBundle> {

    /**
     * Checks if passed process bundle is supported.
     * 
     * @param processBundle
     *            the process bundle
     * @return true, if is supported
     */
    boolean isSupported(ProcessBundle processBundle);

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
    void deploy(T processBundle) throws DeploymentException;

}
