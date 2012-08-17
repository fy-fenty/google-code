package org.hzy.support.impl;

import java.util.List;

import org.hzy.constant.AppConstant;
import org.hzy.entity.DispatchDetail;
import org.hzy.entity.DispatchList;
import org.hzy.entity.DispatchResult;
import org.hzy.entity.LoginUser;
import org.hzy.entity.SysEmployee;
import org.hzy.entity.SysPositions;
import org.hzy.exception.MyException;
import org.hzy.support.ISystemUtil;
import org.hzy.util.MD5;
import org.hzy.util.MyMatcher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author fy
 * @date 2012-8-15
 * @class System
 * @extends
 * @implements ISystem
 * @description 系统功能实现
 */
public class SystemUtil extends BaseDAO<Object, Long> implements ISystemUtil {

	@Override
	public String getMD5(String input) {
		return MD5.getMD5(input);
	}

	@Override
	public DispatchList findDispatchListByDlId(Long dlId) throws MyException {
		if (dlId == null) {
			throw new MyException(AppConstant.A002);
		}
		return super.get(DispatchList.class, dlId);
	}

	@Override
	public DispatchResult findCurrentDispatchResultByDlId(Long dlId) throws MyException {
		if (dlId == null) {
			throw new MyException(AppConstant.A002);
		}
		String hql = "from DispatchResult where drId = (select max(drId)  from DispatchResult where sheetId = ?)";
		return super.findUnique(hql, dlId);
	}

	@Override
	public List<DispatchDetail> findDispatchDetailByDlId(Long dlId) throws MyException {
		if (dlId == null) {
			throw new MyException(AppConstant.A002);
		}
		String hql = "from DispatchDetail dl where dl.flag=1 and dl.sheetId = ?";
		return super.find(hql, dlId);
	}

	@Override
	public List<DispatchResult> findDispatchResultByDlId(Long dlId) throws MyException {
		if (dlId == null) {
			throw new MyException(AppConstant.A002);
		}
		String hql = "from DispatchResult dr where dr.sheetId = ?";
		return super.find(hql, dlId);
	}

	@Override
	public SysPositions findSysPositionsByESn(String eSn) throws MyException {
		if (MyMatcher.isEmpty(eSn)) {
			throw new MyException(AppConstant.A004);
		}
		String hql = "from SysPositions where PId =(select PId from SysEmployee where ESn = ?)";
		return super.findUnique(hql, eSn);
	}

	@Override
	public LoginUser findLoginUserByESn(String eSn) throws MyException {
		if (MyMatcher.isEmpty(eSn)) {
			throw new MyException(AppConstant.A004);
		}
		String hql = "from LoginUser where ESn = ?";
		return super.findUnique(hql, eSn);
	}

	@Override
	public SysEmployee findSysEmployeeByESn(String eSn) throws MyException {
		if (MyMatcher.isEmpty(eSn)) {
			throw new MyException(AppConstant.A004);
		}
		String hql = "from SysEmployee where ESn = ?";
		return super.findUnique(hql, eSn);
	}

	@Override
	public Boolean checkPermissionsByESnAndDlId(String ESn, Long dlId) throws MyException {
		if (MyMatcher.isEmpty(ESn)) {
			throw new MyException(AppConstant.A004);
		}
		if (dlId == null) {
			throw new MyException(AppConstant.A002);
		}
		DispatchResult dr = this.findCurrentDispatchResultByDlId(dlId);
		if (dr != null) {
			return dr.getCheckNext().equals(ESn);
		}
		SysPositions sp = this.findSysPositionsByESn(ESn);
		if (sp == null) {
			return false;
		}
		System.out.println(sp.getPId().intValue());
		System.out.println(AppConstant.EMPLOYEE.intValue());
		System.out.println(sp.getPId().intValue() == AppConstant.EMPLOYEE.intValue());
		return sp.getPId().intValue() == AppConstant.EMPLOYEE.intValue();
	}

	public static void main(String[] args) throws MyException {
		Long dlId = 1L;
		String eSn = "10000000";
		/* MD5 加密 */
		// SystemUtil su = new SystemUtil();
		// System.out.println(su.getMD5("23123"));
		ApplicationContext actc = new ClassPathXmlApplicationContext(new String[] { "hibernate-spring.xml",
				"beans1.xml" });
		ISystemUtil is = actc.getBean("SystemUtil", ISystemUtil.class);

		// /* 查找指定报销单 */
		// System.out.println("查找指定报销单");
		// DispatchList dl = is.findDispatchListByDlId(dlId);
		// System.out.println(dl.getDlId());
		//
		// /* 查询指定报销单当前审批记录 */
		// System.out.println("查询指定报销单当前审批记录");
		// DispatchResult dr = is.findCurrentDispatchResultByDlId(41L);
		// System.out.println(dr.getDrId());
		//
		// /* 查询指定报销单明细 */
		// System.out.println("查询指定报销单明细");
		// List<DispatchDetail> dds = is.findDispatchDetailByDlId(dlId);
		// for (DispatchDetail dispatchDetail : dds) {
		// System.out.println(dispatchDetail.getDsId());
		// }
		//
		// /* 查找指定报销单所有流程 */
		// System.out.println("查找指定报销单所有流程");
		// List<DispatchResult> ddss = is.findDispatchResultByDlId(dlId);
		// for (DispatchResult dispatchResult : ddss) {
		// System.out.println(dispatchResult.getDrId());
		// }
		//
		// /* 根据用户编号查询雇员 */
		// System.out.println("根据用户编号查询雇员");
		// SysEmployee se = is.findSysEmployeeByESn(eSn);
		// System.out.println(se.getEId());
		//
		// /* 通过用户 ID 查找指定用户职位 */
		// System.out.println("通过用户 ID 查找指定用户职位");
		// SysPositions sp = is.findSysPositionsByESn(eSn);
		// System.out.println(sp.getPId());
		//
		// /* 通过用户 ID 查找用户 */
		// System.out.println("通过用户 ID 查找用户");
		// LoginUser lu = is.findLoginUserByESn(eSn);
		// System.out.println(lu.getESn());
		//
		// /* 通过用户 ID 查询雇员 */
		// System.out.println("通过用户 ID 查询雇员");
		// SysEmployee see = is.findSysEmployeeByESn(eSn);
		// System.out.println(see.getEId());
		//
		// /* 通过用户 ID 和报销单 ID 验证是否有权限审批 */
		// System.out.println("通过用户 ID 和报销单 ID 验证是否有权限审批");
		// Boolean bl = is.checkPermissionsByESnAndDlId(eSn, dlId);
		// System.out.println(bl);
	}

}