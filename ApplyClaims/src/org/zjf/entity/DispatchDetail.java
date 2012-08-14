package org.zjf.entity;


/**
 * DispatchDetail entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class DispatchDetail implements java.io.Serializable {

	// Fields

	private Long dsId;
	private Long sheetId;
	private Double money;
	private String costExplain;
	private Boolean flag;
	private Long itemId;
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
	public DispatchDetail(Long dsId, Long sheetId, Double money,
			String costExplain, Boolean flag, Long itemId,
			String accessory) {
		this.dsId = dsId;
		this.sheetId = sheetId;
		this.money = money;
		this.costExplain = costExplain;
		this.flag = flag;
		this.itemId = itemId;
		this.accessory = accessory;
	}

	// Property accessors

	public Long getDsId() {
		return this.dsId;
	}

	public void setDsId(Long dsId) {
		this.dsId = dsId;
	}

	public Long getSheetId() {
		return this.sheetId;
	}

	public void setSheetId(Long sheetId) {
		this.sheetId = sheetId;
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

	public Long getItemId() {
		return this.itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getAccessory() {
		return this.accessory;
	}

	public void setAccessory(String accessory) {
		this.accessory = accessory;
	}

}