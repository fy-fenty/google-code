package org.hzy.service;

import java.util.List;
import java.util.Map;

import org.hzy.vo.HandleDispatchVo;
import org.hzy.vo.ResetPasswordVo;
import org.hzy.vo.Result;

/**
 * 
 * @author fy
 * @date 2012-8-18
 * @class IManagerService
 * @extends Object
 * @description 部门经理功能接口
 */
public interface IDManagerService {

	/**
	 * 部门经理审批员工报销单
	 * 
	 * @param arVo
	 * @return a {@link Result}
	 */
	public Result approval(HandleDispatchVo arVo);

	/**
	 * 部门经理对员工密码进行重置
	 * 
	 * @param rpVo
	 * @return a Result
	 */
	public Result resetPassword(ResetPasswordVo rpVo);

	/**
	 * 查询待我审核报销单
	 * 
	 * @param dsn
	 *            部门经理编号
	 * @return {@link List}<<{@link Map}>>
	 *         <ul>
	 *         <li>DLID：报销单编号</li>
	 *         <li>CREATETIME：报销单创建时间</li>
	 *         <li>ESN：创建人编号</li>
	 *         <li>CURRENTSTATUS：报销单当前状态</li>
	 *         <li>TOTALMONEY：报销单明细总金额</li>
	 *         </ul>
	 */
	public List<Map<String, Object>> findWaitingApprovalForMe(String dsn);

	/**
	 * 查询部门报销单
	 * 
	 * @param dsn
	 *            部门编号
	 * @return {@link List}<<{@link Map}>>
	 *         <ul>
	 *         <li>DLID：报销单编号</li>
	 *         <li>CREATETIME：报销单创建时间</li>
	 *         <li>ESN：创建人编号</li>
	 *         <li>CURRENTSTATUS：报销单当前状态</li>
	 *         <li>TOTALMONEY：报销单明细总金额</li>
	 *         </ul>
	 */
	public List<Map<String, Object>> findDispatchListByDeparment(String dsn);
}
