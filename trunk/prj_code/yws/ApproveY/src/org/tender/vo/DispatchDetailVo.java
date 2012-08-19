package org.tender.vo;

public class DispatchDetailVo extends BaseVo {
	private Long dsId;
	private Long sheetId;
	private Double money;
	private String costExplain;
	private Long itemId;
	private String accessory;
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
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getAccessory() {
		return accessory;
	}
	public void setAccessory(String accessory) {
		this.accessory = accessory;
	}
}
