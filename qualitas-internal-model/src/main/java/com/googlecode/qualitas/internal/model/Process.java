package com.googlecode.qualitas.internal.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.Converter;
import org.eclipse.persistence.annotations.Converters;

import com.googlecode.qualitas.engines.api.configuration.ProcessStatus;
import com.googlecode.qualitas.engines.api.configuration.ProcessType;
import com.googlecode.qualitas.internal.model.converters.ProcessStatusConverter;
import com.googlecode.qualitas.internal.model.converters.ProcessTypeConverter;

/**
 * The persistent class for the PROCESSES database table.
 * 
 */
@Entity
@Table(name = "PROCESSES")
//@ObjectTypeConverter(name = "processStatus", objectType = ProcessStatus.class, dataType = Short.class, 
//conversionValues = { @ConversionValue(objectValue = "UPLOADED", dataValue = "0"),
//    @ConversionValue(objectValue="PROCESSING", dataValue="1"),
//    @ConversionValue(objectValue="INSTALLED", dataValue="2"),
//    @ConversionValue(objectValue="CREATION_ERROR", dataValue="3"),
//    @ConversionValue(objectValue="VALIDATION_ERROR", dataValue="4"),
//    @ConversionValue(objectValue="INSTRUMENTATION_ERROR", dataValue="5"),
//    @ConversionValue(objectValue="INSTALLATION_ERROR", dataValue="6"),
//    @ConversionValue(objectValue="UNINSTALLATION_ERROR", dataValue="7")})
@Converters({
    @Converter(name="processStatus", converterClass=ProcessStatusConverter.class),
    @Converter(name="processType", converterClass=ProcessTypeConverter.class)
})
public class Process implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "PROCESSES_PROCESSID_GENERATOR", sequenceName = "PROCESS_ID_SEQUENCE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROCESSES_PROCESSID_GENERATOR")
	@Column(name = "PROCESS_ID", nullable = false, precision = 22)
	private long processId;

	@Column
	private String configuration;

	@Temporal(TemporalType.DATE)
	@Column(name = "INSTALLATION_DATE", nullable = false)
	private Date installationDate;

	@Column(name = "PROCESS_EPR", unique = true, length = 1000)
	private String processEPR;

	@Column(name = "PROCESS_NAME", length = 1000)
	private String processName;

	@Column(name = "PROCESS_TYPE", nullable = false, length = 1)
	private ProcessType processType;

	@Column(name = "PROCESS_STATUS", nullable = false, length = 1)
	@Convert("processStatus")
	private ProcessStatus processStatus;

	// bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name = "USER_ID", unique = true, nullable = false)
	private User user;

	// bi-directional many-to-one association to ProcessInstance
	@OneToMany(mappedBy = "process")
	private List<ProcessInstance> processInstances;

	// uni-directional one-to-one association to ProcessBundle
	@OneToOne
	@JoinColumn(name = "INSTRUMENTED_PROCESS_BUNDLE_ID")
	private ProcessBundle instrumentedProcessBundle;

	// uni-directional one-to-one association to ProcessBundle
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn(name = "ORIGINAL_PROCESS_BUNDLE_ID", nullable = false)
	private ProcessBundle originalProcessBundle;

	public Process() {
	}

	public long getProcessId() {
		return this.processId;
	}

	public void setProcessId(long processId) {
		this.processId = processId;
	}

	public String getConfiguration() {
		return this.configuration;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}

	public Date getInstallationDate() {
		return this.installationDate;
	}

	public void setInstallationDate(Date installationDate) {
		this.installationDate = installationDate;
	}

	public String getProcessEPR() {
		return this.processEPR;
	}

	public void setProcessEPR(String processEPR) {
		this.processEPR = processEPR;
	}

	public String getProcessName() {
		return this.processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public ProcessType getProcessType() {
		return this.processType;
	}

	public void setProcessType(ProcessType processType) {
		this.processType = processType;
	}

	public ProcessStatus getProcessStatus() {
		return this.processStatus;
	}

	public void setProcessStatus(ProcessStatus processStatus) {
		this.processStatus = processStatus;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<ProcessInstance> getProcessInstances() {
		return this.processInstances;
	}

	public void setProcessInstances(List<ProcessInstance> processInstances) {
		this.processInstances = processInstances;
	}

	public ProcessBundle getInstrumentedProcessBundle() {
		return this.instrumentedProcessBundle;
	}

	public void setInstrumentedProcessBundle(
			ProcessBundle instrumentedProcessBundle) {
		this.instrumentedProcessBundle = instrumentedProcessBundle;
	}

	public ProcessBundle getOriginalProcessBundle() {
		return this.originalProcessBundle;
	}

	public void setOriginalProcessBundle(ProcessBundle originalProcessBundle) {
		this.originalProcessBundle = originalProcessBundle;
	}

}