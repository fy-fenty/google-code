package org.ymm.vo;

import org.ymm.entity.LoginUser;
import org.ymm.entity.SysEmployee;

public class CurrentUser {

	private SysEmployee emp;
	private LoginUser user;
	public SysEmployee getEmp() {
		return emp;
	}
	public void setEmp(SysEmployee emp) {
		this.emp = emp;
	}
	public LoginUser getUser() {
		return user;
	}
	public void setUser(LoginUser user) {
		this.user = user;
	}

}
