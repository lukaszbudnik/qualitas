package com.googlecode.qualitas.engines.ode.deployment;

import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ode.deployapi.DeploymentService;
import org.apache.ode.deployapi.DeploymentServicePortType;
import org.apache.ode.deployapi.PackageNames;

import com.googlecode.qualitas.engines.api.core.Bundle;
import com.googlecode.qualitas.engines.api.deployment.DeploymentException;
import com.googlecode.qualitas.engines.api.deployment.Undeployer;
import com.googlecode.qualitas.engines.ode.component.AbstractOdeComponent;

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

        if (!odeUrl.endsWith("?wsdl")) {
            odeUrl += "?wsdl";
        }

        try {
            DeploymentService deploymentService = new DeploymentService(new URL(odeUrl));
            DeploymentServicePortType deploymentServicePortType = deploymentService
                    .getDeploymentServiceSOAP12PortHttp();

            PackageNames packageNames = deploymentServicePortType.listDeployedPackages();

            List<String> installedProcesses = packageNames.getName();

            LOG.debug("Installed processes ==>" + installedProcesses);

            for (String installedProcess : installedProcesses) {
                if (installedProcess.split("-")[0].equals(processName)) {
                    QName packageName = new QName(installedProcess);
                    boolean result = deploymentServicePortType.undeploy(packageName);
                    if (!result) {
                        throw new DeploymentException("Could not undeploy " + processName);
                    }
                    break;
                }
            }

        } catch (Throwable t) {
            String msg = "Caught exception while trying to undeploy bundle " + processName;
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
