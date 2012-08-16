package org.ymm.services;

import org.zjf.entity.DispatchDetail;
import org.zjf.entity.DispatchList;
import org.zjf.entity.SysEmployee;
import org.zjf.entity.SysPositions;
import org.zjf.exception.MyException;
import org.zjf.vo.Page;
import org.zjf.vo.Result;

/**
 * @project:ApplyDispatch1
 * @author:zjf
 * @date:2012-8-14 下午8:29:01   
 * @class:IEmpService
 * @extends:Object
 * @description:雇员业务接口
 */
public interface IEmpService {
	
	/**
	 * 查询雇员报销单
	 * 				包含：创建人，金额，创建时间，和状态
	 * @param E_SN
	 * 			雇员编号
	 * @return Page
	 * 			Page对象
	 */
	public  Page findAllClaims(final SysEmployee emp,final int start,final int limit)throws MyException;
	
	/**
	 * 通过雇员编号编查询雇员对象
	 * @param SN
	 * 			雇员编号
	 * @return SysEmployee
	 * 			雇员对象
	 * @throws Exception
	 * 			A001	 
	 */
	public SysEmployee findBySn(final String SN) throws MyException;
	
	/**
	 * 通过雇员id和报销单Id删除指定报销单
	 * @param uid
	 * 			雇员id
	 * @param cid
	 * 			报销单Id
	 * @return Result
	 * 			删除成功为true,删除失败为false
	 */
	public Result deleteClaims(final SysEmployee emp,final long cid)throws MyException;
	
	/**
	 * 保存报销单
	 * @param cla
	 * 			封装好的报销单对象
	 * @return Result
	 * 			True:保存成功，false:保存失败
	 * @throws MyException 
	 */
	public Result saveClaims(final SysEmployee emp,final DispatchList cla) throws MyException;
	
	/**
	 * 修改报销单
	 * @param cla
	 * 			报销单对象
	 * @return Result
	 * 			True:修改成功，false:修改失败
	 * @throws MyException 
	 */
	public Result updateClaims(final SysEmployee emp,final DispatchList cla) throws MyException;
	
	/**
	 * 修改报销单明细
	 * @param detail
	 * 			报销单明细
	 * @return Result
	 * 			true:修改成功,false:修改失败
	 * @param uid
	 * 			雇员id
	 * @throws MyException 
	 * 			
	 */
	public Result updateDetail(final SysEmployee emp,final DispatchDetail detail) throws MyException;
	
	/**
	 * 删除报销单明细
	 * @param detail
	 * 			报销单明细
	 * @return Result
	 * 			True:删除成功，删除失败
	 * @throws MyException 
	 */
	public Result deleteDetail(final SysEmployee emp,final DispatchDetail detail) throws MyException;
	
	/**
	 * 增加报销单明细
	 * @param detail
	 * 			报销单明细
	 * @return Result
	 * 			true:删除成功，false:删除失败
	 * @throws MyException 
	 */
	public Result saveDetail(final SysEmployee emp,final DispatchDetail detail) throws MyException;
	
	/**
	 * 提交报销单
	 * @param cla
	 * 			报销单对象
	 * @return Result
	 * 			true：提交成功，false:提交失败
	 * @throws Exception 
	 */
	public Result commitClaims(final SysEmployee emp, final DispatchList cla) throws Exception;
	
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
