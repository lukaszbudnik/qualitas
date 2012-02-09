package com.google.code.qualitas.utils.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

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
     * @param fileName the file name
     * @param directoryName the directory name
     * @throws IOException the IO exception
     */
    public static void extractArchive(String fileName, String directoryName) throws IOException {
        extractArchive(new File(fileName), new File(directoryName));
    }

    /**
     * Extract archive.
     *
     * @param file the file
     * @param directory the directory
     * @throws IOException the IO exception
     */
    public static void extractArchive(File file, File directory) throws IOException {
        try {
            doExtractArchive(file, directory);
        } catch (IOException e) {
            String msg = "Could not extract archive " + file.getAbsolutePath() + " to "
                    + directory.getAbsolutePath() + " directory";
            LOG.error(msg, e);
            throw e;
        }

    }

    /**
     * Do extract archive.
     *
     * @param file the file
     * @param directory the directory
     * @throws IOException the IO exception
     */
    private static void doExtractArchive(File file, File directory) throws IOException {
        LOG.debug("Trying to extract " + file.getAbsolutePath() + " into "
                + directory.getAbsolutePath() + " directory");

        ZipFile zip = new ZipFile(file);

        if (!directory.exists()) {
            directory.mkdirs();
            LOG.debug("Directory '" + directory.getAbsolutePath()
                    + "' did not exists, created a new one");
        }

        if (!directory.isDirectory()) {
            LOG.error("Directory '" + directory.getName() + "' does not point to a directory");
            throw new IllegalArgumentException("Directory name '" + directory.getAbsolutePath()
                    + "' does not point to a directory");
        }

        Enumeration<? extends ZipEntry> entries = zip.entries();

        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            FileOutputStream out = new FileOutputStream(new File(directory, entry.getName()));
            InputStream in = zip.getInputStream(entry);
            byte[] buffer = new byte[BUFFER_SIZE];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            out.close();
            in.close();
        }
    }

    /**
     * Creates the archive.
     *
     * @param fileName the file name
     * @param directoryName the directory name
     * @throws IOException the IO exception
     */
    public static void createArchive(String fileName, String directoryName) throws IOException {
        createArchive(new File(fileName), new File(directoryName));
    }

    /**
     * Creates the archive.
     *
     * @param file the file
     * @param directory the directory
     * @throws IOException the IO exception
     */
    public static void createArchive(File file, File directory) throws IOException {
        try {
            doCreateArchive(file, directory);
        } catch (IOException e) {
            String msg = "Could not create archive " + file.getAbsolutePath() + " from "
                    + directory.getAbsolutePath() + " directory";
            LOG.error(msg, e);
            throw e;
        }
    }

    /**
     * Do create archive.
     *
     * @param file the file
     * @param directory the directory
     * @throws IOException the IO exception
     */
    private static void doCreateArchive(File file, File directory) throws IOException {
        LOG.debug("Trying to create archive " + file.getAbsolutePath() + " from "
                + directory.getAbsolutePath() + " directory");

        if (!directory.isDirectory()) {
            LOG.error("Directory '" + directory.getName() + "' does not point to a directory");
            throw new IllegalArgumentException("Directory name '" + directory.getName()
                    + "' does not point to a directory");
        }

        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(file));

        out.setLevel(Deflater.BEST_COMPRESSION);

        for (File f : directory.listFiles()) {

            if (!f.isFile()) {
                continue;
            }

            FileInputStream in = new FileInputStream(f);

            out.putNextEntry(new ZipEntry(f.getName()));

            byte[] buffer = new byte[BUFFER_SIZE];
            int length;

            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }

            out.closeEntry();
            in.close();
        }

        out.close();

        LOG.debug("Successfully created archive " + file.getAbsolutePath() + " from "
                + directory.getAbsolutePath() + " directory");
    }

}
