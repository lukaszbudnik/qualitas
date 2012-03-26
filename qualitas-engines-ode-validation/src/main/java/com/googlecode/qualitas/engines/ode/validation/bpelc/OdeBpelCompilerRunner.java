package com.googlecode.qualitas.engines.ode.validation.bpelc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
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
     * @throws CompilationException
     *             the compilation exception
     */
    public void compile() throws CompilationException {
        DefaultExecutor executor = new DefaultExecutor();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ByteArrayOutputStream error = new ByteArrayOutputStream();
        ExecuteStreamHandler handler = new PumpStreamHandler(output, error);
        executor.setStreamHandler(handler);

        try {
            executor.execute(commandLine);
        } catch (ExecuteException e) {
            String msg = "Caught execution exception while trying to execute " + commandLine;
            LOG.error(msg, e);
            throw new CompilationException(msg, e);
        } catch (IOException e) {
            String msg = "Caught IO exception while trying to execute " + commandLine;
            LOG.error(msg, e);
            throw new CompilationException(msg, e);
        }

        if (error.size() > 0) {
            String errorMessage = new String(error.toByteArray());
            throw new CompilationException(errorMessage);
        }

    }
}
