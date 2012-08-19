package org.hzy.service;

import java.util.List;
import java.util.Map;

import org.hzy.exception.MyException;
import org.hzy.vo.HandleDispatchVo;
import org.hzy.vo.Result;

/**
 * @author fy
 * @date 2012-8-18
 * @class IGManagerService
 * @extends Object
 * @description 总经理功能借口
 */
public interface IGManagerService {

	/**
	 * 总经理审批报销单
	 * 
	 * @param arVo
	 * @return a {@link Result}
	 */
	public Result approval(HandleDispatchVo arVo);

	/**
	 * 查询带我审批报销单
	 * 
	 * @param gsn
	 * @return {@link List}<<{@link Map}>>
	 *         <ul>
	 *         <li>DLID：报销单编号</li>
	 *         <li>CREATETIME：报销单创建时间</li>
	 *         <li>ESN：创建人编号</li>
	 *         <li>CURRENTSTATUS：报销单当前状态</li>
	 *         <li>TOTALMONEY：报销单明细总金额</li>
	 *         </ul>
	 */
	public List<Map<String, Object>> findWaitingApprovalForMe(String gsn);

	/**
	 * 总经理登入
	 * 
	 * @throws MyException
	 */
	public void Login() throws MyException;

	/**
	 * 总经理终止已审批报销单
	 * 
	 * @param hdVo
	 * @return a {@link Result}
	 */
	public Result repealDispatchListForAlreadyApproval(HandleDispatchVo hdVo);
}
