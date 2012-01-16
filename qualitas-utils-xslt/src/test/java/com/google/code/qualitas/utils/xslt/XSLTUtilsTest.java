package com.google.code.qualitas.utils.xslt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class XSLTUtilsTest {

	private static final String TEST_XML = "src/test/resources/test_transform.xml";
	private static final String TEST_XML_2 = "src/test/resources/test_transform_2.xml";
	private static final String TEST_XSL_RESOURCE = "/test.xsl";
	private static final String TEST_XSL_FILE = "src/test/resources/test.xsl";

	/*
	 * 3 URL methods
	 */
	@Test
	public void testTransformDocumentURLString() throws Exception {
		XSLTUtils.transformDocument(this.getClass().getResource(
				TEST_XSL_RESOURCE), TEST_XML);
	}

	@Test
	public void testTransformDocumentURLStringString() throws Exception {
		XSLTUtils.transformDocument(this.getClass().getResource(
				TEST_XSL_RESOURCE), TEST_XML, TEST_XML_2);
	}

	@Test
	public void testTransformDocumentURLStringStringMap()
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("var", "new value");
		XSLTUtils.transformDocument(this.getClass().getResource(
				TEST_XSL_RESOURCE), TEST_XML, TEST_XML_2, params);
	}

	/*
	 * 3 String methods
	 */
	@Test
	public void testTransformDocumentStringString() throws Exception {
		XSLTUtils.transformDocument(TEST_XSL_FILE, TEST_XML);
	}

	@Test
	public void testTransformDocumentStringStringString()
			throws Exception {
		XSLTUtils.transformDocument(TEST_XSL_FILE, TEST_XML, TEST_XML_2);
	}

	@Test
	public void testTransformDocumentStringStringStringMap()
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("var", "new value");
		XSLTUtils
				.transformDocument(TEST_XSL_FILE, TEST_XML, TEST_XML_2, params);
	}

	/*
	 * 3 File methods
	 */
	@Test
	public void testTransformDocumentFileFile() throws Exception {
		XSLTUtils
				.transformDocument(new File(TEST_XSL_FILE), new File(TEST_XML));
	}

	@Test
	public void testTransformDocumentFileFileFile() throws Exception {
		XSLTUtils.transformDocument(new File(TEST_XSL_FILE),
				new File(TEST_XML), new File(TEST_XML_2));
	}

	@Test
	public void testTransformDocumentFileFileFileMap() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("var", "new value");
		XSLTUtils.transformDocument(new File(TEST_XSL_FILE),
				new File(TEST_XML), new File(TEST_XML_2), params);
	}

	@Test
	public void testTransformDocumentByteByteByteMap()
			throws Exception, IOException {
		byte[] stylesheet = FileUtils
		.readFileToByteArray(new File(TEST_XSL_FILE));
		byte[] input = FileUtils
		.readFileToByteArray(new File(TEST_XML));
		byte[] out = XSLTUtils.transformDocument(stylesheet, input, null);
		String result = new String(out);
		System.out.println(result);
	}
	
	@Test
	public void testTransformDocumentSourceSourceResultMap()
			throws Exception, IOException {
		ByteArrayInputStream bais1 = new ByteArrayInputStream(FileUtils
				.readFileToByteArray(new File(TEST_XSL_FILE)));
		Source stylesheet = new StreamSource(bais1);
		ByteArrayInputStream bais2 = new ByteArrayInputStream(FileUtils
				.readFileToByteArray(new File(TEST_XML)));
		Source input = new StreamSource(bais2);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Result out = new StreamResult(baos);
		XSLTUtils.transformDocument(stylesheet, input, out, null);
		String result = baos.toString();
		System.out.println(result);
	}

	@Test
	public void testTransformDocumentTransformerSourceResult()
			throws Exception, TransformerConfigurationException {
		TransformerFactory xformFactory = TransformerFactory.newInstance();
		Transformer transformer = xformFactory.newTransformer(new StreamSource(
				this.getClass().getResourceAsStream(TEST_XSL_RESOURCE)));
		Source source = new StreamSource(new File(TEST_XML));
		Result result = new StreamResult(new ByteArrayOutputStream());
		XSLTUtils.transformDocument(transformer, source, result, null);
	}

}
