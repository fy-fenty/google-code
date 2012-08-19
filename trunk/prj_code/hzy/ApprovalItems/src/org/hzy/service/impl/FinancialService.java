package org.hzy.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hzy.constant.AppConstant;
import org.hzy.dao.IDispatchResultDao;
import org.hzy.entity.DispatchResult;
import org.hzy.exception.MyException;
import org.hzy.service.IFinancialService;
import org.hzy.support.ISystemUtil;
import org.hzy.util.MyMatcher;
import org.hzy.vo.HandleDispatchVo;
import org.hzy.vo.Result;

/**
 * @author fy
 * @date 2012-8-19
 * @class FinancialService
 * @extends Object
 * @description 财务功能实现
 */

@SuppressWarnings("unchecked")
public class FinancialService implements IFinancialService {

	private ISystemUtil isu;

	private IDispatchResultDao idrDao;

	@Override
	public Result payMoney(HandleDispatchVo hdVo) {
		Result result = new Result();
		try {
			if (hdVo.getDlId() == null || MyMatcher.isEmpty(hdVo.getEsn())) {
				throw new MyException(AppConstant.A0019);
			}
			DispatchResult dr = isu.findCurrentDispatchResultByDlId(hdVo.getDlId());
			if ((dr.getCheckStatus() == 5) == false) {
				throw new MyException(AppConstant.A0010);
			}
			result.setSuccess(true);
			result.setMsg("付款成功");
		} catch (MyException e) {
			result.setSuccess(false);
			result.setMsg("付款失败");
			result.setException(e.getMessage());
		}
		return result;
	}

	@Override
	public Result approval(HandleDispatchVo hdVo) {
		Result result = new Result();
		try {
			if (hdVo.getDlId() == null || MyMatcher.isEmpty(hdVo.getEsn())) {
				throw new MyException(AppConstant.A0019);
			}
			DispatchResult dr = isu.findCurrentDispatchResultByDlId(hdVo.getDlId());
			if ((dr.getCheckNext().equals(hdVo.getEsn()) && dr.getCheckStatus() == 2) == false) {
				throw new MyException(AppConstant.A0010);
			}
			String l = null;
			Long status = 2L;
			if (hdVo.getStatus().equals(4L)) {
				status = 4L;
				l = dr.getCheckSn();
			} else {
				status = 5L;
			}
			DispatchResult dr2 = new DispatchResult();
			dr2.setCheckComment(hdVo.getCheckComment());
			dr2.setCheckNext(null);
			dr2.setCheckSn(l);
			dr2.setCheckStatus(status);
			dr2.setCheckTime(new Date());
			dr2.setSheetId(hdVo.getDlId());
			idrDao.save(dr2);
			result.setSuccess(true);
			result.setMsg("审批成功");
		} catch (MyException e) {
			result.setSuccess(false);
			result.setMsg("审批失败");
			result.setException(e.getMessage());
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> findDispatchListForMe(String eSn) {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from (");
		sb.append("  select d.dl_id DLID,d.e_sn ESN,d.create_time CREATETIME, (");
		sb.append("    select sum(dd.money) from hzy.DISPATCH_DETAIL dd where dd.sheet_id = d.dl_id");
		sb.append("  ) TOTALMONEY,(");
		sb.append("    select d.check_status from hzy.dispatch_result d where d.dr_id = (");
		sb.append("      select max(dr_id) from hzy.dispatch_result where sheet_id = d.dl_id");
		sb.append("    ) and d.check_next in (?, null)");
		sb.append("  ) CURRENTSTATUS ");
		sb.append("  from hzy.dispatch_list d left join hzy.sys_employee s on d.e_sn = s.e_sn where d.flag = 1");
		sb.append(") tt where tt.CURRENTSTATUS in (2, 5)");
		SQLQuery sqlQuery = isu.createSQLQuery(sb.toString(), eSn);
		sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return sqlQuery.list();
	}

	@Override
	public Result generateReport(String eSn) {
		Result result = new Result();
		try {
			if (MyMatcher.isEmpty(eSn)) {
				throw new MyException(AppConstant.A0019);
			}
			if (isu.findSysEmployeeByESn(eSn).getDepartmentId() == 3L) {
				throw new MyException(AppConstant.A0010);
			}
			result.setSuccess(true);
			result.setMsg("生成报表成功");
		} catch (MyException e) {
			result.setSuccess(false);
			result.setMsg("生成报表失败");
			result.setException(e.getMessage());
		}
		return result;
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

}
