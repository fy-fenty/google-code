package org.fy.service;

import org.fy.entity.DispatchList;
import org.fy.entity.DispatchResult;
import org.fy.entity.LoginUser;
import org.fy.entity.SysEmployee;
import org.fy.exception.MyExecption;
import org.fy.vo.BaseVO;
import org.fy.vo.DispatchResultVO;
import org.fy.vo.Page;
import org.fy.vo.Result;

/**
 * @author hzy
 * @date 2012-8-17
 *	@extends Object
 * @class ISysDepartManagerService
 * @description 部门经理业务操作接口
 */
public interface ISysDepartManagerService {
	/**
	 * 部门经理审批
	 * @param sn 编号
	 * @param drsvo 报销单审批VO
	 * @return Result
	 */
	public Result checkDispatchResult(final DispatchResultVO drsvo);
	
	/**
	 * 密码重置
	 * @param sn 部门经理编号
	 * @param sys_emp 该部门下的员工对象
	 * @return Result
	 */
	public Result pwdReset(final String dsn,final String esn);
	
	/**
	 * 部门经理查询待我审核报销单
	 * @param bv 分页VO
	 * @param sn 编号
	 * @return Page
	 */
	public Page findWaitCheckDisList(final BaseVO bv,final String sn) throws MyExecption;
	
	/**
	 * 查询该部门的报销单
	 * @param bv 分页VO
	 * @param sn 编号
	 * @return Page
	 */
	public Page findDepartDisList(final BaseVO bv,final String sn) throws MyExecption;
	
	/**
	 * 部门经理登录
	 * @param sn 编号
	 * @return 
	 */
	public LoginUser loginUser(final String sn);
}
