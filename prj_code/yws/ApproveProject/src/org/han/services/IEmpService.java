package org.han.services;

import org.han.entity.DispatchDetail;
import org.han.entity.DispatchList;
import org.han.entity.LoginUser;
import org.han.vo.BaseVo;
import org.han.vo.DispatchDetailVo;
import org.han.vo.DispatchListVo;
import org.han.vo.LoginVo;
import org.han.vo.Page;
import org.han.vo.Result;

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

	/**
	 * @param vo
	 * @return
	 * @throws Exception
	 * @Description: 雇员登录
	 */
	public LoginUser login(LoginVo vo) throws Exception;
}
