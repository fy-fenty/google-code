package org.hzy.constant;

/**
 * @author hzy
 * @date 2012-8-14
 * @extends Object
 * @class AppConstant
 * @description 用于定义异常码
 */
public interface AppConstant {
	String ACTION_ERROR_MSG = "操作无错误";
	String A001 = "雇员编号不能为空";
	String A002 = "报销单编号不能为空";
	String A003 = "不存在的雇员";
	String A004 = "用户编号不能为空";
	String A005 = "报销单不能为空";
	String A006 = "只有员工才能执行该操作";
	String A007 = "不存在的报销单";
	String A008 = "该报销单不属于您";
	String A009 = "无效的报销单";
	String A0010 = "您当前无权操作该报销单";
	String A0011 = "报销单明细不能为空";
	String A0012 = "无效的报销单明细";
	String A0013 = "删除报销单明细失败";
	String A0014 = "删除报销单失败";
	String A0015 = "该报销单已被删除";
	String A0016 = "该报销单明细已被删除";
	String A0017 = "不允许提交没有明细的报销单";
	String A0018 = "您不属于任何部门";
	/*
	 * 总经理
	 */
	Long GMANAGER = 1L;
	/*
	 * 部门经理
	 */
	Long DMANAGER = 2L;
	/*
	 * 员工
	 */
	Long EMPLOYEE = 3L;
}
