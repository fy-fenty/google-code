package org.han.services;

import org.tender.entity.DispatchDetail;
import org.tender.entity.DispatchList;
import org.tender.entity.DispatchResult;
import org.tender.vo.BaseVo;
import org.tender.vo.DispatchDetailVo;
import org.tender.vo.DispatchListVo;
import org.tender.vo.Page;
import org.tender.vo.Result;

/**
 * @author HanZhou
 * @className: IEmpService
 * @date 2012-8-15
 * @extends Object
 * @description: 员工业务逻辑
 */
public interface IEmpService {

	/**
	 * @param empNo
	 * @param dis
	 * @return Result
	 * @throws Exception
	 * @Description: 保存报销单
	 */
	public Result saveDispatch(String empNo, DispatchList dis) throws Exception;

	/**
	 * @param empSN
	 * @param dis
	 * @return Result
	 * @throws Exception
	 * @Description: 修改报销单
	 */
	public Result updateDispatch(String empSN, DispatchListVo dis)
			throws Exception;

	/**
	 * @param empSN
	 * @param disDetail
	 * @return Result
	 * @Description: 修改报销单明细
	 */
	public Result updateDisDetail(String empSN, DispatchDetailVo disDetail)
			throws Exception;

	/**
	 * @param empSN
	 * @param disId
	 * @return Result
	 * @throws Exception
	 * @Description: 删除报销单
	 */
	public Result deleteDispatch(String empSN, Long disId) throws Exception;

	/**
	 * @param empSN
	 * @param disId
	 * @param detailId
	 * @return Result
	 * @Description: 删除报销单明细
	 */
	public Result deleteDisDetail(String empSN, Long disId, Long detailId)
			throws Exception;

	/**
	 * @param empSN
	 * @param disDetail
	 * @return Result
	 * @throws Exception
	 * @Description: 增加报销单明细
	 */
	public Result saveDisDetail(String empSN, DispatchDetail disDetail)
			throws Exception;

	/**
	 * @param empSN
	 * @param disList
	 * @return Result
	 * @throws Exception
	 * @Description: TODO
	 */
	public Result commitDispatch(String empSN, Long disId) throws Exception;

	/**
	 * @param vo
	 * @param empSN
	 * @return Page
	 * @throws Exception
	 * @Description: 查询雇员报销单
	 */
	public Page findDisByEmpId(BaseVo vo, String empSN) throws Exception;
}
