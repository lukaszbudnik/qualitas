package com.googlecode.qualitas.internal.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the SCORES database table.
 * 
 */
@Entity
@Table(name="SCORES")
public class Score implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SCORES_SCOREID_GENERATOR", sequenceName="SCORE_ID_SEQUENCE", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SCORES_SCOREID_GENERATOR")
	@Column(name="SCORE_ID", nullable=false, precision=22)
	private long scoreId;

	@Column(nullable=false, length=100)
	private String metric;
	
	@Column(name="APPLIED_WEIGHT", nullable=false, precision=126)
	private double appliedWeight;

	@Column(name="MEASURED_VALUE", nullable=false, precision=126)
	private double measuredValue;

	@Column(nullable=false, precision=126)
	private double score;

	//bi-directional many-to-one association to Trace
    @ManyToOne
	@JoinColumn(name="TRACE_ID", nullable=false)
	private Trace trace;

    public Score() {
    }

	public long getScoreId() {
		return this.scoreId;
	}

	public void setScoreId(long scoreId) {
		this.scoreId = scoreId;
	}

	public String getMetric() {
		return metric;
	}

	public void setMetric(String metric) {
		this.metric = metric;
	}

	public double getAppliedWeight() {
		return this.appliedWeight;
	}

	public void setAppliedWeight(double appliedWeight) {
		this.appliedWeight = appliedWeight;
	}

	public double getMeasuredValue() {
		return this.measuredValue;
	}

	public void setMeasuredValue(double measuredValue) {
		this.measuredValue = measuredValue;
	}

	public double getScore() {
		return this.score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public Trace getTrace() {
		return this.trace;
	}

	public void setTrace(Trace trace) {
		this.trace = trace;
	}
	
}