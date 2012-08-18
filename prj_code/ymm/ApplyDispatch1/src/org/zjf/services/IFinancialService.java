package org.zjf.services;

import org.ymm.entity.SysEmployee;
import org.ymm.entity.SysPositions;
import org.ymm.exception.MyException;
import org.ymm.vo.BaseVo;
import org.ymm.vo.DispatchResultVo;
import org.ymm.vo.Page;
import org.ymm.vo.Result;

/**
 * @project:ApplyDispatch1
 * @author:zjf
 * @date:2012-8-16 下午4:31:36
 * @class:IFinancialService
 * @extends:Object
 * @description:财务处业务接口
 */
public interface IFinancialService {
	/**
	 * 审批报销单
	 * 
	 * @param emp
	 *            雇员对象
	 * @param result
	 *            审批报销单对象
	 * @return boolean true:审批成功，false:审批失败
	 */
	public Result applyClaims(final SysEmployee emp,
			final DispatchResultVo vo) throws MyException;

	/**
	 * 登录
	 * 
	 * @param username
	 *            用户名
	 * @param pwd
	 *            密码
	 * @return SysPositions
	 * @throws MyException
	 * 
	 */
	public SysPositions loginUser(final String username, final String pwd)
			throws MyException;

	/**
	 * 查看报销单
	 * 
	 * @param emp
	 *            财务人员
	 * @param start
	 *            起始
	 * @param limit
	 *            条数
	 * @return Page page对象
	 */
	public Page lookClaims(final SysEmployee emp,final BaseVo vo)throws MyException;

	/**
	 * 付款
	 * @param emp
	 * 			财务人员
	 * @param result
	 * 			审批记录
	 * @return boolean
	 * 			True:审批成功，false:审批失败
	 */
	public Result payMent(final SysEmployee emp, final DispatchResultVo vo)throws MyException;

	/**
	 * 生成报表
	 * @param emp
	 * 			财务人员
	 */
	public void GeneralExcel(final SysEmployee emp)throws MyException;
}
