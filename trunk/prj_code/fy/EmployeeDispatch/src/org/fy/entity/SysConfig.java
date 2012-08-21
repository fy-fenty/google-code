package org.fy.entity;
// default package

import java.util.Date;

/**
 * SysConfig entity. @author MyEclipse Persistence Tools
 */

public class SysConfig implements java.io.Serializable {

	// Fields

	private Long CId;
	private Long UserId;
	private Date begintime;
	private Date endtime;
	private Boolean isplaylog;

	// Constructors

	/** default constructor */
	public SysConfig() {
	}

	/** minimal constructor */
	public SysConfig(Long CId, Long UserId) {
		this.CId = CId;
		this.UserId = UserId;
	}

	/** full constructor */
	public SysConfig(Long CId, Long UserId, Date begintime,
			Date endtime, Boolean isplaylog) {
		this.CId = CId;
		this.UserId = UserId;
		this.begintime = begintime;
		this.endtime = endtime;
		this.isplaylog = isplaylog;
	}

	// Property accessors

	public Long getCId() {
		return this.CId;
	}

	public void setCId(Long CId) {
		this.CId = CId;
	}
	public Long getUserId() {
		return UserId;
	}
	public void setUserId(Long userId) {
		UserId = userId;
	}
	public Date getBegintime() {
		return this.begintime;
	}
	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}
	public Date getEndtime() {
		return this.endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public Boolean getIsplaylog() {
		return this.isplaylog;
	}

	public void setIsplaylog(Boolean isplaylog) {
		this.isplaylog = isplaylog;
	}

}