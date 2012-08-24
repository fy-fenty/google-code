package org.zjf.vo;

public class LoginVo {
	private String Uname;
	private String pwd;
	private String vcode;
	private String ivode;
	
	public String getUname() {
		return Uname;
	}
	public void setUname(String uname) {
		Uname = uname;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getVcode() {
		return vcode;
	}
	public void setVcode(String vcode) {
		this.vcode = vcode;
	}
	public String getIvode() {
		return ivode;
	}
	public void setIvode(String ivode) {
		this.ivode = ivode;
	}
}
