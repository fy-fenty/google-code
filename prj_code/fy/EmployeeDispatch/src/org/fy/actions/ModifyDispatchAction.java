package org.fy.actions;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.fy.constant.AppConstant;
import org.fy.entity.DispatchDetail;
import org.fy.entity.DispatchList;
import org.fy.service.ISysEmployeeService;
import org.fy.service.ISystemService;
import org.fy.util.JsonUtil;
import org.fy.vo.CurrentUserVO;
import org.fy.vo.DispatchDetailVO;
import org.fy.vo.DispatchListVO;
import org.fy.vo.Result;

/**
 * @author hzy
 * @date 2012-8-22
 *	@extends Object
 * @class ModifyDispatchAction
 * @description 报销单操作action
 */
public class ModifyDispatchAction {
	private ISysEmployeeService isysemp_service;
	private ISystemService isys_service;
	private DispatchListVO dispatchvo;
	private DispatchDetailVO dvo;
	public ISysEmployeeService getIsysemp_service() {
		return isysemp_service;
	}
	public void setIsysemp_service(ISysEmployeeService isysemp_service) {
		this.isysemp_service = isysemp_service;
	}
	public ISystemService getIsys_service() {
		return isys_service;
	}
	public void setIsys_service(ISystemService isys_service) {
		this.isys_service = isys_service;
	}
	public DispatchListVO getDispatchvo() {
		return dispatchvo;
	}
	public void setDispatchvo(DispatchListVO dispatchvo) {
		this.dispatchvo = dispatchvo;
	}
	public DispatchDetailVO getDvo() {
		return dvo;
	}
	public void setDvo(DispatchDetailVO dvo) {
		this.dvo = dvo;
	}
	@Action(value="updateDlist",className="sys_modifydispatch_action")
	public String updateDlist() throws IOException{
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!updateDlist");
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		System.out.println(dispatchvo.getEventExplain()+" "+dispatchvo.getDlId());
		
		dispatchvo.setSn("10000000"); 
		Result rs=(Result)isysemp_service.updateDispathList(dispatchvo);
		
		if(rs.getSuccess()==false){
			rs=new Result(false, "更新失败");
		}
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		System.out.println(JsonUtil.RsJson(rs));
		out.print(JsonUtil.RsJson(rs));
		return null;
	}
	
	@Action(value="addAll",className="sys_modifydispatch_action")
	public String addAll(){
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!addAll");
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		String event=ServletActionContext.getRequest().getParameter("evend");
		System.out.println(event);
		DispatchList dlist=new DispatchList();
		dlist.setEventExplain(event);
		isysemp_service.saveDispathList("10000000",dlist);
		return null;
	}
	
	@Action(value="commitDispatch",className="sys_modifydispatch_action")
	public String commitDispatch() throws IOException{
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!commitDispatch");
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		System.out.println(dispatchvo.getDlId());
		dispatchvo.setSn("10000000");
		Result rs=isysemp_service.commitDispathList(dispatchvo);
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		System.out.println(JsonUtil.RsJson(rs));
		out.print(JsonUtil.RsJson(rs));
		return null;
	}
	
	@Action(value="addDetail",className="sys_modifydispatch_action")
	public String addDetail() throws IOException{
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!addDetail");
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		String sheetId=ServletActionContext.getRequest().getParameter("sheetId");
		String itemId=ServletActionContext.getRequest().getParameter("itemId");
		String cost=ServletActionContext.getRequest().getParameter("cost");
		String money=ServletActionContext.getRequest().getParameter("money");
		System.out.println(itemId+"  "+cost+"  "+money);
//		dvo.setSn("10000000");
		DispatchDetail detail=new DispatchDetail();
		detail.setSheetId(Long.valueOf(sheetId));
		detail.setItemId(Long.valueOf(itemId));
		detail.setMoney(Double.valueOf(money));
		detail.setCostExplain(cost);
		Result rs=isysemp_service.saveDispathDetail("10000000", detail);
		if(rs.getSuccess()==false){
			rs=new Result(false, "增加失败");
		}
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		System.out.println(JsonUtil.RsJson(rs));
		out.print(JsonUtil.RsJson(rs));
		return null;
	}
	
	@Action(value="updateDetail",className="sys_modifydispatch_action")
	public String updateDetail() throws IOException{
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!updateDetail");
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		System.out.println(dvo.getMoney()+" "+dvo.getDsId()+"  "+dvo.getSheetId());
		dvo.setSn("10000000");
		Result rs=(Result)isysemp_service.updateDispathDetail(dvo);
		if(rs.getSuccess()==false){
			rs=new Result(false, "更新失败");
		}
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		System.out.println(JsonUtil.RsJson(rs));
		out.print(JsonUtil.RsJson(rs));
		return null;
	}
	
	@Action(value="deleteDispatchList",className="sys_modifydispatch_action")
	public String deleteDispatchList() throws IOException{
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!deleteDispatchList");
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
//		CurrentUserVO cv=(CurrentUserVO)ServletActionContext.getRequest().getSession().getAttribute(AppConstant.CURRENT_USER);
		System.out.println(dispatchvo.getDlId());
		
//		dvo.setSn("10000000");
		Result rs=(Result)isysemp_service.deleteDispathList("10000000",Long.valueOf(dispatchvo.getDlId()));
		if(rs.getSuccess()==false){
			rs=new Result(false, "删除失败");
		}
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		System.out.println(JsonUtil.RsJson(rs));
		out.print(JsonUtil.RsJson(rs));
		return null;
	}
	
	@Action(value="deleteDetail",className="sys_modifydispatch_action")
	public String deleteDetail() throws IOException{
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!deleteDetail");
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
//		CurrentUserVO cv=(CurrentUserVO)ServletActionContext.getRequest().getSession().getAttribute(AppConstant.CURRENT_USER);
		System.out.println(dvo.getDsId()+"  "+dvo.getSheetId());
		
		dvo.setSn("10000000");
		Result rs=(Result)isysemp_service.deleteDispathDetail(dvo);
		if(rs.getSuccess()==false){
			rs=new Result(false, "删除失败");
		}
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		System.out.println(JsonUtil.RsJson(rs));
		out.print(JsonUtil.RsJson(rs));
		return null;
	}
	
	
}
