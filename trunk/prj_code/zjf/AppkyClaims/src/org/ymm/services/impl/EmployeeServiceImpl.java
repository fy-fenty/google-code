package org.ymm.services.impl;

import org.hibernate.SQLQuery;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.ymm.services.IEmpService;
import org.ymm.services.ISystemService;
import org.zjf.constant.AppConstant;
import org.zjf.dao.IDispatchDetailDao;
import org.zjf.dao.IDispatchListDao;
import org.zjf.dao.ISysEmployeeDao;
import org.zjf.entity.DispatchList;
import org.zjf.entity.DispatchResult;
import org.zjf.entity.SysEmployee;
import org.zjf.entity.SysPositions;
import org.zjf.exception.MyException;
import org.zjf.support.impl.BaseDao;
import org.zjf.util.StringUtil;
import org.zjf.vo.BaseVo;
import org.zjf.vo.DispatchDetailVo;
import org.zjf.vo.DispatchListVo;
import org.zjf.vo.Page;
import org.zjf.vo.Result;

public class EmployeeServiceImpl implements IEmpService {

	private BaseDao baseDao;
	private ISystemService iSystemService;
	private IDispatchListDao iDispatchListDao;
	private ISysEmployeeDao iSysEmployeeDao;
	private IDispatchDetailDao iDispatchDetailDao;

	public ISysEmployeeDao getiSysEmployeeDao() {
		return iSysEmployeeDao;
	}

	public void setiSysEmployeeDao(ISysEmployeeDao iSysEmployeeDao) {
		this.iSysEmployeeDao = iSysEmployeeDao;
	}

	public IDispatchDetailDao getiDispatchDetailDao() {
		return iDispatchDetailDao;
	}

	public void setiDispatchDetailDao(IDispatchDetailDao iDispatchDetailDao) {
		this.iDispatchDetailDao = iDispatchDetailDao;
	}

	public void setiDispatchListDao(IDispatchListDao iDispatchListDao) {
		this.iDispatchListDao = iDispatchListDao;
	}

	public ISystemService getiSystemService() {
		return iSystemService;
	}

	public void setiSystemService(ISystemService iSystemService) {
		this.iSystemService = iSystemService;
	}

	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public IDispatchListDao getiDispatchListDao() {
		return iDispatchListDao;
	}

	@Override
	public Page findAllClaims(final SysEmployee emp, final BaseVo vo) throws MyException {
		Page page = null;
		if (emp != null && StringUtil.isEmpty(emp.getESn()) == true) {
			String sql = "select t1.*,t2.money,t3.cs as status from "
					+ " (select dl.dl_id,dl.e_sn,se.e_name,dl.create_time,dl.flag from dispatch_list dl left join sys_employee se on dl.e_sn=se.e_sn) t1 "
					+ " left join "
					+ " (select dd.sheet_id, sum(money) as money from dispatch_detail dd group by dd.sheet_id) t2 "
					+ "  on t1.dl_id=t2.sheet_id "
					+ " left join "
					+ " (select d1.sheet_id,(select ds1.da_status from dispatch_status ds1 where ds1.da_id=d1.check_status) as cs from dispatch_result d1 where check_time =(select max(check_time) from dispatch_result d where d.sheet_id=d1.sheet_id)) t3 "
					+ "  on t3.sheet_id=t2.sheet_id where t1.e_sn=?";
			page = baseDao.findPageBySql(vo, sql, emp.getESn());
		}
		return page;// cs
	}

	@Override
	public Result saveClaims(final SysEmployee sysEmployee,
			final DispatchListVo dispatchList) throws MyException {
		Result result = new Result(true, AppConstant.DEFAULT_MSG, "A001");
		if (dispatchList == null || sysEmployee == null) {
			return new Result(false, AppConstant.SAVE_ERROR, "A003");
		}
		if (sysEmployee.getPId() == null) {
			return new Result(false, AppConstant.SAVE_ERROR, "A003");
		}

		SysPositions sysPositions = iSystemService.findPositionById(sysEmployee
				.getPId());
		if (!"雇员".equals(sysPositions.getPNameCn())) {
			return new Result(false, AppConstant.SAVE_ERROR, "A012");
		}
		
		
		
		//iDispatchListDao.save(dispatchList);// cs
		return result;
	}

