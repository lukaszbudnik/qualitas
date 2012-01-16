package com.google.qualitas.engines.ode.validation;

import java.io.ByteArrayOutputStream;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteStreamHandler;
import org.apache.commons.exec.PumpStreamHandler;

/**
 * The Class OdeBpelCompilerRunner.
 */
public class OdeBpelCompilerRunner {

    /** The command line. */
    private CommandLine commandLine;

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
     * @throws Exception the exception
     */
    public void compile() throws Exception {
        DefaultExecutor executor = new DefaultExecutor();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ByteArrayOutputStream error = new ByteArrayOutputStream();
        ExecuteStreamHandler handler = new PumpStreamHandler(output, error);
        executor.setStreamHandler(handler);

        executor.execute(commandLine);

        if (error.size() > 0) {
            String errorMessage = new String(error.toByteArray());
            throw new Exception(errorMessage);
        }

    }
}
