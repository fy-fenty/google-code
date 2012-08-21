package org.fy.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.fy.entity.DispatchDetail;
import org.fy.entity.DispatchList;
import org.fy.entity.DispatchResult;
import org.fy.entity.LoginUser;
import org.fy.entity.SysEmployee;
import org.fy.entity.SysPositions;
/**
 * @author hzy
 * @date 2012-8-15
 *	@extends Object
 * @class ISystemService
 * @description 系统接口
 */
public interface ISystemService {
	/**
	 * 加密密码
	 * @param pwd
	 * @return 加密后的字符串
	 */
	public String getMd5(final String pwd) throws NoSuchAlgorithmException;
	
	/**
	 * 查询指定报销单
	 * @param id
	 * @return DispatchList 报销单对象
	 */
	public DispatchList findByDlistId(final Long id);
	
	/**
	 *	查询指定报销单最后的审批记录
	 * @param id
	 * @return DispatchResult 审批对象
	 */
	public DispatchResult findResultById(final Long id);
	
	/**
	 * 查询指定报销单明细
	 * @param id
	 * @return List 报销单明细集合
	 */
	public List findDetailById(final Long id);
	
	/**
	 * 查询指定报销单流程
	 * @param id
	 * @return 
	 */
	public List findDlistResultById(final Long id);
	
	/**
	 * 查询用户职位
	 * @param sn
	 * @return SysPositions 职位对象
	 */
	public SysPositions findPositionBySn(final String sn);
	
	/**
	 * 根据用户编号查询用户
	 * @param sn
	 * @return LoginUser 用户对象
	 */
	public LoginUser findUserBySn(final String sn);
	
	/**
	 * 根据用户编号查询雇员
	 * @param sn
	 * @return SysEmployee 雇员对象
	 */
	public SysEmployee findEmployeeBySn(final String sn);
	
	/**
	 * 验证报销单权限,下一个审核人是否是你
	 * @param id
	 * @param sn
	 * @return boolean
	 */
	public boolean checkDlist(final Long id,final String sn);
}
