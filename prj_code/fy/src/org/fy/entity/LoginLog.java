package org.fy.entity;

import java.lang.Long;
import java.util.Date;

/**
 * LoginLog entity. @author MyEclipse Persistence Tools
 */

public class LoginLog implements java.io.Serializable {

	// Fields

	private Long logid;
	private Long userId;
	private String userSn;
	private Long position;
	private String ipAddr;
	private String macAddr;
	private Date logDate;
	private String sessionId;
	private String remark;

	// Constructors

	/** default constructor */
	public LoginLog() {
	}

	/** minimal constructor */
	public LoginLog(Long logid) {
		this.logid = logid;
	}

	/** full constructor */
	public LoginLog(Long logid, Long userId, String userSn,
			Long position, String ipAddr, String macAddr, Date logDate,
			String sessionId, String remark) {
		this.logid = logid;
		this.userId = userId;
		this.userSn = userSn;
		this.position = position;
		this.ipAddr = ipAddr;
		this.macAddr = macAddr;
		this.logDate = logDate;
		this.sessionId = sessionId;
		this.remark = remark;
	}

	// Property accessors

	public Long getLogid() {
		return this.logid;
	}

	public void setLogid(Long logid) {
		this.logid = logid;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserSn() {
		return this.userSn;
	}

	public void setUserSn(String userSn) {
		this.userSn = userSn;
	}

	public Long getPosition() {
		return this.position;
	}

	public void setPosition(Long position) {
		this.position = position;
	}

	public String getIpAddr() {
		return this.ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getMacAddr() {
		return this.macAddr;
	}

	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}

	public Date getLogDate() {
		return this.logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public String getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}