package org.fy.vo;

public class CurrentUserVO extends BaseVO{
	private Long UId;
	private String ESn;
	private String UPwd;
	private Long departmentId;
	private Long PId;
	public Long getUId() {
		return UId;
	}
	public void setUId(Long uId) {
		UId = uId;
	}
	public String getESn() {
		return ESn;
	}
	public void setESn(String eSn) {
		ESn = eSn;
	}
	public String getUPwd() {
		return UPwd;
	}
	public void setUPwd(String uPwd) {
		UPwd = uPwd;
	}
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public Long getPId() {
		return PId;
	}
	public void setPId(Long pId) {
		PId = pId;
	}
	
	
	
}
