package org.hzy.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hzy.constant.AppConstant;
import org.hzy.dao.IDispatchDetailDao;
import org.hzy.dao.IDispatchListDao;
import org.hzy.dao.IDispatchResultDao;
import org.hzy.dao.ISysEmployeeDao;
import org.hzy.entity.DispatchDetail;
import org.hzy.entity.DispatchList;
import org.hzy.entity.DispatchResult;
import org.hzy.entity.LoginUser;
import org.hzy.entity.SysEmployee;
import org.hzy.entity.SysPositions;
import org.hzy.exception.MyException;
import org.hzy.service.IEmployeeService;
import org.hzy.support.ISystemUtil;
import org.hzy.util.MyMatcher;
import org.hzy.vo.Result;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author fy
 * @date 2012-8-16
 * @class SysEmployeeService
 * @extends Object
 * @implements ISysEmployeeService
 * @description 员工业务的接口实现
 */
public class EmployeeService implements IEmployeeService {

	private ISysEmployeeDao iseDao;

	private IDispatchListDao idlDao;

	private IDispatchDetailDao iddDao;

	private IDispatchResultDao idrDao;

	private ISystemUtil isu;

	@Override
	public List findAllDispatchListByESn(String eSn) throws MyException {
		if (MyMatcher.isEmpty(eSn)) {
			throw new MyException(AppConstant.A001);
		}
		String sql = "select d.dl_id dlId,d.e_sn ESn,d.create_time createTime,(select sum(dd.money) from hzy.DISPATCH_DETAIL dd where dd.sheet_id = d.dl_id)  totalMoney,(select d.check_status from hzy.dispatch_result d where d.dr_id = (select max(dr_id) from hzy.dispatch_result where sheet_id = d.dl_id)) currentStatus from hzy.dispatch_list d left join hzy.sys_employee s on d.e_sn = s.e_sn where s.e_sn = ? and d.flag = 1";
		SQLQuery sqlQuery = iseDao.createSQLQuery(sql, eSn);
		sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return sqlQuery.list();
	}

	@Override
	public Result saveDispatchList(String eSn, DispatchList dl) {
		Result rs = new Result();
		try {
			if (MyMatcher.isEmpty(eSn)) {
				throw new MyException(AppConstant.A004);
			}
			if (dl == null) {
				throw new MyException(AppConstant.A005);
			}
			SysPositions sp = isu.findSysPositionsByESn(eSn);
			if (sp.getPId().equals(AppConstant.EMPLOYEE) == false) {
				throw new MyException(AppConstant.A006);
			}
			idlDao.save(dl);
			rs.setSuccess(true);
			rs.setMsg("保存报销单成功！");
		} catch (MyException e) {
			rs.setSuccess(false);
			rs.setMsg("保存报销单失败！");
			rs.setException(e.getMessage());
		}
		return rs;
	}

	@Override
	public Result updateDispatchList(String eSn, Long dlId, String eventExplain) {
		Result rs = new Result();
		String hql = "update dispatch_list dl set event_explain = :ex where dl.flag = 1 and dl.dl_id = :dlId and dl.e_sn = :eSn and ("
				+ "(select count(1) from dispatch_result dr where dr.sheet_id = :dlId)=0 or (select check_next from dispatch_result where dr_id = "
				+ "(select max(dr_id) from dispatch_result where sheet_id = :dlId) and check_status = 4)= :eSn)";
		Map map = new HashMap();
		map.put("dlId", dlId);
		map.put("ex", eventExplain);
		map.put("eSn", eSn);
		SQLQuery query = idlDao.createSQLQuery(hql, map);
		if (query.executeUpdate() > 0) {
			rs.setSuccess(false);
			rs.setMsg("修改报销单失败！");
		} else {
			rs.setSuccess(true);
			rs.setMsg("修改报销单成功！");
		}
		return rs;
	}

