package org.zjf.services.impl;

import java.util.Date;
import java.util.List;

import org.ymm.constant.MyConstant;
import org.ymm.dao.IAreaDao;
import org.ymm.dao.IDispatchDetailDao;
import org.ymm.dao.IDispatchListDao;
import org.ymm.dao.IDispatchResultDao;
import org.ymm.dao.IDispatchStatusDao;
import org.ymm.dao.ILoginUserDao;
import org.ymm.dao.ISysDepartmentDao;
import org.ymm.dao.ISysEmployeeDao;
import org.ymm.entity.DispatchDetail;
import org.ymm.entity.DispatchResult;
import org.ymm.entity.LoginUser;
import org.ymm.entity.SysEmployee;
import org.ymm.entity.SysPositions;
import org.ymm.exception.MyException;
import org.ymm.util.StringUtil;
import org.ymm.vo.BaseVo;
import org.ymm.vo.DispatchResultVo;
import org.ymm.vo.LoginUserVo;
import org.ymm.vo.Page;
import org.ymm.vo.Result;
import org.zjf.services.IManagerService;
import org.zjf.services.ISystemService;

public class ManagerServiceImpl implements IManagerService {

	private ISysEmployeeDao empdao;
	private IAreaDao areadao;
	private IDispatchListDao listdao;
	private IDispatchResultDao resultdao;
	private IDispatchDetailDao detaildao;
	private IDispatchStatusDao statusdao;
	private ISysDepartmentDao departdao;
	private ISystemService system;
	private ILoginUserDao logindao;
	

	public ILoginUserDao getLogindao() {
		return logindao;
	}

	public void setLogindao(ILoginUserDao logindao) {
		this.logindao = logindao;
	}

	public ISysEmployeeDao getEmpdao() {
		return empdao;
	}

	public void setEmpdao(ISysEmployeeDao empdao) {
		this.empdao = empdao;
	}

	public IAreaDao getAreadao() {
		return areadao;
	}

	public void setAreadao(IAreaDao areadao) {
		this.areadao = areadao;
	}

	public IDispatchListDao getListdao() {
		return listdao;
	}

	public void setListdao(IDispatchListDao listdao) {
		this.listdao = listdao;
	}

	public IDispatchResultDao getResultdao() {
		return resultdao;
	}

	public void setResultdao(IDispatchResultDao resultdao) {
		this.resultdao = resultdao;
	}

	public IDispatchDetailDao getDetaildao() {
		return detaildao;
	}

	public void setDetaildao(IDispatchDetailDao detaildao) {
		this.detaildao = detaildao;
	}

	public IDispatchStatusDao getStatusdao() {
		return statusdao;
	}

	public void setStatusdao(IDispatchStatusDao statusdao) {
		this.statusdao = statusdao;
	}

	public ISysDepartmentDao getDepartdao() {
		return departdao;
	}

	public void setDepartdao(ISysDepartmentDao departdao) {
		this.departdao = departdao;
	}

	public ISystemService getSystem() {
		return system;
	}

