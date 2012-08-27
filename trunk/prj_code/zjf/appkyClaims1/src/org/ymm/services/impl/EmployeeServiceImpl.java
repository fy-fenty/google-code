package org.ymm.services.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.hibernate.SQLQuery;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.ymm.services.IEmpService;
import org.ymm.services.ISystemService;
import org.zjf.constant.AppConstant;
import org.zjf.constant.MyConstant;
import org.zjf.dao.IDispatchDetailDao;
import org.zjf.dao.IDispatchListDao;
import org.zjf.dao.IDispatchResultDao;
import org.zjf.dao.ILoginUserDao;
import org.zjf.dao.ISysEmployeeDao;
import org.zjf.entity.DispatchDetail;
import org.zjf.entity.DispatchList;
import org.zjf.entity.DispatchResult;
import org.zjf.entity.LoginUser;
import org.zjf.entity.SysEmployee;
import org.zjf.entity.SysPositions;
import org.zjf.exception.MyException;
import org.zjf.support.impl.BaseDao;
import org.zjf.util.DateJsonValueProcessor;
import org.zjf.util.MD5;
import org.zjf.util.SessionListener;
import org.zjf.util.StringUtil;
import org.zjf.vo.BaseVo;
import org.zjf.vo.CurrentUser;
import org.zjf.vo.DispatchDetailVo;
import org.zjf.vo.DispatchListVo;
import org.zjf.vo.LoginVo;
import org.zjf.vo.Page;
import org.zjf.vo.Result;

public class EmployeeServiceImpl implements IEmpService {

	private BaseDao baseDao;
	private ISystemService iSystemService;
	private IDispatchListDao iDispatchListDao;
	private ISysEmployeeDao iSysEmployeeDao;
	private IDispatchDetailDao iDispatchDetailDao;
	private IDispatchResultDao iDispatchResultDao;
	private ILoginUserDao iLoginUserDao;

	public ILoginUserDao getiLoginUserDao() {
		return iLoginUserDao;
	}

	public void setiLoginUserDao(ILoginUserDao iLoginUserDao) {
		this.iLoginUserDao = iLoginUserDao;
	}

	public IDispatchResultDao getiDispatchResultDao() {
		return iDispatchResultDao;
	}

	public void setiDispatchResultDao(IDispatchResultDao iDispatchResultDao) {
		this.iDispatchResultDao = iDispatchResultDao;
	}

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

	public Page findAllClaims(final SysEmployee emp, final BaseVo vo)
			throws MyException {
		Page page = null;
		if (emp != null && StringUtil.isEmpty(emp.getESn()) == true) {
			String sql = "select t1.*,(case when t2.money is null then 0 else t2.money  end ) as money  ,(case when t3.cs is null then '未提交' else t3.cs end ) as status from "
					+ " (select dl.dl_id,dl.e_sn,se.e_name,dl.create_time,dl.flag,dl.event_explain from dispatch_list dl left join sys_employee se on dl.e_sn=se.e_sn) t1 "
					+ " left join "
					+ " (select dd.sheet_id, sum(money) as money from dispatch_detail dd group by dd.sheet_id) t2 "
					+ "  on t1.dl_id=t2.sheet_id "
					+ " left join "
					+ " (select d1.sheet_id,(select ds1.da_status from dispatch_status ds1 where ds1.da_id=d1.check_status) as cs from dispatch_result d1 where check_time =(select max(check_time) from dispatch_result d where d.sheet_id=d1.sheet_id)) t3 "
					+ "  on t3.sheet_id=t2.sheet_id where t1.e_sn=? and t1.flag=1";
			page = baseDao.findPageBySql(vo, sql, emp.getESn());
		}
		return page;// cs ok
	}
	
	public static void main(String[] args) throws Exception {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { "spring-sessinfactory.xml",
						"spring-dao-beans.xml" });
		IEmpService ies = ac.getBean("employeeServiceImpl", IEmpService.class);
		SysEmployee se=new SysEmployee();
		se.setESn("xxxx1001");
		BaseVo vo=new BaseVo();

