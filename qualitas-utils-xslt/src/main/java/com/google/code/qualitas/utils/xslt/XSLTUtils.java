package com.google.code.qualitas.utils.xslt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Class XSLTUtils.
 */
public final class XSLTUtils {

    /** The LOG. */
    private static final Log LOG = LogFactory.getLog(XSLTUtils.class);

    /** The xform factory. */
    private static TransformerFactory xformFactory = TransformerFactory.newInstance();

    /**
     * The Constructor.
     */
    private XSLTUtils() {
    }

    /**
     * Transform document.
     * 
     * @param stylesheetURL
     *            the stylesheet url
     * @param fileInAndOut
     *            the file in and out
     * @throws TransformerException
     *             the transformer exception
     * @throws IOException
     *             the IO exception
     * @throws URISyntaxException
     *             the URI syntax exception
     */
    public static void transformDocument(URL stylesheetURL, String fileInAndOut)
            throws TransformerException, IOException, URISyntaxException {
        transformDocument(stylesheetURL, fileInAndOut, fileInAndOut, null);
    }

    /**
     * Transform document.
     * 
     * @param stylesheetURL
     *            the stylesheet url
     * @param fileIn
     *            the file in
     * @param fileOut
     *            the file out
     * @throws TransformerException
     *             the transformer exception
     * @throws IOException
     *             the IO exception
     * @throws URISyntaxException
     *             the URI syntax exception
     */
    public static void transformDocument(URL stylesheetURL, String fileIn, String fileOut)
            throws TransformerException, IOException, URISyntaxException {
        transformDocument(stylesheetURL, fileIn, fileOut, null);
    }

    /**
     * Transform document.
     * 
     * @param stylesheetURL
     *            the stylesheet url
     * @param fileIn
     *            the file in
     * @param fileOut
     *            the file out
     * @param params
     *            the params
     * @throws TransformerException
     *             the transformer exception
     * @throws IOException
     *             the IO exception
     * @throws URISyntaxException
     *             the URI syntax exception
     */
    public static void transformDocument(URL stylesheetURL, String fileIn, String fileOut,
            Map<String, Object> params) throws TransformerException, IOException,
            URISyntaxException {
        try {
            transformDocument(new File(stylesheetURL.toURI()), new File(fileIn), new File(fileOut),
                    params);
        } catch (URISyntaxException e) {
            String msg = "Could not converto URL to URI";
            LOG.error(msg, e);
            throw e;
        }
    }

    /**
     * Transform document.
     * 
     * @param stylesheet
     *            the stylesheet
     * @param fileInAndOut
     *            the file in and out
     * @throws TransformerException
     *             the transformer exception
     * @throws IOException
     *             the IO exception
     */
    public static void transformDocument(String stylesheet, String fileInAndOut)
            throws TransformerException, IOException {
        transformDocument(new File(stylesheet), new File(fileInAndOut), new File(fileInAndOut),
                null);
    }

    /**
     * Transform document.
     * 
     * @param stylesheet
     *            the stylesheet
     * @param fileIn
     *            the file in
     * @param fileOut
     *            the file out
     * @throws TransformerException
     *             the transformer exception
     * @throws IOException
     *             the IO exception
     */
    public static void transformDocument(String stylesheet, String fileIn, String fileOut)
            throws TransformerException, IOException {
        transformDocument(stylesheet, fileIn, fileOut, null);
    }

    /**
     * Transform document.
     * 
     * @param stylesheet
     *            the stylesheet
     * @param fileIn
     *            the file in
     * @param fileOut
     *            the file out
     * @param params
     *            the params
     * @throws TransformerException
     *             the transformer exception
     * @throws IOException
     *             the IO exception
     */
    public static void transformDocument(String stylesheet, String fileIn, String fileOut,
            Map<String, Object> params) throws TransformerException, IOException {
        transformDocument(new File(stylesheet), new File(fileIn), new File(fileOut), params);
    }

    /**
     * Transform document.
     * 
     * @param stylesheet
     *            the stylesheet
     * @param fileInAndOut
     *            the file in and out
     * @throws TransformerException
     *             the transformer exception
     * @throws IOException
     *             the IO exception
     */
    public static void transformDocument(File stylesheet, File fileInAndOut)
            throws TransformerException, IOException {
        transformDocument(stylesheet, fileInAndOut, fileInAndOut);
    }

