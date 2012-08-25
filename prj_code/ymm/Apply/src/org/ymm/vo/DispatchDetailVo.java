package org.ymm.vo;

import java.sql.Blob;

public class DispatchDetailVo extends BaseVo {

	private Long dsId;
	private Long sheetId;
	private Double money;
	private String costExplain;
	private Boolean flag;
	private Long itemId;
	private Blob accessory;
	public Long getDsId() {
		return dsId;
	}
	public void setDsId(Long dsId) {
		this.dsId = dsId;
	}
	public Long getSheetId() {
		return sheetId;
	}
	public void setSheetId(Long sheetId) {
		this.sheetId = sheetId;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public String getCostExplain() {
		return costExplain;
	}
	public void setCostExplain(String costExplain) {
		this.costExplain = costExplain;
	}
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public Blob getAccessory() {
		return accessory;
	}
	public void setAccessory(Blob accessory) {
		this.accessory = accessory;
	}
	
}
