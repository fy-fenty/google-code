package org.hzy.web.action;

import net.sf.json.JSONSerializer;

import org.apache.struts2.ServletActionContext;
import org.hzy.constant.AppConstant;
import org.hzy.entity.LoginUser;
import org.hzy.exception.MyException;
import org.hzy.service.IEmployeeService;
import org.hzy.util.MyMatcher;
import org.hzy.util.SessionListener;
import org.hzy.vo.LoginVo;
import org.hzy.vo.Result;

public class LoginAciton {
	private IEmployeeService ieService;
	private LoginVo lgVo;

	public String login() {
		Result rs = new Result();
		String rand = (String) ServletActionContext.getRequest().getSession().getAttribute("rand");
		if (MyMatcher.isEmpty(rand)) {
			rs.setSuccess(false);
			rs.setMsg(AppConstant.A0024);
		}
		try {
			LoginUser lgUser = ieService.login(lgVo);
			if (lgUser != null) {
				System.out.println("removeSession ing............");
				SessionListener.removeSession(lgUser.getESn());
				System.out.println("addSession ing............");
				SessionListener.addSession(lgUser.getESn(), ServletActionContext.getRequest().getSession());
				System.out.println("login success");
			} else {
				System.out.println("login fail");
			}
			rs.setSuccess(true);
		} catch (MyException e) {
			rs.setSuccess(false);
			rs.setMsg(e.getMessage());
		}
		System.out.println(JSONSerializer.toJSON(rs));
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