	@Override
	public Result updateDispatchDetail(String eSn, DispatchDetail dd) {
		Result rs = new Result();
		try {
			if (MyMatcher.isEmpty(eSn)) {
				throw new MyException(AppConstant.A004);
			}
			if (dd == null) {
				throw new MyException(AppConstant.A0011);
			}
			if (dd.getSheetId() == null || dd.getFlag() == false) {
				throw new MyException(AppConstant.A0012);
			}
			DispatchList dl = isu.findDispatchListByDlId(dd.getSheetId());
			if (dl == null || dl.getFlag() == false) {
				throw new MyException(AppConstant.A0012);
			}
			if (dl.getESn().equals(eSn) == false) {
				throw new MyException(AppConstant.A008);
			}
			if (isu.checkPermissionsByESnAndDlId(eSn, dl.getDlId()) == false) {
				throw new MyException(AppConstant.A0010);
			}
			iddDao.save(dd);
			rs.setSuccess(true);
			rs.setMsg("修改报销单明细成功！");
		} catch (MyException e) {
			rs.setSuccess(false);
			rs.setMsg("修改报销单明细失败！");
			rs.setException(e.getMessage());
		}
		return rs;
	}

	@Override
	public Result deleteDispatchList(String eSn, DispatchList dl) {
		Result rs = new Result();
		try {
			if (MyMatcher.isEmpty(eSn)) {
				throw new MyException(AppConstant.A004);
			}
			if (dl == null) {
				throw new MyException(AppConstant.A005);
			}
			if (dl.getDlId() == null) {
				throw new MyException(AppConstant.A009);
			}
			if (dl.getFlag() == false) {
				throw new MyException(AppConstant.A0015);
			}
			if (dl.getESn().equals(eSn) == false) {
				throw new MyException(AppConstant.A008);
			}
			if (isu.checkPermissionsByESnAndDlId(eSn, dl.getDlId()) == false) {
				throw new MyException(AppConstant.A0010);
			}
			List<DispatchDetail> dds = isu.findDispatchDetailByDlId(dl.getDlId());
			if (dds.size() > 0) {
				String hql = "update DispatchDetail set flag = 0 where sheetId = ?";
				Query query = isu.createQuery(hql, dl.getDlId());
				if (query.executeUpdate() < dds.size()) {
					throw new MyException(AppConstant.A0013);
				}
			}
			String hql = "update DispatchList set flag = 0 where dlId = ?";
			Query query = isu.createQuery(hql, dl.getDlId());
			if (query.executeUpdate() == 0) {
				throw new MyException(AppConstant.A0014);
			}
			rs.setSuccess(true);
			rs.setMsg("删除报销单成功！");
		} catch (MyException e) {
			rs.setSuccess(false);
			rs.setMsg("删除报销单失败！");
			rs.setException(e.getMessage());
		}
		return rs;
	}

	@Override
	public Result deleteDispatchDetail(String eSn, DispatchDetail dd) {
		Result rs = new Result();
		try {
			if (MyMatcher.isEmpty(eSn)) {
				throw new MyException(AppConstant.A004);
			}
			if (dd == null) {
				throw new MyException(AppConstant.A0011);
			}
			if (dd.getDsId() == null || dd.getSheetId() == null) {
				throw new MyException(AppConstant.A0012);
			}
			if (dd.getFlag() == false) {
				throw new MyException(AppConstant.A0016);
			}
			DispatchList dl = idlDao.get(dd.getSheetId());
			if (dl == null || dl.getFlag() == false) {
				throw new MyException(AppConstant.A0012);
			}
			if (dl.getESn().equals(eSn) == false) {
				throw new MyException(AppConstant.A008);
			}
			if (isu.checkPermissionsByESnAndDlId(eSn, dl.getDlId()) == false) {
				throw new MyException(AppConstant.A0010);
			}
			String hql = "update DispatchDetail set flag = 0 where dsId = ?";
			Query query = isu.createQuery(hql, dd.getDsId());
			if (query.executeUpdate() == 0) {
				throw new MyException(AppConstant.A0013);
			}
			rs.setSuccess(true);
			rs.setMsg("删除报销单明细成功！");
		} catch (MyException e) {
			rs.setSuccess(false);
			rs.setMsg("删除报销单明细失败！");
			rs.setException(e.getMessage());
		}
		return rs;
	}

