package org.fy.service;

import java.util.List;

import org.fy.entity.DispatchDetail;
import org.fy.entity.DispatchList;
import org.fy.entity.LoginUser;
import org.fy.entity.SysEmployee;
import org.fy.exception.MyExecption;
import org.fy.vo.BaseVO;
import org.fy.vo.Page;
import org.fy.vo.Result;

/**
 * @author hzy
 * @date 2012-8-15
 *	@extends Object
 * @class ISysEmployeeService
 * @description 员工接口
 */
public interface ISysEmployeeService {
	/**
	 * 员工保存报销单
	 * @param dlist
	 * @return Result
	 */
	public Result saveDispathList(final DispatchList dlist) throws MyExecption;
	
	/**
	 * 雇员增加报销单明细
	 * @param detail
	 * @return Result
	 */
	public Result saveDispathDetail(final SysEmployee sys_emp,final DispatchDetail detail) throws MyExecption;
	
	/**
	 * 雇员修改报销单
	 * @param dlist
	 * @return Result
	 */
	public Result updateDispathList(final String sn,final DispatchList dlist) throws MyExecption;
	
	/**
	 * 雇员修改报销单明细
	 * @param detail
	 * @return Result
	 */
	public Result updateDispathDetail(final String sn,final DispatchDetail detail) throws MyExecption;
	
	/**
	 * 雇员提交报销单
	 * @param dlist
	 * @return Result
	 */
	public Result commitDispathList(final String sn,final DispatchList dlist) throws MyExecption;
	
	/**
	 * 雇员查询报销单
	 * @param sn
	 * @return Page
	 */
	public Page findDispathList(final BaseVO bv,final String sn) throws MyExecption;
	
	/**
	 * 雇员删除报销单
	 * @param id
	 * @return Result
	 */
	public Result deleteDispathList(final String sn,final Long id) throws MyExecption;
	
	/**
	 * 雇员删除报销单明细
	 * @param id
	 * @return Result
	 */
	public Result deleteDispathDetail(final String sn,final Long id) throws MyExecption;	
	
	/**
	 * 登录
	 * @param sn去32
	 * @return LoginUser
	 */
	public LoginUser login_user(final String sn) throws MyExecption;
}