    /**
     * Transform document.
     * 
     * @param stylesheet
     *            the stylesheet
     * @param fileIn
     *            the file in
     * @param fileOut
     *            the file out
     * @throws TransformerException
     *             the transformer exception
     * @throws IOException
     *             the IO exception
     */
    public static void transformDocument(File stylesheet, File fileIn, File fileOut)
            throws TransformerException, IOException {
        transformDocument(stylesheet, fileIn, fileOut, null);
    }

    /**
     * Transform document.
     * 
     * @param stylesheet
     *            the stylesheet
     * @param fileIn
     *            the file in
     * @param fileOut
     *            the file out
     * @param params
     *            the params
     * @throws TransformerException
     *             the transformer exception
     * @throws IOException
     *             the IO exception
     */
    public static void transformDocument(File stylesheet, File fileIn, File fileOut,
            Map<String, Object> params) throws TransformerException, IOException {
        if (!fileIn.exists()) {
            throw new IllegalStateException("Input file must exists");
        }
        FileInputStream stylesheetInput = null;
        try {
            stylesheetInput = new FileInputStream(stylesheet);
        } catch (Exception e) {
            String msg = "Could not read input stream for given stylesheet file";
            LOG.error(msg, e);
            throw new TransformerException(msg, e);
        }
        // files are different - no problem at all
        if (!fileIn.equals(fileOut)) {
            transformDocument(new StreamSource(stylesheetInput), new StreamSource(fileIn),
                    new StreamResult(fileOut), params);
        } else {
            // files are the same - we have to copy input into a temporary
            // in-memory stream
            // 1. read the input from file
            byte[] input;
            try {
                input = FileUtils.readFileToByteArray(fileIn);
            } catch (IOException e) {
                String msg = "Could not read content from input file " + fileIn.getAbsolutePath();
                LOG.debug(msg, e);
                throw e;
            }
            ByteArrayInputStream bais = new ByteArrayInputStream(input);
            // 2. create source
            Source in = new StreamSource(bais);
            // 3. create result - file as argument
            Result out = new StreamResult(fileOut);
            // 4. do the transformation
            transformDocument(new StreamSource(stylesheetInput), in, out, params);
        }
    }

    /**
     * Transform document.
     * 
     * @param stylesheet
     *            the stylesheet
     * @param input
     *            the input
     * @param params
     *            the params
     * @return the byte[]
     * @throws TransformerException
     *             the transformer exception
     */
    public static byte[] transformDocument(byte[] stylesheet, byte[] input,
            Map<String, Object> params) throws TransformerException {
        ByteArrayInputStream baisStylesheet = new ByteArrayInputStream(stylesheet);
        ByteArrayInputStream baisInput = new ByteArrayInputStream(input);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Source stylesheetSource = new StreamSource(baisStylesheet);
        Source inputSource = new StreamSource(baisInput);
        Result result = new StreamResult(baos);
        transformDocument(stylesheetSource, inputSource, result, params);
        return baos.toByteArray();
    }

    /**
     * Transform document.
     * 
     * @param stylesheet
     *            the stylesheet
     * @param in
     *            the in
     * @param out
     *            the out
     * @param params
     *            the params
     * @throws TransformerException
     *             the transformer exception
     */
    public static void transformDocument(Source stylesheet, Source in, Result out,
            Map<String, Object> params) throws TransformerException {

        Transformer transformer = null;

        try {
            transformer = xformFactory.newTransformer(stylesheet);
        } catch (TransformerConfigurationException e) {
            String msg = "Could not create Transformer";
            LOG.error(msg, e);
            throw e;
        }

        transformDocument(transformer, in, out, params);
    }

    /**
     * Transform document.
     * 
     * @param transformer
     *            the transformer
     * @param in
     *            the in
     * @param out
     *            the out
     * @param params
     *            the params
     * @throws TransformerException
     *             the transformer exception
     */
    public static void transformDocument(Transformer transformer, Source in, Result out,
            Map<String, Object> params) throws TransformerException {
        if (params != null) {
            for (String key : params.keySet()) {
                transformer.setParameter(key, params.get(key));
            }
        }
        try {
            transformer.transform(in, out);
        } catch (TransformerException e) {
            String msg = "Could not transform XML document with supplied XSLT stylesheet";
            LOG.error(msg, e);
            throw e;
        }

    }

}
