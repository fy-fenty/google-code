package org.hzy.support;

import java.util.List;

import org.hzy.entity.DispatchDetail;
import org.hzy.entity.DispatchList;
import org.hzy.entity.DispatchResult;
import org.hzy.entity.LoginUser;
import org.hzy.entity.SysEmployee;
import org.hzy.entity.SysPositions;
import org.hzy.exception.MyException;

/**
 * @author fy
 * @date 2012-8-15
 * @class ISystem
 * @extends IBaseDAO
 * @description 系统功能接口
 */
public interface ISystemUtil extends IBaseDAO<Object, Long> {
	/**
	 * 得到指定参数的MD5
	 * 
	 * @param input
	 *            加密字符串
	 * @return
	 */
	public abstract String getMD5(String input) throws MyException;

	/**
	 * 通过 ID 查找指定报销单
	 * 
	 * @param dlId
	 *            报销单 ID
	 * @return
	 */
	public abstract DispatchList findDispatchListByDlId(Long dlId) throws MyException;

	/**
	 * 通过报销单 ID 查找该报销单当前审批记录
	 * 
	 * @param dlId
	 *            报销单 ID
	 * @return
	 */
	public abstract DispatchResult findCurrentDispatchResultByDlId(Long dlId) throws MyException;

	/**
	 * 通过报销单 ID 查找报销单所有有效明细
	 * 
	 * @param dlId
	 *            报销单 ID
	 * @return
	 */
	public abstract List<DispatchDetail> findDispatchDetailByDlId(Long dlId) throws MyException;

	/**
	 * 通过报销单 ID 查找报销单所有流程
	 * 
	 * @param dlId
	 *            报销单 ID
	 * @return
	 */
	public abstract List<DispatchResult> findDispatchResultByDlId(Long dlId) throws MyException;

	/**
	 * 通过用户编号 查找指定用户职位
	 * 
	 * @param eSn
	 *            用户编号
	 * @return
	 */
	public abstract SysPositions findSysPositionsByESn(String eSn) throws MyException;

	/**
	 * 通过用户编号 查找用户
	 * 
	 * @param eSn
	 *            用户编号
	 * @return
	 */
	public abstract LoginUser findLoginUserByESn(String eSn) throws MyException;

	/**
	 * 通过用户编号 查询雇员
	 * 
	 * @param eSn
	 *            用户编号
	 * @return
	 */
	public abstract SysEmployee findSysEmployeeByESn(String eSn) throws MyException;

	/**
	 * 通过用户编号 和报销单 ID 验证是否有权限审批
	 * 
	 * @param eSn
	 *            用户编号
	 * @param dlId
	 *            报销单 ID
	 * @return
	 */
	public abstract Boolean checkPermissionsByESnAndDlId(String eSn, Long dlId) throws MyException;
}