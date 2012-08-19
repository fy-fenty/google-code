package org.fy.vo;

public class DispatchListVO extends BaseVO{
	private Long dlId;
	private String sn;
	private String eventExplain;
	public Long getDlId() {
		return dlId;
	}
	public void setDlId(Long dlId) {
		this.dlId = dlId;
	}
	
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getEventExplain() {
		return eventExplain;
	}
	public void setEventExplain(String eventExplain) {
		this.eventExplain = eventExplain;
	}
	public DispatchListVO(Long dlId, String eventExplain) {
		super();
		this.dlId = dlId;
		this.eventExplain = eventExplain;
	}
	public DispatchListVO() {
		super();
	}
	
}
