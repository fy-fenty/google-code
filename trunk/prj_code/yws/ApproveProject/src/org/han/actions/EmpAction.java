package org.han.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.han.constant.AppConstant;
import org.han.services.IEmpService;
import org.han.services.ISystemServices;
import org.han.util.CurrentUser;
import org.han.util.JsonValueProcessorImpl;
import org.han.vo.BaseVo;
import org.han.vo.Page;
import org.han.vo.TreeBean;

import com.opensymphony.xwork2.ActionSupport;

@Namespace("/emp")
public class EmpAction extends ActionSupport {

	private IEmpService empBiz;
	private ISystemServices sysBiz;
	private Integer start;
	private Integer limit;

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

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

	@Action(value = "dis", className = "empAction")
	public String getDispatchByEmpSn() throws Exception {
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		CurrentUser currUser = (CurrentUser) session
				.getAttribute(AppConstant.CURRENT_USER);
		if (null == currUser) {
			// return null;
		}
		Page page = empBiz.findDisByEmpId(new BaseVo(start, limit), "10000000");
		String json = new JsonValueProcessorImpl("yyyy-MM-dd HH:mm:ss")
				.getStrByObject(page);
		out.print(json);
		out.close();
		return null;
	}

	@Action(value = "menu", className = "empAction")
	public String getMenu() throws Exception {
		try {
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			CurrentUser currUser = (CurrentUser) session
					.getAttribute(AppConstant.CURRENT_USER);
			List treeList = new ArrayList();
			if (null != currUser) {
				TreeBean tree = sysBiz.getTreeBean(currUser.getPos());
				treeList.add(tree);
			}
			if (null == treeList.get(0)) {
				return null;
			}
			String json = new JsonValueProcessorImpl("yyyy-MM-dd HH:mm:ss")
					.getStrByList(treeList);
			out.print(json);
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Action(value = "disById", className = "empAction")
	public String getDispatchById() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String disId = request.getParameter("disId");
		if (null == disId) {
			return null;
		}
		String str = new JsonValueProcessorImpl("yyyy-MM-dd HH:mm:ss")
				.getStrByList(sysBiz.getDisListById(Long.valueOf(disId)));
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(str);
		out.close();
		return null;
	}

	@Action(value = "detail", className = "empAction")
	public String getDetailByDisId() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String disId = request.getParameter("disId");
		if (null == disId) {
			return null;
		}
		String str = new JsonValueProcessorImpl("yyyy-MM-dd HH:mm:ss")
				.getStrByList(sysBiz.getDispatchDetails(Long.valueOf(disId)));
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(str);
		out.close();
		return null;
	}

	@Action(value = "ditem", className = "empAction")
	public String getDetailItems() throws IOException {
		String str = new JsonValueProcessorImpl("yyyy-MM-dd HH:mm:ss")
				.getStrByList(sysBiz.getDetailItems());
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(str);
		System.out.println(str);
		out.close();
		return null;
	}

	@Action(value = "savedis", className = "empAction")
	public String saveDispatch() {
		System.out.println("aaa");
		return null;
	}
}
