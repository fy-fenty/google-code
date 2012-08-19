package org.han.services.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.han.services.IGManagerService;
import org.han.services.ISystemServices;
import org.tender.constant.AppConstant;
import org.tender.dao.IDispatchListDao;
import org.tender.dao.IDispatchResultDao;
import org.tender.dao.ISysDepartmentDao;
import org.tender.entity.DispatchResult;
import org.tender.exception.MyException;
import org.tender.util.StringUtil;
import org.tender.vo.ApprovalVo;
import org.tender.vo.BaseVo;
import org.tender.vo.Page;
import org.tender.vo.Result;

public class GManagerServiceImpl implements IGManagerService {

	private IDispatchListDao disListDao;
	private IDispatchResultDao disResultDao;
	private ISysDepartmentDao deptDao;
	private ISystemServices sysBiz;

	public ISysDepartmentDao getDeptDao() {
		return deptDao;
	}

	public void setDeptDao(ISysDepartmentDao deptDao) {
		this.deptDao = deptDao;
	}

	public ISystemServices getSysBiz() {
		return sysBiz;
	}

	public void setSysBiz(ISystemServices sysBiz) {
		this.sysBiz = sysBiz;
	}

	public IDispatchResultDao getDisResultDao() {
		return disResultDao;
	}

	public void setDisResultDao(IDispatchResultDao disResultDao) {
		this.disResultDao = disResultDao;
	}

	public IDispatchListDao getDisListDao() {
		return disListDao;
	}

	public void setDisListDao(IDispatchListDao disListDao) {
		this.disListDao = disListDao;
	}

	@Override
	public Result approval(ApprovalVo vo) throws Exception {
		// TODO Auto-generated method stub
		if (null == vo || StringUtil.isEmpty(vo.getEmpSN())
				|| !StringUtil.isNaN(vo.getDisId() + "")
				|| !StringUtil.isNaN(vo.getDisResul() + "")) {
			throw new MyException("A003");
		}
		String sql = "insert all "
				+ " when 1=:app_status then into hzy.DISPATCH_RESULT values (SEQ_DISPATCH_RESULT.nextval,sheet_id,f_sn,sysdate,:g_sn,:comm,2)	"
				+ " when 2=:app_status then into hzy.DISPATCH_RESULT values (SEQ_DISPATCH_RESULT.nextval,sheet_id,d_sn,sysdate,:g_sn,:comm,4)"
				+ " when 3=:app_status then into hzy.DISPATCH_RESULT values (SEQ_DISPATCH_RESULT.nextval,sheet_id,null,sysdate,:g_sn,:comm,3)"
				+ " select dr.*,(select  manage_sn from hzy.SYS_DEPARTMENT where d_id=3)  f_sn,"
				+ " (select manage_sn from hzy.SYS_DEPARTMENT where d_id=(select department_id from hzy.SYS_EMPLOYEE where e_sn=(select e_sn from hzy.DISPATCH_LIST where dl_id=dr.sheet_id)))  d_sn"
				+ " from hzy.DISPATCH_RESULT dr   where sheet_id=:dl_id and check_next=(select e_sn from hzy.SYS_EMPLOYEE where p_id=1 and e_sn=:g_sn)  and check_status=1 and"
				+ " check_time=(select max(check_time) from hzy.DISPATCH_RESULT where dr.sheet_id=sheet_id)";
		Map m = new HashMap();
		m.put("g_sn", vo.getEmpSN());
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
	public Page findMeApproval(String empSN, BaseVo vo) throws Exception {
		// TODO Auto-generated method stub
		if (StringUtil.isEmpty(empSN) || null == vo) {
			throw new MyException("A003");
		}
		String sql = "select t3.*,t4.money from (select sum(dd.money) money,dd.sheet_id from hzy.dispatch_detail dd group by dd.sheet_id) t4"
				+ " right join (select dl.dl_id,dl.e_sn,e.e_name,dl.create_time,t2.check_status from hzy.dispatch_list dl "
				+ " right join (select * from hzy.dispatch_result dr where dr.check_next=(select e_sn from hzy.SYS_EMPLOYEE where e_sn=? and p_id=1) and dr.check_status=1 and dr.check_time=("
				+ " select max(check_time) from hzy.dispatch_result where sheet_id=dr.sheet_id)) t2 "
				+ " on dl.dl_id=t2.sheet_id left join hzy.SYS_EMPLOYEE e on dl.e_sn=e.e_sn  where dl.flag=1)t3 "
				+ " on t4.sheet_id=t3.dl_id";
		return disListDao.findPageBySQL(vo, sql, empSN);
	}

	@Override
	public Result finishDispatch(ApprovalVo vo) throws Exception {
		// TODO Auto-generated method stub
		if (null == vo || StringUtil.isEmpty(vo.getEmpSN())
				|| !StringUtil.isNaN(vo.getDisId() + "")) {
			throw new MyException("A003");
		}
		String sqlCheck = "select  count(1) re  from (select * from hzy.DISPATCH_RESULT dr where check_status in(1,2) and check_time="
				+ " (select max(check_time) from hzy.DISPATCH_RESULT where dr.sheet_id=sheet_id and sheet_id=?) ) d"
				+ "  left join hzy.SYS_EMPLOYEE e on d.check_sn=e.e_sn and e.p_id=2";
		Map result = disListDao.findUniqueBySQL(sqlCheck, vo.getDisId());
		if (result.size() <= 0) {
			throw new MyException("A007");
		}
		DispatchResult dr = new DispatchResult();
		dr.setSheetId(vo.getDisId());
		dr.setCheckSn(vo.getEmpSN());
		dr.setCheckStatus(3L);
		dr.setCheckTime(new Date());
		dr.setCheckComment(vo.getComment());
		disResultDao.save(dr);
		return new Result(true, AppConstant.A000);
	}
}
