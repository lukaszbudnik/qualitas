package com.googlecode.qualitas.utils.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Class ZipUtils.
 */
public final class ZipUtils {

    /** The Constant BUFFER_SIZE. */
    private static final int BUFFER_SIZE = 4096;

    /** The Constant LOG. */
    private static final Log LOG = LogFactory.getLog(ZipUtils.class);

    /**
     * The Constructor.
     */
    private ZipUtils() {
    }

    /**
     * Extract archive.
     * 
     * @param fileName
     *            the file name
     * @param directoryName
     *            the directory name
     * @throws IOException
     *             the IO exception
     */
    public static void extractArchive(String fileName, String directoryName)
            throws IOException {
        extractArchive(new File(fileName), new File(directoryName));
    }

    /**
     * Extract archive.
     * 
     * @param file
     *            the file
     * @param directory
     *            the directory
     * @throws IOException
     *             the IO exception
     */
    public static void extractArchive(File file, File directory)
            throws IOException {
        try {
            doExtractArchive(file, directory);
        } catch (IOException e) {
            String msg = "Could not extract archive " + file.getAbsolutePath()
                    + " to " + directory.getAbsolutePath() + " directory";
            LOG.error(msg, e);
            throw e;
        }

    }

    /**
     * Do extract archive.
     * 
     * @param file
     *            the file
     * @param directory
     *            the directory
     * @throws IOException
     *             the IO exception
     */
    private static void doExtractArchive(File file, File directory) throws IOException
            {
        LOG.debug("Trying to extract " + file.getAbsolutePath() + " into "
                + directory.getAbsolutePath() + " directory");

        ZipFile zip = new ZipFile(file);

        if (!directory.exists()) {
            boolean directoryCreated = directory.mkdirs();

            if (!directoryCreated) {
                String msg = "Could not create directory to extract contents of zip archive "
                        + directory.getAbsolutePath();
                LOG.error(msg);
                throw new IOException(msg);
            }

            LOG.debug("Directory " + directory.getAbsolutePath()
                    + " did not exist, created a new one");
        }

        if (!directory.isDirectory()) {
            LOG.error("Directory " + directory.getName()
                    + " does not point to a directory");
            throw new IllegalArgumentException("Directory name "
                    + directory.getAbsolutePath()
                    + " does not point to a directory");
        }

        Enumeration<? extends ZipEntry> entries = zip.entries();

        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            
            byte[] buffer = null;
            InputStream in = null;
            try {
                in = zip.getInputStream(entry);
                buffer = IOUtils.toByteArray(in);
            } finally {
                if (in != null) {
                    in.close();
                }
            }

            OutputStream out = null;
            try {
                out = new FileOutputStream(new File(directory,
                        entry.getName()));
                IOUtils.write(buffer, out);
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        }

        zip.close();
    }

    /**
     * Creates the archive.
     * 
     * @param fileName
     *            the file name
     * @param directoryName
     *            the directory name
     * @throws IOException
     *             the IO exception
     */
    public static void createArchive(String fileName, String directoryName)
            throws IOException {
        createArchive(new File(fileName), new File(directoryName));
    }

    /**
     * Creates the archive.
     * 
     * @param file
     *            the file
     * @param directory
     *            the directory
     * @throws IOException
     *             the IO exception
     */
    public static void createArchive(File file, File directory)
            throws IOException {
        LOG.debug("Trying to create zip archive " + file.getAbsolutePath()
                + " from " + directory.getAbsolutePath() + " directory");

        if (!directory.isDirectory()) {
            LOG.error("Directory '" + directory.getName()
                    + "' does not point to a directory");
            throw new IllegalArgumentException("Directory name '"
                    + directory.getName() + "' does not point to a directory");
        }

        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(file));
        out.setLevel(Deflater.BEST_COMPRESSION);

        try {
            doCreateArchive(directory, out);
        } catch (IOException e) {
            String msg = "Could not create archive " + file.getAbsolutePath()
                    + " from " + directory.getAbsolutePath() + " directory";
            LOG.error(msg, e);
            throw e;
        } finally {
            out.close();
        }

        LOG.debug("Successfully created archive " + file.getAbsolutePath()
                + " from " + directory.getAbsolutePath() + " directory");
    }

    /**
     * Do create archive.
     * 
     * @param file
     *            the file
     * @param directory
     *            the directory
     * @throws IOException
     *             the IO exception
     */
    private static void doCreateArchive(File directory, ZipOutputStream out)
            throws IOException {

        for (File f : directory.listFiles()) {

            if (!f.isFile()) {
                continue;
            }

            out.putNextEntry(new ZipEntry(f.getName()));

            byte[] buffer;
            FileInputStream in = null;

            try {
                in = new FileInputStream(f);
                buffer = IOUtils.toByteArray(in);
            } catch (IOException e) {
                throw e;
            } finally {
                if (in != null) {
                    in.close();
                }
            }

            IOUtils.write(buffer, out);

            out.closeEntry();

        }
    }

}
