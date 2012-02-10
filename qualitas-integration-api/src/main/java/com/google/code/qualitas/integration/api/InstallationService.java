package com.google.code.qualitas.integration.api;

/**
 * The Interface BundleInstallationService.
 */
public interface InstallationService {

    /**
     * Install.
     *
     * @param processBundleInstallationOrder the process bundle installation order
     * @throws InstallationException the installation exception
     */
    void install(InstallationOrder processBundleInstallationOrder) throws InstallationException;

}