	@Override
	public Result updateClaims(final SysEmployee sysEmployee,
			DispatchListVo dispatchList) throws MyException {
		Result result = new Result(true, AppConstant.DEFAULT_MSG, "A001");
		if (dispatchList == null || sysEmployee == null) {
			return new Result(false, AppConstant.UPDATE_ERROR, "A003");
		}
		if (StringUtil.isEmpty(dispatchList.getDlId().toString()) == false
				|| StringUtil.isEmpty(sysEmployee.getESn()) == false) {
			return new Result(false, AppConstant.UPDATE_ERROR, "A003");
		}
		DispatchList dl = iSystemService.findById(dispatchList.getDlId());
		if (dl == null || dl.getFlag() == false) {
			return new Result(false, AppConstant.UPDATE_ERROR, "A003");
		}
		if (!sysEmployee.getESn().equals(dl.getESn())) {
			return new Result(false, AppConstant.UPDATE_ERROR, "A012");
		}

		DispatchResult dr1 = iSystemService.findResultById(dispatchList.getDlId());
		if (dr1 != null) {
			if (dr1.getCheckStatus() == 4) {
				return new Result(false, AppConstant.UPDATE_ERROR, "A013");
			}
			return new Result(false, AppConstant.UPDATE_ERROR, "A003");
		}
		
		//iDispatchListDao.saveNew(dispatchList);
		return result;
	}

	@Override
	public SysEmployee findBySn(String SN) throws MyException {
		String sql = " from SysEmployee where E_SN=?";
		return iSysEmployeeDao.findUniqueByHQL(sql, SN);
	}

	@Override
	public Result deleteClaims(final SysEmployee emp, final DispatchListVo cid)
			throws MyException {
		Result result = new Result(true, AppConstant.DEFAULT_MSG, "A001");
		if (emp == null || cid == null){
			return new Result(false,AppConstant.DELETE_ERROR, "A003");
		}
		if (StringUtil.isEmpty(emp.getEId().toString())
				|| StringUtil.isEmpty(cid.getDlId() + "")
				|| StringUtil.isEmpty(emp.getESn())) {
			return new Result(false, AppConstant.DELETE_ERROR, "A003");
		}
		// 查询报销单
		DispatchList dispatchList = iSystemService.findById(cid.getDlId());
		if (dispatchList == null) {
			return new Result(false, AppConstant.DELETE_ERROR, "A003");
		}
		if (dispatchList.getESn() != emp.getESn()) {
			return new Result(false, AppConstant.DELETE_ERROR, "A012");
		}
		if (dispatchList.getFlag() == false) {
			return new Result(false, AppConstant.DELETE_ERROR, "A013");
		}
		String sql = "update dispatch_detail set flag=1 where sheet_id=?";
		SQLQuery sq = iDispatchDetailDao.createSQLQuery(sql,dispatchList.getDlId());
		sq.executeUpdate();
		
		String sql1 = "update dispatch_list set flag=0 where dl_id=?";
		SQLQuery sq1 = iDispatchListDao.createSQLQuery(sql,dispatchList.getDlId());
		sq1.executeUpdate();
		return result;
	}
	

	

	@Override
	public Result updateDetail(SysEmployee emp, DispatchDetailVo detail)
			throws MyException {
		Result result = new Result(false, AppConstant.DEFAULT_MSG, "A001");
		if (emp == null || detail == null) {
			return new Result(false, AppConstant.UPDATE_ERROR, "A003");
		}
		if (StringUtil.isEmpty(detail.getDsId().toString()) == false) {
			return new Result(false, AppConstant.UPDATE_ERROR, "A003");
		}

		return result;
	}

	@Override
	public Result deleteDetail(SysEmployee emp, DispatchDetailVo detail)
			throws MyException {
		Result result = new Result(true, AppConstant.DEFAULT_MSG, "A001");
		if (emp == null || detail == null) {
			return new Result(false, AppConstant.DELETE_ERROR, "A003");
		}
		if (StringUtil.isEmpty(detail.getSheetId().toString()) == false) {
			return new Result(false, AppConstant.DELETE_ERROR, "A003");
		}
		// 查询报销单
		DispatchList dl = iSystemService.findById(detail.getSheetId());

		if (dl == null) {
			return new Result(false, AppConstant.DELETE_ERROR, "A003");
		}
		if (dl.getFlag() == false) {
			return new Result(false, AppConstant.DELETE_ERROR, "A003");
		}
		// 查询报销单状态
		DispatchResult dr1 = iSystemService.findResultById(dl.getDlId());
		if (dr1 != null) {
			if (dr1.getCheckStatus() == 4) {
				return new Result(false, AppConstant.UPDATE_ERROR, "A013");
			}
			return new Result(false, AppConstant.UPDATE_ERROR, "A003");
		}

		String sql = "update dispatch_detail set flag=0 where ds_id=?";
		SQLQuery sq = iDispatchDetailDao.createSQLQuery(sql,detail.getDsId());
		sq.executeUpdate();
		// System.out.println(sq.executeUpdate());
		return result;// cs
	}

