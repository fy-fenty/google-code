package org.zjf.actions;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.ymm.vo.LoginUserVo;

public class LoginAction {

	private LoginUserVo uservo = new LoginUserVo();

	public LoginUserVo getUservo() {
		return uservo;
	}

	public void setUservo(LoginUserVo uservo) {
		this.uservo = uservo;
	}

	public String login() {
		System.out.println(111);
		HttpSession sess=ServletActionContext.getRequest().getSession();
		if((sess.getAttribute("rand")+"").equals(uservo.getCore()))
		{
			
			
		}
		return null;
	}
}
