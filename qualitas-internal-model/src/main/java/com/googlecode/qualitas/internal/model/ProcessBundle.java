package com.googlecode.qualitas.internal.model;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the PROCESS_BUNDLES database table.
 * 
 */
@Entity
@Table(name="PROCESS_BUNDLES")
public class ProcessBundle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PROCESS_BUNDLES_PROCESSBUNDLEID_GENERATOR", sequenceName="PROCESS_BUNDLE_ID_SEQUENCE", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PROCESS_BUNDLES_PROCESSBUNDLEID_GENERATOR")
	@Column(name="PROCESS_BUNDLE_ID", nullable=false, precision=22)
	private long processBundleId;

    @Lob()
	@Column(name="\"CONTENTS\"", nullable=false)
	private byte[] contents;
    
    public ProcessBundle() {
    }

	public long getProcessBundleId() {
		return this.processBundleId;
	}

	public void setProcessBundleId(long processBundleId) {
		this.processBundleId = processBundleId;
	}

	public byte[] getContents() {
		return this.contents;
	}

	public void setContents(byte[] contents) {
		this.contents = Arrays.copyOf(contents, contents.length);
	}

}