package org.ymm.services.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.ymm.services.IManagerService;
import org.ymm.services.ISystemService;
import org.zjf.constant.AppConstant;
import org.zjf.dao.IDispatchListDao;
import org.zjf.dao.ILoginUserDao;
import org.zjf.dao.ISysEmployeeDao;
import org.zjf.entity.DispatchResult;
import org.zjf.entity.SysEmployee;
import org.zjf.entity.SysPositions;
import org.zjf.exception.MyException;
import org.zjf.util.MD5;
import org.zjf.util.StringUtil;
import org.zjf.vo.BaseVo;
import org.zjf.vo.DispatchResultVo;
import org.zjf.vo.Page;
import org.zjf.vo.Result;
import org.zjf.vo.SysEmployeeVo;

public class ManagerServiceImpl implements IManagerService {

	private IDispatchListDao iDispatchListDao;
	private ISysEmployeeDao iSysEmployeeDao;
	private ILoginUserDao iLoginUserDao;
	private ISystemService iSystemService;
	
	public ISysEmployeeDao getiSysEmployeeDao() {
		return iSysEmployeeDao;
	}

	public void setiSysEmployeeDao(ISysEmployeeDao iSysEmployeeDao) {
		this.iSysEmployeeDao = iSysEmployeeDao;
	}

	public ILoginUserDao getiLoginUserDao() {
		return iLoginUserDao;
	}

	public void setiLoginUserDao(ILoginUserDao iLoginUserDao) {
		this.iLoginUserDao = iLoginUserDao;
	}

	public IDispatchListDao getiDispatchListDao() {
		return iDispatchListDao;
	}

	public void setiDispatchListDao(IDispatchListDao iDispatchListDao) {
		this.iDispatchListDao = iDispatchListDao;
	}

	@Override
	public Result applyClaims(SysEmployee sysEmployee, DispatchResultVo vo)
			throws MyException {
		Result result = new Result(true, AppConstant.OTHER_ERROR, "A001");
		if(sysEmployee==null||vo==null){
			return new Result(true, AppConstant.OTHER_ERROR, "A003");
		}
		if(StringUtil.isEmpty(sysEmployee.getDepartmentId()+"")==false){
			return new Result(true, AppConstant.OTHER_ERROR, "A003");
		}
		SysPositions sysPositions= iSystemService.findPositionById(sysEmployee.getDepartmentId());
		if(sysPositions==null){
			return new Result(true, AppConstant.OTHER_ERROR, "A003");
		}
		if(!"部门经理".equals(sysPositions.getPNameCn())){
			return new Result(true, AppConstant.OTHER_ERROR, "A005");
		}
		
		// 查询报销单状态
		DispatchResult dr1 = iSystemService.findResultById(vo.getSheetId());
		if (dr1 != null) {
			if (dr1.getCheckStatus() == 4) {
				return new Result(false, AppConstant.UPDATE_ERROR, "A013");
			}
			return new Result(false, AppConstant.UPDATE_ERROR, "A013");
		}
		
		if(!dr1.getCheckNext().equals(sysEmployee.getESn())){
			return new Result(false, AppConstant.UPDATE_ERROR, "A013");
		}
		
		//审批报销单
		
		return result;
	}

	//查询当前审批状态是否是新建
	public Result getDispatchResultEnd(long dl_id) throws MyException{
		DispatchResult dr1 = iSystemService.findResultById(dl_id);
		if (dr1 != null) {
			if (dr1.getCheckStatus() == 4) {
				return new Result(false, AppConstant.UPDATE_ERROR, "A013");
			}
			return new Result(false, AppConstant.UPDATE_ERROR, "A013");
		}
		return new Result(true, AppConstant.DEFAULT_MSG, "A001");
	}
	
