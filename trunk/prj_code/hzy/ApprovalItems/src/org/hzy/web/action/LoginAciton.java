package org.hzy.web.action;

import java.io.IOException;

import net.sf.json.JSONSerializer;

import org.apache.struts2.ServletActionContext;
import org.hzy.exception.MyException;
import org.hzy.service.IEmployeeService;
import org.hzy.vo.LoginVo;
import org.hzy.vo.Result;

public class LoginAciton {
	private IEmployeeService ieService;
	private LoginVo lgVo;

	public String login() {
		Result rs = new Result();
		try {
			String esn = ieService.login(lgVo);
			if (esn != null) {
				rs.setSuccess(true);
			} else {
				rs.setSuccess(false);
			}
		} catch (MyException e) {
			rs.setSuccess(false);
			rs.setMsg(e.getMessage());
		}
		Object json = JSONSerializer.toJSON(rs);
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		try {
			ServletActionContext.getResponse().getWriter().print(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public IEmployeeService getIeService() {
		return ieService;
	}

	public void setIeService(IEmployeeService ieService) {
		this.ieService = ieService;
	}

	public LoginVo getLgVo() {
		return lgVo;
	}

	public void setLgVo(LoginVo lgVo) {
		this.lgVo = lgVo;
	}

}
