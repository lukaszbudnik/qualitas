package com.googlecode.qualitas.engines.ode.deployment;

import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ode.deployapi.DeployUnit;
import org.apache.ode.deployapi.DeploymentService;
import org.apache.ode.deployapi.DeploymentServicePortType;
import org.apache.ode.deployapi.Package;
import org.w3._2005._05.xmlmime.Base64Binary;

import com.googlecode.qualitas.engines.api.core.Bundle;
import com.googlecode.qualitas.engines.api.deployment.Deployer;
import com.googlecode.qualitas.engines.api.deployment.DeploymentException;
import com.googlecode.qualitas.engines.ode.component.AbstractOdeComponent;
import com.googlecode.qualitas.engines.ode.core.OdeBundle;

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

        String odeUrl;
        if (this.deploymentServiceEndpoint == null) {
            odeUrl = this.deploymentServiceEndpoint;
        } else {
            odeUrl = this.defaultDeploymentServiceEndpoint;
        }
        
        if (!odeUrl.endsWith("?wsdl")) {
            odeUrl += "?wsdl";
        }

        OdeBundle odeBundle = (OdeBundle) bundle;

        try {
            DeploymentService deploymentService = new DeploymentService(new URL(odeUrl));
            DeploymentServicePortType deploymentServicePortType = deploymentService
                    .getDeploymentServiceSOAP12PortHttp();

            Package _package = new Package();
            Base64Binary value = new Base64Binary();
            value.setContentType("application/zip");
            value.setValue(odeBundle.buildBundle());
            _package.setZip(value);
            DeployUnit deployUnit = deploymentServicePortType.deploy(odeBundle
                    .getMainProcessQName().getLocalPart(), _package);

            if (deployUnit.getId().size() == 0
                    || !deployUnit.getId().get(0).getLocalPart()
                            .startsWith(bundle.getMainProcessQName().getLocalPart())) {
                throw new DeploymentException("Deployment of " + bundle.getMainProcessQName()
                        + " failed");
            }
        } catch (Throwable t) {
            String msg = "Caught exception while trying to deploy bundle "
                    + odeBundle.getMainProcessQName();
            LOG.error(msg, t);
            throw new DeploymentException(msg, t);
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
