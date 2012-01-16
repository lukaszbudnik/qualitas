package com.google.qualitas.engines.api.validation;

import com.google.code.qualitas.engines.api.core.ProcessBundle;

/**
 * The Interface ProcessBundleValidator.
 * 
 * @param <T>
 *            the generic type
 */
public interface Validator<T extends ProcessBundle> {

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
     * @throws ValidationException
     *             the validation exception
     */
    void validate(T processBundle) throws ValidationException;

    /**
     * Validate.
     * 
     * @param processBundle
     *            the processBundle
     * @param processName
     *            the process name
     * @throws ValidationException
     *             the validation exception
     */
    void validate(T processBundle, String processName) throws ValidationException;

}
