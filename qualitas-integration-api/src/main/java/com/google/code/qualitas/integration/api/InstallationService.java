package com.google.code.qualitas.integration.api;

import com.google.code.qualitas.engines.api.core.ProcessType;

/**
 * The Interface BundleInstallationService.
 */
public interface InstallationService {

    /**
     * Install.
     * 
     * @param bundle
     *            the bundle
     * @param contentType
     *            the content type
     * @param processType
     *            the process type
     * @throws InstallationException
     *             the installation exception
     */
    void install(byte[] bundle, String contentType, ProcessType processType)
            throws InstallationException;

}
