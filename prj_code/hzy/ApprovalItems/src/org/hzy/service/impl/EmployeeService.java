package org.hzy.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
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
import org.hzy.util.SessionListener;
import org.hzy.vo.CurrentUser;
import org.hzy.vo.HandleDispatchVo;
import org.hzy.vo.LoginVo;
import org.hzy.vo.Page;
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
	public Page findAllDispatchListByESn(HandleDispatchVo hdVo) throws MyException {
		if (hdVo == null) {
			throw new MyException(AppConstant.A0021);
		}
		if (MyMatcher.isEmpty(hdVo.getEsn())) {
			throw new MyException(AppConstant.A004);
		}
		StringBuilder sb = new StringBuilder();
		sb.append("select tt.dlId,tt.Esn,tt.createTime,tt.totalMoney,NVL(ds.da_status,'草稿') currentStatus from (");
		sb.append("  select d.dl_id dlId,cast(d.e_sn as varchar2(8)) ESn,d.create_time createTime,(");
		sb.append("    select sum(dd.money) from hzy.DISPATCH_DETAIL dd where dd.sheet_id = d.dl_id");
		sb.append("  ) totalMoney, (");
		sb.append("    select d.check_status from hzy.dispatch_result d where d.dr_id = (");
		sb.append("      select max(dr_id) from hzy.dispatch_result where sheet_id = d.dl_id");
		sb.append("    )");
		sb.append("  ) currentStatus ");
		sb.append("  from hzy.dispatch_list d left join hzy.sys_employee s on d.e_sn = s.e_sn where s.e_sn = ? and d.flag = 1");
		sb.append(") tt left join hzy.dispatch_status ds on ds.da_id = tt.currentStatus order by tt.dlId asc");
		return iseDao.findPageBySQL(hdVo, sb.toString(), hdVo.getEsn());
	}

	@Override
	public Result saveDispatchList(HandleDispatchVo hdVo) {
		Result rs = new Result();
		try {
			if (MyMatcher.isEmpty(hdVo.getEsn())) {
				throw new MyException(AppConstant.A004);
			}
			SysPositions sp = isu.findSysPositionsByESn(hdVo.getEsn());
			if (sp.getPId().equals(AppConstant.EMPLOYEE) == false) {
				throw new MyException(AppConstant.A006);
			}
			DispatchList dl = new DispatchList();
			dl.setCreateTime(new Date());
			dl.setESn(hdVo.getEsn());
			dl.setEventExplain(hdVo.getComment());
			dl.setFlag(true);
			Serializable id = idlDao.saveForGetId(dl);
			rs.setSuccess(true);
			rs.setSer(id);
			rs.setMsg("保存报销单成功！");
		} catch (MyException e) {
			rs.setSuccess(false);
			rs.setMsg("保存报销单失败！");
			rs.setException(e.getMessage());
		}
		return rs;
	}

	@Override
	public Result updateDispatchList(HandleDispatchVo hdVo) {
		Result rs = new Result();
		try {
			if (hdVo == null) {
				throw new MyException(AppConstant.A0021);
			}
			if (hdVo.getDlId() == null) {
				throw new MyException(AppConstant.A002);
			}
			if (MyMatcher.isEmpty(hdVo.getEsn())) {
				throw new MyException(AppConstant.A004);
			}
			DispatchList dl = idlDao.get(hdVo.getDlId());
			dl.setEventExplain(hdVo.getComment());
			idlDao.save(dl);
			rs.setSuccess(true);
			rs.setMsg("修改报销单成功！");
		} catch (MyException e) {
			rs.setSuccess(false);
			rs.setMsg("修改报销单失败！");
			rs.setException(e.getMessage());
		}
		return rs;
	}

	@Override
	public Result updateDispatchDetail(HandleDispatchVo hdVo) {
		Result rs = new Result();
		try {
			if (hdVo == null) {
				throw new MyException(AppConstant.A0021);
			}
			if (MyMatcher.isEmpty(hdVo.getEsn())) {
				throw new MyException(AppConstant.A004);
			}
			if (hdVo.getDd() == null) {
				throw new MyException(AppConstant.A0011);
			}
			DispatchDetail dd = iddDao.get(hdVo.getDd().getDsId());
			if (dd == null) {
				throw new MyException(AppConstant.A0012);
			}
			if (dd.getFlag() == false) {
				throw new MyException(AppConstant.A0012);
			}
			DispatchList dl = isu.findDispatchListByDlId(dd.getSheetId());
			if (dl == null || dl.getFlag() == false) {
				throw new MyException(AppConstant.A0012);
			}
			if (dl.getESn().equals(hdVo.getEsn()) == false) {
				throw new MyException(AppConstant.A008);
			}
			if (isu.checkPermissionsByESnAndDlId(hdVo.getEsn(), dl.getDlId()) == false) {
				throw new MyException(AppConstant.A0010);
			}
			dd.setAccessory(hdVo.getDd().getAccessory());
			dd.setCostExplain(hdVo.getDd().getCostExplain());
			dd.setItemId(hdVo.getDd().getItemId());
			dd.setMoney(hdVo.getDd().getMoney());
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
	public Result deleteDispatchList(HandleDispatchVo hdVo) {
		Result rs = new Result();
		try {
			if (MyMatcher.isEmpty(hdVo.getEsn())) {
				throw new MyException(AppConstant.A004);
			}
			if (hdVo.getDlId() == null) {
				throw new MyException(AppConstant.A009);
			}
			if (isu.checkPermissionsByESnAndDlId(hdVo.getEsn(), hdVo.getDlId()) == false) {
				throw new MyException(AppConstant.A0010);
			}
			List<DispatchDetail> dds = isu.findDispatchDetailByDlId(hdVo.getDlId());
			if (dds.size() > 0) {
				String hql = "update DispatchDetail set flag = 0 where sheetId = ?";
				Query query = isu.createQuery(hql, hdVo.getDlId());
				if (query.executeUpdate() < dds.size()) {
					throw new MyException(AppConstant.A0013);
				}
			}
			DispatchList dl = idlDao.get(hdVo.getDlId());
			dl.setFlag(false);
			idlDao.save(dl);
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
	public Result deleteDispatchDetail(HandleDispatchVo hdVo) {
		Result rs = new Result();
		try {
			if (hdVo == null) {
				throw new MyException(AppConstant.A0021);
			}
			if (MyMatcher.isEmpty(hdVo.getEsn())) {
				throw new MyException(AppConstant.A004);
			}
			if (hdVo.getDd() == null) {
				throw new MyException(AppConstant.A0011);
			}
			if (hdVo.getDd().getDsId() == null) {
				throw new MyException(AppConstant.A0012);
			}
			DispatchDetail dd = iddDao.get(hdVo.getDd().getDsId());
			if (dd.getFlag() == false) {
				throw new MyException(AppConstant.A0016);
			}
			DispatchList dl = idlDao.get(dd.getSheetId());
			if (dl == null || dl.getFlag() == false) {
				throw new MyException(AppConstant.A0012);
			}
			if (dl.getESn().equals(hdVo.getEsn()) == false) {
				throw new MyException(AppConstant.A008);
			}
			if (isu.checkPermissionsByESnAndDlId(hdVo.getEsn(), dl.getDlId()) == false) {
				throw new MyException(AppConstant.A0010);
			}
			dd.setFlag(false);
			iddDao.save(dd);
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
	public Result addDispatchDetail(HandleDispatchVo hdVo, DispatchDetail[] dds) {
		Result rs = new Result();
		try {
			if (hdVo == null) {
				throw new MyException(AppConstant.A0021);
			}
			if (MyMatcher.isEmpty(hdVo.getEsn())) {
				throw new MyException(AppConstant.A004);
			}
			if (dds == null || dds.length == 0) {
				throw new MyException(AppConstant.A0011);
			}
			if (hdVo.getDlId() == null) {
				throw new MyException(AppConstant.A0012);
			}
			DispatchList dl = isu.findDispatchListByDlId(hdVo.getDlId());
			if (dl == null || dl.getFlag() == false) {
				throw new MyException(AppConstant.A0012);
			}
			if (dl.getESn().equals(hdVo.getEsn()) == false) {
				throw new MyException(AppConstant.A008);
			}
			if (isu.checkPermissionsByESnAndDlId(hdVo.getEsn(), dl.getDlId()) == false) {
				throw new MyException(AppConstant.A0010);
			}
			for (DispatchDetail dd : dds) {
				dd.setSheetId(hdVo.getDlId());
				dd.setFlag(true);
				iddDao.saveNew(dd);
			}
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
	public Result submitDispatchList(HandleDispatchVo hdVo) {
		Result rs = new Result();
		try {
			if (hdVo == null) {
				throw new MyException(AppConstant.A0021);
			}
			if (MyMatcher.isEmpty(hdVo.getEsn())) {
				throw new MyException(AppConstant.A004);
			}
			if (hdVo.getDlId() == null) {
				throw new MyException(AppConstant.A005);
			}
			if (isu.findDispatchListByDlId(hdVo.getDlId()).getFlag() == false) {
				throw new MyException(AppConstant.A009);
			}
			if (isu.checkPermissionsByESnAndDlId(hdVo.getEsn(), hdVo.getDlId()) == false) {
				throw new MyException(AppConstant.A0010);
			}
			DispatchResult dr = new DispatchResult();
			dr.setCheckStatus(AppConstant.待审批);
			dr.setCheckSn(hdVo.getEsn());
			dr.setSheetId(hdVo.getDlId());
			dr.setCheckTime(new Date());
			dr.setCheckComment(hdVo.getComment());
			String hql = "from SysEmployee where departmentId = (select departmentId from SysEmployee where ESn = ?) and PId = ?";
			SysEmployee se = iseDao.findUnique(hql, hdVo.getEsn(), AppConstant.DMANAGER);
			if (se == null) {
				throw new MyException(AppConstant.A0018);
			}
			dr.setCheckNext(se.getESn());
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
	public String login(LoginVo lgVo) throws MyException {
		String rand = (String) ServletActionContext.getRequest().getSession().getAttribute("rand");
		if (MyMatcher.isEmpty(rand)) {
			throw new MyException(AppConstant.A0024);
		}
		if (lgVo == null) {
			throw new MyException(AppConstant.A0021);
		}

		// 清除 session
		HttpSession se = ServletActionContext.getRequest().getSession();
		se.setAttribute(AppConstant.CURRENT_UNAME, lgVo.getEsn());
		se.setAttribute(AppConstant.CURRENT_USER, null);
		SessionListener.removeSession(se.getId());

		// 验证有效性
		if (MyMatcher.isEmpty(lgVo.getEsn())) {
			throw new MyException(AppConstant.A004);
		}
		if (MyMatcher.isEmpty(lgVo.getPwd())) {
			throw new MyException(AppConstant.A0022);
		}
		if (rand.equals(lgVo.getCheckCode()) == false) {
			throw new MyException(AppConstant.A0024);
		}

		// 获取用户
		LoginUser lgUser = isu.findLoginUserByESn(lgVo.getEsn());
		if (lgUser == null) {
			throw new MyException(AppConstant.A0020);
		}
		if (lgUser.getUPwd().equals(isu.getMD5(lgVo.getPwd())) == false) {
			throw new MyException(AppConstant.A0023);
		}

		// 剔除已在线用户
		Map<String, HttpSession> map = SessionListener.getSessionMaps();
		for (String s : map.keySet()) {
			HttpSession se1 = map.get(s);
			CurrentUser cu = (CurrentUser) se1.getAttribute(AppConstant.CURRENT_USER);
			if (s.equals(se.getId())) {
				continue;
			}
			if (lgUser.getUId().equals(cu.getUid())) {
				se1.setAttribute(AppConstant.CURRENT_USER, null);
				SessionListener.removeSession(se1.getId());
				break;
			}
		}

		CurrentUser cu = new CurrentUser();
		cu.setUid(lgUser.getUId());
		cu.setUname(lgVo.getEsn());

		se.setAttribute(AppConstant.CURRENT_USER, cu);
		SessionListener.addSession(se.getId(), se);
		return cu.getUname();
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
		// String eSn = "10000000";
		ApplicationContext actc = new ClassPathXmlApplicationContext(new String[] { "hibernate-spring.xml", "beans1.xml" });
		IEmployeeService is = actc.getBean("EmployeeService", IEmployeeService.class);
		ISystemUtil isu = actc.getBean("SystemUtil", ISystemUtil.class);
		IDispatchDetailDao idd = actc.getBean("DispatchDetailDao", IDispatchDetailDao.class);
		/* 雇员查找所有当前状态的全部报销单 */
		// System.out.println("雇员查找所有当前状态的全部报销单");
		// HandleDispatchVo hdVo = new HandleDispatchVo();
		// hdVo.setEsn("10000000");
		// System.out.println(StringUtil.parseToJsonFromObject(is.findAllDispatchListByESn(hdVo)));

		/* 用户保存报销单 */
		// System.out.println("用户保存报销单");
		// HandleDispatchVo hdVo = new HandleDispatchVo();
		// Result rs = is.saveDispatchList(hdVo);
		// System.out.println(rs.getSuccess());
		// System.out.println(rs.getMsg());

		/* 用户修改报销单 */
		// Result rs = is.updateDispatchList(eSn, 22L, "cccccccc");
		// System.out.println(rs.getSuccess());
		// System.out.println(rs.getMsg());
		// System.out.println(rs.getException());

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
		System.out.println("用户删除报销单明细");
		HandleDispatchVo hdVo = new HandleDispatchVo();
		hdVo.setEsn("10000000");
		DispatchDetail dd = new DispatchDetail();
		dd.setDsId(185L);
		hdVo.setDd(dd);
		Result rs = is.deleteDispatchDetail(hdVo);
		System.out.println(rs.getSuccess());
		System.out.println(rs.getMsg());
		System.out.println(rs.getException());

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
		// LoginVo lg = new LoginVo("10000000", "000000");
		// is.login(lg);
		// HandleDispatchVo hdVo = new HandleDispatchVo();
		// hdVo.setEsn("10000000");
		// hdVo.setDlId(1L);
		// hdVo.setComment("aaaaaaaaaaaaaaa");
		// Result rs = is.updateDispatchList(hdVo);
		// System.out.println(rs.getMsg());
	}
}