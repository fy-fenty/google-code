/**
 * 
 */
package org.han.vo;

/**
 * @author tender  
 * @date 2012-8-17   
 * @ClassName: DetailVo    
 * @Description: TODO   
 * @version    
 *  
 */
public class DetailVo {
	private Double money;
	private String costExplain;
	private Long itemId;
	private String accessory;
	private Long detailId;
	public Long getDetailId() {
		return detailId;
	}
	public void setDetailId(Long detailId) {
		this.detailId = detailId;
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
