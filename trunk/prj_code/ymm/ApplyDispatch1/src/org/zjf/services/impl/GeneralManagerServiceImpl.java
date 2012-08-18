package org.zjf.services.impl;

import java.util.Date;
import java.util.List;

import org.ymm.constant.MyConstant;
import org.ymm.dao.IDispatchListDao;
import org.ymm.dao.IDispatchResultDao;
import org.ymm.dao.IDispatchStatusDao;
import org.ymm.dao.ISysEmployeeDao;
import org.ymm.entity.DispatchDetail;
import org.ymm.entity.DispatchResult;
import org.ymm.entity.DispatchStatus;
import org.ymm.entity.SysEmployee;
import org.ymm.entity.SysPositions;
import org.ymm.exception.MyException;
import org.ymm.util.StringUtil;
import org.ymm.vo.BaseVo;
import org.ymm.vo.DispatchListVo;
import org.ymm.vo.DispatchResultVo;
import org.ymm.vo.Page;
import org.ymm.vo.Result;
import org.zjf.services.IGeneralManagerService;
import org.zjf.services.ISystemService;

/**
 * @project:ApplyDispatch1
 * @author:zjf
 * @date:2012-8-18 下午4:49:12   
 * @class:GeneralManagerServiceImpl
 * @extends:Object
 * @description:总经理业务接口
 */
public class GeneralManagerServiceImpl implements IGeneralManagerService {

	private ISysEmployeeDao empdao;
	private ISystemService system;
	private IDispatchResultDao resultdao;
	private IDispatchListDao listdao;
	private IDispatchStatusDao statusdao;

	public IDispatchStatusDao getStatusdao() {
		return statusdao;
	}

	public void setStatusdao(IDispatchStatusDao statusdao) {
		this.statusdao = statusdao;
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

	public ISysEmployeeDao getEmpdao() {
		return empdao;
	}

	public void setEmpdao(ISysEmployeeDao empdao) {
		this.empdao = empdao;
	}

	public ISystemService getSystem() {
		return system;
	}

	public void setSystem(ISystemService system) {
		this.system = system;
	}

	@Override
	public Result applyClaims(SysEmployee emp, DispatchResultVo cla)
			throws MyException {
		if (emp == null || emp.getPId() == null)
			return getResult("A002");
		if (cla == null || cla.getSheetId() == null)
			return getResult("A002");
		if (StringUtil.isEmpty(emp.getESn()) == false)
			return getResult("A002");

		Result res = checkEmpPos(emp.getDepartmentId());
		if (res.getSuccess() == false)
			return res;

		String sql1 = "from DispatchDetail where sheetId=?";
		List<DispatchDetail> list = empdao.find(sql1, cla.getSheetId() + "");
		if (list == null || list.size() <= 0)
			return getResult("A010");

		try {
			DispatchResult result = system.findResultById(cla.getSheetId());
			if (result == null)
				return this.getResult("A003");
			if (result.getCheckStatus() != 1)
				return this.getResult("A005");
			if (!(result.getCheckNext() + "").equals(emp.getESn()))
				return this.getResult("A005");
			DispatchResult rea = new DispatchResult();
			rea.setCheckNext(null);
			if (cla.getCheckStatus() == 2) {
				rea.setCheckStatus(2L);
			} else {
				DispatchStatus status = statusdao.get(cla.getCheckStatus());
				if (status == null)
					return this.getResult("A003");
				rea.setCheckStatus(cla.getCheckStatus());
			}
			rea.setCheckSn(emp.getESn());
			rea.setCheckTime(new Date());
			rea.setSheetId(cla.getSheetId());
			resultdao.save(rea);
		} catch (Exception e) {
			return getResult("A008");
		}
		Result result = new Result();
		return result;
	}

	@Override
	public Page findMyApply(SysEmployee emp, BaseVo vo) throws MyException {
		if (emp == null || emp.getPId() == null)
			throw new MyException("A002");
		Result re = this.checkEmpPos(emp.getPId());
		if (re.getSuccess() == false)
			throw new MyException("A005");
		String sql = "select * from(select r.sheet_id,r.DR_ID,r.CHECK_STATUS,r.check_next from ( "
				+ " select sheet_id,DR_ID,CHECK_STATUS,check_next,row_number() over (partition by e.sheet_id order by e.DR_ID desc nulls last) rn"
				+ "         from dispatch_result e   "
				+ " ) r where rn =1) dr   where dr.check_status in (1,2)";
		Page page = empdao.findPageBySQL(vo, sql);
		if (page == null)
			throw new MyException("A003");
		return page;
	}

	@Override
	public SysPositions loginUser(String username, String pwd)
			throws MyException {
		return null;
	}

	@Override
	public Result stopClaims(SysEmployee emp, DispatchListVo list)
			throws MyException {
		if (emp == null || emp.getPId() == null)
			return this.getResult("A002");
		if (list.getDlId() == null)
			return this.getResult("A002");
		Result result = this.checkEmpPos(emp.getPId());
		if (result.getSuccess() == false)
			return this.getResult("A005");
		DispatchResult dr = system.findResultById(list.getDlId());
		if (dr == null)
			return this.getResult("A003");
		if (dr.getCheckStatus() > 2)
			return this.getResult("A005");
		if ((dr.getCheckSn() + "").equals(emp.getESn()))
			return this.getResult("A005");
		dr.setCheckStatus(3L);
		resultdao.save(dr);
		Result res = new Result();
		return res;
	}

	private Result checkEmpPos(long p_id) throws MyException {
		SysPositions pos = system.findPositionById(p_id);
		if (pos == null)
			return this.getResult("A003");
		if (!pos.getPNameCn().equals("总经理"))
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
