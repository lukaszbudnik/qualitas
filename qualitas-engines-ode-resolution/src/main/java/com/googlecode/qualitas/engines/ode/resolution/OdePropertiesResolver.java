package com.googlecode.qualitas.engines.ode.resolution;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.googlecode.qualitas.engines.api.configuration.QualitasConfiguration;
import com.googlecode.qualitas.engines.api.core.Bundle;
import com.googlecode.qualitas.engines.api.core.Entry;
import com.googlecode.qualitas.engines.api.resolution.Properties;
import com.googlecode.qualitas.engines.api.resolution.PropertiesResolver;
import com.googlecode.qualitas.engines.api.resolution.ResolutionException;
import com.googlecode.qualitas.engines.ode.component.AbstractOdeComponent;
import com.googlecode.qualitas.engines.ode.core.OdeBundle;

/**
 * The Class OdePropertiesResolver.
 */
public class OdePropertiesResolver extends AbstractOdeComponent implements PropertiesResolver {

    /** The Constant LOG. */
    private static final Log LOG = LogFactory.getLog(OdePropertiesResolver.class);
    
    /** The process q name. */
    private QName processQName;
    
    /** The service q name. */
    private QName serviceQName;
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.code.qualitas.engines.api.resolver.PropertiesResolver#resolve
     * (com.google.code.qualitas.engines.api.core.Bundle)
     */
    @Override
    public Properties resolve(Bundle bundle) throws ResolutionException {

        checkBundle(bundle);

        OdeBundle odeBundle = (OdeBundle) bundle;

        Document deploy = getDeployDocument(odeBundle);

        resolveProcessQName(deploy);
        resolveServiceQName(deploy);

        List<QName> servicesQNames = getServicesQNames(deploy);

        QualitasConfiguration qualitasConfiguration = doGetQualitasConfiguration(odeBundle);

        Document wsdl = getMainProcessWSDLDocument(odeBundle);

        String processEPR = getProcessEPR(deploy, wsdl);

        bundle.setMainProcessQName(processQName);

        Properties properties = new Properties();
        properties.setProcessQName(processQName);
        properties.setServiceQName(serviceQName);
        properties.setServicesQNames(servicesQNames);
        properties.setQualitasConfiguration(qualitasConfiguration);
        properties.setProcessEPR(processEPR);

        return properties;
    }

    private Document getMainProcessWSDLDocument(OdeBundle odeBundle) throws ResolutionException {
        try {
            List<Entry> wsdls = odeBundle.getEntries("*.wsdl");
            
            for (Entry wsdl: wsdls) {
                InputStream is = new ByteArrayInputStream(wsdl.getContent());
                Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
                is.close();
                
                Element definitions = document.getDocumentElement();
                String targetNamespace = definitions.getAttribute("targetNamespace");
                
                if (targetNamespace.equals(serviceQName.getNamespaceURI())) {
                    return document;
                }
            }
        } catch (Exception e) {
            String msg = "Could not read main process WSDL from bundle "
                    + odeBundle.getDirTempPath();
            LOG.error(msg, e);
            throw new ResolutionException(msg, e);
        }

        throw new ResolutionException("Could not find WSDL file for process " + processQName + " and service " + serviceQName);
    }

