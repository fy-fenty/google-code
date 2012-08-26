package org.han.entity;

import java.lang.Long;
import java.util.Date;

/**
 * DispatchResult entity. @author MyEclipse Persistence Tools
 */

public class DispatchResult implements java.io.Serializable {

	// Fields

	private Long drId;
	private Long sheetId;
	private String checkNext;
	private Date checkTime;
	private String checkSn;
	private String checkComment;
	private Long checkStatus;

	// Constructors

	/** default constructor */
	public DispatchResult() {
	}

	/** minimal constructor */
	public DispatchResult(Long drId) {
		this.drId = drId;
	}

	/** full constructor */
	public DispatchResult(Long drId, Long sheetId,
			String checkNext, Date checkTime, String checkSn,
			String checkComment, Long checkStatus) {
		this.drId = drId;
		this.sheetId = sheetId;
		this.checkNext = checkNext;
		this.checkTime = checkTime;
		this.checkSn = checkSn;
		this.checkComment = checkComment;
		this.checkStatus = checkStatus;
	}

	// Property accessors

	public Long getDrId() {
		return this.drId;
	}

	public void setDrId(Long drId) {
		this.drId = drId;
	}

	public Long getSheetId() {
		return this.sheetId;
	}

	public void setSheetId(Long sheetId) {
		this.sheetId = sheetId;
	}

	public String getCheckNext() {
		return this.checkNext;
	}

	public void setCheckNext(String checkNext) {
		this.checkNext = checkNext;
	}

	public Date getCheckTime() {
		return this.checkTime;
	}

	public void setCheckTime(Date checkTime) {
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

	public Long getCheckStatus() {
		return this.checkStatus;
	}

	public void setCheckStatus(Long checkStatus) {
		this.checkStatus = checkStatus;
	}

}