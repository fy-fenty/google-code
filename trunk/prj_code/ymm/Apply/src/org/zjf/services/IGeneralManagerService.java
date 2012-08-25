package org.zjf.services;

import org.ymm.entity.SysEmployee;
import org.ymm.entity.SysPositions;
import org.ymm.exception.MyException;
import org.ymm.vo.BaseVo;
import org.ymm.vo.DispatchListVo;
import org.ymm.vo.DispatchResultVo;
import org.ymm.vo.LoginUserVo;
import org.ymm.vo.Page;
import org.ymm.vo.Result;

/**
 * @project:ApplyDispatch1
 * @author:zjf
 * @date:2012-8-16 上午11:47:26   
 * @class:IGeneralManagerService
 * @extends:Object
 * @description:总经理业务接口
 */
public interface IGeneralManagerService {

	/**
	 * 审批报销单
	 * @param emp
	 * 			雇员对象
	 * @param result
	 * 			审批报销单对象
	 * @return boolean
	 * 			true:审批成功，false:审批失败
	 */
	public Result applyClaims(final SysEmployee emp,final DispatchResultVo vo)throws MyException;
	
	/**
	 * 查询待我审核
	 * @param emp
	 * 			雇员对象
	 * @param start
	 * 			起始位置
	 * @param limit
	 * 			条数
	 * @return Page
	 * 			 返回Page对象
	 * @throws MyException
	 */
	public Page findMyApply(final SysEmployee emp,final BaseVo vo)throws MyException;
	
	/**
	 * 登录
	 * @param username
	 * 			用户名
	 * @param pwd
	 * 			密码
	 * @return SysPositions
	 * @throws MyException 
	 * 			
	 */
	public Result loginUser(final LoginUserVo vo) throws MyException;
	
	/**
	 * 终止报销单
	 * @param emp
	 * 			雇员
	 * @param list
	 * 			报销单
	 * @return Result
	 * 			Result对象
	 * @throws MyException 
	 */
	public Result stopClaims(final SysEmployee emp,final DispatchListVo vo) throws MyException;
}
