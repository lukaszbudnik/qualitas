package com.googlecode.qualitas.internal.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the TRACES database table.
 * 
 */
@Entity
@Table(name="TRACES")
public class Trace implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TRACES_TRACEID_GENERATOR", sequenceName="TRACE_ID_SEQUENCE", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TRACES_TRACEID_GENERATOR")
	@Column(name="TRACE_ID", nullable=false, precision=22)
	private long traceId;

    @Temporal( TemporalType.DATE)
	@Column(name="END_TIME", nullable=false)
	private Date endTime;

	@Column(nullable=false, length=1000)
	private String EPR;

	@Column(name="\"INPUT\"", length=20)
	private String input;

	@Column(name="INVOCATION_STEP", nullable=false, length=100)
	private String invocationStep;

	@Column(nullable=false, length=10)
	private String mep;

	@Column(name="\"OUTPUT\"", length=20)
	private String output;

	@Column(nullable=false, length=200)
	private String partner;

	@Column(name="SEQUENCE_NUMBER", nullable=false, precision=22)
	private BigDecimal sequenceNumber;

	@Column(name="\"SERVICE\"", nullable=false, length=200)
	private String service;

    @Temporal( TemporalType.DATE)
	@Column(name="START_TIME", nullable=false)
	private Date startTime;

	//bi-directional many-to-one association to Score
	@OneToMany(mappedBy="trace")
	private List<Score> scores;

	//bi-directional many-to-one association to ProcessInstance
    @ManyToOne
	@JoinColumn(name="PROCESS_INSTANCE_ID", nullable=false)
	private ProcessInstance processInstance;

    public Trace() {
    }

	public long getTraceId() {
		return this.traceId;
	}

	public void setTraceId(long traceId) {
		this.traceId = traceId;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getEPR() {
		return this.EPR;
	}

	public void setEPR(String EPR) {
		this.EPR = EPR;
	}

	public String getInput() {
		return this.input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getInvocationStep() {
		return this.invocationStep;
	}

	public void setInvocationStep(String invocationStep) {
		this.invocationStep = invocationStep;
	}

	public String getMep() {
		return this.mep;
	}

	public void setMep(String mep) {
		this.mep = mep;
	}

	public String getOutput() {
		return this.output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getPartner() {
		return this.partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public BigDecimal getSequenceNumber() {
		return this.sequenceNumber;
	}

	public void setSequenceNumber(BigDecimal sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public String getService() {
		return this.service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public List<Score> getScores() {
		return this.scores;
	}

	public void setScores(List<Score> scores) {
		this.scores = scores;
	}
	
	public ProcessInstance getProcessInstance() {
		return this.processInstance;
	}

	public void setProcessInstance(ProcessInstance processInstance) {
		this.processInstance = processInstance;
	}
	
}