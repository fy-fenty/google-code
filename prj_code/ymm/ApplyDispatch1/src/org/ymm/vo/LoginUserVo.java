package org.ymm.vo;

public class LoginUserVo extends BaseVo {

	private Long UId;
	private String ESn;
	private String UPwd;
	private String core;
	public String getCore() {
		return core;
	}
	public void setCore(String core) {
		this.core = core;
	}
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
	
}
