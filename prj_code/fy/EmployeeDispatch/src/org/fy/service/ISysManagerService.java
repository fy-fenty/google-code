package org.fy.service;

import org.fy.entity.DispatchList;
import org.fy.entity.DispatchResult;
import org.fy.entity.LoginUser;
import org.fy.exception.MyExecption;
import org.fy.vo.BaseVO;
import org.fy.vo.DispatchListVO;
import org.fy.vo.DispatchResultVO;
import org.fy.vo.Page;
import org.fy.vo.Result;

/**
 * @author hzy
 * @date 2012-8-17
 *	@extends Object
 * @class ISysManagerService
 * @description 总经理业务操作接口
 */
public interface ISysManagerService {
	/**
	 * 总经理审批
	 * @param drsvo 审批VO
	 * @return Result
	 */
	public Result checkDispatchResult(final DispatchResultVO drsvo);
	
	/**
	 * 总经理查询待我审核报销单
	 * @param bv 分页VO
	 * @param sn 编号
	 * @return Page
	 */
	public Page findWaitCheckDisList(final BaseVO bv,final String sn) throws MyExecption;
	
	/**
	 * 总经理登录
	 * @param sn 编号
	 * @return 
	 */
	public LoginUser loginUser(final String sn);
	
	/**
	 *  终止已审批的报销单
	 * @param sn 编号
	 * @param dlist 报销单
	 * @return Result
	 */
	public Result stopAlreadyCheck(final DispatchListVO dlistvo);
}
