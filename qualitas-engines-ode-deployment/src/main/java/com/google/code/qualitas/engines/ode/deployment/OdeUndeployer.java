package com.google.code.qualitas.engines.ode.deployment;

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.code.qualitas.engines.api.core.Bundle;
import com.google.code.qualitas.engines.api.deployment.DeploymentException;
import com.google.code.qualitas.engines.api.deployment.Undeployer;
import com.google.code.qualitas.engines.ode.component.AbstractOdeComponent;
import com.google.code.qualitas.engines.ode.deployment.manager.OdeDeploymentManager;

/**
 * The Class OdeProcessBundleUndeployer.
 */
public class OdeUndeployer extends AbstractOdeComponent implements Undeployer {

    /** The log. */
    private static final Log LOG = LogFactory.getLog(OdeUndeployer.class);

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
    public void undeploy(Bundle bundle) throws DeploymentException {

        checkBundle(bundle);

        QName mainProcessQName = bundle.getMainProcessQName();

        undeploy(mainProcessQName.getLocalPart());
    }

    @Override
    public void undeploy(String processName) throws DeploymentException {
        String odeUrl;
        if (this.deploymentServiceEndpoint == null) {
            odeUrl = this.deploymentServiceEndpoint;
        } else {
            odeUrl = this.defaultDeploymentServiceEndpoint;
        }

        OdeDeploymentManager manager = new OdeDeploymentManager(odeUrl);

        try {
            manager.undeploy(processName);
        } catch (Exception e) {
            String msg = "Caught exception while trying to undeploy bundle " + processName;
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
