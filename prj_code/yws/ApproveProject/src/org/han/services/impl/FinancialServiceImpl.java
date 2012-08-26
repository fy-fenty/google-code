package org.han.services.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.han.constant.AppConstant;
import org.han.dao.IDispatchListDao;
import org.han.dao.IDispatchResultDao;
import org.han.dao.ISysEmployeeDao;
import org.han.entity.DispatchResult;
import org.han.exception.MyException;
import org.han.services.IFinancialService;
import org.han.util.StringUtil;
import org.han.vo.ApprovalVo;
import org.han.vo.BaseVo;
import org.han.vo.Page;
import org.han.vo.Result;

public class FinancialServiceImpl implements IFinancialService {

	private IDispatchResultDao disResultDao;
	private IDispatchListDao disListDao;
	private ISysEmployeeDao empDao;

	public ISysEmployeeDao getEmpDao() {
		return empDao;
	}

	public void setEmpDao(ISysEmployeeDao empDao) {
		this.empDao = empDao;
	}

	public IDispatchListDao getDisListDao() {
		return disListDao;
	}

	public void setDisListDao(IDispatchListDao disListDao) {
		this.disListDao = disListDao;
	}

	public IDispatchResultDao getDisResultDao() {
		return disResultDao;
	}

	public void setDisResultDao(IDispatchResultDao disResultDao) {
		this.disResultDao = disResultDao;
	}

	@Override
	public Result approval(ApprovalVo vo) throws Exception {
		// TODO Auto-generated method stub
		if (null == vo || StringUtil.isEmpty(vo.getEmpSN())
				|| !StringUtil.isNaN(vo.getDisId() + "")
				|| !StringUtil.isNaN(vo.getDisResul() + "")) {
			throw new MyException("A003");
		}
		String sql = "insert all when app_status=1 then into hzy.DISPATCH_RESULT values (SEQ_DISPATCH_RESULT.nextval,sheet_id,null,sysdate,:f_sn,:comm,5)"
				+ " when app_status=2 then into hzy.DISPATCH_RESULT values (SEQ_DISPATCH_RESULT.nextval,sheet_id,check_sn,sysdate,:f_sn,:comm,4)"
				+ " select dr.*,:app_status app_status from hzy.DISPATCH_RESULT dr where sheet_id=:dl_id and check_next=(select e_sn from hzy.SYS_EMPLOYEE where department_id=3 and e_sn=:f_sn)  and check_status=2 and"
				+ " check_time=(select max(check_time) from hzy.DISPATCH_RESULT where dr.sheet_id=sheet_id)";
		Map m = new HashMap();
		m.put("f_sn", vo.getEmpSN());
		m.put("app_status", vo.getDisResul());
		m.put("dl_id", vo.getDisId());
		m.put("comm", vo.getComment());
		int result = disResultDao.createSQLQuery(sql, m).executeUpdate();
		if (result != 1) {
			throw new MyException("A007");
		}
		return new Result(true, AppConstant.A000);
	}

	@Override
	public Result createStatistics(String empSN) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("生成报表");
		return null;
	}

	@Override
	public Page findApproval(String empSN, BaseVo vo) throws Exception {
		// TODO Auto-generated method stub
		String sql = "select t3.*,t4.money from (select sum(dd.money) money,dd.sheet_id from hzy.dispatch_detail dd group by dd.sheet_id) t4"
				+ " right join (select dl.dl_id,dl.e_sn,e.e_name,dl.create_time,t2.check_status from hzy.dispatch_list dl "
				+ " right join (select * from hzy.dispatch_result dr where 1=(select count(1) from hzy.SYS_EMPLOYEE where e_sn=:sn and DEPARTMENT_id=3) and dr.check_status in(2,5)  and :sn in (check_sn,check_next)  and dr.check_time=("
				+ " select max(check_time) from hzy.dispatch_result where sheet_id=dr.sheet_id)) t2 "
				+ " on dl.dl_id=t2.sheet_id left join hzy.SYS_EMPLOYEE e on dl.e_sn=e.e_sn  where dl.flag=1)t3 "
				+ "on t4.sheet_id=t3.dl_id";
		Map m = new HashMap();
		m.put("sn", empSN);
		return empDao.findPageBySQL(vo, sql, m);
	}

	@Override
	public Result pay(ApprovalVo vo) throws Exception {
		// TODO Auto-generated method stub
		if (null == vo || StringUtil.isEmpty(vo.getEmpSN())
				|| !StringUtil.isNaN(vo.getDisId() + "")) {
			throw new MyException("A003");
		}
		String sql = "select 1  from dual where  (select count(1) from hzy.DISPATCH_RESULT dr where sheet_id=? and check_status=5 and"
				+ " check_time=(select max(check_time) from hzy.DISPATCH_RESULT where dr.sheet_id=sheet_id))+"
				+ " (select count(1) from hzy.SYS_EMPLOYEE where department_id=3 and e_sn=?)=2";
		Map check = disResultDao.findUniqueBySQL(sql, vo.getDisId(), vo
				.getEmpSN());
		if (null == check || check.size() != 1) {
			throw new MyException("A007");
		}
		DispatchResult dResult = new DispatchResult();
		dResult.setSheetId(vo.getDisId());
		dResult.setCheckSn(vo.getEmpSN());
		dResult.setCheckStatus(6L);
		dResult.setCheckTime(new Date());
		dResult.setCheckComment(vo.getComment());
		disResultDao.save(dResult);
		return new Result(true, AppConstant.A000);
	}
}
