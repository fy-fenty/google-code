package org.hzy.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hzy.constant.AppConstant;
import org.hzy.dao.IDispatchResultDao;
import org.hzy.entity.DispatchDetail;
import org.hzy.entity.DispatchResult;
import org.hzy.exception.MyException;
import org.hzy.service.IDManagerService;
import org.hzy.support.ISystemUtil;
import org.hzy.util.MyMatcher;
import org.hzy.vo.HandleDispatchVo;
import org.hzy.vo.ResetPasswordVo;
import org.hzy.vo.Result;

@SuppressWarnings("unchecked")
public class DManagerService implements IDManagerService {

	private ISystemUtil isu;

	private IDispatchResultDao idrDao;

	@Override
	public Result approval(HandleDispatchVo arVo) {
		Result result = new Result();
		try {
			if (arVo.getDlId() == null || MyMatcher.isEmpty(arVo.getEsn())) {
				throw new MyException("A0019");
			}
			DispatchResult dr = isu.findCurrentDispatchResultByDlId(arVo.getDlId());
			List<DispatchDetail> dds = isu.findDispatchDetailByDlId(arVo.getDlId());
			Double mons = 0D;
			for (DispatchDetail dispatchDetail : dds) {
				mons += dispatchDetail.getMoney();
			}
			if (dr.getCheckNext().equals(arVo.getEsn()) && dr.getCheckStatus() == 2) {
				String l = null;
				Long status = 2L;
				if (arVo.getApprovalStatus().equals(3L)) {
					status = 3L;
					isu.findDispatchListByDlId(arVo.getDlId()).setFlag(false);
				} else if (arVo.getApprovalStatus().equals(4L)) {
					status = 4L;
					l = dr.getCheckSn();
				} else {
					StringBuilder sb = new StringBuilder();
					if (mons > 5000D) {
						sb.append("select se.e_sn from hzy.sys_employee se where se.department_id in (");
						sb.append("  select dp.manage_sn from hzy.sys_department dp where dp.d_id = 3 and dp.area_id = (");
						sb.append("    select dp.area_id from hzy.sys_department dp where dp.d_id = (");
						sb.append("      select se.department_id from hzy.sys_employee se where se.e_sn = ?");
						sb.append("    )");
						sb.append("  )");
						sb.append(") and se.p_id = 1");
						Map<String, Object> mp = isu.findUniqueBySQL(sb.toString(), arVo.getEsn());
						if (mp.get("MANAGE_SN") == null) {
							throw new MyException("A0010");
						}
						l = (String) mp.get("MANAGE_SN");
					} else {
						sb.append("select dp.manage_sn from hzy.sys_department dp where dp.d_id = 3 and dp.area_id = (");
						sb.append("  select dp.area_id from hzy.sys_department dp where dp.d_id = (");
						sb.append("    select se.department_id from hzy.sys_employee se where se.e_sn = ?");
						sb.append("  )");
						sb.append(")");
						Map<String, Object> mp = isu.findUniqueBySQL(sb.toString(), arVo.getEsn());
						if (mp.get("MANAGE_SN") == null) {
							throw new MyException("A0010");
						}
						l = (String) mp.get("MANAGE_SN");
					}
				}
				DispatchResult dr2 = new DispatchResult(null, arVo.getDlId(), l, new Date(), arVo.getEsn(), arVo.getComment(), status);
				idrDao.save(dr2);
				result.setSuccess(true);
				result.setMsg("审批成功");
			}
		} catch (MyException e) {
			result.setSuccess(true);
			result.setMsg("审批失败");
			result.setException(e.getMessage());
		}
		return result;
	}

