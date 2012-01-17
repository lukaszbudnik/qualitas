package com.google.qualitas.engines.ode.validation;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.code.qualitas.engines.api.core.ProcessBundle;
import com.google.code.qualitas.engines.ode.core.OdeProcessBundle;
import com.google.qualitas.engines.api.validation.ValidationException;
import com.google.qualitas.engines.api.validation.Validator;
import com.google.qualitas.engines.ode.validation.bpelc.CompilationException;
import com.google.qualitas.engines.ode.validation.bpelc.OdeBpelCompilerRunner;

/**
 * The Class OdeProcessBundleValidator.
 */
public class OdeValidator implements Validator<OdeProcessBundle> {

    /** The Constant COMPILED_WS_BPEL_EXTENSION. */
    private static final String COMPILED_WS_BPEL_EXTENSION = ".cbp";

    /** The Constant LOG. */
    private static final Log LOG = LogFactory.getLog(OdeValidator.class);

    /** The home. */
    private String home;

    /** The platform. */
    private String platform;

    /*
     * (non-Javadoc)
     * 
     * @see com.google.qualitas.engines.api.validation.ProcessBundleValidator
     * #isSupported(com.google.code.qualitas.engines.api.core.ProcessBundle)
     */
    @Override
    public boolean isSupported(ProcessBundle processBundle) {
        return (processBundle instanceof OdeProcessBundle);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.google.qualitas.engines.api.validation.ProcessBundleValidator
     * #setExternalToolHome(java.lang.String)
     */
    @Override
    public void setExternalToolHome(String home) {
        this.home = home;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.google.qualitas.engines.api.validation.ProcessBundleValidator
     * #setExternalToolPlatform(java.lang.String)
     */
    @Override
    public void setExternalToolPlatform(String platform) {
        this.platform = platform;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.qualitas.engines.api.validation.ProcessBundleValidator#validate
     * (com.google.code.qualitas.engines.api.core.ProcessBundle)
     */
    @Override
    public void validate(OdeProcessBundle processBundle)
            throws ValidationException {
        validate(processBundle, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.qualitas.engines.api.validation.ProcessBundleValidator#validate
     * (com.google.code.qualitas.engines.api.core.ProcessBundle,
     * java.lang.String)
     */
    @Override
    public void validate(OdeProcessBundle processBundle, 
            String processName)
            throws ValidationException {
        String command = home + "/bin/bpelc";

        if ("win".equalsIgnoreCase(platform)) {
            command = command + ".bat";
        }

        LOG.debug("Trying to compile process with following command ==> " + command);

        String pathToBpelFile = null;

        if (processName == null) {
            processName = processBundle.getMainProcessName();
        }

        pathToBpelFile = processBundle.getDirTempPath() + File.separator + processName;

        pathToBpelFile += ".bpel";

        LOG.debug("Path to main process definition file ==> " + pathToBpelFile);

        OdeBpelCompilerRunner bpelc = new OdeBpelCompilerRunner(command, pathToBpelFile);

        try {
            bpelc.compile();
        } catch (CompilationException e) {
            String msg = "Bpelc failed to compile " + pathToBpelFile;
            LOG.error(msg, e);
            throw new ValidationException(msg, e);
        }

        try {
            // remove ODE's compiled CBP file
            processBundle.removeEntry(processName + COMPILED_WS_BPEL_EXTENSION);
        } catch (IOException e) {
            // there is no harm if cbp file ends in the archive
            // it is OK to ignore this exception
            LOG.info("Could not remove ODE's compiled CBP file " + pathToBpelFile);
        }

    }

}
