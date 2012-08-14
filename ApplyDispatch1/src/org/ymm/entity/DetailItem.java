package org.ymm.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * DetailItem entity. @author MyEclipse Persistence Tools
 */

public class DetailItem {

	// Fields

	private Long deId;
	private String itemName;

	// Constructors

	/** default constructor */
	public DetailItem() {
	}

	/** minimal constructor */
	public DetailItem(Long deId) {
		this.deId = deId;
	}

	/** full constructor */
	public DetailItem(Long deId, String itemName) {
		this.deId = deId;
		this.itemName = itemName;
	}

	// Property accessors

	public Long getDeId() {
		return this.deId;
	}

	public void setDeId(Long deId) {
		this.deId = deId;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
}