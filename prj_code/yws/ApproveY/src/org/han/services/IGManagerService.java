package org.han.services;

import org.tender.vo.ApprovalVo;
import org.tender.vo.BaseVo;
import org.tender.vo.Page;
import org.tender.vo.Result;

/**
 * @author HanZhou
 * @className: IGManagerService
 * @date 2012-8-17
 * @extends Object
 * @description: 总经理业务逻辑接口
 */
public interface IGManagerService {
	/**
	 * @param vo
	 * @return
	 * @throws Exception
	 * @Description: 总经理审批
	 */
	public Result approval(ApprovalVo vo) throws Exception;

	/**
	 * @param empSN
	 * @param vo
	 * @return
	 * @throws Exception
	 * @Description: 查询待总经理审核报销单
	 */
	public Page findMeApproval(String empSN, BaseVo vo) throws Exception;

	/**
	 * @param vo
	 * @return
	 * @throws Exception
	 * @Description: 总经理终止报销单
	 */
	public Result finishDispatch(ApprovalVo vo) throws Exception;
}
