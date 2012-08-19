package org.fy.service;

import org.fy.entity.DispatchList;
import org.fy.entity.DispatchResult;
import org.fy.exception.MyExecption;
import org.fy.vo.BaseVO;
import org.fy.vo.DispatchListVO;
import org.fy.vo.DispatchResultVO;
import org.fy.vo.Page;
import org.fy.vo.Result;

/**
 * @author hzy
 * @date 2012-8-17
 *	@extends  Object
 * @class ISysFinanceService
 * @description  财务业务接口
 */
public interface ISysFinanceService {
	/**
	 * 财务审批
	 * @param drsvo 审批VO
	 * @return Result
	 */
	public Result checkDispatchResult(final DispatchResultVO drsvo);
	
	/**
	 * 查看报销单
	 * @param sn 编号
	 * @return Page
	 */
	public Page findDispatchList(final BaseVO bv,final String sn) throws MyExecption;
	
	/**
	 * 生成报表
	 * @param sn 编号
	 */
	public void  generateTable(final String sn);
	
	/**
	 * 付款
	 * @param dlistvo 报销单VO
	 * return Result
	 */
	public Result pay(DispatchListVO dlistvo);
}
