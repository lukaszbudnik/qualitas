package com.google.code.qualitas.utils.zip;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.code.qualitas.utils.zip.ZipUtils;

public class ZipUtilsTest {

	private static final String TMP_DIR = System.getProperty("java.io.tmpdir");
	private static final String TEST_ZIP_ARCHIVE = "src/test/resources/archive.zip";
	private static final String[] TEST_ZIP_ARCHIVE_CONTENTS = new String[] {"README", "deploy.xml", "nuntius.xml"};
	private static final String TEST_DIRECTORY = "src/test/resources";

	private String extractDirectoryName;
	private File extractDirectory;
	private String archiveName;
	private File archive;
	
	@Before
	public void setUp() throws Exception {
		extractDirectoryName = TMP_DIR + "extract-zip-utils-dir" + System.currentTimeMillis();
		extractDirectory = new File(extractDirectoryName);
		extractDirectory.deleteOnExit();
		archiveName = TMP_DIR + "create-zip-utils-file" + System.currentTimeMillis() + ".zip";
		archive = new File(archiveName);
		archive.deleteOnExit();
	}

	@After
	public void tearDown() throws Exception {
		FileUtils.deleteDirectory(extractDirectory);
		extractDirectory.delete();
		archive.delete();
	}

	@Test
	public void testExtractArchiveStringString() throws IOException {
		ZipUtils.extractArchive(TEST_ZIP_ARCHIVE, extractDirectoryName);
		String[] files = extractDirectory.list();
		Arrays.sort(files);
		Assert.assertArrayEquals(TEST_ZIP_ARCHIVE_CONTENTS, files);
	}

	@Test
	public void testExtractArchiveFileFile() throws IOException {
		ZipUtils.extractArchive(new File(TEST_ZIP_ARCHIVE), extractDirectory);
		String[] files = extractDirectory.list();
		Arrays.sort(files);
		Assert.assertArrayEquals(TEST_ZIP_ARCHIVE_CONTENTS, files);
	}

	@Test
	public void testCreateArchiveStringString() throws IOException {
		ZipUtils.createArchive(archiveName, TEST_DIRECTORY);
		Assert.assertTrue(archive.exists());
	}

	@Test
	public void testCreateArchiveFileFile() throws IOException {
		ZipUtils.createArchive(archive, new File(TEST_DIRECTORY));
		Assert.assertTrue(archive.exists());
	}

}
