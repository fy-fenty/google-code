package org.fy.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.fy.constant.AppConstant;
import org.fy.entity.DispatchDetail;
import org.fy.entity.DispatchList;
import org.fy.exception.MyExecption;
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
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		Result rs=null;
		CurrentUserVO cv=(CurrentUserVO)ServletActionContext.getRequest().getSession().getAttribute(AppConstant.CURRENT_USER);
		if(cv==null){
			rs=new Result(false, "用户在其它地方登陆");
			out.print(JsonUtil.RsJson(rs));
			return null;
		}
		
		System.out.println(dispatchvo.getEventExplain()+" "+dispatchvo.getDlId());
		
		dispatchvo.setSn(cv.getESn()); 
		rs=isysemp_service.updateDispathList(dispatchvo);
		
		if(rs.getSuccess()==false){
			rs=new Result(false, "更新失败");
		}
		System.out.println(JsonUtil.RsJson(rs));
		out.print(JsonUtil.RsJson(rs));
		return null;
	}
	
	@Action(value="addAll",className="sys_modifydispatch_action")
	public String addAll() throws IOException{
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!addAll");
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		Result rs=null;
		CurrentUserVO cv=(CurrentUserVO)ServletActionContext.getRequest().getSession().getAttribute(AppConstant.CURRENT_USER);
		if(cv==null){
			rs=new Result(false, "用户在其它地方登陆");
			out.print(JsonUtil.RsJson(rs));
			return null;
		}
//		String event=ServletActionContext.getRequest().getParameter("evend");
		String data=ServletActionContext.getRequest().getParameter("data");
		DispatchList dlist=new DispatchList();
		dlist.setEventExplain(dispatchvo.getEventExplain());
		rs=new Result(true, "增加成功");
		Long id=null;
		try {
			id=isysemp_service.saveReturnId(cv.getESn(), dlist);
		} catch (MyExecption e) {
			rs=new Result(false, "增加报销单失败");
			out.print(JsonUtil.RsJson(rs));
			return null;
		}
		if(!"".equals(data)){
			DispatchDetail detail=new DispatchDetail();
			List list=JsonUtil.JsonToBean(data);
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) {
				Map map=(Map) list.get(i);
				detail.setCostExplain(map.get("costExplain")+"");
				detail.setItemId(Long.valueOf(map.get("itemId")+""));
				detail.setMoney(Double.valueOf(map.get("money")+""));
				detail.setSheetId(id);
				rs=isysemp_service.saveDispathDetail(cv.getESn(), detail);
//				System.out.println("sdfffffffffffffffffffffffffffffffffffadfas");
				if(rs.getSuccess()==false){
					rs=new Result(false, "增加报销单明细失败");
					out.print(JsonUtil.RsJson(rs));
					return null;
				}
			}
		}
		out.print(JsonUtil.RsJson(rs));
		return null;
	}
	@Action(value="commitAddAllDispatch",className="sys_modifydispatch_action")
	public String commitAddAllDispatch() throws IOException, MyExecption{
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!commitAddAllDispatch");
	/*	DispatchList dlist=new DispatchList();
		dlist.setEventExplain("bbs");
		Long Id=isysemp_service.aa("10000000", dlist);
		isysemp_service.bb(Id);*/
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		Result rs=null;
		CurrentUserVO cv=(CurrentUserVO)ServletActionContext.getRequest().getSession().getAttribute(AppConstant.CURRENT_USER);
		if(cv==null){
			rs=new Result(false, "用户在其它地方登陆");
			out.print(JsonUtil.RsJson(rs));
			return null;
		}
		String data=ServletActionContext.getRequest().getParameter("data");
		DispatchList dlist=new DispatchList();
		dlist.setEventExplain(dispatchvo.getEventExplain());
		rs=new Result(true, "提交成功");
		Long id=null;
		try {
			id=isysemp_service.saveReturnId(cv.getESn(), dlist);
		} catch (MyExecption e) {
			rs=new Result(false, "增加报销单失败");
			out.print(JsonUtil.RsJson(rs));
			return null;
		}
		if(!"".equals(data)){
			DispatchDetail detail=new DispatchDetail();
			List list=JsonUtil.JsonToBean(data);
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) {
				Map map=(Map) list.get(i);
				detail.setCostExplain(map.get("costExplain")+"");
				detail.setItemId(Long.valueOf(map.get("itemId")+""));
				detail.setMoney(Double.valueOf(map.get("money")+""));
				detail.setSheetId(id);
				rs=isysemp_service.saveDispathDetail(cv.getESn(), detail);
				if(rs.getSuccess()==false){
					rs=new Result(false, "增加报销单明细失败");
					out.print(JsonUtil.RsJson(rs));
					return null;
				}
			}
		}
		dispatchvo.setSn(cv.getESn());
		dispatchvo.setDlId(id);
		rs=isysemp_service.commitDispathList(dispatchvo);
		if(rs.getSuccess()==false){
			rs=new Result(false, "提交失败");
		}
		out.print(JsonUtil.RsJson(rs));
		return null;
	}
	
	
	@Action(value="commitDispatch",className="sys_modifydispatch_action")
	public String commitDispatch() throws IOException{
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!commitDispatch");
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		Result rs=null;
		CurrentUserVO cv=(CurrentUserVO)ServletActionContext.getRequest().getSession().getAttribute(AppConstant.CURRENT_USER);
		if(cv==null){
			rs=new Result(false, "用户在其它地方登陆");
			out.print(JsonUtil.RsJson(rs));
			return null;
		}
		System.out.println(dispatchvo.getDlId());
		dispatchvo.setSn(cv.getESn());
		rs=isysemp_service.updateDispathList(dispatchvo);
		if(rs.getSuccess()==false){
			rs=new Result(false, "提交失败");
			out.print(JsonUtil.RsJson(rs));
			return null;
		}
		rs=isysemp_service.commitDispathList(dispatchvo);
		if(rs.getSuccess()==false){
			rs=new Result(false, "提交失败");
			out.print(JsonUtil.RsJson(rs));
			return null;
		}
		System.out.println(JsonUtil.RsJson(rs));
		out.print(JsonUtil.RsJson(rs));
		return null;
	}
	
	@Action(value="addDetail",className="sys_modifydispatch_action")
	public String addDetail() throws IOException{
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!addDetail");
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		Result rs=null;
		CurrentUserVO cv=(CurrentUserVO)ServletActionContext.getRequest().getSession().getAttribute(AppConstant.CURRENT_USER);
		if(cv==null){
			rs=new Result(false, "用户在其它地方登陆");
			out.print(JsonUtil.RsJson(rs));
			return null;
		}
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
		rs=isysemp_service.saveDispathDetail(cv.getESn(), detail);
		if(rs.getSuccess()==false){
			rs=new Result(false, "增加失败");
		}
		
		System.out.println(JsonUtil.RsJson(rs));
		out.print(JsonUtil.RsJson(rs));
		return null;
	}
	
	@Action(value="updateDetail",className="sys_modifydispatch_action")
	public String updateDetail() throws IOException{
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!updateDetail");
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		Result rs=null;
		CurrentUserVO cv=(CurrentUserVO)ServletActionContext.getRequest().getSession().getAttribute(AppConstant.CURRENT_USER);
		if(cv==null){
			rs=new Result(false, "用户在其它地方登陆");
			out.print(JsonUtil.RsJson(rs));
			return null;
		}
		System.out.println(dvo.getMoney()+" "+dvo.getDsId()+"  "+dvo.getSheetId());
		dvo.setSn(cv.getESn());
		rs=(Result)isysemp_service.updateDispathDetail(dvo);
		if(rs.getSuccess()==false){
			rs=new Result(false, "更新失败");
		}
		System.out.println(JsonUtil.RsJson(rs));
		out.print(JsonUtil.RsJson(rs));
		return null;
	}
	
	@Action(value="deleteDispatchList",className="sys_modifydispatch_action")
	public String deleteDispatchList() throws IOException{
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!deleteDispatchList");
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		Result rs=null;
		CurrentUserVO cv=(CurrentUserVO)ServletActionContext.getRequest().getSession().getAttribute(AppConstant.CURRENT_USER);
		if(cv==null){
			rs=new Result(false, "用户在其它地方登陆");
			out.print(JsonUtil.RsJson(rs));
			return null;
		}
		System.out.println(dispatchvo.getDlId());
		
		rs=isysemp_service.deleteDispathList(cv.getESn(),Long.valueOf(dispatchvo.getDlId()));
		System.out.println(rs.getException()+" "+rs.getMsg());
		if(rs.getSuccess()==false){
			rs=new Result(false, "删除失败");
		}
		
		System.out.println(JsonUtil.RsJson(rs));
		out.print(JsonUtil.RsJson(rs));
		return null;
	}
	
	@Action(value="deleteDetail",className="sys_modifydispatch_action")
	public String deleteDetail() throws IOException{
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!deleteDetail");
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		Result rs=null;
		CurrentUserVO cv=(CurrentUserVO)ServletActionContext.getRequest().getSession().getAttribute(AppConstant.CURRENT_USER);
		if(cv==null){
			rs=new Result(false, "用户在其它地方登陆");
			out.print(JsonUtil.RsJson(rs));
			return null;
		}
		System.out.println(dvo.getDsId()+"  "+dvo.getSheetId());
		
		dvo.setSn(cv.getESn());
		rs=(Result)isysemp_service.deleteDispathDetail(dvo);
		if(rs.getSuccess()==false){
			rs=new Result(false, "删除失败");
		}
		
		System.out.println(JsonUtil.RsJson(rs));
		out.print(JsonUtil.RsJson(rs));
		return null;
	}
	
	
}
