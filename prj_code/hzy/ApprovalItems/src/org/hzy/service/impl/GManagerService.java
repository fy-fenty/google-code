package org.hzy.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hzy.constant.AppConstant;
import org.hzy.dao.IDispatchResultDao;
import org.hzy.entity.DispatchResult;
import org.hzy.entity.SysPositions;
import org.hzy.exception.MyException;
import org.hzy.service.IGManagerService;
import org.hzy.support.ISystemUtil;
import org.hzy.util.MyMatcher;
import org.hzy.vo.HandleDispatchVo;
import org.hzy.vo.Result;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author fy
 * @date 2012-8-18
 * @class GManagerService
 * @extends Object
 * @description 总经理功能实现
 */

@SuppressWarnings("unchecked")
public class GManagerService implements IGManagerService {

	private ISystemUtil isu;
	private IDispatchResultDao idrDao;

	@Override
	public Result approval(HandleDispatchVo arVo) {
		Result result = new Result();
		try {
			if (arVo.getDlId() == null || MyMatcher.isEmpty(arVo.getEsn())) {
				throw new MyException(AppConstant.A0019);
			}
			DispatchResult dr = isu.findCurrentDispatchResultByDlId(arVo.getDlId());
			if ((dr.getCheckNext().equals(arVo.getEsn()) && dr.getCheckStatus() == 2) == false) {
				throw new MyException(AppConstant.A0010);
			}
			String l = null;
			Long status = 2L;
			if (arVo.getStatus().equals(3L)) {
				status = 3L;
				isu.findDispatchListByDlId(arVo.getDlId()).setFlag(false);
			} else if (arVo.getStatus().equals(4L)) {
				status = 4L;
				l = dr.getCheckSn();
			} else {
				StringBuilder sb = new StringBuilder();
				sb.append("select dp.manage_sn from hzy.sys_department dp where dp.d_id = 3 and dp.area_id = (");
				sb.append("  select dp.area_id from hzy.sys_department dp where dp.d_id = (");
				sb.append("    select se.department_id from hzy.sys_employee se where se.e_sn = ?");
				sb.append("  )");
				sb.append(")");
				Map<String, Object> mp = isu.findUniqueBySQL(sb.toString(), arVo.getEsn());
				if (mp.get("MANAGE_SN") == null) {
					throw new MyException(AppConstant.A0010);
				}
				l = (String) mp.get("MANAGE_SN");
			}
			DispatchResult dr2 = new DispatchResult(null, arVo.getDlId(), l, new Date(), arVo.getEsn(), arVo.getCheckComment(), status);
			idrDao.save(dr2);
			result.setSuccess(true);
			result.setMsg("审批成功");
		} catch (MyException e) {
			result.setSuccess(true);
			result.setMsg("审批失败");
			result.setException(e.getMessage());
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> findWaitingApprovalForMe(String gsn) {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from (");
		sb.append("  select d.dl_id DLID,d.e_sn ESN,d.create_time CREATETIME, (");
		sb.append("    select sum(dd.money) from hzy.DISPATCH_DETAIL dd where dd.sheet_id = d.dl_id");
		sb.append("  ) TOTALMONEY,(");
		sb.append("    select d.check_status from hzy.dispatch_result d where d.dr_id = (");
		sb.append("      select max(dr_id) from hzy.dispatch_result where sheet_id = d.dl_id");
		sb.append("    ) and d.check_next = ?");
		sb.append("  ) CURRENTSTATUS ");
		sb.append("  from hzy.dispatch_list d left join hzy.sys_employee s on d.e_sn = s.e_sn where d.flag = 1");
		sb.append(") tt where tt.CURRENTSTATUS = 2");
		SQLQuery sqlQuery = isu.createSQLQuery(sb.toString(), gsn);
		sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return sqlQuery.list();
	}

	@Override
	public Result repealDispatchListForAlreadyApproval(HandleDispatchVo hdVo) {
		Result result = new Result();
		result.setSuccess(false);
		try {
			SysPositions ps = isu.findSysPositionsByESn(hdVo.getEsn());
			if (ps.getPId().intValue() != AppConstant.GMANAGER.intValue()) {
				throw new MyException(AppConstant.A0010);
			}
			DispatchResult dr = isu.findCurrentDispatchResultByDlId(hdVo.getDlId());
			if (dr == null) {
				throw new MyException(AppConstant.A0010);
			}
			if (isu.findSysPositionsByESn(dr.getCheckSn()).getPId().intValue() == AppConstant.DMANAGER.intValue()
					&& dr.getCheckStatus().intValue() == 2) {
				DispatchResult dr2 = new DispatchResult();
				dr2.setCheckComment(hdVo.getCheckComment());
				dr2.setCheckNext(null);
				dr2.setCheckSn(hdVo.getEsn());
				dr2.setCheckStatus(3L);
				dr2.setCheckTime(new Date());
				dr2.setSheetId(hdVo.getDlId());
				idrDao.save(dr2);
				result.setSuccess(true);
				result.setMsg("终止报销单成功");
			} else {
				throw new MyException(AppConstant.A0010);
			}
		} catch (MyException e) {
			result.setMsg("终止报销单失败");
			result.setException(e.getMessage());
		}
		return result;
	}

	@Override
	public void Login() throws MyException {
		// TODO Auto-generated method stub

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
		ApplicationContext actc = new ClassPathXmlApplicationContext(new String[] { "hibernate-spring.xml", "beans1.xml" });
		IGManagerService is = actc.getBean("GManagerService", IGManagerService.class);
		Result r = is.approval(new HandleDispatchVo("10000003", 2L, ""));
		System.out.println(r.getSuccess());
		System.out.println(r.getMsg());
		System.out.println(r.getException());
	}
}
