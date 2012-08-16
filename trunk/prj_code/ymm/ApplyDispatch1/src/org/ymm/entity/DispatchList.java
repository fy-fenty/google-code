package org.ymm.entity;

import java.util.Date;

/**
 * DispatchList entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class DispatchList implements java.io.Serializable {

	// Fields

	private Long dlId;
	private String ESn;
	private Date createTime;
	private String eventExplain;
	private Boolean flag;

	// Constructors

	/** default constructor */
	public DispatchList() {
	}

	/** minimal constructor */
	public DispatchList(Long dlId) {
		this.dlId = dlId;
	}

	/** full constructor */
	public DispatchList(Long dlId, String ESn, Date createTime,
			String eventExplain, Boolean flag) {
		this.dlId = dlId;
		this.ESn = ESn;
		this.createTime = createTime;
		this.eventExplain = eventExplain;
		this.flag = flag;
	}

	// Property accessors

	public Long getDlId() {
		return this.dlId;
	}

	public void setDlId(Long dlId) {
		this.dlId = dlId;
	}

	public String getESn() {
		return this.ESn;
	}

	public void setESn(String ESn) {
		this.ESn = ESn;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getEventExplain() {
		return this.eventExplain;
	}

	public void setEventExplain(String eventExplain) {
		this.eventExplain = eventExplain;
	}

	public Boolean getFlag() {
		return this.flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

}