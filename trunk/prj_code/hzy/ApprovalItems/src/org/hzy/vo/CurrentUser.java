package org.hzy.vo;

public class CurrentUser {
	private Long uid;
	private String uname;

	public CurrentUser() {
		super();
	}

	public CurrentUser(Long uid, String uname) {
		super();
		this.uid = uid;
		this.uname = uname;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

}