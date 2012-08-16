package org.ymm.services;

import org.zjf.entity.DispatchResult;
import org.zjf.entity.SysEmployee;
import org.zjf.entity.SysPositions;
import org.zjf.exception.MyException;
import org.zjf.vo.Page;

/**
 * @project:ApplyDispatch1
 * @author:zjf
 * @date:2012-8-16 下午4:22:54   
 * @class:IManagerService
 * @extends:Object
 * @description:部门经理业务接口
 */
public interface IManagerService {

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
	public Page findMyApplyClaims(final SysEmployee emp,final int start,int limit)throws MyException;
	
	/**
	 * 密码重置
	 * @param manager
	 * 			部门经理
	 * @param emp
	 * 			雇员
	 * @return boolean
	 * 			true:修改成功，false:修改失败
	 * 			
	 * @throws MyException
	 */
	public boolean SetPwd(final SysEmployee manager,final SysEmployee emp)throws MyException;
	
	/**
	 * 	查询部门报销单
	 * @param emp
	 * 			部门经理
	 * @param start
	 * 			起始
	 * @param limit
	 * 			条数
	 * @return Page
	 * 			Page对象
	 * @throws MyException
	 */
	public Page findMyDepartClaims(final SysEmployee emp,final int start,int limit)throws MyException;
	
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
