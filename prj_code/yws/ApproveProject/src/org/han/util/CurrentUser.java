package org.han.util;

import org.han.entity.SysPositions;

public class CurrentUser {
	private String eSN;
	private String eName;
	private String pwd;
	private String ipAddr;
	private SysPositions pos;

	public SysPositions getPos() {
		return pos;
	}

	public void setPos(SysPositions pos) {
		this.pos = pos;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String geteSN() {
		return eSN;
	}

	public void seteSN(String eSN) {
		this.eSN = eSN;
	}

	public String geteName() {
		return eName;
	}

	public void seteName(String eName) {
		this.eName = eName;
	}
}
