package org.ymm.services;

import org.zjf.entity.DispatchResult;
import org.zjf.entity.SysEmployee;
import org.zjf.entity.SysPositions;
import org.zjf.exception.MyException;
import org.zjf.vo.Page;

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
	public boolean applyClaims(final SysEmployee emp,final DispatchResult result)throws MyException;
	
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
	public Page findMyApply(final SysEmployee emp,final int start,int limit)throws MyException;
	
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
}
