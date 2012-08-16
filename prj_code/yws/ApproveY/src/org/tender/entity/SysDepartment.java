package org.tender.entity;

import java.lang.Long;

/**
 * SysDepartment entity. @author MyEclipse Persistence Tools
 */

public class SysDepartment implements java.io.Serializable {

	// Fields

	private Long DId;
	private String DName;
	private Long areaId;
	private String manageSn;

	// Constructors

	/** default constructor */
	public SysDepartment() {
	}

	/** minimal constructor */
	public SysDepartment(Long DId) {
		this.DId = DId;
	}

	/** full constructor */
	public SysDepartment(Long DId, String DName, Long areaId,
			String manageSn) {
		this.DId = DId;
		this.DName = DName;
		this.areaId = areaId;
		this.manageSn = manageSn;
	}

	// Property accessors

	public Long getDId() {
		return this.DId;
	}

	public void setDId(Long DId) {
		this.DId = DId;
	}

	public String getDName() {
		return this.DName;
	}

	public void setDName(String DName) {
		this.DName = DName;
	}

	public Long getAreaId() {
		return this.areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public String getManageSn() {
		return this.manageSn;
	}

	public void setManageSn(String manageSn) {
		this.manageSn = manageSn;
	}

}