	@Override
	public Result saveDetail(SysEmployee emp, DispatchDetailVo detail)
			throws MyException {
		Result result = new Result(false, AppConstant.DEFAULT_MSG, "A001");
		if (emp == null || detail == null) {
			return new Result(false, AppConstant.SAVE_ERROR, "A003");
		}
		if (StringUtil.isEmpty(detail.getSheetId().toString())) {
			return new Result(false, AppConstant.SAVE_ERROR, "A003");
		}
		// 查询报销单
		DispatchList dl = iSystemService.findById(detail.getSheetId());
		if (dl == null) {
			return new Result(false, AppConstant.SAVE_ERROR, "A003");
		}
		if (dl.getFlag() == false) {
			return new Result(false, AppConstant.SAVE_ERROR, "A013");
		}
		if (emp.getESn().equals(dl.getESn())) {
			return new Result(false, AppConstant.SAVE_ERROR, "A013");
		}
		// 查询报销单状态
		DispatchResult dr1 = iSystemService.findResultById(dl.getDlId());
		if (dr1 != null || dr1.getCheckStatus() == 4) {
			return new Result(false, AppConstant.SAVE_ERROR, "A013");
		}

		
		//iDispatchDetailDao.save(detail);
		
		return result;
	}

	public static void main(String[] args) throws MyException {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { "spring-sessinfactory.xml",
						"spring-dao-beans.xml", "spring-trans.xml" });
		IEmpService ies = ac.getBean("employeeServiceImpl", IEmpService.class);
		SysEmployee emp = new SysEmployee();
		emp.setEId((long)20);
		emp.setESn("xxxx1001");
		
		DispatchList dl = new DispatchList();
		
		//ies.deleteClaims(emp, );
	
		/*
		 * SysEmployee emp =new SysEmployee(); emp.setPId((long)3);
		 * emp.setESn("xxxx1002");
		 * 
		 * DispatchList dispatchList=new DispatchList();
		 * dispatchList.setDlId((long)10);
		 * dispatchList.setEventExplain("给钱做事1"); dispatchList.setFlag(true);
		 * dispatchList.setESn("xxxx1001");
		 * System.out.println(ies.updateClaims(emp,dispatchList).getMsg());
		 * System.out.println(AppConstant.INEQUALITY_ERROR);
		 */
	}
	
	@Override
	public Result commitClaims(SysEmployee emp, DispatchListVo cla)
			throws Exception {
		Result result = new Result(false, AppConstant.DEFAULT_MSG, "A001");
		if (emp == null || cla == null) {
			return new Result(false, AppConstant.SAVE_ERROR, "A003");
		}
		if (StringUtil.isEmpty(cla.getDlId().toString()) == false) {
			return new Result(false, AppConstant.SAVE_ERROR, "A003");
		}
		// 查询报销单
		DispatchList dl = iSystemService.findById(cla.getDlId());
		if (dl == null) {
			return new Result(false, AppConstant.SAVE_ERROR, "A003");
		}
		if (dl.getFlag() == false) {
			return new Result(false, AppConstant.SAVE_ERROR, "A013");
		}
		if (emp.getESn().equals(dl.getESn())) {
			return new Result(false, AppConstant.SAVE_ERROR, "A012");
		}
		// 查询报销单状态
		DispatchResult dr1 = iSystemService.findResultById(dl.getDlId());
		if (dr1 != null || dr1.getCheckStatus() == 4) {
			return new Result(false, AppConstant.SAVE_ERROR, "A013");
		}
		Page page = iSystemService.findDetailById(dl.getDlId(), 0, 5);
		if (page.getResult().size() == 0) {
			return new Result(false, AppConstant.SAVE_ERROR, "A013");
		}
		
			//iDispatchListDao.save(cla);

		return result;
	}

	@Override
	public SysEmployee loginUser(String username, String pwd)
			throws MyException {

		return null;
	}

}
