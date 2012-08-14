package org.ymm.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * SysDepartment entity. @author MyEclipse Persistence Tools
 */

public class SysDepartment implements java.io.Serializable {

	// Fields

	private Long DId;
	private Long area;
	private String DName;
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
	public SysDepartment(Long DId, Long area, String DName,
			String manageSn) {
		this.DId = DId;
		this.area = area;
		this.DName = DName;
		this.manageSn = manageSn;
	}

	// Property accessors

	public Long getDId() {
		return this.DId;
	}

	public void setDId(Long DId) {
		this.DId = DId;
	}

	public Long getArea() {
		return this.area;
	}

	public void setArea(Long area) {
		this.area = area;
	}

	public String getDName() {
		return this.DName;
	}

	public void setDName(String DName) {
		this.DName = DName;
	}

	public String getManageSn() {
		return this.manageSn;
	}

	public void setManageSn(String manageSn) {
		this.manageSn = manageSn;
	}

}