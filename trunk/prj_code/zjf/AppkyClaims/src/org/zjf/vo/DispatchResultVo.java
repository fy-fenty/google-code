package org.zjf.vo;

import java.util.Date;

public class DispatchResultVo extends BaseVo {

	private Long drId;
	private Long sheetId;
	private String checkNext;
	private Date checkTime;
	private String checkSn;
	private String checkComment;
	private Long checkStatus;
	public Long getDrId() {
		return drId;
	}
	public void setDrId(Long drId) {
		this.drId = drId;
	}
	public Long getSheetId() {
		return sheetId;
	}
	public void setSheetId(Long sheetId) {
		this.sheetId = sheetId;
	}
	public String getCheckNext() {
		return checkNext;
	}
	public void setCheckNext(String checkNext) {
		this.checkNext = checkNext;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public String getCheckSn() {
		return checkSn;
	}
	public void setCheckSn(String checkSn) {
		this.checkSn = checkSn;
	}
	public String getCheckComment() {
		return checkComment;
	}
	public void setCheckComment(String checkComment) {
		this.checkComment = checkComment;
	}
	public Long getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(Long checkStatus) {
		this.checkStatus = checkStatus;
	}
	
}