		Page page= ies.findAllClaims(se,vo);
		JSONObject ja =new JSONObject(); 
		JsonConfig jf = new JsonConfig();
		//jf.registerJsonValueProcessor(java.sql.Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		jf.registerJsonValueProcessor(java.sql.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		jf.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		System.out.println(ja.fromObject(page,jf).toString());	

	}

	
	
	public Result saveClaims(final SysEmployee sysEmployee,
			final DispatchListVo dispatchListVo) throws MyException {
		Result result = new Result(true, AppConstant.DEFAULT_MSG, "A001");
		if (dispatchListVo == null || sysEmployee == null) {
			return new Result(false, AppConstant.SAVE_ERROR, "A003");
		}
		// System.out.println(String.valueOf(sysEmployee.getPId()+""));
		if (StringUtil.isEmpty(sysEmployee.getPId() + "") == false
				|| StringUtil.isEmpty(sysEmployee.getESn()) == false) {
			return new Result(false, AppConstant.SAVE_ERROR, "A003");
		}

		SysPositions sysPositions = iSystemService.findPositionById(sysEmployee
				.getPId());
		if (!"雇员".equals(sysPositions.getPNameCn())) {
			return new Result(false, AppConstant.SAVE_ERROR, "A012");
		}

		DispatchList dispatchList = getDispatchListValue(dispatchListVo,
				sysEmployee);

		iDispatchListDao.save(dispatchList);// cs ok
		return result;
	}

	public Result updateClaims(final SysEmployee sysEmployee,
			DispatchListVo dispatchListVo) throws MyException {
		Result result = new Result(true, AppConstant.DEFAULT_MSG, "A001");
		if (dispatchListVo == null || sysEmployee == null) {
			return new Result(false, AppConstant.UPDATE_ERROR, "A003");
		}
		if (StringUtil.isEmpty(dispatchListVo.getDlId() + "") == false
				|| StringUtil.isEmpty(sysEmployee.getESn()) == false) {
			return new Result(false, AppConstant.UPDATE_ERROR, "A003");
		}

		DispatchList dl = iSystemService.findById(dispatchListVo.getDlId());

		if (dl == null) {
			return new Result(false, AppConstant.UPDATE_ERROR, "A003");
		}
		if (dl.getFlag() == false) {
			return new Result(false, AppConstant.FLAG_ERROR, "A006");
		}
		if (!sysEmployee.getESn().equals(dl.getESn())) {
			return new Result(false, AppConstant.UPDATE_ERROR, "A012");
		}

		if (this.getDispatchResultEnd(dispatchListVo.getDlId()).getSuccess() == false) {
			return this.getDispatchResultEnd(dispatchListVo.getDlId());
		}

		DispatchList dispatchList = getDispatchListValue(dispatchListVo,
				sysEmployee);
		// 设置需要修改的列
		dl.setEventExplain(dispatchList.getEventExplain());
		iDispatchListDao.save(dl); // cs ok
		return result;
	}

	public SysEmployee findBySn(String SN) throws MyException {
		String sql = " from SysEmployee where E_SN=?";
		return iSysEmployeeDao.findUniqueByHQL(sql, SN);
	}

	public Result deleteClaims(final SysEmployee emp, final DispatchListVo cid)
			throws MyException {
		Result result = new Result(true, AppConstant.DEFAULT_MSG, "A001");
		if (emp == null || cid == null) {
			return new Result(false, AppConstant.DELETE_ERROR, "A003");
		}
		if (StringUtil.isEmpty(emp.getEId() + "") == false
				|| StringUtil.isEmpty(cid.getDlId() + "") == false
				|| StringUtil.isEmpty(emp.getESn()) == false) {
			return new Result(false, AppConstant.DELETE_ERROR, "A003");
		}
		// 查询报销单
		DispatchList dispatchList = iSystemService.findById(cid.getDlId());
		if (dispatchList == null) {
			return new Result(false, AppConstant.DELETE_ERROR, "A003");
		}
		if (!dispatchList.getESn().equals(emp.getESn())) {
			return new Result(false, AppConstant.DELETE_ERROR, "A012");
		}
		if (dispatchList.getFlag() == false) {
			return new Result(false, AppConstant.DELETE_ERROR, "A013");
		}
		
		if (this.getDispatchResultEnd(dispatchList.getDlId()).getSuccess() == false) {
			return this.getDispatchResultEnd(dispatchList.getDlId());
		}
		
		String sql = "update dispatch_detail set flag=0 where sheet_id=?";
		SQLQuery sq = iDispatchDetailDao.createSQLQuery(sql,
				dispatchList.getDlId());
		sq.executeUpdate();

		String sql1 = "update dispatch_list set flag=0 where dl_id=?";
		SQLQuery sq1 = iDispatchListDao.createSQLQuery(sql1,
				dispatchList.getDlId());
		sq1.executeUpdate(); // cs ok
		return result;
	}

	public Result updateDetail(SysEmployee emp,
			DispatchDetailVo dispatchDetailVo) throws MyException {
		Result result = new Result(false, AppConstant.DEFAULT_MSG, "A001");
		if (emp == null || dispatchDetailVo == null) {
			return new Result(false, AppConstant.UPDATE_ERROR, "A003");
		}
		if (StringUtil.isEmpty(dispatchDetailVo.getSheetId() + "") == false
				|| StringUtil.isEmpty(dispatchDetailVo.getDsId() + "") == false) {
			return new Result(false, AppConstant.UPDATE_ERROR, "A003");
		}
		DispatchList dispatchList = iSystemService.findById(dispatchDetailVo
				.getSheetId());
		if (dispatchList == null) {
			return new Result(false, AppConstant.UPDATE_ERROR, "A003");
		}

		if (!dispatchList.getESn().equals(emp.getESn())) {
			return new Result(false, AppConstant.UPDATE_ERROR, "A012");
		}
		if (dispatchList.getFlag() == false) {
			return new Result(false, AppConstant.UPDATE_ERROR, "A013");
		}
		if (this.getDispatchResultEnd(dispatchList.getDlId()).getSuccess() == false) {
			return this.getDispatchResultEnd(dispatchList.getDlId());
		}
		// 查询报销单明细
		DispatchDetail dispatchDetail = getDispatchDetailBydsId(dispatchDetailVo
				.getDsId());
		if (dispatchDetail.getFlag() == false) {
			return new Result(false, AppConstant.UPDATE_ERROR, "A013");
		}
		// 设置需要修改的值
		dispatchDetail.setMoney(dispatchDetailVo.getMoney());
		dispatchDetail.setCostExplain(dispatchDetailVo.getCostExplain());
		dispatchDetail.setItemId(dispatchDetailVo.getItemId());
		dispatchDetail.setAccessory(dispatchDetailVo.getAccessory());
		iDispatchDetailDao.save(dispatchDetail);
		return result;// cs ok
	}

	// 通过明细id查询指定报销单明细
	public DispatchDetail getDispatchDetailBydsId(long dsid) {
		String sql = " from DispatchDetail where dsId=? ";
		DispatchDetail dispatchDetail = iDispatchDetailDao.findUniqueByHQL(sql,
				dsid);
		return dispatchDetail;
	}

	public Result deleteDetail(final SysEmployee emp,
			final DispatchDetailVo dispatchDetailVo) throws MyException {
		Result result = new Result(true, AppConstant.DEFAULT_MSG, "A001");
		if (emp == null || dispatchDetailVo == null) {
			return new Result(false, AppConstant.DELETE_ERROR, "A003");
		}
		if (StringUtil.isEmpty(dispatchDetailVo.getSheetId() + "") == false) {
			return new Result(false, AppConstant.DELETE_ERROR, "A003");
		}
		// 查询报销单
		DispatchList dl = iSystemService
				.findById(dispatchDetailVo.getSheetId());

		if (dl == null) {
			return new Result(false, AppConstant.DELETE_ERROR, "A003");
		}
		if (dl.getFlag() == false) {
			return new Result(false, AppConstant.DELETE_ERROR, "A003");
		}
		// 查询报销单状态
		if (this.getDispatchResultEnd(dl.getDlId()).getSuccess() == false) {
			return this.getDispatchResultEnd(dl.getDlId());
		}

		String sql = "update dispatch_detail set flag=0 where ds_id=?";
		SQLQuery sq = iDispatchDetailDao.createSQLQuery(sql,
				dispatchDetailVo.getDsId());
		sq.executeUpdate();

		return result; // cs ok
	}

	public Result saveDetail(final SysEmployee sysEmployee,
			final DispatchDetailVo dispatchDetailVo) throws MyException {
		Result result = new Result(false, AppConstant.DEFAULT_MSG, "A001");
		if (sysEmployee == null || dispatchDetailVo == null) {
			return new Result(false, AppConstant.SAVE_ERROR, "A003");
		}

		if (StringUtil.isEmpty(dispatchDetailVo.getSheetId().toString()) == false) {
			return new Result(false, AppConstant.SAVE_ERROR, "A003");
		}

		// 查询报销单
		DispatchList dl = iSystemService
				.findById(dispatchDetailVo.getSheetId());
		if (dl == null) {
			return new Result(false, AppConstant.SAVE_ERROR, "A003");
		}

		if (dl.getFlag() == false) {
			return new Result(false, AppConstant.SAVE_ERROR, "A013");
		}
		if (!sysEmployee.getESn().equals(dl.getESn())) {
			return new Result(false, AppConstant.SAVE_ERROR, "A013");
		}

		// 查询报销单状态
		if (this.getDispatchResultEnd(dl.getDlId()).getSuccess() == false) {
			return this.getDispatchResultEnd(dl.getDlId());
		}

		DispatchDetail dd = this.getDispatchDetailValue(sysEmployee,
				dispatchDetailVo);
		dd.setDsId(null);// 主键自增长
		iDispatchDetailDao.save(dd);// cs ok

		return result;
	}

	public Result commitClaims(SysEmployee sysEmployee,
			DispatchListVo dispatchListVo) throws Exception {
		Result result = new Result(false, AppConstant.DEFAULT_MSG, "A001");
		if (sysEmployee == null || dispatchListVo == null) {
			return new Result(false, AppConstant.SAVE_ERROR, "A003");
		}

		if (StringUtil.isEmpty(dispatchListVo.getDlId() + "") == false
				|| StringUtil.isEmpty(sysEmployee.getDepartmentId() + "") == false) {
			return new Result(false, AppConstant.SAVE_ERROR, "A003");
		}
		// 查询报销单
		DispatchList dl = iSystemService.findById(dispatchListVo.getDlId());
		if (dl == null) {
			return new Result(false, AppConstant.SAVE_ERROR, "A003");
		}
		if (dl.getFlag() == false) {
			return new Result(false, AppConstant.SAVE_ERROR, "A013");
		}
		if (!sysEmployee.getESn().equals(dl.getESn())) {
			return new Result(false, AppConstant.SAVE_ERROR, "A012");
		}
		// 查询报销单状态
		if (this.getDispatchResultEnd(dl.getDlId()).getSuccess() == false) {
			return this.getDispatchResultEnd(dl.getDlId());
		}
		BaseVo vo=new BaseVo();
		Page page = iSystemService.findDetailById(dl.getDlId(),vo);
		if (page == null) {
			return new Result(false, AppConstant.SAVE_ERROR, "A003");
		}
		if (page.getResult().size() == 0) {
			return new Result(false, AppConstant.SAVE_ERROR, "A013");
		}

		String sql = " from SysEmployee where department_id=? and p_id=2";
		SysEmployee jingli = iSysEmployeeDao.findUniqueByHQL(sql,
				sysEmployee.getDepartmentId());
		if (jingli == null) {
			return new Result(false, AppConstant.SAVE_ERROR, "A003");
		}

		DispatchResult dr = new DispatchResult();
		dr.setSheetId(dispatchListVo.getDlId());
		dr.setCheckNext(jingli.getESn());
		dr.setCheckTime(new Date());
		dr.setCheckSn(sysEmployee.getESn());
		dr.setCheckComment(dispatchListVo.getEventExplain());
		dr.setCheckStatus((long) 1);

		iDispatchResultDao.save(dr);// cs ok

		return result;
	}

	

	public Result loginUser(LoginVo lv) throws MyException {

		Result result = new Result(true, AppConstant.DEFAULT_MSG, "A001");
		if (lv == null) {
			 return new Result(false, AppConstant.LOGIN_ERROR, "A003");
		}

		if (StringUtil.isEmpty(lv.getIvode()) == false
				|| StringUtil.isEmpty(lv.getVcode()) == false) {
			return new Result(false, AppConstant.LOGIN_ERROR, "A003");
		}

		if (!lv.getIvode().equals(lv.getVcode())) {
			return new Result(false, AppConstant.LOGIN_ERROR, "A012");
		}

		String sql = " from LoginUser where E_SN=?";
		LoginUser login = iLoginUserDao.findUniqueByHQL(sql, lv.getUname());
		//LoginUser login= iSystemService.findUserBySn(lv.getUname());
		SysEmployee sysEmployee = this.findBySn(lv.getUname());
		
		
		if (login == null) {
			return new Result(false, AppConstant.LOGIN_ERROR, "A003");
		}
		if (!login.getUPwd().equals(MD5.MD5Encode(lv.getPwd()))) {
			return new Result(false, AppConstant.LOGIN_ERROR, "A012");
		}

		ServletActionContext.getRequest().setAttribute(
				MyConstant.CURRENT_UNAME, lv.getUname());
		HttpSession se = ServletActionContext.getRequest().getSession();

		se.setAttribute(MyConstant.CURRENT_USER, null);
		SessionListener.removeSession(se.getId());

		Map<String, HttpSession> map = SessionListener.getSessionMaps();

		for (String s : map.keySet()) {
			HttpSession se1 = map.get(s);
			CurrentUser cu = (CurrentUser) se1
					.getAttribute(MyConstant.CURRENT_USER);

			if (s.equals(se.getId())) {
				continue;
			}

			if (login.getUId().equals(cu.getId())) {
				se1.setAttribute(MyConstant.CURRENT_USER, null);
				SessionListener.removeSession(se1.getId());
				break;
			}
		}

		CurrentUser cu = new CurrentUser();
		cu.setId((long) login.getUId());
		cu.setPid((long)sysEmployee.getPId());
		cu.setUname(login.getESn());
		// 设置若干属性

		se.setAttribute(MyConstant.CURRENT_USER, cu);
		SessionListener.addSession(se.getId(), se);
	
		
		return result;
	}

	// 查询当前审批状态是否是新建
	public Result getDispatchResultEnd(long dl_id) throws MyException {
		DispatchResult dr1 = iSystemService.findResultById(dl_id);
		if (dr1 != null) {
			return new Result(false, AppConstant.UPDATE_ERROR, "A003");
		}
		return new Result(true, AppConstant.DEFAULT_MSG, "A001");
	}

	public DispatchList getDispatchListValue(DispatchListVo dispatchListVo,
			SysEmployee SysEmployee) {
		DispatchList dispatchList = new DispatchList();
		dispatchList.setDlId(dispatchListVo.getDlId());
		dispatchList.setESn(SysEmployee.getESn());
		dispatchList.setCreateTime(new Timestamp(new Date().getTime()));
		dispatchList.setEventExplain(dispatchListVo.getEventExplain());
		dispatchList.setFlag(dispatchListVo.getFlag());
		return dispatchList;
	}

	public DispatchDetail getDispatchDetailValue(SysEmployee sysEmployee,
			DispatchDetailVo dispatchDetailVo) {
		DispatchDetail dd = new DispatchDetail();
		dd.setDsId(dispatchDetailVo.getDsId());
		dd.setCostExplain(dispatchDetailVo.getCostExplain());
		dd.setFlag(dispatchDetailVo.getFlag());
		dd.setMoney(dispatchDetailVo.getMoney());
		dd.setSheetId(dispatchDetailVo.getSheetId());
		dd.setItemId(dispatchDetailVo.getItemId());
		dd.setAccessory(dispatchDetailVo.getAccessory());
		return dd;
	}

}
