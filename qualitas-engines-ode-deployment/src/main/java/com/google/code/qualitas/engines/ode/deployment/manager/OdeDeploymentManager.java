package com.google.code.qualitas.engines.ode.deployment.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.OMText;
import org.apache.axiom.om.util.Base64;
import org.apache.axis2.AxisFault;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ode.axis2.service.ServiceClientUtil;
import org.apache.ode.utils.Namespaces;

import com.google.code.qualitas.engines.ode.core.OdeProcessBundle;

/**
 * The Class OdeDeploymentManager.
 */
public class OdeDeploymentManager {

    /** The log. */
    private static final Log LOG = LogFactory.getLog(OdeDeploymentManager.class);

    /** The factory. */
    private OMFactory factory;

    /** The client. */
    private ServiceClientUtil client;

    /** The ODE url. */
    private String odeUrl;

    /**
     * Instantiates a new ode deployment manager.
     * 
     * @param odeURL
     *            the ode url
     */
    public OdeDeploymentManager(String odeURL) {
        this.odeUrl = odeURL;
        factory = OMAbstractFactory.getOMFactory();
        client = new ServiceClientUtil();
    }

    /**
     * Gets the processes.
     * 
     * @return the processes
     * @throws RemoteDeploymentException
     *             the remote deployment exception
     */
    public List<String> getProcesses() throws RemoteDeploymentException {
        OMElement listDeployedPackagesResponse = doGetProcesses();
        OMElement deployedPackages = listDeployedPackagesResponse.getFirstElement();
        @SuppressWarnings("unchecked")
        Iterator<OMElement> iter = deployedPackages.getChildElements();
        List<String> processesList = new ArrayList<String>();
        while (iter.hasNext()) {
            OMElement name = iter.next();
            processesList.add(name.getText());
        }
        return processesList;
    }

    /**
     * Deploy.
     * 
     * @param processBundle
     *            the process bundle
     * @return the string
     * @throws RemoteDeploymentException
     *             the remote deployment exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public String deploy(OdeProcessBundle processBundle) throws RemoteDeploymentException,
            IOException {

        OMElement deploy = createDeployMessage(processBundle);

        OMElement deployResponse = doDeploy(deploy);

        String packageName = deployResponse.getFirstElement().getFirstElement().getText();

        return packageName;
    }

    /**
     * Undeploy.
     * 
     * @param process
     *            the process
     * @return true, if successful
     * @throws RemoteDeploymentException
     *             the remote deployment exception
     */
    public boolean undeploy(String process) throws RemoteDeploymentException {
        List<String> installedProcesses = getProcesses();

        LOG.debug("Installed processes ==>" + installedProcesses);

        boolean found = false;
        for (String installedProcess : installedProcesses) {
            if (installedProcess.split("-")[0].equals(process)) {
                found = true;
                process = installedProcess;
                break;
            }
        }

        if (!found) {
            return true;
        }

        OMElement undeployResponse = doUndeploy(process);

        OMElement response = undeployResponse.getFirstElement();

        return Boolean.parseBoolean(response.getText());
    }

    /**
     * Do deploy.
     * 
     * @param deploy
     *            the deploy
     * @return the oM element
     * @throws RemoteDeploymentException
     *             the remote deployment exception
     */
    private OMElement doDeploy(OMElement deploy) throws RemoteDeploymentException {
        OMElement result = null;

        try {
            result = sendToOdeDeploymentService(deploy);
        } catch (AxisFault e) {
            String msg = "Could not deploy " + deploy.getFirstElement().getText();
            LOG.error(msg, e);
            throw new RemoteDeploymentException(msg, e);
        }

        return result;
    }

    /**
     * Creates the deploy message.
     * 
     * @param processBundle
     *            the process bundle
     * @return the oM element
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private OMElement createDeployMessage(OdeProcessBundle processBundle) throws IOException {
        String processName = processBundle.getMainProcessName();

        OMNamespace pmapins = factory.createOMNamespace(Namespaces.ODE_PMAPI_NS, "pmapi");

        OMNamespace deployapins = factory.createOMNamespace(Namespaces.ODE_DEPLOYAPI_NS,
                "deployapi");

        OMElement deploy = factory.createOMElement("deploy", pmapins);
        deploy.declareNamespace(deployapins);

        OMElement name = factory.createOMElement("name", null);
        name.setText(processName);

        OMElement processPackage = factory.createOMElement("package", null);
        OMElement zip = factory.createOMElement("zip", deployapins);

        String base64Enc = Base64.encode(processBundle.buildBundle());
        OMText zipContent = factory.createOMText(base64Enc, "application/zip", false);
        zip.addChild(zipContent);

        deploy.addChild(name);
        deploy.addChild(processPackage);
        processPackage.addChild(zip);

        return deploy;
    }

    /**
     * Do undeploy.
     * 
     * @param process
     *            the process
     * @return the oM element
     * @throws RemoteDeploymentException
     *             the remote deployment exception
     */
    private OMElement doUndeploy(String process) throws RemoteDeploymentException {
        OMNamespace pmapins = factory.createOMNamespace(Namespaces.ODE_PMAPI_NS, "pmapi");
        OMElement undeploy = factory.createOMElement("undeploy", pmapins);
        OMElement packageName = factory.createOMElement("packageName", pmapins);
        packageName.setText(process);
        undeploy.addChild(packageName);

        OMElement result = null;
        try {
            result = sendToOdeDeploymentService(undeploy);
        } catch (AxisFault e) {
            String msg = "Could not undeploy " + process;
            LOG.error(msg, e);
            throw new RemoteDeploymentException(msg, e);
        }

        return result;
    }

    /**
     * Do get processes.
     * 
     * @return the oM element
     * @throws RemoteDeploymentException
     *             the remote deployment exception
     */
    private OMElement doGetProcesses() throws RemoteDeploymentException {
        OMElement listDeployedPackages = client.buildMessage("listDeployedPackages",
                new String[] {}, new Object[] {});

        OMElement result = null;

        try {
            result = sendToOdeDeploymentService(listDeployedPackages);
        } catch (AxisFault e) {
            String msg = "Could not list processes deployed on remote server";
            LOG.error(msg, e);
            throw new RemoteDeploymentException(msg, e);
        }

        return result;
    }

    /**
     * Sends message to ODE deployment service.
     * 
     * @param msg
     *            the message
     * @return the OM element
     * @throws AxisFault
     *             the axis fault
     */
    private OMElement sendToOdeDeploymentService(OMElement msg) throws AxisFault {
        return client.send(msg, odeUrl);
    }

}
