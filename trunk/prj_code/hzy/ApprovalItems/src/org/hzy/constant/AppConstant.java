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
	String A006 = "只有员工才能保存报销单";
	Long GMANAGER = 1L;
	Long DMANAGER = 2L;
	Long EMPLOYEE = 3L;
}
