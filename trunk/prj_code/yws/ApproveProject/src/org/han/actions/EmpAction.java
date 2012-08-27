package org.han.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.han.constant.AppConstant;
import org.han.dao.IDispatchDetailDao;
import org.han.dao.IDispatchListDao;
import org.han.entity.DetailItem;
import org.han.entity.DispatchDetail;
import org.han.entity.DispatchList;
import org.han.services.IEmpService;
import org.han.services.ISystemServices;
import org.han.util.CurrentUser;
import org.han.util.JsonValueProcessorImpl;
import org.han.util.StringUtil;
import org.han.vo.BaseVo;
import org.han.vo.DispatchDetailVo;
import org.han.vo.DispatchListVo;
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
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		CurrentUser currUser = this.getCurrUser();
		if (null == currUser) {
			return null;
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
	public String saveDispatch() throws Exception {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			HttpServletResponse response = ServletActionContext.getResponse();
			CurrentUser currUser = (CurrentUser) session
					.getAttribute(AppConstant.CURRENT_USER);
			if (null == currUser) {
				// return null;
			}
			String eSn = currUser.geteSN();
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			String data = request.getParameter("data");
			String eventExplain = request.getParameter("eventExplain");
			DispatchList dis = new DispatchList();
			dis.setEventExplain(eventExplain);
			dis.setESn(eSn);
			if (null == data || "" == data) {
				return null;
			}
			Long disId = empBiz.saveDispatch(dis, eSn);
			if (disId < 0) {
				return null;
			}
			JSONArray jsonArray = JSONArray.fromObject(data);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
				DispatchDetail disDetail = (DispatchDetail) JSONObject.toBean(
						jsonObject, DispatchDetail.class);
				disDetail.setSheetId(disId);
				empBiz.saveDisDetail(eSn, disDetail);
			}
			out.print('1');
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Action(value = "updateOrAddDetail", className = "empAction")
	public String updateOrAddDetail() throws Exception {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String data = request.getParameter("data");
			String dlId = request.getParameter("disId");
			CurrentUser currUser = this.getCurrUser();
			if (null == currUser) {
				return null;
			}
			if (StringUtil.isEmpty(data) || StringUtil.isEmpty(dlId)) {
				return null;
			}
			JSONObject jsonObject = JSONObject.fromObject(data);
			DispatchDetail disDetail = (DispatchDetail) JSONObject.toBean(
					jsonObject, DispatchDetail.class);
			disDetail.setSheetId(Long.valueOf(dlId));
			if (disDetail.getDsId() == null) {
				empBiz.saveDisDetail(currUser.geteSN(), disDetail);
			} else {
				DispatchDetailVo disDetailVo = (DispatchDetailVo) JSONObject
						.toBean(jsonObject, DispatchDetailVo.class);
				disDetailVo.setSheetId(Long.valueOf(dlId));
				empBiz.updateDisDetail(currUser.geteSN(), disDetailVo);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Action(value = "updateDispatch", className = "empAction")
	public String updateDispatch() throws Exception {
		CurrentUser currUser = this.getCurrUser();
		if (null == currUser) {
			return null;
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String disId = request.getParameter("disId");
		String eventExplain = request.getParameter("eventExplain");
		if (StringUtil.isEmpty(disId) || StringUtil.isEmpty(eventExplain)) {
			return null;
		}
		DispatchListVo dis = new DispatchListVo();
		dis.setDlId(Long.valueOf(disId));
		dis.setEventExplain(eventExplain);
		empBiz.updateDispatch(currUser.geteSN(), dis);
		return null;
	}

	@Action(value = "commitDispatch", className = "empAction")
	public String commitDispatch() throws Exception {
		CurrentUser currUser = this.getCurrUser();
		if (null == currUser) {
			return null;
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String eventExplain = request.getParameter("eventExplain");
		String disId = request.getParameter("disId");
		if (StringUtil.isEmpty(disId) || StringUtil.isEmpty(eventExplain)) {
			return null;
		}
		DispatchListVo dis = new DispatchListVo();
		dis.setDlId(Long.valueOf(disId));
		dis.setEventExplain(eventExplain);
		empBiz.updateDispatch(currUser.geteSN(), dis);
		empBiz.commitDispatch(currUser.geteSN(), Long.valueOf(disId));
		return null;
	}

	private CurrentUser getCurrUser() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		CurrentUser currUser = (CurrentUser) session
				.getAttribute(AppConstant.CURRENT_USER);
		return currUser;
	}
}