	@Override
	public Result addDispatchDetail(String eSn, DispatchDetail dd) {
		Result rs = new Result();
		try {
			if (MyMatcher.isEmpty(eSn)) {
				throw new MyException(AppConstant.A004);
			}
			if (dd == null) {
				throw new MyException(AppConstant.A0011);
			}
			if (dd.getSheetId() == null) {
				throw new MyException(AppConstant.A0012);
			}
			if (dd.getFlag() == false) {
				throw new MyException(AppConstant.A0016);
			}
			DispatchList dl = idlDao.get(dd.getSheetId());
			if (dl == null || dl.getFlag() == false) {
				throw new MyException(AppConstant.A0012);
			}
			if (dl.getESn().equals(eSn) == false) {
				throw new MyException(AppConstant.A008);
			}
			if (isu.checkPermissionsByESnAndDlId(eSn, dl.getDlId()) == false) {
				throw new MyException(AppConstant.A0010);
			}
			iddDao.save(dd);
			rs.setSuccess(true);
			rs.setMsg("增加报销单明细成功！");
		} catch (MyException e) {
			rs.setSuccess(false);
			rs.setMsg("增加报销单明细失败！");
			rs.setException(e.getMessage());
		}
		return rs;
	}

	@Override
	public Result submitDispatchList(String eSn, DispatchList dl, DispatchResult dr) {
		Result rs = new Result();
		try {
			if (MyMatcher.isEmpty(eSn)) {
				throw new MyException(AppConstant.A004);
			}
			if (dl == null || dl.getDlId() == null) {
				throw new MyException(AppConstant.A005);
			}
			if (dl.getFlag() == false) {
				throw new MyException(AppConstant.A009);
			}
			if (isu.checkPermissionsByESnAndDlId(eSn, dl.getDlId()) == false) {
				throw new MyException(AppConstant.A0010);
			}
			List<DispatchDetail> dds = isu.findDispatchDetailByDlId(dl.getDlId());
			if (dds == null || dds.size() == 0) {
				throw new MyException(AppConstant.A0017);
			}
			dr.setCheckSn(eSn);
			dr.setCheckTime(new Date());
			String hql = "from SysEmployee where departmentId = (select departmentId from SysEmployee where ESn = ?) and PId = ?";
			SysEmployee se = iseDao.findUnique(hql, eSn, AppConstant.DMANAGER);
			if (se == null) {
				throw new MyException(AppConstant.A0018);
			}
			dr.setCheckNext(se.getESn());
			dr.setSheetId(dl.getDlId());
			rs.setSuccess(true);
			idrDao.save(dr);
			rs.setMsg("提交报销单成功！");
		} catch (MyException e) {
			rs.setSuccess(false);
			rs.setMsg("提交报销单失败！");
			rs.setException(e.getMessage());
		}
		return rs;
	}

	@Override
	public Result login(LoginUser lu) {
		// TODO Auto-generated method stub
		return null;
	}

	public ISysEmployeeDao getIseDao() {
		return iseDao;
	}

	public void setIseDao(ISysEmployeeDao iseDao) {
		this.iseDao = iseDao;
	}

	public IDispatchListDao getIdlDao() {
		return idlDao;
	}

	public void setIdlDao(IDispatchListDao idlDao) {
		this.idlDao = idlDao;
	}

	public ISystemUtil getIsu() {
		return isu;
	}

	public void setIsu(ISystemUtil isu) {
		this.isu = isu;
	}

	public IDispatchDetailDao getIddDao() {
		return iddDao;
	}

	public void setIddDao(IDispatchDetailDao iddDao) {
		this.iddDao = iddDao;
	}

	public IDispatchResultDao getIdrDao() {
		return idrDao;
	}

	public void setIdrDao(IDispatchResultDao idrDao) {
		this.idrDao = idrDao;
	}

