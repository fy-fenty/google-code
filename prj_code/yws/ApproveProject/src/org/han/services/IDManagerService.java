package org.han.services;

import org.han.vo.ApprovalVo;
import org.han.vo.BaseVo;
import org.han.vo.Page;
import org.han.vo.Result;

public interface IDManagerService {

	/**
	 * @param vo
	 * @return
	 * @throws Exception
	 * @Description: 部门经理审批
	 */
	public Result approval(ApprovalVo vo) throws Exception;

	/**
	 * @param d_sn
	 *            部门经理编号
	 * @param empSN
	 *            需要重置密码雇员编号
	 * @return
	 * @throws Exception
	 * @Description: 密码重置
	 */
	public Result resetPwd(String dSN, String empSN) throws Exception;

	/**
	 * @param empSN
	 * @param vo
	 * @return
	 * @throws Exception
	 * @Description: 查询待部门经理审核报销单
	 */
	public Page findMeApproval(String empSN, BaseVo vo) throws Exception;

	/**
	 * @param empSN
	 * @param vo
	 * @return
	 * @throws Exception
	 * @Description: 查询该部门报销单
	 */
	public Page findDeptDispatch(String empSN, BaseVo vo) throws Exception;
}
