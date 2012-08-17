package org.zjf.services.impl;

import java.util.Date;
import java.util.List;

import org.ymm.dao.IAreaDao;
import org.ymm.dao.IDispatchDetailDao;
import org.ymm.dao.IDispatchListDao;
import org.ymm.dao.IDispatchResultDao;
import org.ymm.dao.IDispatchStatusDao;
import org.ymm.dao.ISysDepartmentDao;
import org.ymm.dao.ISysEmployeeDao;
import org.ymm.entity.DispatchDetail;
import org.ymm.entity.DispatchResult;
import org.ymm.entity.SysDepartment;
import org.ymm.entity.SysEmployee;
import org.ymm.entity.SysPositions;
import org.ymm.exception.MyException;
import org.ymm.util.StringUtil;
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
	public Result applyClaims(SysEmployee emp, DispatchResult cla)
			throws MyException {
		if (emp == null || emp.getDepartmentId() == null
				|| emp.getPId() == null)
			throw new MyException("A002");
		if (cla == null || cla.getSheetId() == null)
			throw new MyException("A002");
		if (StringUtil.isEmpty(emp.getESn()) == false
				|| StringUtil.isEmpty(cla.getCheckNext()) == false)
			throw new MyException("A002");

		checkEmpPos(emp.getPId());

		List<DispatchDetail> list = system.findDetailById(cla.getSheetId(), 0,
				999).getResult();
		if (list == null || list.size() <= 0)
			throw new MyException("A010");
		double money = 0;
		for (DispatchDetail detail : list) {
			money += detail.getMoney();
		}

		DispatchResult res = new DispatchResult();
		res.setCheckComment(cla.getCheckComment());
		if (money >= 5000){
			String sql="fom SysEmployee where PId=1";
			SysEmployee a=empdao.findUnique(sql);
			res.setCheckNext(a.getESn());
		}
		else
			
		res.setCheckSn(emp.getESn());
		res.setCheckTime(new Date());
		res.setSheetId(cla.getSheetId());
		res.setCheckStatus(1L);

		try {
			resultdao.save(res);
		} catch (Exception e) {
			throw new MyException("A008");
		}
		Result result = new Result();
		return result;
	}

	@Override
	public Page findMyApplyClaims(SysEmployee emp, int start, int limit)
			throws MyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result SetPwd(SysEmployee manager, SysEmployee emp)
			throws MyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page findMyDepartClaims(SysEmployee emp, int start, int limit)
			throws MyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SysPositions loginUser(String username, String pwd)
			throws MyException {
		// TODO Auto-generated method stub
		return null;
	}

	private void checkEmpPos(long p_id) throws MyException {
		SysPositions pos = system.findPositionById(p_id);
		if (pos == null)
			throw new MyException("A003");
		if (!pos.getPNameCn().equals("部门经理"))
			throw new MyException("A005");
	}
}
