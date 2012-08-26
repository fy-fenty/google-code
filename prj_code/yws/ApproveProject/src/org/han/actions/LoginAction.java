package org.han.actions;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.han.constant.AppConstant;
import org.han.dao.ISysPositionsDao;
import org.han.entity.LoginUser;
import org.han.entity.SysPositions;
import org.han.services.IEmpService;
import org.han.services.ISystemServices;
import org.han.services.impl.EmpServiceImpl;
import org.han.util.AppUtils;
import org.han.util.CurrentUser;
import org.han.util.JsonValueProcessorImpl;
import org.han.util.SessionListener;
import org.han.vo.LoginVo;
import org.han.vo.Result;

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
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = ServletActionContext.getResponse().getWriter();
			HttpSession session = request.getSession();
			String rand = (String) session.getAttribute("rand");
			if (!loginVo.getRandCode().equals(rand)) {// 验证码不匹配
			}
			String ip = request.getRemoteAddr();
			if (!AppUtils.isIpAddress(ip)) {// 如果不是IP地址
				return null;
			}
			SysPositions pos = sysBiz.getSysPositionsBySN(loginVo.getUname());
			if (null == pos) {
				return null;
			}
			Long pId = pos.getPId();
			LoginUser user = null;
			if (pId == 3) {
				user = empBiz.login(loginVo);
			}
			if (null != user) {
				CurrentUser currUser = new CurrentUser();
				currUser.seteSN(user.getESn());
				currUser.setIpAddr(ip);
				currUser.setPos(pos);
				Map<String, HttpSession> map = SessionListener.getSessionMaps();
				for (String s : map.keySet()) {
					HttpSession sess = map.get(s);
					CurrentUser cu = (CurrentUser) sess
							.getAttribute(AppConstant.CURRENT_USER);
					if (s.equals(session.getId())) {
						continue;
					}
					if (null != cu && user.getESn().equals(cu.geteSN())) {
						sess.setAttribute(AppConstant.CURRENT_USER, null);
						SessionListener.removeSession(sess.getId());
						break;
					}
				}
				SessionListener.addSession(session.getId(), session);
				session.setAttribute(AppConstant.CURRENT_USER, currUser);
				String str = new JsonValueProcessorImpl("yyyy-MM-dd HH:mm:ss")
						.getStrByObject(new Result(true, "登录成功"));
				out.print(str);
			}
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

}
