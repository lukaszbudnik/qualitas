package com.googlecode.qualitas.internal.services;

import java.util.Date;
import java.util.List;

import com.googlecode.qualitas.engines.api.configuration.ProcessStatus;
import com.googlecode.qualitas.engines.api.configuration.ProcessType;
import com.googlecode.qualitas.internal.dao.Repository;
import com.googlecode.qualitas.internal.integration.api.InstallationException;
import com.googlecode.qualitas.internal.integration.api.InstallationOrder;
import com.googlecode.qualitas.internal.integration.api.InstallationService;
import com.googlecode.qualitas.internal.model.Process;
import com.googlecode.qualitas.internal.model.ProcessBundle;
import com.googlecode.qualitas.internal.model.Process_;
import com.googlecode.qualitas.internal.model.User;
import com.googlecode.qualitas.internal.model.User_;

/**
 * The Class ProcessManager.
 */
public class ProcessManager {

    /** The installation service. */
    private InstallationService installationService;

    /** The repository. */
    private Repository repository;

    /**
     * Instantiates a new process manager.
     * 
     * @param installationService
     *            the installation service
     * @param repository
     *            the repository
     */
    public ProcessManager(InstallationService installationService,
            Repository repository) {
        super();
        this.installationService = installationService;
        this.repository = repository;
    }

    /**
     * Update process status.
     * 
     * @param processId
     *            the process id
     * @param processStatus
     *            the process status
     */
    public void updateProcessStatus(Long processId, ProcessStatus processStatus) {
        Process process = repository.findById(Process.class, processId);
        process.setProcessStatus(processStatus);
        repository.merge(process);
    }

    /**
     * Install new process.
     * 
     * @param username
     *            the username
     * @param processType
     *            the process type
     * @param contentType
     *            the content type
     * @param bundle
     *            the bundle
     * @return the process
     */
    public Process installNewProcess(String username, ProcessType processType,
            String contentType, byte[] bundle) {
        User user = repository.getSingleResultBySingularAttribute(User.class,
                User_.openIDUsername, username);

        Process process = new Process();
        process.setUser(user);
        process.setInstallationDate(new Date());
        process.setProcessStatus(ProcessStatus.UPLOADED);
        process.setProcessType(processType);

        ProcessBundle originalProcessBundle = new ProcessBundle();
        originalProcessBundle.setContents(bundle);
        process.setOriginalProcessBundle(originalProcessBundle);

        repository.persist(process);

        InstallationOrder installationOrder = new InstallationOrder();

        installationOrder.setBundle(bundle);
        installationOrder.setUsername(username);
        installationOrder.setContentType(contentType);
        installationOrder.setProcessId(process.getProcessId());
        installationOrder.setProcessType(processType);

        try {
            installationService.install(installationOrder);
        } catch (InstallationException e) {
        }

        return process;
    }

    /**
     * Gets the processes by username.
     * 
     * @param username
     *            the username
     * @return the processes by username
     */
    public List<Process> getProcessesByUsername(String username) {
        User user = repository.getSingleResultBySingularAttribute(User.class,
                User_.openIDUsername, username);

        List<Process> processes = repository.getResultListBySingularAttribute(
                Process.class, Process_.user, user);

        return processes;
    }

}
