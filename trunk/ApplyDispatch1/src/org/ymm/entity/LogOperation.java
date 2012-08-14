package org.ymm.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * LogOperation entity. @author MyEclipse Persistence Tools
 */

public class LogOperation{

	// Fields

	private Long loId;
	private String ipAddr;
	private Long userId;
	private String methodType;
	private String methodName;
	private String className;
	private String describe;
	private String exceptionInfo;
	private Timestamp operateDate;
	private Long expandTime;

	// Constructors

	/** default constructor */
	public LogOperation() {
	}

	/** minimal constructor */
	public LogOperation(Long loId) {
		this.loId = loId;
	}

	/** full constructor */
	public LogOperation(Long loId, String ipAddr, Long userId,
			String methodType, String methodName, String className,
			String describe, String exceptionInfo, Timestamp operateDate,
			Long expandTime) {
		this.loId = loId;
		this.ipAddr = ipAddr;
		this.userId = userId;
		this.methodType = methodType;
		this.methodName = methodName;
		this.className = className;
		this.describe = describe;
		this.exceptionInfo = exceptionInfo;
		this.operateDate = operateDate;
		this.expandTime = expandTime;
	}

	// Property accessors

	public Long getLoId() {
		return this.loId;
	}

	public void setLoId(Long loId) {
		this.loId = loId;
	}

	public String getIpAddr() {
		return this.ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getMethodType() {
		return this.methodType;
	}

	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}

	public String getMethodName() {
		return this.methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getDescribe() {
		return this.describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getExceptionInfo() {
		return this.exceptionInfo;
	}

	public void setExceptionInfo(String exceptionInfo) {
		this.exceptionInfo = exceptionInfo;
	}

	public Timestamp getOperateDate() {
		return this.operateDate;
	}

	public void setOperateDate(Timestamp operateDate) {
		this.operateDate = operateDate;
	}

	public Long getExpandTime() {
		return this.expandTime;
	}

	public void setExpandTime(Long expandTime) {
		this.expandTime = expandTime;
	}

}