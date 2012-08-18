package org.zjf.services.impl;

import java.util.Date;
import java.util.List;

import org.ymm.constant.MyConstant;
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
import org.ymm.vo.BaseVo;
import org.ymm.vo.DispatchResultVo;
import org.ymm.vo.Page;
import org.ymm.vo.Result;
import org.zjf.services.IFinancialService;
import org.zjf.services.ISystemService;

public class FinancialServiceImpl implements IFinancialService{

	private ISysEmployeeDao empdao;
	private IAreaDao areadao;
	private IDispatchListDao listdao;
	private IDispatchResultDao resultdao;
	private ISystemService system;
	private IDispatchDetailDao detaildao;
	private IDispatchStatusDao statusdao;
	private ISysDepartmentDao departdao;
	private EmpServiceImpl empservice;
	
	
	public EmpServiceImpl getEmpservice() {
		return empservice;
	}

	public void setEmpservice(EmpServiceImpl empservice) {
		this.empservice = empservice;
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

	public ISystemService getSystem() {
		return system;
	}

	public void setSystem(ISystemService system) {
		this.system = system;
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

	@Override
	public SysPositions loginUser(String username, String pwd)
			throws MyException {
		return null;
	}
 
	@Override
	public Page lookClaims(SysEmployee emp,final BaseVo vo )
			throws MyException {
		if(emp==null)
			throw new MyException("A002");
		String sql="select * from dispatch_result where check_status in(2,5,6)";
		Page page=empdao.findPageBySQL(vo, sql);
		return page;
	}

	@Override
	public void GeneralExcel(SysEmployee emp) throws MyException {
		
		
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
		
		Result re=this.checkEmpPos(emp.getPId());
		if(re.getSuccess()==false)
			return re;

		List<DispatchDetail> list = system.findDetailById(cla.getSheetId(), 0,
				999).getResult();
		if (list == null || list.size() <= 0)
			return getResult("A010");
		try {
			DispatchResult result=resultdao.get(cla.getSheetId());
			result.setCheckNext(emp.getESn());
			result.setCheckStatus(1L);
			result.setCheckNext(null);
			result.setCheckStatus(6L);
			result.setCheckSn(emp.getESn());
			result.setCheckTime(new Date());
			result.setSheetId(cla.getSheetId());
		} catch (Exception e) {
			return getResult("A008");
		}
		Result res = new Result();
		return res;
	}

	@Override
	public Result payMent(SysEmployee emp, DispatchResultVo  result)
			throws MyException {
		if(emp==null)
			return this.getResult("A002");
		Result re=this.checkEmpPos(emp.getPId());
		if(re.getSuccess()==false)
			return re;
		String sql="update dispatch_result set check_status=6 where  dr_id=? and check_status=?";
		try{
		resultdao.createSQLQuery(sql, result.getDrId()+"",result.getCheckStatus()+"").uniqueResult();
		}catch(Exception e){
			return this.getResult("A006");
		}
		Result res=new Result();
		return res;
	}

	private Result checkEmpPos(long p_id) throws MyException {
		SysDepartment pos = departdao.findUnique("from SysDepartment where DId=?", p_id+"");
		if (pos == null)
			return getResult("A003");
		if (!pos.getDName().equals("财务部"))
			return getResult("A005");
		Result res=new Result();
		return res;
	}
	
	private Result getResult(String str){
		Result res=new Result();
		res.setException(str);
		res.setSuccess(false);
		res.setMsg(MyConstant.map.get(str));
		return res;
	}
}
