package org.ymm.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * DispatchResult entity. @author MyEclipse Persistence Tools
 */

public class DispatchResult implements java.io.Serializable {

	// Fields

	private Long drId;
	private Long dispatchStatus;
	private Long dispatchList;
	private String checkNext;
	private Timestamp checkTime;
	private String checkSn;
	private String checkComment;

	// Constructors

	/** default constructor */
	public DispatchResult() {
	}

	/** minimal constructor */
	public DispatchResult(Long drId) {
		this.drId = drId;
	}

	/** full constructor */
	public DispatchResult(Long drId, Long dispatchStatus,
			Long dispatchList, String checkNext, Timestamp checkTime,
			String checkSn, String checkComment) {
		this.drId = drId;
		this.dispatchStatus = dispatchStatus;
		this.dispatchList = dispatchList;
		this.checkNext = checkNext;
		this.checkTime = checkTime;
		this.checkSn = checkSn;
		this.checkComment = checkComment;
	}

	// Property accessors

	public Long getDrId() {
		return this.drId;
	}

	public void setDrId(Long drId) {
		this.drId = drId;
	}

	public Long getDispatchStatus() {
		return this.dispatchStatus;
	}

	public void setDispatchStatus(Long dispatchStatus) {
		this.dispatchStatus = dispatchStatus;
	}

	public Long getDispatchList() {
		return this.dispatchList;
	}

	public void setDispatchList(Long dispatchList) {
		this.dispatchList = dispatchList;
	}

	public String getCheckNext() {
		return this.checkNext;
	}

	public void setCheckNext(String checkNext) {
		this.checkNext = checkNext;
	}

	public Timestamp getCheckTime() {
		return this.checkTime;
	}

	public void setCheckTime(Timestamp checkTime) {
		this.checkTime = checkTime;
	}

	public String getCheckSn() {
		return this.checkSn;
	}

	public void setCheckSn(String checkSn) {
		this.checkSn = checkSn;
	}

	public String getCheckComment() {
		return this.checkComment;
	}

	public void setCheckComment(String checkComment) {
		this.checkComment = checkComment;
	}

}