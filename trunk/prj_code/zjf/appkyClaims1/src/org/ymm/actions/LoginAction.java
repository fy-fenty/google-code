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
import org.ymm.services.ISystemService;
import org.zjf.constant.MyConstant;
import org.zjf.entity.SysEmployee;
import org.zjf.exception.MyException;
import org.zjf.util.DateJsonValueProcessor;
import org.zjf.vo.BaseVo;
import org.zjf.vo.CurrentUser;
import org.zjf.vo.DispatchListVo;
import org.zjf.vo.LoginVo;
import org.zjf.vo.Page;
import org.zjf.vo.Result;

public class LoginAction{
	
	private IEmpService iEmpService;
	private IShowMenuService iShowMenuService;
	private ISystemService iSystemService;

	public ISystemService getiSystemService() {
		return iSystemService;
	}

	public void setiSystemService(ISystemService iSystemService) {
		this.iSystemService = iSystemService;
	}

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
		//System.out.println(uname+"-"+passwprd+"-"+vcode+"="+ivode+"-"+sessionId);
		
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
	
	public void selectDispatch_list() throws MyException, InterruptedException{
		HttpServletRequest request=ServletActionContext.getRequest();
		
		CurrentUser cu= (CurrentUser) request.getSession().getAttribute(MyConstant.CURRENT_USER);
		SysEmployee se=new SysEmployee();
		se.setESn(cu.getUname());
		BaseVo vo=new BaseVo();
		vo.setLimit(Integer.parseInt(request.getParameter("limit")));
		vo.setStart(Integer.parseInt(request.getParameter("start")));
		Page page=iEmpService.findAllClaims(se, vo);
		
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		try {
			
			PrintWriter out= response.getWriter();			
			JSONObject ja =new JSONObject(); 
			JsonConfig jf = new JsonConfig();
			
			jf.registerJsonValueProcessor(java.sql.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
			jf.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
			Thread.sleep(500);
			out.print(ja.fromObject(page,jf).toString());
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void delDis() throws MyException, IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		Long DL_ID=(long) Integer.parseInt(request.getParameter("DL_ID").toString());
		CurrentUser cu= (CurrentUser) request.getSession().getAttribute(MyConstant.CURRENT_USER);
		SysEmployee se=new SysEmployee();
		se.setESn(cu.getUname());
		se.setEId(cu.getId());
		DispatchListVo d=new DispatchListVo();
		d.setDlId(DL_ID);
		
		Result result= iEmpService.deleteClaims(se, d);
		
		JSONObject ja =new JSONObject();
		
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		PrintWriter out= response.getWriter();
		out.print(ja.fromObject(result));
		out.close();
	}

	public void selectDetailById() throws MyException{
		HttpServletRequest request=ServletActionContext.getRequest();
		Long DL_ID=(long) Integer.parseInt(request.getParameter("DL_ID").toString());
		BaseVo vo=new BaseVo();
		vo.setLimit(Integer.parseInt(request.getParameter("limit")));
		vo.setStart(Integer.parseInt(request.getParameter("start")));
		Page page= iSystemService.findDetailById(DL_ID, vo);
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter out= response.getWriter();			
			JSONObject ja =new JSONObject(); 
			out.print(ja.fromObject(page).toString());
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateClaims() throws MyException, IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		Long DL_ID=(long) Integer.parseInt(request.getParameter("DL_ID").toString());
		String EVENT_EXPLAIN=request.getParameter("EVENT_EXPLAIN").toString();
		CurrentUser cu= (CurrentUser) request.getSession().getAttribute(MyConstant.CURRENT_USER);
		SysEmployee se=new SysEmployee();
		se.setESn(cu.getUname());
		se.setEId(cu.getId());
		DispatchListVo d=new DispatchListVo();
		d.setDlId(DL_ID);
		d.setEventExplain(EVENT_EXPLAIN);
		Result result = iEmpService.updateClaims(se, d);
		
		JSONObject ja =new JSONObject();
		
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		PrintWriter out= response.getWriter();
		out.print(ja.fromObject(result));
		out.close();
	}
	
	
}
