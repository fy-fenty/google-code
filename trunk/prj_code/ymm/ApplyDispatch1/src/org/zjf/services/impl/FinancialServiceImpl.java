package org.zjf.services.impl;

import java.util.Date;
import java.util.List;

import org.ymm.constant.MyConstant;
import org.ymm.dao.IDispatchListDao;
import org.ymm.dao.IDispatchResultDao;
import org.ymm.dao.IDispatchStatusDao;
import org.ymm.dao.ISysDepartmentDao;
import org.ymm.dao.ISysEmployeeDao;
import org.ymm.entity.DispatchDetail;
import org.ymm.entity.DispatchResult;
import org.ymm.entity.DispatchStatus;
import org.ymm.entity.SysEmployee;
import org.ymm.exception.MyException;
import org.ymm.util.StringUtil;
import org.ymm.vo.BaseVo;
import org.ymm.vo.DispatchResultVo;
import org.ymm.vo.LoginUserVo;
import org.ymm.vo.Page;
import org.ymm.vo.Result;
import org.zjf.services.IFinancialService;
import org.zjf.services.ISystemService;

/**
 * 
 * @project:ApplyDispatch1
 * @author:zjf
 * @date:2012-8-18 下午4:48:28   
 * @class:FinancialServiceImpl
 * @extends:Object
 * @description:财务业务接口实现类
 */
public class FinancialServiceImpl implements IFinancialService {

	private ISysEmployeeDao empdao;
	private IDispatchListDao listdao;
	private IDispatchResultDao resultdao;
	private ISystemService system;
	private ISysDepartmentDao departdao;
	private IDispatchStatusDao statusdao;

	public IDispatchStatusDao getStatusdao() {
		return statusdao;
	}

	public void setStatusdao(IDispatchStatusDao statusdao) {
		this.statusdao = statusdao;
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

	public ISystemService getSystem() {
		return system;
	}

	public void setSystem(ISystemService system) {
		this.system = system;
	}

	public ISysDepartmentDao getDepartdao() {
		return departdao;
	}

	public void setDepartdao(ISysDepartmentDao departdao) {
		this.departdao = departdao;
	}

	@Override
	public Result loginUser(final LoginUserVo vo)
			throws MyException {
		return null;
	}

	@Override
	public Page lookClaims(SysEmployee emp, final BaseVo vo) throws MyException {
		if (emp == null)
			throw new MyException("A002");
		String sql = "select * from dispatch_result where check_status in(2,5,6)";
		Page page = empdao.findPageBySQL(vo, sql);
		return page;
	}

	@Override
	public void GeneralExcel(SysEmployee emp) throws MyException {

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

		Result re = this.checkEmpPos(emp.getPId());
		if (re.getSuccess() == false)
			return re;

		String sql1 = "from DispatchDetail where sheetId=?";
		List<DispatchDetail> list = empdao.find(sql1, cla.getSheetId() + "");
		if (list == null || list.size() <= 0)
			return getResult("A010");
		try {
			DispatchResult result =system.findResultById(cla.getSheetId());
			if (result == null)
				return this.getResult("A003");
			if(result.getCheckStatus()!=2)
				return this.getResult("A005");
			DispatchResult rea=new DispatchResult();
			rea.setCheckNext(null);
			if (cla.getCheckStatus() == 5) {
				rea.setCheckStatus(5L);
			} else if(cla.getCheckStatus()==4){
				rea.setCheckNext(result.getCheckSn());
				rea.setCheckStatus(cla.getCheckStatus());
			}
			else{
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
			e.printStackTrace();
			return getResult("A008");
		}
		Result res = new Result();
		return res;
	}

	@Override
	public Result payMent(SysEmployee emp, DispatchResultVo result)
			throws MyException {
		if (emp == null||emp.getPId()==null||result.getDrId()==null)
			return this.getResult("A002");
		Result re = this.checkEmpPos(emp.getPId());
		if (re.getSuccess() == false)
			return re;
		DispatchResult resu=resultdao.get(result.getDrId());
		if(resu==null)
			return this.getResult("A003");
		if(resu.getCheckStatus()!=5)
			return this.getResult("A005");
		try {
			String sql = "update dispatch_result set check_status=6 where  dr_id=?";
			resultdao.createSQLQuery(sql, result.getDrId() + "").executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return this.getResult("A006");
		}
		Result res = new Result();
		return res;
	}

	private Result checkEmpPos(long p_id) throws MyException {
		String sql="select count(1) from sys_employee se left join sys_department sd on se.department_id=sd.d_id where se.p_id=? and sd.d_name='财务部'";
		long pos = departdao.countSqlResult(sql, p_id+"");
		if (pos<=0)
			return getResult("A003");
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