	public static void main(String[] args) throws MyException {
		String eSn = "10000000";
		ApplicationContext actc = new ClassPathXmlApplicationContext(new String[] { "hibernate-spring.xml",
				"beans1.xml" });
		IEmployeeService is = actc.getBean("EmployeeService", IEmployeeService.class);
		ISystemUtil isu = actc.getBean("SystemUtil", ISystemUtil.class);
		IDispatchDetailDao idd = actc.getBean("DispatchDetailDao", IDispatchDetailDao.class);
		/* 雇员查找所有当前状态的全部报销单 */
		// System.out.println("雇员查找所有当前状态的全部报销单");
		// System.out.println(is.findAllDispatchListByESn("10000000"));

		/* 用户保存报销单 */
		// System.out.println("用户保存报销单");
		// DispatchList dl = new DispatchList(null, eSn, new Date(),
		// "aasldhfkjashfkj", true);
		// Result rs = is.saveDispatchList(eSn, dl);
		// System.out.println(rs.getSuccess());
		// System.out.println(rs.getMsg());

		/* 用户修改报销单 */
		Result rs = is.updateDispatchList(eSn, 22L, "cccccccc");
		System.out.println(rs.getSuccess());
		System.out.println(rs.getMsg());
		System.out.println(rs.getException());

		/* 用户修改报销单明细 */
		// System.out.println("用户修改报销单明细");
		// DispatchDetail dr = new DispatchDetail(20L, 3L, 200D, "qqqqqqq",
		// false, 1L, null);
		// Result rs = is.updateDispatchDetail(eSn, dr);
		// System.out.println(rs.getSuccess());
		// System.out.println(rs.getMsg());
		// System.out.println(rs.getException());

		/* 用户删除报销单 */
		// System.out.println("用户删除报销单");
		// DispatchList dl = isu.findDispatchListByDlId(40L );
		// Result rs = is.deleteDispatchList(eSn, dl);
		// System.out.println(rs.getSuccess());
		// System.out.println(rs.getMsg());
		// System.out.println(rs.getException());

		/* 用户删除报销单明细 */
		// System.out.println("用户删除报销单明细");
		// List<DispatchDetail> dl = isu.findDispatchDetailByDlId(3L);
		// System.out.println(dl.size());
		// Result rs = is.deleteDispatchDetail(eSn, dl.get(1));
		// System.out.println(rs.getSuccess());
		// System.out.println(rs.getMsg());
		// System.out.println(rs.getException());

		/* 用户新增报销单明细 */
		// System.out.println("用户新增报销单明细");
		// DispatchDetail dd = new DispatchDetail(null, 41L, 41D, null, true,
		// 3L, null);
		// Result rs = is.addDispatchDetail(eSn, dd);
		// System.out.println(rs.getSuccess());
		// System.out.println(rs.getMsg());
		// System.out.println(rs.getException());
		//
		/* 用户提交报销单 */
		// Result rs = new Result();
		// eSn = "10000000";
		// System.out.println("用户提交报销单");
		// DispatchList dl = new DispatchList(null, eSn, new Date(),
		// "aasldhfkjashfkj", true);
		// dl = isu.findDispatchListByDlId(22L);
		// System.out.println("保存保存报销单");
		// rs = is.saveDispatchList(eSn, dl);
		// System.out.println(rs.getSuccess());
		// System.out.println(rs.getMsg());
		// System.out.println(rs.getException());
		// if (rs.getSuccess() == false)
		// return;
		// DispatchDetail dd = new DispatchDetail(null, dl.getDlId(), 43D, null,
		// true, 3L, null);
		// System.out.println("添加保存报销单明细");
		// rs = is.addDispatchDetail(eSn, dd);
		// System.out.println(rs.getSuccess());
		// System.out.println(rs.getMsg());
		// System.out.println(rs.getException());
		// if (rs.getSuccess() == false)
		// return;
		// DispatchResult dr = new DispatchResult();
		// dr.setCheckComment("aaaaaaaaaaaaaaaa");
		// System.out.println("提交报销单");
		// rs = is.submitDispatchList(eSn, dl, dr);
		// System.out.println(rs.getSuccess());
		// System.out.println(rs.getMsg());
		// System.out.println(rs.getException());
	}
}