package org.hzy.service;

import java.util.List;
import java.util.Map;

import org.hzy.exception.MyException;
import org.hzy.vo.HandleDispatchVo;
import org.hzy.vo.Result;

/**
 * @author fy
 * @date 2012-8-19
 * @class IFinancialService
 * @extends Object
 * @description 财务功能借口
 */
public interface IFinancialService {
	/**
	 * 财务付款
	 * 
	 * @param hdVo
	 * @return a {@link Result}
	 */
	public Result payMoney(HandleDispatchVo hdVo);

	/**
	 * 财务审批
	 * 
	 * @param hdVo
	 * @return a {@link Result}
	 */
	public Result approval(HandleDispatchVo hdVo);

	/**
	 * 财务查看待付款报销单
	 * 
	 * @param eSn
	 *            财务编号
	 * @throws MyException
	 */
	public List<Map<String, Object>> findDispatchListForMe(String eSn);

	/**
	 * 财务生成报表
	 * 
	 * @param eSn
	 * @return a {@link Result}
	 */
	public Result generateReport(String eSn);
}