	@Override
	public Page findMyApplyClaims(SysEmployee sysEmployee, BaseVo baseVo)
			throws MyException {
		if(sysEmployee==null||baseVo==null){
			throw new MyException("A003");
		}
		String sql="select tt2.* from " 
				+"  (select * from dispatch_list where dl_id in  "
				+"  (select sheet_id from  dispatch_result dr1 where check_time =(select max(check_time) from dispatch_result dr2 where dr1.sheet_id=dr2.sheet_id ) "
				+"	and check_next=?) and e_sn in (select e_sn from sys_employee where department_id=?)) tt1 "
				+"  left join "
				+"  (select t1.dl_id,t1.e_name,t2.money,t1.create_time as create_time,t3.cs as disp_status from  "
				+"  (select dl.dl_id,dl.e_sn,se.e_name,dl.create_time,dl.flag from dispatch_list dl left join sys_employee se on dl.e_sn=se.e_sn) t1 "
				+"  left join "
				+"  (select dd.sheet_id, sum(money) as money from dispatch_detail dd group by dd.sheet_id) t2 "
				+"  on t1.dl_id=t2.sheet_id "
				+"  left join "
				+"  (select d1.sheet_id,(select ds1.da_status from dispatch_status ds1 where ds1.da_id=d1.check_status) as cs from dispatch_result d1 where check_time =(select max(check_time) from dispatch_result d where d.sheet_id=d1.sheet_id)) t3 "
				+"  on t3.sheet_id=t2.sheet_id ) tt2 "
				+"  on  tt1.dl_id=tt2.dl_id where   tt2.money<=5000 ";     
         
		if(StringUtil.isEmpty(sysEmployee.getDepartmentId()+"")==false){
			throw new MyException("A003");
		}
		Page page = iDispatchListDao.findPageBySql(baseVo, sql,new String[]{sysEmployee.getESn(),sysEmployee.getDepartmentId()+""});
		return page;
	}

	@Override
	public Result SetPwd(SysEmployee manger, SysEmployeeVo vo)
			throws MyException {
		Result result = new Result(true, AppConstant.UPDATE_ERROR, "A001");
		if(manger==null||vo==null){
			return new Result(true, AppConstant.UPDATE_ERROR, "A003");
		}
		if(StringUtil.isEmpty(manger.getEId()+"")==false
				||StringUtil.isEmpty(vo.getEId()+"")==false){
			return new Result(true, AppConstant.UPDATE_ERROR, "A003");
		}
		SysEmployee manger1= iSysEmployeeDao.get(manger.getEId());
		SysEmployee sysEmp2= iSysEmployeeDao.get(vo.getEId());
		if(manger1.getPId()!=2){
			return new Result(true, AppConstant.UPDATE_ERROR, "A005");
		}
		if(manger1.getPId().equals(vo.getPId())){
			return new Result(true, AppConstant.UPDATE_ERROR, "A012");
		}

		String sql="update login_user set u_pwd=? where E_SN=?";
		
		iLoginUserDao.createSQLQuery(sql, new String[]{vo.getESn(),MD5.MD5Encode("123456")});
		
		return null;
	}

	@Override
	public Page findMyDepartClaims(SysEmployee sysEmployee, BaseVo baseVo)
			throws MyException {
		if(sysEmployee==null||baseVo==null){
			throw new MyException("A003");
		}
		String sql="select tt2.* from "
				 +" (select * from dispatch_list where  e_sn in (select e_sn from sys_employee where department_id =?) "  
				 +" and dl_id in (select sheet_id from dispatch_result group by sheet_id)) tt1 "
				 +" left join "
				 +" (select t1.dl_id,t1.e_name,t2.money,t1.create_time as create_time,t3.cs as disp_status from "
				 +" (select dl.dl_id,dl.e_sn,se.e_name,dl.create_time,dl.flag from dispatch_list dl left join sys_employee se on dl.e_sn=se.e_sn) t1 "
				 +" left join "
				 +" (select dd.sheet_id, sum(money) as money from dispatch_detail dd group by dd.sheet_id) t2 "
				 +" on t1.dl_id=t2.sheet_id "
				 +" left join "
				 +" (select d1.sheet_id,(select ds1.da_status from dispatch_status ds1 where ds1.da_id=d1.check_status) as cs from dispatch_result d1 where check_time =(select max(check_time) from dispatch_result d where d.sheet_id=d1.sheet_id)) t3 "
				 +" on t3.sheet_id=t2.sheet_id) tt2 "
				 +" on tt1.dl_id=tt2.dl_id ";
		if(StringUtil.isEmpty(sysEmployee.getDepartmentId()+"")==false){
			throw new MyException("A003");
		}
		Page page = iDispatchListDao.findPageBySql(baseVo, sql,sysEmployee.getDepartmentId());
		return page;
	}
	public static void main(String[] args) throws Exception {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { "spring-sessinfactory.xml",
						"spring-dao-beans.xml", "spring-trans.xml" });
		IManagerService ies = ac.getBean("managerServiceImpl", IManagerService.class);
		SysEmployee sysEmployee=new SysEmployee();
		sysEmployee.setDepartmentId((long)1);
		BaseVo bv=new BaseVo(0,100);
		System.out.println(ies.findMyDepartClaims(sysEmployee, bv).getResult().size());
	}
	
	@Override
	public SysPositions loginUser(String username, String pwd)
			throws MyException {
		// TODO Auto-generated method stub
		return null;
	}

}
