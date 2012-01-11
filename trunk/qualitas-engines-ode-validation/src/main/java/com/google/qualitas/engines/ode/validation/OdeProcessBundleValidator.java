package com.google.qualitas.engines.ode.validation;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.code.qualitas.engines.api.core.OdeProcessBundle;
import com.google.code.qualitas.engines.api.core.ProcessBundle;
import com.google.qualitas.engines.api.validation.ProcessBundleValidationResult;
import com.google.qualitas.engines.api.validation.ProcessBundleValidator;

/**
 * The Class OdeProcessBundleValidator.
 */
public class OdeProcessBundleValidator implements ProcessBundleValidator<OdeProcessBundle> {

    /** The Constant COMPILED_WS_BPEL_EXTENSION. */
    private static final String COMPILED_WS_BPEL_EXTENSION = ".cbp";

    /** The Constant LOG. */
    private static final Log LOG = LogFactory.getLog(OdeProcessBundleValidator.class);

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
    public ProcessBundleValidationResult validate(OdeProcessBundle processBundle)
            throws IOException {
        return validate(processBundle, null);
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
    public ProcessBundleValidationResult validate(OdeProcessBundle processBundle, 
            String processName)
            throws IOException {
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

        boolean result = bpelc.compile();

        // remove ODE's compiled CBP file
        processBundle.removeEntry(processName + COMPILED_WS_BPEL_EXTENSION);

        LOG.debug("Validation result ==> " + result);

        ProcessBundleValidationResult validationResult = new ProcessBundleValidationResult();
        validationResult.setSuccess(result);
        if (!result) {
            validationResult.setErrorMessage(bpelc.getErrorMessage());
        }

        return validationResult;
    }

}
