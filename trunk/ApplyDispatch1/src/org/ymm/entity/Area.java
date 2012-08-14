package org.ymm.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Area entity. @author MyEclipse Persistence Tools
 */

public class Area{

	// Fields

	private Long areaId;
	private String areaName;

	// Constructors

	/** default constructor */
	public Area() {
	}

	/** minimal constructor */
	public Area(Long areaId) {
		this.areaId = areaId;
	}

	/** full constructor */
	public Area(Long areaId, String areaName) {
		this.areaId = areaId;
		this.areaName = areaName;
	}

	// Property accessors

	public Long getAreaId() {
		return this.areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

}