package com.googlecode.qualitas.engines.api.validation;

import com.googlecode.qualitas.engines.api.component.Component;
import com.googlecode.qualitas.engines.api.core.Bundle;

/**
 * The Interface ProcessBundleValidator.
 * 
 */
public interface Validator extends Component {

    /**
     * Sets the external tool home.
     * 
     * @param home
     *            the external tool home
     */
    void setExternalToolHome(String home);

    /**
     * Gets the external tool home.
     * 
     * @return the external tool home
     */
    String getExternalToolHome();

    /**
     * Sets the external tool platform.
     * 
     * @param platform
     *            the external tool platform
     */
    void setExternalToolPlatform(String platform);

    /**
     * Gets the external tool platform.
     * 
     * @return the external tool platform
     */
    String getExternalToolPlatform();

    /**
     * Validate.
     * 
     * @param processBundle
     *            the processBundle
     * @throws ValidationException
     *             the validation exception
     */
    void validate(Bundle processBundle) throws ValidationException;

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
    void validate(Bundle processBundle, String processName) throws ValidationException;

}
