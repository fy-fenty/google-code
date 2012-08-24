package org.ymm.actions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.ymm.services.IEmpService;
import org.ymm.services.IShowMenuService;
import org.zjf.constant.MyConstant;
import org.zjf.entity.SysEmployee;
import org.zjf.exception.MyException;
import org.zjf.util.DateJsonValueProcessor;
import org.zjf.vo.BaseVo;
import org.zjf.vo.CurrentUser;
import org.zjf.vo.LoginVo;
import org.zjf.vo.Page;
import org.zjf.vo.Result;

public class LoginAction{
	
	private IEmpService iEmpService;
	private IShowMenuService iShowMenuService;

	public IShowMenuService getiShowMenuService() {
		return iShowMenuService;
	}

	public void setiShowMenuService(IShowMenuService iShowMenuService) {
		this.iShowMenuService = iShowMenuService;
	}
	
	public IEmpService getiEmpService() {
		return iEmpService;
	}

	public void setiEmpService(IEmpService iEmpService) {
		this.iEmpService = iEmpService;
	}

	public void login() throws MyException, IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		String uname= request.getParameter("uname");
		String passwprd=request.getParameter("passwprd");
		String vcode=request.getParameter("vcode");
		String ivode=(String) request.getSession().getAttribute("rand");
		String sessionId = request.getSession().getId();
		System.out.println(uname+"-"+passwprd+"-"+vcode+"="+ivode+"-"+sessionId);
		
		LoginVo lv=new LoginVo();
		lv.setUname(uname);
		lv.setPwd(passwprd);
		lv.setVcode(vcode);
		lv.setIvode(ivode);
		
		Result result = iEmpService.loginUser(lv);
		
		response.setCharacterEncoding("UTF-8");
		PrintWriter out= response.getWriter();
		JSONArray ja1=new JSONArray().fromObject(result);
		out.print(ja1.toString());
	}
	
	
	public void tree() throws MyException{
		HttpServletRequest request=ServletActionContext.getRequest();
		CurrentUser cu= (CurrentUser) request.getSession().getAttribute(MyConstant.CURRENT_USER);
		
		String str=iShowMenuService.findEmpShowMenu(cu.getPid());
		
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter out= response.getWriter();
			out.print(str);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void selectDispatch_list() throws MyException{
		HttpServletRequest request=ServletActionContext.getRequest();
		CurrentUser cu= (CurrentUser) request.getSession().getAttribute(MyConstant.CURRENT_USER);
		SysEmployee se=new SysEmployee();
		se.setESn(cu.getUname());
		BaseVo vo=new BaseVo();
		Page page=iEmpService.findAllClaims(se, vo);
		
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		try {
			
			PrintWriter out= response.getWriter();			
			JSONObject ja =new JSONObject(); 
			JsonConfig jf = new JsonConfig();
			
			jf.registerJsonValueProcessor(java.sql.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
			jf.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
			
			out.print(ja.fromObject(page,jf).toString());
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
