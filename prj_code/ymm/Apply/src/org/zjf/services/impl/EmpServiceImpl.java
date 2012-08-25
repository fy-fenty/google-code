package org.zjf.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.ymm.constant.AppConstant;
import org.ymm.constant.MyConstant;
import org.ymm.dao.IDispatchDetailDao;
import org.ymm.dao.IDispatchListDao;
import org.ymm.dao.IDispatchResultDao;
import org.ymm.dao.IDispatchStatusDao;
import org.ymm.dao.ISysDepartmentDao;
import org.ymm.dao.ISysEmployeeDao;
import org.ymm.entity.DispatchDetail;
import org.ymm.entity.DispatchList;
import org.ymm.entity.DispatchResult;
import org.ymm.entity.DispatchStatus;
import org.ymm.entity.LoginUser;
import org.ymm.entity.SysConfig;
import org.ymm.entity.SysDepartment;
import org.ymm.entity.SysEmployee;
import org.ymm.entity.SysMenu;
import org.ymm.entity.SysPositions;
import org.ymm.exception.MyException;
import org.ymm.util.MD5;
import org.ymm.util.SessionListener;
import org.ymm.util.StringUtil;
import org.ymm.vo.BaseVo;
import org.ymm.vo.CurrentUser;
import org.ymm.vo.DispatchDetailVo;
import org.ymm.vo.DispatchListVo;
import org.ymm.vo.LoginUserVo;
import org.ymm.vo.Page;
import org.ymm.vo.Result;
import org.zjf.services.IEmpService;
import org.zjf.services.ISysConfigService;
import org.zjf.services.ISysMenuService;
import org.zjf.services.ISystemService;

/**
 * @project:ApplyDispatch1
 * @author:zjf
 * @date:2012-8-14 下午8:30:09
 * @class:EmpServiceImpl
 * @extends:IEmpService
 * @description:雇员业务接口实现
 */
public class EmpServiceImpl implements IEmpService {

	private ISysEmployeeDao empdao;
	private IDispatchListDao listdao;
	private IDispatchResultDao resultdao;
	private ISystemService system;
	private IDispatchDetailDao detaildao;
	private IDispatchStatusDao statusdao;
	private ISysDepartmentDao departdao;
	private ISysConfigService configservice;
	private ISysMenuService menuservice;

	public ISysConfigService getConfigservice() {
		return configservice;
	}

	public void setConfigservice(ISysConfigService configservice) {
		this.configservice = configservice;
	}

	public ISysMenuService getMenuservice() {
		return menuservice;
	}

	public void setMenuservice(ISysMenuService menuservice) {
		this.menuservice = menuservice;
	}

	public void setConfigservice(SysConfigServiceImpl configservice) {
		this.configservice = configservice;
	}

	public ISysDepartmentDao getDepartdao() {
		return departdao;
	}

	public void setDepartdao(ISysDepartmentDao departdao) {
		this.departdao = departdao;
	}

	public IDispatchStatusDao getStatusdao() {
		return statusdao;
	}

	public void setStatusdao(IDispatchStatusDao statusdao) {
		this.statusdao = statusdao;
	}

	public IDispatchDetailDao getDetaildao() {
		return detaildao;
	}

	public void setDetaildao(IDispatchDetailDao detaildao) {
		this.detaildao = detaildao;
	}

	public ISystemService getSystem() {
		return system;
	}

	public void setSystem(ISystemService system) {
		this.system = system;
	}

	public ISysEmployeeDao getEmpdao() {
		return empdao;
	}

