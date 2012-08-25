package org.zjf.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.ymm.constant.AppConstant;
import org.ymm.entity.SysMenu;
import org.ymm.exception.MyException;
import org.ymm.util.Json;
import org.ymm.vo.BaseVo;
import org.ymm.vo.CurrentUser;
import org.ymm.vo.Page;
import org.ymm.vo.TreeBean;
import org.zjf.services.IEmpService;

public class EmpAction {

	private IEmpService empservice;
	private BaseVo vo;
	private Integer start;
	private Integer limit;
	
	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		if(start!=null&&start!=0)
			vo.setStart(start);
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		if(limit!=null&&limit!=0)
			vo.setLimit(limit);
	}

	public BaseVo getVo() {
		return vo;
	}

	public void setVo(BaseVo vo) {
		this.vo = vo;
	}

	public IEmpService getEmpservice() {
		return empservice;
	}

	public void setEmpservice(IEmpService empservice) {
		this.empservice = empservice;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String gernateMenu() throws IOException {
		HttpSession sess = ServletActionContext.getRequest().getSession();
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();
//		if (sess.getAttribute("menu") == null) {
			List<SysMenu> menu = (List<SysMenu>) sess.getAttribute("menu");
			TreeBean tree=new TreeBean();
			tree.setFunName("员工操作列表");
			tree.setId("empTree");
			tree.setCls("folder");
			tree.setLeaf(false);
			tree.setText("员工操作列表");
			List<TreeBean> list=new ArrayList();
			for (int i = 0; i < menu.size(); i++) {
				SysMenu men=menu.get(i);
				TreeBean bean=new TreeBean();
				bean.setText(men.getMenuName());
				bean.setLeaf(true);
				bean.setId(men.getMId()+"");
				bean.setFunName(men.getUrl());
				list.add(bean);
			}
//			TreeBean bean1=new TreeBean();
//			bean1.setFunName("empLook");
//			bean1.setLeaf(true);
//			bean1.setText("查看报销单");
//			list.add(bean1);
//			TreeBean bean2=new TreeBean();
//			bean2.setFunName("sendClaims");
//			bean2.setLeaf(true);
//			bean2.setText("申请报销单");
//			list.add(bean2);
			tree.setChildren(list);
			String json=Json.JSON_List(tree);
			out.print(json);
			return null;
//		}
//		return "none";
	}
	
	public String LookEmpClaims() throws IOException{
		HttpSession sess = ServletActionContext.getRequest().getSession();
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();
		CurrentUser user=(CurrentUser) sess.getAttribute(AppConstant.CURRENT_USER);
		try {
			Page page=empservice.findAllClaims(user.getEmp(), vo);
			String json=Json.JSON_Object(page);
			out.print(json);
			System.out.println(json);
		} catch (MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
