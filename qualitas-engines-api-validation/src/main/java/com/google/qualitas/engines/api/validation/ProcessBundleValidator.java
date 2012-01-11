package com.google.qualitas.engines.api.validation;

import java.io.IOException;

import com.google.code.qualitas.engines.api.core.ProcessBundle;

/**
 * The Interface ProcessBundleValidator.
 * 
 * @param <T>
 *            the generic type
 */
public interface ProcessBundleValidator<T extends ProcessBundle> {

    /**
     * Checks if is supported.
     * 
     * @param processBundle
     *            the process bundle
     * @return true, if checks if is supported
     */
    boolean isSupported(ProcessBundle processBundle);

    /**
     * Sets the external tool home.
     * 
     * @param home
     *            the external tool home
     */
    void setExternalToolHome(String home);

    /**
     * Sets the external tool platform.
     * 
     * @param platform
     *            the external tool platform
     */
    void setExternalToolPlatform(String platform);

    /**
     * Validate.
     * 
     * @param processBundle
     *            the processBundle
     * @return the process bundle validation result
     * @throws IOException
     *             the IO exception
     */
    ProcessBundleValidationResult validate(T processBundle) throws IOException;

    /**
     * Validate.
     * 
     * @param processBundle
     *            the processBundle
     * @param processName
     *            the process name
     * @return the process bundle validation result
     * @throws IOException
     *             the IO exception
     */
    ProcessBundleValidationResult validate(T processBundle, String processName) throws IOException;

}