	public void setSystem(ISystemService system) {
		this.system = system;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result applyClaims(SysEmployee emp, DispatchResultVo cla)
			throws MyException {
		if (emp == null || emp.getPId() == null)
			return getResult("A002");
		if (cla == null || cla.getSheetId() == null)
			return getResult("A002");
		if (StringUtil.isEmpty(emp.getESn()) == false
				|| StringUtil.isEmpty(cla.getCheckNext()) == false)
			return getResult("A002");

		Result re = checkEmpPos(emp.getPId());
		if (re.getSuccess() == false)
			return re;
		List<DispatchDetail> list = system.findDetailById(cla.getSheetId(), 0,
				999).getResult();
		if (list == null || list.size() <= 0)
			return getResult("A010");
		double money = 0;
		for (DispatchDetail detail : list) {
			money += detail.getMoney();
		}

		try {
			DispatchResult result = resultdao.get(cla.getSheetId());
			if (money >= 5000) {
				String sql = "fom SysEmployee where PId=1";
				SysEmployee a = empdao.findUnique(sql);
				result.setCheckNext(a.getESn());
				result.setCheckStatus(1L);
			} else {
				result.setCheckNext(null);
				result.setCheckStatus(2L);
			}
			result.setCheckSn(emp.getESn());
			result.setCheckTime(new Date());
			result.setSheetId(cla.getSheetId());
		} catch (Exception e) {
			return getResult("A008");
		}
		Result result = new Result();
		return result;
	}

	@Override
	public Page findMyApplyClaims(SysEmployee emp, final BaseVo vo)
			throws MyException {
		if (emp == null)
			throw new MyException("A002");
		Result re = this.checkEmpPos(emp.getPId());
		if (re.getSuccess() == false)
			throw new MyException("A005");

		String sql = "select t1.e_sn,t1.d_name,dr.sheet_id,ds.DA_status from(select se.E_SN,sd.d_name from sys_department sd left join  sys_employee se on sd.d_id=se.department_id where sd.d_id=?) t1 right join dispatch_list dl on t1.E_SN=dl.e_sn"
				+ " right join  (select  r.sheet_id,r.CHECK_STATUS from ( "
				+ "     select sheet_id,CHECK_STATUS,row_number() over (partition by e.sheet_id order by e.DR_ID desc nulls last) rn  "
				+ "              from dispatch_result e "
				+ " ) r where rn =1) dr  on dr.sheet_id=dl.dl_id left join  dispatch_status ds on dr.check_status=ds.DA_ID; ";
		Page page = empdao.findPageBySQL(vo, sql, emp.getDepartmentId() + "");
		if (page == null)
			throw new MyException("A003");
		return page;
	}

	@Override
	public Result SetPwd(SysEmployee manager, LoginUserVo emp)
			throws MyException {
		if(manager==null||manager.getPId()==null)
			return this.getResult("A002");
		if(emp==null||emp.getESn()==null)
			return this.getResult("A002");
		Result result=this.checkEmpPos(manager.getPId());
		if(result.getSuccess()==false)
			return this.getResult("A005");
		LoginUser user=system.findUserBySn(emp.getESn());
		if(user==null)
			return this.getResult("A003");
		user.setUPwd(emp.getUPwd());
		Result res=new Result();
		return res;
	}

	@Override
	public Page findMyDepartClaims(SysEmployee emp, final BaseVo vo)
			throws MyException {
		if (emp == null||emp.getDepartmentId()==null||emp.getPId()==null)
			throw new MyException("A002");
		Result re = checkEmpPos(emp.getPId());
		if (re.getSuccess() == false)
			throw new MyException("A005");
		String sql = "select se.e_sn,se.E_name,dl_id,create_time,ds.da_status from  sys_employee se left join dispatch_list dl on se.e_sn=dl.e_sn right join "
				+ " dispatch_result dr on dl.dl_id=dr.sheet_id left join dispatch_status ds on dr.check_status=ds.da_id  where se.department_id=?";
		Page page=resultdao.findPageBySQL(vo, sql, emp.getDepartmentId()+"");
		if(page==null)
			throw new MyException("A003");
		return page;
	}

	@Override
	public SysPositions loginUser(String username, String pwd)
			throws MyException {
		// TODO Auto-generated method stub
		return null;
	}

	private Result checkEmpPos(long p_id) throws MyException {
		SysPositions pos = system.findPositionById(p_id);
		if (pos == null)
			return this.getResult("A003");
		if (!pos.getPNameCn().equals("部门经理"))
			return this.getResult("A005");
		Result result = new Result();
		return result;
	}

	private Result getResult(String str) {
		Result res = new Result();
		res.setException(str);
		res.setSuccess(false);
		res.setMsg(MyConstant.map.get(str));
		return res;
	}
}
