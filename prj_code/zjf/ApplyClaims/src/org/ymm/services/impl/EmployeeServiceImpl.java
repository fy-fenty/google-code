package org.ymm.services.impl;

import org.ymm.services.IEmpService;
import org.ymm.services.ISystemService;
import org.zjf.dao.IDispatchListDao;
import org.zjf.dao.ISysEmployeeDao;
import org.zjf.entity.DispatchDetail;
import org.zjf.entity.DispatchList;
import org.zjf.entity.DispatchResult;
import org.zjf.entity.SysEmployee;
import org.zjf.entity.SysPositions;
import org.zjf.exception.MyException;
import org.zjf.support.impl.BaseDao;
import org.zjf.util.StringUtil;
import org.zjf.vo.BaseVO;
import org.zjf.vo.Page;
import org.zjf.vo.Result;

public class EmployeeServiceImpl implements IEmpService {

	public static void main(String[] args) throws MyException {
		
		/*ApplicationContext ac=new ClassPathXmlApplicationContext(new String[]{"spring-sessinfactory.xml","spring-dao-beans.xml","spring-trans.xml"});
		IEmployeeService ies= ac.getBean("employeeServiceImpl",IEmployeeService.class);
		DispatchList dispatchList=new DispatchList();
		dispatchList.setESn("xxxx1003");
		SysEmployee sysEmployee=new SysEmployee();
		sysEmployee.setESn("xxxx1003");
		
		ies.saveDispatchList(dispatchList,sysEmployee);*/
	}
	
	private BaseDao baseDao;
	private ISystemService iSystemService;
	private IDispatchListDao iDispatchListDao;
	private ISysEmployeeDao iSysEmployeeDao;
	
	

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
	public Page findAllClaims(final SysEmployee emp,final int start,final int limit)throws MyException {
		String sql="select t1.*,t2.money,t3.cs as status from " +
				" (select dl.dl_id,dl.e_sn,se.e_name,dl.create_time,dl.flag from dispatch_list dl left join sys_employee se on dl.e_sn=se.e_sn) t1 "+
				" left join "+
				" (select dd.sheet_id, sum(money) as money from dispatch_detail dd group by dd.sheet_id) t2 "+
				"  on t1.dl_id=t2.sheet_id "+
				" left join "+
				" (select d1.sheet_id,(select ds1.da_status from dispatch_status ds1 where ds1.da_id=d1.check_status) as cs from dispatch_result d1 where check_time =(select max(check_time) from dispatch_result d where d.sheet_id=d1.sheet_id)) t3 "+
				"  on t3.sheet_id=t2.sheet_id where t1.e_sn=?";
		BaseVO vo=new BaseVO();
		vo.setLimit(limit);
		vo.setStart(start);
		Page page= baseDao.findPageBySql(vo, sql, emp.getESn());
		return page;
	}
	

	@Override
	public Result saveClaims(final SysEmployee sysEmployee,final DispatchList dispatchList) throws MyException {
		Result result=null;
		if(dispatchList==null||sysEmployee==null){
			throw new MyException("A001");
		}
		
		if(sysEmployee.getESn()==null){
			throw new MyException("A001");
		} 
		
		SysPositions sysPositions = iSystemService.findPositionById(sysEmployee.getPId());
		if(!"雇员".equals(sysPositions.getPNameCn())){
			throw new MyException("A001");
		}
		try{
			iDispatchListDao.save(dispatchList);
		}catch (Exception e) {
			// TODO: handle exception
			throw new MyException("sql语句");
		}
		return result;
	}
	


	@Override
	public Result updateClaims(final SysEmployee sysEmployee,final DispatchList dispatchList) throws MyException {
		Result result=null;
		if(dispatchList==null||sysEmployee==null){
			throw new MyException("A001");
		}
		if(dispatchList.getDlId()==null){
			throw new MyException("A001");
		}
		DispatchList  dl = iSystemService.findById(dispatchList.getDlId());
		if(dl==null||dl.getFlag()==false){
			throw new MyException("A001");
		}
		if(sysEmployee.getESn()==dl.getESn()){
			throw new MyException("A001");
		}
		
		DispatchResult dr1 = iSystemService.findResultById(dispatchList.getDlId());
		if(dr1!=null||dr1.getCheckStatus()==4){
			throw new MyException("A001");
		}
		iDispatchListDao.save(dispatchList);
		return result;
	}

	@Override
	public SysEmployee findBySn(String SN) throws MyException {
		if(StringUtil.isEmpty(SN)==false){
			throw new MyException("A001");
		}
		String sql=" from SysEmployee where E_SN=?";
		return iSysEmployeeDao.findUniqueByHQL(sql, SN);
	}

	@Override
	public Result deleteClaims(SysEmployee emp, long cid) throws MyException {
		Result result=null;
		if(StringUtil.isEmpty(emp.getEId().toString())||StringUtil.isEmpty(cid+"")||StringUtil.isEmpty(emp.getESn())){
			throw new MyException("A001");
		}
		DispatchList dispatchList = iSystemService.findById(cid);
		if(dispatchList==null){
			throw new MyException("A001");
		}
		if(dispatchList.getESn()!=emp.getESn()){
			throw new MyException("A001");
		}
		if(dispatchList.getFlag()==false){
			throw new MyException("A001");
		}
		
		
		return result;
	}

	@Override
	public Result updateDetail(SysEmployee emp, DispatchDetail detail)
			throws MyException {
		Result result=null;
		return result;
	}

	@Override
	public Result deleteDetail(SysEmployee emp, DispatchDetail detail)
			throws MyException {
		Result result=null;
		return result;
	}

	@Override
	public Result saveDetail(SysEmployee emp, DispatchDetail detail)
			throws MyException {
		Result result=null;
		return result;
	}

	@Override
	public Result commitClaims(SysEmployee emp, DispatchList cla)
			throws Exception {
		Result result=null;
		return result;
	}

	@Override
	public SysPositions loginUser(String username, String pwd)
			throws MyException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
