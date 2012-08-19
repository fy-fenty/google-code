package org.han.services;

import java.util.List;

import org.tender.entity.DispatchList;
import org.tender.entity.DispatchResult;
import org.tender.entity.LoginUser;
import org.tender.entity.SysEmployee;
import org.tender.entity.SysPositions;

/**
 * @author HanZhou
 * @className: ISystemServices
 * @date 2012-8-15
 * @extends Object
 * @description: 系统业务逻辑services接口
 */
public interface ISystemServices {

	/**
	 * @param pwd
	 * @return 加密后的密码
	 * @throws Exception
	 * @Description: 密码加密
	 */
	public String getMD5Encryption(String pwd);

	/**
	 * @param disId
	 * @return 报销单对象
	 * @throws Exception
	 * @Description: 查询指定报销单
	 */
	public DispatchList getDisListById(Long disId);

	/**
	 * @param disId
	 * @return 当前最新审批报销单记录
	 * @throws Exception
	 * @Description: 查询指定报销单当前审批记录
	 */
	public DispatchResult getDisResultByDisId(Long disId);

	/**
	 * @param disId
	 * @return 明细集合
	 * @throws Exception
	 * @Description: 查询指定报销单明细
	 */
	public List getDispatchDetails(Long disId);

	/**
	 * @param disId
	 * @return 报销单审批记录集合
	 * @throws Exception
	 * @Description: 查询指定报销单流程
	 */
	public List getDispatchResults(Long disId);

	/**
	 * @param empSN
	 * @return 雇员职位
	 * @throws Exception
	 * @Description: 查询用户职位
	 */
	public SysPositions getSysPositionsBySN(String empSN);

	/**
	 * @param empSN
	 * @return 员工对象
	 * @throws Exception
	 * @Description: 根据用户编号查询雇员
	 */
	public SysEmployee getSysEmployeeBySN(String empSN);

	/**
	 * @param empSN
	 * @return 用户对象
	 * @throws Exception
	 * @Description: 根据雇员编号查询用户
	 */
	public LoginUser getLoginUserBySN(String empSN);

	/**
	 * @param disId
	 * @param empSN
	 * @return 该雇员是否有操作权限
	 * @throws Exception
	 * @Description: 验证报销单权限
	 */
	public boolean checkEmpPermi(Long disId, String empSN);
}
