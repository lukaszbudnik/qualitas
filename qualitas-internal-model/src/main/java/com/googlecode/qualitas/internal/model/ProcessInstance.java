package com.googlecode.qualitas.internal.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the PROCESS_INSTANCES database table.
 * 
 */
@Entity
@Table(name="PROCESS_INSTANCES")
public class ProcessInstance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PROCESS_INSTANCES_PROCESSINSTANCEID_GENERATOR", sequenceName="PROCESS_INSTANCE_ID_SEQUENCE", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PROCESS_INSTANCES_PROCESSINSTANCEID_GENERATOR")
	@Column(name="PROCESS_INSTANCE_ID", nullable=false, precision=22)
	private long processInstanceId;

    @Temporal( TemporalType.DATE)
	@Column(name="END_TIME")
	private Date endTime;

	@Column(name="ERROR_MESSAGE", length=1000)
	private String errorMessage;

	@Column(name="PROCESS_INSTANCE_STATE", nullable=false, length=100)
	private String processInstanceState;

    @Temporal( TemporalType.DATE)
	@Column(name="START_TIME")
	private Date startTime;

	//bi-directional many-to-one association to Process
    @ManyToOne
	@JoinColumn(name="PROCESS_ID", nullable=false)
	private Process process;

	//bi-directional many-to-one association to Trace
	@OneToMany(mappedBy="processInstance")
	private List<Trace> traces;

    public ProcessInstance() {
    }

	public long getProcessInstanceId() {
		return this.processInstanceId;
	}

	public void setProcessInstanceId(long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getProcessInstanceState() {
		return this.processInstanceState;
	}

	public void setProcessInstanceState(String processInstanceState) {
		this.processInstanceState = processInstanceState;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Process getProcess() {
		return this.process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}
	
	public List<Trace> getTraces() {
		return this.traces;
	}

	public void setTraces(List<Trace> traces) {
		this.traces = traces;
	}
	
}