	public void setEmpdao(ISysEmployeeDao empdao) {
		this.empdao = empdao;
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

	public Page findAllClaims(final SysEmployee emp, final BaseVo vo)
			throws MyException {
		if (StringUtil.isEmpty(emp.getESn()) == false)
			throw new MyException("A002");
		String sql = "select x.E_SN as E_SN,x.E_name as Ename,y.DL_ID as sheet_id,y.create_time as time,NVL(z.money,0) as money,NVL(j.DA_STATUS,'新建')as status"
				+ " from sys_employee x left join"
				+ " DisPATCH_LIST y on x.E_sn=y.E_SN left join (select sheet_id,sum(money)as money from"
				+ " dispatch_detail group by sheet_id) z"
				+ " on y.DL_ID=z.sheet_id left join"
				+ " (select kk.* from(select dr.sheet_id,dr.DR_ID,s.DA_STATUS from (select r.sheet_id,r.DR_ID,r.CHECK_STATUS from ( "
				+ "    select sheet_id,DR_ID,CHECK_STATUS,row_number() over (partition by e.sheet_id order by e.DR_ID desc nulls last) rn"
				+ "             from dispatch_result e    "
				+ " ) r where rn =1) dr left join dispatch_status s on dr.CHECK_STATUS=s.DA_ID) kk) j"
				+ " on z.sheet_id=j.sheet_id where x.E_SN=? and y.flag=1";
		Page page = empdao.findPageBySQL(vo, sql, emp.getESn());
		if (page == null)
			throw new MyException("A003");
		return page;
	}

	public SysEmployee findBySn(String SN) throws MyException {
		if (StringUtil.isEmpty(SN) == false)
			throw new MyException("A002");
		String sql = "from SysEmployee where e_sn=?";
		SysEmployee emp = (SysEmployee) empdao.findUnique(sql, SN);
		if (emp == null)
			throw new MyException("A003");
		return emp;
	}

	public Result deleteClaims(final SysEmployee emp, final DispatchListVo cla)
			throws MyException {
		if (emp == null || StringUtil.isEmpty(emp.getESn()) == false)
			return getResult("A002");
		DispatchList dis = listdao.get(cla.getDlId());
		if (dis == null)
			return getResult("A003");
		if (dis.getFlag() == false)
			return getResult("A006");
		if (!(dis.getESn() + "").equals(emp.getESn()))
			return getResult("A005");

		DispatchResult obj = system.findResultById(cla.getDlId());
		Result r = checkStatus(obj);
		if (r.getSuccess() == false)
			return r;
		try {
			String sql = "update dispatch_detail set FLAG=0 where sheet_ID=?";
			detaildao.createSQLQuery(sql, dis.getDlId() + "").executeUpdate();
			dis.setFlag(false);
		} catch (Exception e) {
			e.printStackTrace();
			return getResult("A007");
		}
		Result result = new Result();
		return result;
	}

	public Result saveClaims(final SysEmployee emp, DispatchListVo cla)
			throws MyException {
		if (emp == null || StringUtil.isEmpty(emp.getESn()) == false)
			return getResult("A002");
		if (cla == null)
			return getResult("A002");

		SysEmployee emp1 = empdao.findUnique("from SysEmployee where ESn=?",
				emp.getESn());
		if (emp1 == null)
			return getResult("A003");
		if (!emp.getESn().equals(emp1.getESn()))
			return getResult("A005");
		Result re = checkEmpPos(emp.getPId());
		if (re.getSuccess() == false)
			return re;
		DispatchList list = new DispatchList();
		list.setEventExplain(cla.getEventExplain());
		list.setCreateTime(new Date());
		list.setESn(emp.getESn());
		list.setFlag(true);
		try {
			listdao.saveNew(list);
		} catch (Exception e) {
			return getResult("A008");
		}
		Result result = new Result();
		return result;
	}

	public Result updateClaims(final SysEmployee emp, DispatchListVo cla)
			throws MyException {
		if (emp == null || StringUtil.isEmpty(emp.getESn()) == false)
			return getResult("A002");
		if (cla == null || cla.getDlId() == null)
			return getResult("A002");
		DispatchList list = system.findById(cla.getDlId());
		if (list == null)
			return getResult("A010");
		if (list.getFlag() == false)
			return getResult("A006");
		if (!(list.getESn() + "").equals(emp.getESn()))
			return getResult("A005");
		DispatchResult result = system.findResultById(cla.getDlId());
		Result r = checkStatus(result);
		if (r.getSuccess() == false)
			return r;
		try {
			DispatchList dl = listdao.get(cla.getDlId());
			dl.setEventExplain(cla.getEventExplain());
		} catch (Exception e) {
			e.printStackTrace();
			return getResult("A009");
		}
		Result res = new Result();
		return res;
	}

	public Result updateDetail(final SysEmployee emp,
			final DispatchDetailVo detail) throws MyException {
		if (emp == null || StringUtil.isEmpty(emp.getESn()) == false)
			return getResult("A002");
		if (detail == null || detail.getDsId() == null
				|| detail.getSheetId() == null)
			return getResult("A002");
		DispatchList dis = system.findById(detail.getSheetId());
		if (dis == null)
			return getResult("A003");
		if (dis.getFlag() == false)
			throw new MyException("A006");
		if (!(dis.getESn() + "").equals(emp.getESn()))
			throw new MyException("A005");
		DispatchResult result = system.findResultById(detail.getSheetId());
		Result r = checkStatus(result);
		if (r.getSuccess() == false)
			return r;
		try {
			DispatchDetail de = detaildao.get(detail.getDsId());
			de.setAccessory(detail.getAccessory());
			de.setCostExplain(detail.getCostExplain());
			de.setItemId(detail.getItemId());
			de.setMoney(detail.getMoney());
		} catch (Exception e) {
			return getResult("A008");
		}
		Result res = new Result();
		return res;
	}

	public Result deleteDetail(final SysEmployee emp,
			final DispatchDetailVo detail) throws MyException {
		if (emp == null || StringUtil.isEmpty(emp.getESn()) == false)
			return getResult("A002");
		if (detail == null || detail.getDsId() == null
				|| detail.getSheetId() == null)
			return getResult("A002");

		DispatchDetail dd = detaildao.get(detail.getDsId());
		if (dd == null)
			return getResult("A003");
		DispatchList dis = system.findById(detail.getSheetId());
		if (dis == null)
			return this.getResult("A003");
		if (!(dis.getESn() + "").equals(emp.getESn()))
			return getResult("A005");
		DispatchResult result = system.findResultById(detail.getSheetId());
		Result r = checkStatus(result);
		if (r.getSuccess() == false)
			return r;
		try {
			DispatchDetail deta = detaildao.get(detail.getDsId());
			deta.setFlag(false);
		} catch (Exception e) {
			return getResult("A007");
		}
		Result res = new Result();
		return res;
	}

	public Result saveDetail(final SysEmployee emp, DispatchDetailVo detail)
			throws MyException {

		if (emp == null || emp.getPId() == null
				|| StringUtil.isEmpty(emp.getESn()) == false)
			return getResult("A002");
		if (detail == null || detail.getSheetId() == null)
			return getResult("A002");

		Result re = checkEmpPos(emp.getPId());
		if (re.getSuccess() == false)
			return re;
		DispatchList dis = system.findById(detail.getSheetId());
		if (dis == null)
			return getResult("A003");
		if (!(dis.getESn() + "").equals(emp.getESn()))
			return getResult("A006");
		DispatchResult result = system.findResultById(detail.getSheetId());
		Result r = checkStatus(result);
		if (r.getSuccess() == false)
			return r;
		DispatchDetail deta = new DispatchDetail();
		deta.setAccessory(detail.getAccessory());
		deta.setCostExplain(detail.getCostExplain());
		deta.setFlag(true);
		deta.setItemId(detail.getItemId());
		deta.setMoney(detail.getMoney());
		deta.setSheetId(detail.getSheetId());
		try {
			detaildao.saveNew(deta);
		} catch (Exception e) {
			return getResult("A009");
		}

		Result res = new Result();
		return res;
	}

	@SuppressWarnings({ "unchecked" })
	public Result commitClaims(final SysEmployee emp, final DispatchListVo cla)
			throws MyException {

		if (emp == null || emp.getDepartmentId() == null
				|| emp.getPId() == null)
			return getResult("A002");
		if (cla == null || cla.getDlId() == null || cla.getFlag() == null
				|| cla.getFlag() == false)
			return getResult("A002");
		if (StringUtil.isEmpty(emp.getESn()) == false
				|| StringUtil.isEmpty(cla.getESn()) == false)
			return getResult("A002");

		Result re = checkEmpPos(emp.getPId());

		if (re.getSuccess() == false)
			return re;
		DispatchList dis = listdao.get(cla.getDlId());
		if (!(dis.getESn() + "").equals(emp.getESn()))
			return this.getResult("A005");
		DispatchResult r = system.findResultById(cla.getDlId());
		if (r != null) {
			if (!(r.getCheckNext() + "").equals(emp.getESn()))
				return getResult("A006");

		}
		List<DispatchDetail> list = system.findDetailById(cla.getDlId(), 0, 2)
				.getResult();
		if (list == null || list.size() <= 0)
			return getResult("A010");

		SysDepartment depa = departdao.findUnique(
				"from SysDepartment where DId=?", emp.getDepartmentId() + "");
		if (depa == null || StringUtil.isEmpty(depa.getManageSn()) == false)
			return getResult("A006");

		DispatchResult res = new DispatchResult();
		res.setCheckComment(cla.getEventExplain());
		res.setCheckNext(depa.getManageSn());
		res.setCheckSn(emp.getESn());
		res.setCheckTime(new Date());
		res.setSheetId(cla.getDlId());
		res.setCheckStatus(1L);
		try {
			resultdao.save(res);
		} catch (Exception e) {
			e.printStackTrace();
			return this.getResult("A008");
		}

		Result result = new Result();
		return result;
	}

	public Result loginUser(LoginUserVo lv) {
		if (StringUtil.isEmpty(lv.getESn()) == false
				|| StringUtil.isEmpty(lv.getUPwd()) == false)
			return this.getResult("A002");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession se = ServletActionContext.getRequest().getSession();
		// 把用户名放进request
		request.setAttribute(AppConstant.CURRENT_UNAME, lv.getESn());

		// 清除Session
		se.setAttribute(AppConstant.CURRENT_USER, null);
		SessionListener.removeSession(se.getId());
		// 效验验证码
		if (!(se.getAttribute("rand") + "").equals(lv.getCore()))
			return this.getResult("A015");
		// 效验Ip地址
		// if (AppUtils.isIpAddress(request.getLocalAddr()) == false)
		// return this.getResult("A013");
		// 效验Ip地址
		// if (AppUtils.getMacAddress(request.getLocalAddr()) == null)
		// return this.getResult("A013");
		// 根据用户名取得用户对象
		LoginUser user = null;
		try {
			user = system.findUserBySn(lv.getESn());
		} catch (Exception e) {
			return this.getResult(e.getMessage());
		}
		if (user == null)
			return this.getResult("A012");
		// 比较密码
		// if ((user.getUPwd() + "").equals(MD5.ecodeByMD5(lv.getUPwd())))
		if (!MD5.checkPWD(user.getUPwd(), lv.getUPwd()))
			return this.getResult("A014");
		SysConfig config = configservice.findSysConfig(user.getUId());
		if (config != null) {
			if (config.getBegintime().before(new Date()))
				return this.getResult("A016");
			if (config.getEndtime().after(new Date()))
				return this.getResult("A016");
		}
		SysEmployee emp;
		try {
			System.out.println(lv.getESn());
			emp = this.findBySn(lv.getESn());
		} catch (Exception e) {
			e.printStackTrace();
			return this.getResult(e.getMessage());
		}
		if(emp==null)
			return this.getResult("A003");
		List<SysMenu> list = menuservice.findMenuByPID(emp.getPId());
		se.setAttribute("menu", list);
		Map<String, HttpSession> map = SessionListener.getSessionMaps();

		for (String s : map.keySet()) {
			HttpSession se1 = map.get(s);
			CurrentUser cu = (CurrentUser) se1
					.getAttribute(AppConstant.CURRENT_USER);
			if (s.equals(se.getId())) {
				continue;
			}

			if (lv.getESn().equals(cu.getUser().getESn())) {
				se1.setAttribute(AppConstant.CURRENT_USER, null);
				SessionListener.removeSession(se1.getId());
				break;
			}
		}
		CurrentUser curre = new CurrentUser();
		curre.setEmp(emp);
		curre.setUser(user);
		// 设置若干属性
		se.setAttribute(AppConstant.CURRENT_USER, curre);
		SessionListener.addSession(se.getId(), se);
		Result re = new Result();
		re.setSuccess(true);
		if (emp.getPId() == 3)
			re.setMsg("emp");
		else
			re.setMsg("manager");
		return re;
	}

	private Result checkStatus(DispatchResult result) throws MyException {
		String sql = "from DispatchStatus where daStatus='已打回'";
		DispatchStatus status = statusdao.findUnique(sql);
		if (result != null) {
			if (result.getCheckStatus() != status.getDaId())
				return this.getResult("A005");
		}
		Result res = new Result();
		return res;
	}

	private Result checkEmpPos(long p_id) throws MyException {
		SysPositions pos = system.findPositionById(p_id);
		if (pos == null)
			return this.getResult("A003");
		if (!pos.getPNameCn().equals("雇员"))
			return this.getResult("A005");
		Result res = new Result();
		return res;
	}

	private Result getResult(String str) {
		Result res = new Result();
		res.setException(str);
		res.setSuccess(false);
		res.setMsg(MyConstant.map.get(str));
		return res;
	}
}
