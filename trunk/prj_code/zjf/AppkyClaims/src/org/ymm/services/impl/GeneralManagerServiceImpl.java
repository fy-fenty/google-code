package org.ymm.services.impl;

import org.ymm.services.IGeneralManagerService;
import org.zjf.constant.AppConstant;
import org.zjf.dao.IDispatchListDao;
import org.zjf.entity.SysEmployee;
import org.zjf.entity.SysPositions;
import org.zjf.exception.MyException;
import org.zjf.util.StringUtil;
import org.zjf.vo.BaseVo;
import org.zjf.vo.DispatchListVo;
import org.zjf.vo.DispatchResultVo;
import org.zjf.vo.Page;
import org.zjf.vo.Result;

public class GeneralManagerServiceImpl implements IGeneralManagerService{

	private IDispatchListDao iDispatchListDao;
	
	public IDispatchListDao getiDispatchListDao() {
		return iDispatchListDao;
	}

	public void setiDispatchListDao(IDispatchListDao iDispatchListDao) {
		this.iDispatchListDao = iDispatchListDao;
	}

	@Override
	public Result applyClaims(SysEmployee emp, DispatchResultVo vo)
			throws MyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page findMyApply(SysEmployee sysEmployee, BaseVo baseVo) throws MyException {
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
				+"  on  tt1.dl_id=tt2.dl_id tt2.money>=5000 ";     
         
		if(StringUtil.isEmpty(sysEmployee.getDepartmentId()+"")==false){
			throw new MyException("A003");
		}
		Page page = iDispatchListDao.findPageBySql(baseVo, sql,new String[]{sysEmployee.getESn(),sysEmployee.getDepartmentId()+""});
		return page;
	}

	@Override
	public SysPositions loginUser(String username, String pwd)
			throws MyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result stopClaims(SysEmployee sysEmployee, DispatchListVo vo) {
		Result result = new Result(true, AppConstant.OTHER_ERROR, "A001");
		if(sysEmployee==null||vo==null){
			return new Result(true, AppConstant.OTHER_ERROR, "A003");
		}
		
		return result;
	}

}
