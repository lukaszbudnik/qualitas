package com.googlecode.qualitas.utils.dom;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.googlecode.qualitas.utils.xslt.XSLTUtils;

/**
 * The Class DOMUtils.
 */
public final class DOMUtils {

    /** The Constant LOG. */
    private static final Log LOG = LogFactory.getLog(DOMUtils.class);

    /** The document builder factory. */
    private static DocumentBuilderFactory documentBuilderFactory;

    static {
        documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
    }

    /**
     * The Constructor.
     */
    private DOMUtils() {
    }

    /**
     * Parses the.
     * 
     * @param is
     *            the is
     * @return the document
     * @throws ParserConfigurationException
     *             the parser configuration exception
     * @throws SAXException
     *             the SAX exception
     * @throws IOException
     *             the IO exception
     */
    public static Document parse(InputStream is) throws ParserConfigurationException, SAXException,
            IOException {
        DocumentBuilder documentBuilder = createDocumentBuilder();
        Document document = null;
        try {
            document = documentBuilder.parse(is);
        } catch (SAXException e) {
            String msg = "Could not parse XML document";
            LOG.error(msg, e);
            throw e;
        } catch (IOException e) {
            String msg = "Could not read from input stream";
            LOG.error(msg, e);
            throw e;
        }
        return document;
    }

    /**
     * To string.
     * 
     * @param node
     *            the node
     * @return the string
     * @throws TransformerException
     *             the transformer exception
     */
    public static String toString(Node node) throws TransformerException {
        Source source = new DOMSource(node);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);

        XSLTUtils.transformDocument(source, result);

        String xml = writer.toString();
        return xml;
    }

    /**
     * Creates the document builder.
     * 
     * @return the document builder
     * @throws ParserConfigurationException
     *             the parser configuration exception
     */
    private static DocumentBuilder createDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            String msg = "Could not create document builder";
            LOG.error(msg, e);
            throw e;
        }
        return documentBuilder;
    }

}
