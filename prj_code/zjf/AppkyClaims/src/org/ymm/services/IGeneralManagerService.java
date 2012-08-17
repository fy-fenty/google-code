package org.ymm.services;

import org.zjf.entity.SysEmployee;
import org.zjf.entity.SysPositions;
import org.zjf.exception.MyException;
import org.zjf.vo.DispatchListVo;
import org.zjf.vo.DispatchResultVo;
import org.zjf.vo.Page;
import org.zjf.vo.Result;

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
	public Page findMyApply(final SysEmployee emp)throws MyException;
	
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
	public SysPositions loginUser(final String username,final String pwd) throws MyException;
	
	/**
	 * 终止报销单
	 * @param emp
	 * 			雇员
	 * @param list
	 * 			报销单
	 * @return Result
	 * 			Result对象
	 */
	public Result stopClaims(final SysEmployee emp,final DispatchListVo vo);
}
