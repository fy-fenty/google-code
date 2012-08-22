package org.zjf.actions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.ymm.util.Json;
import org.ymm.vo.LoginUserVo;
import org.ymm.vo.Result;
import org.zjf.services.IEmpService;

public class LoginAction {

	private LoginUserVo uservo = new LoginUserVo();
	private IEmpService emp;

	public IEmpService getEmp() {
		return emp;
	}

	public void setEmp(IEmpService emp) {
		this.emp = emp;
	}

	public LoginUserVo getUservo() {
		return uservo;
	}

	public void setUservo(LoginUserVo uservo) {
		this.uservo = uservo;
	}

	public String login() throws IOException {
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();
		Result result = emp.loginUser(uservo);
		String json=Json.JSON_Object(result);
		out.print(json);
		return null;
	}
}
