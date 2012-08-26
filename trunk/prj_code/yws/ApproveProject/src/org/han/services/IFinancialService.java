package org.han.services;

import org.han.vo.ApprovalVo;
import org.han.vo.BaseVo;
import org.han.vo.Page;
import org.han.vo.Result;

/**
 * @author HanZhou
 * @className: IFinancialService
 * @date 2012-8-17
 * @extends Object
 * @description: 财务业务逻辑接口
 */
public interface IFinancialService {

	/**
	 * @param vo
	 * @return
	 * @throws Exception
	 * @Description: 财务付款
	 */
	public Result pay(ApprovalVo vo) throws Exception;

	/**
	 * @param vo
	 * @return
	 * @throws Exception
	 * @Description: 财务审批
	 */
	public Result approval(ApprovalVo vo) throws Exception;

	/**
	 * @param empSN
	 * @param vo
	 * @return
	 * @throws Exception
	 * @Description: 查询以审批或以付款的报销单
	 */
	public Page findApproval(String empSN, BaseVo vo) throws Exception;

	/**
	 * @param empSN
	 * @return
	 * @throws Exception
	 * @Description: 生成报表
	 */
	public Result createStatistics(String empSN) throws Exception;
}
