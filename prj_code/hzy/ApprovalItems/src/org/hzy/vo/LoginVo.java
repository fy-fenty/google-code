package org.hzy.vo;


public class LoginVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	private String esn;
	private String pwd;
	private String checkCode;
	private String ipaddr;

	public LoginVo() {
		super();
	}

	public LoginVo(String esn, String pwd) {
		super();
		this.esn = esn;
		this.pwd = pwd;
	}

	public LoginVo(String esn, String pwd, String checkCode, String ipaddr) {
		super();
		this.esn = esn;
		this.pwd = pwd;
		this.checkCode = checkCode;
		this.ipaddr = ipaddr;
	}

	public String getEsn() {
		return esn;
	}

	public void setEsn(String esn) {
		this.esn = esn;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public String getIpaddr() {
		return ipaddr;
	}

	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}

}
