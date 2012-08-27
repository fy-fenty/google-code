package org.ymm.services;

import org.zjf.entity.DispatchList;
import org.zjf.entity.DispatchResult;
import org.zjf.entity.LoginUser;
import org.zjf.entity.SysPositions;
import org.zjf.exception.MyException;
import org.zjf.vo.BaseVo;
import org.zjf.vo.Page;
import org.zjf.vo.Result;

/**
 * @project:ApplyDispatch1
 * @author:zjf
 * @date:2012-8-15 下午1:22:27   
 * @class:ISystemService
 * @extends:Object
 * @description:系统提供的服务
 */
public interface ISystemService {

	/**
	 * MD5加密字符串
	 * @param pwd
	 * 			要加密的字符串
	 * @return String
	 * 			已加密的字符串
	 */
	public String getMD5(final String pwd);
	
	/**
	 * 通过id查询指定报销单
	 * @param cid
	 * 			报销单id
	 * @return DispatchList
	 * 			报销单对象
	 * @throws MyException
	 * 			A006 
	 */
	public DispatchList findById(final long cid) throws MyException;
	
	/**
	 * 通过id查询指定报销单的当前审批记录
	 * @param cid
	 * 			报销单id
	 * @return DispatchResult
	 * 			报销单审批对象
	 * @throws MyException 
	 * 			A005
	 */
	public DispatchResult findResultById(final long cid) throws MyException;
	
	/**
	 * 通过报销单id查询报销单的明细
	 * @param id
	 * 			报销单id
	 * @return List
	 * 			明细集合
	 * @throws MyException
	 * 			A005 
	 */
	public Page findDetailById(final long id,BaseVo vo) throws MyException;
	
	/**
	 * 查询指定报销单流程
	 * @param id
	 * 			报销单id
	 * @return List
	 * 			审批记录集合	
	 * @throws MyException 
	 * 			A004
	 */
	public Page findResultListById(final long id,final int start,final int limit) throws MyException;
	
	/**
	 * 通过用户职位id查询用户职位
	 * @param id
	 * 			用户职位id
	 * @return SysPositions
	 * 			职位对象
	 * @throws MyException
	 * 			A003 
	 */
	public SysPositions findPositionById(final long id) throws MyException;
	
	/**
	 * 通过雇员编号查询用户
	 * @param sn
	 * 			雇员编号
	 * @return LoginUser
	 * 			用户对象
	 * @throws MyException
	 * 			A003 
	 */
	public LoginUser findUserBySn(final String sn) throws MyException;
	
	/**
	 * 验证报销单权限
	 * @param sn
	 * 			雇员编号
	 * @param cid
	 * 			报销单id
	 * @return boolean
	 * 			true：有权，false:无权
	 * @throws MyException 
	 * 			A002,A005
	 */
	public Result checkSys(final String sn,final long cid) throws MyException;
	
}
