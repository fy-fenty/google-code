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
			result=new Result(false,"dispatchList或者sysEmployee为null","A003");
		}
		if(sysEmployee.getESn()==null){
			result=new Result(false,"sysEmployee.getESn() is null","A003");
		} 
		
		SysPositions sysPositions = iSystemService.findPositionById(sysEmployee.getPId());
		if(!"雇员".equals(sysPositions.getPNameCn())){
			result=new Result(false,"用户不是雇员","A012");
		}
		
		iDispatchListDao.save(dispatchList);
		
		return result;
	}
	


	@Override
	public Result updateClaims(final SysEmployee sysEmployee,final DispatchList dispatchList) throws MyException {
		Result result=null;
		if(dispatchList==null||sysEmployee==null){
			result=new Result(false,"dispatchList或者sysEmployee为null","A003");
		}
		if(dispatchList.getDlId()==null){
			result=new Result(false,"dispatchList.getDlId() is null","A003");
		}
		DispatchList  dl = iSystemService.findById(dispatchList.getDlId());
		if(dl==null||dl.getFlag()==false){
			result=new Result(false,"dl is null或者dl.getFlag()为false","A003");
		}
		if(sysEmployee.getESn()==dl.getESn()){
			result=new Result(false,"sysEmployee.getESn()和dl.getESn()不匹配","A012");
		}
		
		DispatchResult dr1 = iSystemService.findResultById(dispatchList.getDlId());
		if(dr1!=null||dr1.getCheckStatus()==4){
			result=new Result(false,"dr1为null或者dr1.getCheckStatus()已废弃","A003");
		}
		iDispatchListDao.save(dispatchList);
		return result;
	}

	@Override
	public SysEmployee findBySn(String SN) throws MyException {
		Result result=null;
		if(!StringUtil.isEmpty(SN)){
			result=new Result(false,"SN 为null","A003");
		}
		String sql=" from SysEmployee where E_SN=?";
		return iSysEmployeeDao.findUniqueByHQL(sql, SN);
	}

	@Override
	public Result deleteClaims(final SysEmployee emp,final DispatchDetail cid) throws MyException {
		Result result=new Result(true,"删除报销单成功","A007");
		if(emp==null||cid==null){
			result=new Result(false,"雇员对象空或者报销单明细对象空","A003");
		}
		if(StringUtil.isEmpty(emp.getEId().toString())||StringUtil.isEmpty(cid.getSheetId()+"")||StringUtil.isEmpty(emp.getESn())){
			result=new Result(false,"雇员id为空或者报销单id为空","A003");
		}
		//查询报销单
		DispatchList dispatchList = iSystemService.findById(cid.getSheetId());
		if(dispatchList==null){
			result=new Result(false,"报销单为空","A003");
		}
		if(dispatchList.getESn()!=emp.getESn()){
			result=new Result(false,"创建报销单和雇员不匹配","A012");
		}
		if(dispatchList.getFlag()==false){
			result=new Result(false,"报销单已经废弃","A013");
		}
		Page page= iSystemService.findDetailById(cid.getSheetId(), 0, 5);
		if(page.getResult().size()>0){
			//删除报销单明细
			this.deleteDetail(emp, cid);
		}
		if(result.getSuccess()){
			dispatchList.setFlag(false);
			iDispatchListDao.save(dispatchList);
		}
		return result;
	}

	@Override
	public Result updateDetail(SysEmployee emp, DispatchDetail detail)
			throws MyException {
		Result result=null;
		if(emp==null||detail==null){
			result=new Result(false,"雇员或者报销单明细为空","A003");
		}
		if(StringUtil.isEmpty(detail.getDsId().toString())==false){
			result=new Result(false,"报销单id为空","A003");
		}
		
		return result;
	}

	@Override
	public Result deleteDetail(SysEmployee emp, DispatchDetail detail)
			throws MyException {
		Result result=new Result(true,"删除报销单明细成功","A007");
		if(emp==null||detail==null){
			result=new Result(false,"雇员或者报销单明细为空","A003");
		}
		if(StringUtil.isEmpty(detail.getSheetId().toString())==false){
			result=new Result(false,"报销单id为空","A003");
		}
		//查询报销单
		DispatchList dl= iSystemService.findById(detail.getSheetId());
		
		if(dl==null){
			result=new Result(false,"报销单为null","A003");
		}
		if(dl.getFlag()==false){
			result=new Result(false,"报销单无效","A013");
		}
		//查询报销单状态
		DispatchResult dr1 = iSystemService.findResultById(dl.getDlId());
		if(dr1!=null||dr1.getCheckStatus()==4){
			result=new Result(false,"报销单为审批状态或报销单为已废弃","A013");
		}
		
		if(result.getSuccess()){
			String sql="update dispatch_detail set flag=1 where sheet_id=?";
			iSysEmployeeDao.createSQLQuery(sql, detail.getSheetId());
		}
		return result;
	}

	@Override
	public Result saveDetail(SysEmployee emp, DispatchDetail detail)
			throws MyException {
		Result result=new Result(true,"删除报销单明细成功","A007");
		if(emp==null||detail==null){
			
		}
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
