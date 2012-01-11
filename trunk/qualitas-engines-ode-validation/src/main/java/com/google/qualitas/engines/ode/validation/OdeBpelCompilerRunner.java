package com.google.qualitas.engines.ode.validation;

import java.io.ByteArrayOutputStream;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteStreamHandler;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Class OdeBpelCompilerRunner.
 */
public class OdeBpelCompilerRunner {

    /** The Constant LOG. */
    private static final Log LOG = LogFactory.getLog(OdeBpelCompilerRunner.class);

    /** The command line. */
    private CommandLine commandLine;

    /** The error message. */
    private String errorMessage;

    /**
     * The Constructor.
     * 
     * @param command
     *            the command
     * @param bpel
     *            the bpel
     */
    public OdeBpelCompilerRunner(String command, String bpel) {
        commandLine = CommandLine.parse(command + " " + bpel);
    }

    /**
     * Compile.
     * 
     * @return true, if compile
     */
    public boolean compile() {
        DefaultExecutor executor = new DefaultExecutor();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ByteArrayOutputStream error = new ByteArrayOutputStream();
        ExecuteStreamHandler handler = new PumpStreamHandler(output, error);
        executor.setStreamHandler(handler);

        try {
            executor.execute(commandLine);
        } catch (Exception e) {
            String msg = "Exception cuaght while invoking Apache ODE bpelc tool";
            LOG.error(msg, e);
            errorMessage = msg;
            return false;
        } finally {
            if (error.size() > 0) {
                errorMessage = new String(error.toByteArray());
                return false;
            }
        }

        return true;
    }

    /**
     * Gets the error message.
     * 
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

}
