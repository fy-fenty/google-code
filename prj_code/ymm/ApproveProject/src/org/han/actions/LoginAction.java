package org.han.actions;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.han.dao.ISysPositionsDao;
import org.han.entity.LoginUser;
import org.han.entity.SysPositions;
import org.han.services.IEmpService;
import org.han.services.ISystemServices;
import org.han.services.impl.EmpServiceImpl;
import org.han.util.AppUtils;
import org.han.util.SessionListener;
import org.han.vo.LoginVo;

import com.opensymphony.xwork2.ActionSupport;

@Namespace("/login")
public class LoginAction extends ActionSupport {

	private IEmpService empBiz;
	private ISystemServices sysBiz;
	private LoginVo loginVo;

	public ISystemServices getSysBiz() {
		return sysBiz;
	}

	public void setSysBiz(ISystemServices sysBiz) {
		this.sysBiz = sysBiz;
	}

	public IEmpService getEmpBiz() {
		return empBiz;
	}

	public void setEmpBiz(IEmpService empBiz) {
		this.empBiz = empBiz;
	}

	public LoginVo getLoginVo() {
		return loginVo;
	}

	public void setLoginVo(LoginVo loginVo) {
		this.loginVo = loginVo;
	}

	@Action(value = "login", className = "loginAction")
	public String login() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			PrintWriter out = ServletActionContext.getResponse().getWriter();
			HttpSession session = request.getSession();
			String rand = (String) session.getAttribute("rand");
			if (!loginVo.getRandCode().equals(rand)) {// 验证码不匹配
			}
			String ip = request.getRemoteAddr();
			System.out.println(ip);
			if (!AppUtils.isIpAddress(ip)) {// 如果不是IP地址
			}
			LoginUser user = empBiz.login(loginVo);
			if (null != user) {
				HttpSession sess = SessionListener.getSessionMaps().get(
						user.getESn());
				if (sess != null) {
					sess.invalidate();
					SessionListener.removeSession(user.getESn());
				}
				SessionListener.addSession(user.getESn(), session);
				session.setAttribute("user", user);

				SysPositions pos = sysBiz.getSysPositionsBySN(user.getESn());
				Long pId = pos.getPId();

				out.print("1");
			}
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
}
