package org.han.vo;

import java.util.Date;

public class DispatchListVo extends BaseVo {
	private Long dlId;
	private String ESn;
	private Date createTime;
	private String eventExplain;
	public Long getDlId() {
		return dlId;
	}
	public void setDlId(Long dlId) {
		this.dlId = dlId;
	}
	public String getESn() {
		return ESn;
	}
	public void setESn(String eSn) {
		ESn = eSn;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getEventExplain() {
		return eventExplain;
	}
	public void setEventExplain(String eventExplain) {
		this.eventExplain = eventExplain;
	}
	
}
