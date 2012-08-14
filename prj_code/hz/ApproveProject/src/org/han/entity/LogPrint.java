package org.han.entity;

import java.util.Date;

/**
 * LogPrint entity. @author MyEclipse Persistence Tools
 */

public class LogPrint implements java.io.Serializable {

	// Fields

	private Long lpId;
	private Date printDate;
	private String ipAddr;
	private String macAddr;
	private Long userId;

	// Constructors

	/** default constructor */
	public LogPrint() {
	}

	/** minimal constructor */
	public LogPrint(Long lpId) {
		this.lpId = lpId;
	}

	/** full constructor */
	public LogPrint(Long lpId, Date printDate, String ipAddr,
			String macAddr, Long userId) {
		this.lpId = lpId;
		this.printDate = printDate;
		this.ipAddr = ipAddr;
		this.macAddr = macAddr;
		this.userId = userId;
	}

	// Property accessors

	public Long getLpId() {
		return this.lpId;
	}

	public void setLpId(Long lpId) {
		this.lpId = lpId;
	}

	public Date getPrintDate() {
		return this.printDate;
	}

	public void setPrintDate(Date printDate) {
		this.printDate = printDate;
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

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}