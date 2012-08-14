package org.ymm.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * DispatchStatus entity. @author MyEclipse Persistence Tools
 */

public class DispatchStatus{

	// Fields

	private Long daId;
	private String daStatus;

	// Constructors

	/** default constructor */
	public DispatchStatus() {
	}

	/** minimal constructor */
	public DispatchStatus(Long daId) {
		this.daId = daId;
	}

	/** full constructor */
	public DispatchStatus(Long daId, String daStatus) {
		this.daId = daId;
		this.daStatus = daStatus;
	}

	// Property accessors

	public Long getDaId() {
		return this.daId;
	}

	public void setDaId(Long daId) {
		this.daId = daId;
	}

	public String getDaStatus() {
		return this.daStatus;
	}

	public void setDaStatus(String daStatus) {
		this.daStatus = daStatus;
	}

}