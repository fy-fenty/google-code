package org.fy.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.fy.entity.LoginUser;
import org.fy.exception.MyExecption;
import org.fy.service.ISysEmployeeService;
import org.fy.util.JsonUtil;
import org.fy.utils.SessionListener;
import org.fy.vo.BaseVO;
import org.fy.vo.Page;
import org.fy.vo.UserVO;

/**
 * @author hzy
 * @date 2012-8-20
 *	@extends Object
 * @class SysEmployeeAction
 * @description action类
 */
public class SysEmployeeAction {
	private ISysEmployeeService isysemp_service;
	private UserVO uv;
	
	public ISysEmployeeService getIsysemp_service() {
		return isysemp_service;
	}
	public void setIsysemp_service(ISysEmployeeService isysemp_service) {
		this.isysemp_service = isysemp_service;
	}
	public UserVO getUv() {
		return uv;
	}
	public void setUv(UserVO uv) {
		this.uv = uv;
	}
	
	@Action(value="login",className="sys_employee_action",results={@Result(name="success",location="/index.jsp",type="dispatcher"),
			@Result(name="error",location="/html/login.html",type="dispatcher")})
	public String sys_login(){
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!syslogin");
		System.out.println(uv.getEsn()+"  "+uv.getPwd());
		
		HttpServletRequest request=(HttpServletRequest)ServletActionContext.getRequest();	
		
//		String code = (String)request.getSession().getAttribute("rand");
//		if(!uv.getCode().equals(code)){
//			return "error";
//		}
		String esn=uv.getEsn().trim();
//		HttpSession sessionold=SessionListener.getSessionMaps().get(esn);
		System.out.println(request.getSession().getId());
		
		 if (SessionListener.getSessionMaps().get(esn)!= null
                 && SessionListener.getSessionMaps().get(esn)
                                 .toString().length() > 0) {
			 System.out.println(SessionListener.getSessionMaps().get(esn).getId());
			 HttpSession sessionold=SessionListener.getSessionMaps().get(esn);
	         sessionold.invalidate();
	         SessionListener.removeSession(esn);
	         SessionListener.addSession(esn,request.getSession());
//	         SessionListener.getSessionMaps().remove(request.getSession().getId());
		 } else {
			 SessionListener.addSession(esn,request.getSession());
//	         SessionListener.getSessionMaps().put(esn,SessionListener.getSessionMaps().get(request.getSession().getId()));
//	         SessionListener.getSessionMaps().remove(request.getSession().getId());
		 }
			LoginUser lu=null;
			try {
				lu = isysemp_service.login_user(uv);
			} catch (NoSuchAlgorithmException e1) {
				return "error";
			} catch (MyExecption e1) {
				return "error";
			}
			
			if(lu==null){
				return "error";
			}else{
				return "success";
			}
		
		
//		String ip=ServletActionContext.getRequest().getRemoteAddr();
//		if(AppUtils.isIpAddress(ip)==false){
//			return "error";
//		}
//		String mac=AppUtils.getMacAddress(ip);
//		try {
//			if(!lu.getUPwd().equals(AppUtils.encodeByMD5(uv.getPwd()))){
//				return "error";
//			}
//		} catch (NoSuchAlgorithmException e) {
//			return "error";
//		}
		
//		return null;
	}
	
	@Action(value="showEmpDList",className="sys_employee_action")
	public String empFindDispathList() throws IOException{
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!dispiatchlist");
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		String start=ServletActionContext.getRequest().getParameter("start");
		String limit=ServletActionContext.getRequest().getParameter("limit");
		System.out.println(start+" "+limit);
		BaseVO bv=new BaseVO();
		bv.setStart(Integer.valueOf(start));
		bv.setLimit(Integer.valueOf(limit));
		try {
			Page page=isysemp_service.findDispathList(bv, "10000000");
			System.out.println(JsonUtil.PageJson(page));
			out.print(JsonUtil.PageJson(page));			
		} catch (MyExecption e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
//		JsonUtil.PageJson(page);
		return null;
	}
}