	@Override
	public Result resetPassword(ResetPasswordVo rpVo) {
		Result rs = new Result();
		try {
			if (MyMatcher.isEmpty(rpVo.getDsn())) {
				throw new MyException("A0019");
			}
			String npwd = "pwd" + Math.round(Math.random() * 1000000) + "xx";
			String md5pwd = isu.getMD5(npwd);
			StringBuilder sql = new StringBuilder();
			sql.append("update hzy.login_user lu set lu.u_pwd = ? where (");
			sql.append("  select 1 from hzy.sys_employee se where se.e_sn = ? and  se.department_id = (");
			sql.append("    select se.department_id from hzy.sys_employee se where se.e_sn = ? and se.p_id = ?");
			sql.append("  )");
			sql.append(")=1");
			SQLQuery sqlQuery = isu.createSQLQuery(sql.toString(), md5pwd, rpVo.getEsn(), rpVo.getDsn(), AppConstant.DMANAGER);
			if (sqlQuery.executeUpdate() == 0) {
				rs.setSuccess(false);
				rs.setMsg("密码重置失败");
			} else {
				rs.setSuccess(true);
				rs.setMsg(md5pwd);
			}
		} catch (MyException e) {
			rs.setSuccess(false);
			rs.setException(e.getMessage());
			rs.setException("密码重置失败");
		}
		return rs;
	}

	@Override
	public List<Map<String, Object>> findWaitingApprovalForMe(String dsn) {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from (");
		sb.append("  select d.dl_id DLID,d.e_sn ESN,d.create_time CREATETIME, (");
		sb.append("    select sum(dd.money) from hzy.DISPATCH_DETAIL dd where dd.sheet_id = d.dl_id");
		sb.append("  ) TOTALMONEY,(");
		sb.append("    select d.check_status from hzy.dispatch_result d where d.dr_id = (");
		sb.append("      select max(dr_id) from hzy.dispatch_result where sheet_id = d.dl_id");
		sb.append("    ) and d.check_next = ?");
		sb.append("  ) CURRENTSTATUS");
		sb.append("  from hzy.dispatch_list d left join hzy.sys_employee s on d.e_sn = s.e_sn where d.flag = 1");
		sb.append(") tt where tt.CURRENTSTATUS = 1");
		SQLQuery sqlQuery = isu.createSQLQuery(sb.toString(), dsn);
		sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (List<Map<String, Object>>) sqlQuery.list();
	}

	@Override
	public List<Map<String, Object>> findDispatchListByDeparment(String dsn) {
		String sql = "select * from (select d.dl_id DLID,d.e_sn ESN,d.create_time CREATETIME,(select sum(dd.money) from hzy.DISPATCH_DETAIL dd where dd.sheet_id = d.dl_id) TOTALMONEY,(select d.check_status from hzy.dispatch_result d where d.dr_id = (select max(dr_id) from hzy.dispatch_result where sheet_id = d.dl_id)) CURRENTSTATUS from hzy.dispatch_list d left join hzy.sys_employee s on d.e_sn = s.e_sn where s.e_sn in (select se.e_sn from hzy.sys_employee se where se.p_id = ? and se.department_id = (select se.department_id from hzy.sys_employee se where se.e_sn = ?)) and d.flag = 1) tt where tt.CURRENTSTATUS > 0";
		SQLQuery sqlQuery = isu.createSQLQuery(sql, AppConstant.EMPLOYEE, dsn);
		sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (List<Map<String, Object>>) sqlQuery.list();
	}

	public ISystemUtil getIsu() {
		return isu;
	}

	public void setIsu(ISystemUtil isu) {
		this.isu = isu;
	}

	public IDispatchResultDao getIdrDao() {
		return idrDao;
	}

	public void setIdrDao(IDispatchResultDao idrDao) {
		this.idrDao = idrDao;
	}

	public static void main(String[] args) {
		// ApplicationContext actc = new ClassPathXmlApplicationContext(new
		// String[] { "hibernate-spring.xml",
		// "beans1.xml" });
		// IDManagerService is = actc.getBean("DManagerService",
		// IDManagerService.class);

		/* 部门经理重置报销单 */
		// System.out.println("部门经理重置报销单");
		// Result rs = is.resetPassword(new ResetPasswordVo("10000000",
		// "10000003"));
		// System.out.println(rs.getSuccess());
		// System.out.println(rs.getMsg());
		// System.out.println(rs.getException());

		/* 查询部门报销单 */
		// System.out.println("查询部门报销单");
		// List list = is.findDispatchListByDeparment("10000003");
		// for (int i = 0; i < list.size(); i++) {
		// System.out.println(list.get(i));
		// }

		/* 查询部门待我审核报销单 */
		// System.out.println("查询部门待我审核报销单");
		// List list = is.findWaitingApprovalForMe("10000003");
		// for (int i = 0; i < list.size(); i++) {
		// System.out.println(list.get(i));
		// }
	}
}
