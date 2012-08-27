package org.fy.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.fy.vo.Result;
import org.fy.constant.AppConstant;
import org.fy.entity.DispatchList;
import org.fy.entity.LoginUser;
import org.fy.exception.MyExecption;
import org.fy.service.ISysEmployeeService;
import org.fy.service.ISysMenuService;
import org.fy.service.ISystemService;
import org.fy.util.JsonUtil;
import org.fy.utils.AppUtils;
import org.fy.utils.SessionListener;
import org.fy.vo.BaseVO;
import org.fy.vo.CurrentUserVO;
import org.fy.vo.Page;
import org.fy.vo.UserVO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author hzy
 * @date 2012-8-20
 *	@extends Object
 * @class SysEmployeeAction
 * @description action类
 */
public class SysEmployeeAction {
	private ISysEmployeeService isysemp_service;
	private ISysMenuService isysmenu_service;
	private ISystemService isys_service;
	private UserVO uv;
	
	public ISysEmployeeService getIsysemp_service() {
		return isysemp_service;
	}
	public void setIsysemp_service(ISysEmployeeService isysemp_service) {
		this.isysemp_service = isysemp_service;
	}
	public ISysMenuService getIsysmenu_service() {
		return isysmenu_service;
	}
	public void setIsysmenu_service(ISysMenuService isysmenu_service) {
		this.isysmenu_service = isysmenu_service;
	}
	public ISystemService getIsys_service() {
		return isys_service;
	}
	public void setIsys_service(ISystemService isys_service) {
		this.isys_service = isys_service;
	}
	public UserVO getUv() {
		return uv;
	}
	public void setUv(UserVO uv) {
		this.uv = uv;
	}
	
	@Action(value="login",className="sys_employee_action")
	public String sys_login() throws IOException{
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!syslogin");
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		System.out.println(uv.getEsn()+"  "+uv.getPwd());
		Result rs=(Result) isysemp_service.login_user(uv);
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		System.out.println(JsonUtil.RsJson(rs));
		out.print(JsonUtil.RsJson(rs));
		return null;
	}
	
	@Action(value="getMenu",className="sys_employee_action")
	public String getMenuTree() throws IOException{
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!getMenu");
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		CurrentUserVO cv=(CurrentUserVO)ServletActionContext.getRequest().getSession().getAttribute(AppConstant.CURRENT_USER);
		if(cv==null){
			return null;
		}
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		try {
			String str=isysmenu_service.findMenu(3L);
			System.out.println(str);
			out.print(str);
		} catch (MyExecption e) {
			System.out.println(e.getMessage());
		}
		return null;	
	}
	
	@Action(value="showCombo",className="sys_employee_action")
	public String showCombox() throws IOException{
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!showCombox");
		String start=ServletActionContext.getRequest().getParameter("start");
		String limit=ServletActionContext.getRequest().getParameter("limit");
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		BaseVO bv=new BaseVO();
		bv.setStart(Integer.valueOf(start));
		bv.setLimit(Integer.valueOf(limit));
		Page page=isys_service.findDetailItem(bv);
		System.out.println(JsonUtil.PageJson(page));
		out.print(JsonUtil.PageJson(page));	
		return null;
	}
	
	@Action(value="showEmpDList",className="sys_employee_action")
	public String empFindDispathList() throws IOException{
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!dispiatchlist");
		CurrentUserVO cv=(CurrentUserVO)ServletActionContext.getRequest().getSession().getAttribute(AppConstant.CURRENT_USER);
		System.out.println(cv);
		if(cv==null){
			return null;
		}
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		String start=ServletActionContext.getRequest().getParameter("start");
		String limit=ServletActionContext.getRequest().getParameter("limit");
		System.out.println(start+" "+limit);
		BaseVO bv=new BaseVO();
		bv.setStart(Integer.valueOf(start));
		bv.setLimit(Integer.valueOf(limit));
		try {
			Page page=isysemp_service.findDispathList(bv,cv.getESn());
			System.out.println(JsonUtil.PageJson(page));
			out.print(JsonUtil.PageJson(page));			
		} catch (MyExecption e) {
			System.out.println(e.getMessage());
		}
//		JsonUtil.PageJson(page);
		return null;
	}
	
	@Action(value="findSpecidDispatch",className="sys_employee_action")
	public String findSpecidDispatch() throws IOException{
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		String sheetId=ServletActionContext.getRequest().getParameter("sheetId");
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		DispatchList dlist=isys_service.findByDlistId(Long.valueOf(sheetId));
		String str=JsonUtil.ObjectJson(dlist);
		System.out.println(str);
		out.print(str);
		return null;
	}
	
	@Action(value="findDetial",className="sys_employee_action")
	public String findDetial() throws IOException{
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		String sheetId=ServletActionContext.getRequest().getParameter("sheetId");
		PrintWriter out=ServletActionContext.getResponse().getWriter();	
		List data=isys_service.findDetailById(Long.valueOf(sheetId));
		String str=JsonUtil.ArrayJson(data);
		System.out.println(str);
		out.print(str);
		return null;
	}
	
	@Action(value="findDisResult",className="sys_employee_action")
	public String findDisResult() throws IOException{
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		String sheetId=ServletActionContext.getRequest().getParameter("sheetId");
		PrintWriter out=ServletActionContext.getResponse().getWriter();	
		List data=isys_service.findDlistResultById(Long.valueOf(sheetId));
		String str=JsonUtil.ArrayJson(data);
		System.out.println(str);
		out.print(str);
		return null;
	}
	
	public static void main(String[] args) {
		ApplicationContext apc = new ClassPathXmlApplicationContext(
				new String[] { "hibernate-spring.xml", "beans.xml" });
		SysEmployeeAction sf = apc.getBean("sys_employee_action", SysEmployeeAction.class);
		try {
			sf.findSpecidDispatch();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