    private String getProcessEPR(Document deploy, Document wsdl)
            throws ResolutionException  {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        String servicePort = null;
        try {
            XPathExpression portExpr = xpath.compile("/deploy/process/provide/service/@port");
            servicePort = (String) portExpr.evaluate(deploy, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            String msg = "Could not parse deploy.xml file";
            LOG.error(msg, e);
            throw new ResolutionException(msg, e);
        }

        XPathExpression soapAddressExpr;
        String location = null;
        try {
            soapAddressExpr = xpath.compile("/definitions/service[@name = '"
                    + serviceQName.getLocalPart() + "']/port[@name='" + servicePort + "']/address/@location");
            location = (String) soapAddressExpr.evaluate(wsdl, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            String msg = "Could not parse deploy.xml file";
            LOG.error(msg, e);
            throw new ResolutionException(msg, e);
        }

        return location;
    }

    private QualitasConfiguration doGetQualitasConfiguration(OdeBundle odeBundle)
            throws ResolutionException {
        QualitasConfiguration qualitasConfiguration = null;

        try {
            qualitasConfiguration = getQualitasConfiguration(odeBundle);
        } catch (IOException e) {
            String msg = "Could not read Qualitas Configuration from bundle "
                    + odeBundle.getDirTempPath();
            LOG.error(msg, e);
            throw new ResolutionException(msg, e);
        }
        return qualitasConfiguration;
    }

    private Document getDeployDocument(OdeBundle odeBundle) throws ResolutionException {
        Document document = null;

        try {
            InputStream is = new ByteArrayInputStream(odeBundle.getOdeDescriptor().getContent());
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
        } catch (Exception e) {
            String msg = "Could not read ODE descriptor from bundle " + odeBundle.getDirTempPath();
            LOG.error(msg, e);
            throw new ResolutionException(msg, e);
        }

        return document;
    }

    /**
     * Gets the process q name.
     * 
     * @param deployElement
     *            the deploy element
     * @return the process q name
     * @throws ResolutionException
     */
    private void resolveProcessQName(Document deploy) throws ResolutionException {

        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        XPathExpression nameExpr;
        String processName = null;
        try {
            nameExpr = xpath.compile("/deploy/process/@name");
            processName = (String) nameExpr.evaluate(deploy, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            String msg = "Could not parse deploy.xml file";
            LOG.error(msg, e);
            throw new ResolutionException(msg, e);
        }

        String prefix = processName.substring(0, processName.indexOf(':'));
        String localPart = processName.substring(processName.indexOf(':') + 1);

        String namespaceURI = findNamespaceForPrefix(deploy, prefix);

        processQName = new QName(namespaceURI, localPart, prefix);
    }
    
    /**
     * Gets the process q name.
     * 
     * @param deployElement
     *            the deploy element
     * @return the process q name
     * @throws ResolutionException
     */
    private void resolveServiceQName(Document deploy) throws ResolutionException {

        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        XPathExpression nameExpr;
        String serviceName = null;
        try {
            nameExpr = xpath.compile("/deploy/process[@name='" + processQName.getPrefix() + ":" + processQName.getLocalPart() + "']/provide/service/@name");
            serviceName = (String) nameExpr.evaluate(deploy, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            String msg = "Could not parse deploy.xml file";
            LOG.error(msg, e);
            throw new ResolutionException(msg, e);
        }

        String prefix = serviceName.substring(0, serviceName.indexOf(':'));
        String localPart = serviceName.substring(serviceName.indexOf(':') + 1);

        String namespaceURI = findNamespaceForPrefix(deploy, prefix);

        serviceQName = new QName(namespaceURI, localPart, prefix);
    }

    /**
     * Gets the services q name.
     * 
     * @param processQName
     * 
     * @param deployElement
     *            the deploy element
     * @return the services q name
     * @throws ResolutionException
     */
    private List<QName> getServicesQNames(Document deploy)
            throws ResolutionException {

        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        XPathExpression nameExpr;
        NodeList services = null;
        try {
            nameExpr = xpath.compile("/deploy/process[@name = '" + processQName.getPrefix() + ":"
                    + processQName.getLocalPart() + "']/invoke/service");
            services = (NodeList) nameExpr.evaluate(deploy, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            String msg = "Could not parse deploy.xml file";
            LOG.error(msg, e);
            throw new ResolutionException(msg, e);
        }

        List<QName> servicesQNames = new ArrayList<QName>();
        for (int i = 0; i < services.getLength(); i++) {
            Element serviceElement = (Element) services.item(i);
            String serviceName = serviceElement.getAttribute("name");
            String prefix = serviceName.substring(0, serviceName.indexOf(':'));
            String localPart = serviceName.substring(serviceName.indexOf(':') + 1);

            String namespaceURI = findNamespaceForPrefix(deploy, prefix);

            servicesQNames.add(new QName(namespaceURI, localPart, prefix));
        }

        return servicesQNames;
    }

    /**
     * Finds namespace for prefix.
     * 
     * @param node
     *            the node
     * @param prefix
     *            the prefix
     * @return the string
     */
    private String findNamespaceForPrefix(Node node, String prefix) {
        NamedNodeMap namedNodeMap = node.getAttributes();

        if (namedNodeMap != null) {
            for (int i = 0; i < namedNodeMap.getLength(); i++) {
                Attr attribute = (Attr) namedNodeMap.item(i);
                String attributeName = attribute.getNodeName();
                if (attributeName.equals("xmlns:" + prefix)) {
                    return attribute.getNodeValue();
                }
            }
        }

        for (int i = 0; i < node.getChildNodes().getLength(); i++) {
            String namespace = findNamespaceForPrefix(node.getChildNodes().item(i), prefix);
            if (namespace != null) {
                return namespace;
            }
        }

        return null;
    }
}
