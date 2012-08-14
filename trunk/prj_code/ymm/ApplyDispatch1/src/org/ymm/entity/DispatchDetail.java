package org.ymm.entity;

import java.math.BigDecimal;

/**
 * DispatchDetail entity. @author MyEclipse Persistence Tools
 */

public class DispatchDetail implements java.io.Serializable {

	// Fields

	private Long dsId;
	private Long detailItem;
	private Long dispatchList;
	private Double money;
	private String costExplain;
	private Boolean flag;
	private String accessory;

	// Constructors

	/** default constructor */
	public DispatchDetail() {
	}

	/** minimal constructor */
	public DispatchDetail(Long dsId) {
		this.dsId = dsId;
	}

	/** full constructor */
	public DispatchDetail(Long dsId, Long detailItem,
			Long dispatchList, Double money, String costExplain,
			Boolean flag, String accessory) {
		this.dsId = dsId;
		this.detailItem = detailItem;
		this.dispatchList = dispatchList;
		this.money = money;
		this.costExplain = costExplain;
		this.flag = flag;
		this.accessory = accessory;
	}

	// Property accessors

	public Long getDsId() {
		return this.dsId;
	}

	public void setDsId(Long dsId) {
		this.dsId = dsId;
	}

	public Long getDetailItem() {
		return this.detailItem;
	}

	public void setDetailItem(Long detailItem) {
		this.detailItem = detailItem;
	}

	public Long getDispatchList() {
		return this.dispatchList;
	}

	public void setDispatchList(Long dispatchList) {
		this.dispatchList = dispatchList;
	}

	public Double getMoney() {
		return this.money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getCostExplain() {
		return this.costExplain;
	}

	public void setCostExplain(String costExplain) {
		this.costExplain = costExplain;
	}

	public Boolean getFlag() {
		return this.flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public String getAccessory() {
		return this.accessory;
	}

	public void setAccessory(String accessory) {
		this.accessory = accessory;
	}

}