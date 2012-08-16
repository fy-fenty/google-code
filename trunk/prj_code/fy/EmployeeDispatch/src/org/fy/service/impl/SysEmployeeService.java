package org.fy.service.impl;

import java.util.Date;
import java.util.List;

import org.fy.constant.AppConstant;
import org.fy.dao.IDispatchDetailDao;
import org.fy.dao.IDispatchListDao;
import org.fy.dao.IDispatchResultDao;
import org.fy.dao.ISysDepartmentDao;
import org.fy.dao.ISysEmployeeDao;
import org.fy.entity.DispatchDetail;
import org.fy.entity.DispatchList;
import org.fy.entity.DispatchResult;
import org.fy.entity.LoginUser;
import org.fy.entity.SysDepartment;
import org.fy.entity.SysEmployee;
import org.fy.entity.SysPositions;
import org.fy.exception.MyExecption;
import org.fy.service.ISysEmployeeService;
import org.fy.service.ISystemService;
import org.fy.util.MyMatcher;
import org.fy.vo.BaseVO;
import org.fy.vo.Page;
import org.fy.vo.Result;
import org.hibernate.SQLQuery;

public class SysEmployeeService implements ISysEmployeeService {

	private ISysEmployeeDao isys_employeeDao;
	private ISystemService isystem_service;	
	private IDispatchListDao idispatch_list;
	private IDispatchDetailDao idispatch_detail;
	private IDispatchResultDao idispatch_result;
	private ISysDepartmentDao isys_department;
	
	public ISysEmployeeDao getIsys_employeeDao() {
		return isys_employeeDao;
	}

	public void setIsys_employeeDao(ISysEmployeeDao isys_employeeDao) {
		this.isys_employeeDao = isys_employeeDao;
	}

	public ISystemService getIsystem_service() {
		return isystem_service;
	}

	public void setIsystem_service(ISystemService isystem_service) {
		this.isystem_service = isystem_service;
	}

	public IDispatchListDao getIdispatch_list() {
		return idispatch_list;
	}

	public void setIdispatch_list(IDispatchListDao idispatch_list) {
		this.idispatch_list = idispatch_list;
	}

	public IDispatchDetailDao getIdispatch_detail() {
		return idispatch_detail;
	}

	public void setIdispatch_detail(IDispatchDetailDao idispatch_detail) {
		this.idispatch_detail = idispatch_detail;
	}

	public IDispatchResultDao getIdispatch_result() {
		return idispatch_result;
	}

	public void setIdispatch_result(IDispatchResultDao idispatch_result) {
		this.idispatch_result = idispatch_result;
	}

	public ISysDepartmentDao getIsys_department() {
		return isys_department;
	}

	public void setIsys_department(ISysDepartmentDao isys_department) {
		this.isys_department = isys_department;
	}

	public Result saveDispathList(DispatchList dlist) throws MyExecption {
		Result rs=new Result(true,AppConstant.DEFAULT_ERROR);
		if(dlist==null||MyMatcher.isEmpty(dlist.getESn())){
			throw new MyExecption("A002");
		}
		SysPositions sys_postion=isystem_service.findPositionBySn(dlist.getESn());
		if(sys_postion==null||MyMatcher.isEmpty(sys_postion.getPId()+"")){
			throw new MyExecption("A002");
		}
		if(sys_postion.getPId()!=3){
			throw new MyExecption("A005");
		}
		idispatch_list.save(dlist);
		return rs;
	}

	public Result saveDispathDetail(SysEmployee sys_emp,DispatchDetail detail) throws MyExecption {
		Result rs=new Result();
		if(detail==null||sys_emp==null||MyMatcher.isEmpty(detail.getSheetId()+"")){
			throw new MyExecption("A002");
		}
		DispatchList dislist=isystem_service.findByDlistId(detail.getSheetId());
		if(dislist==null){
			throw new MyExecption("A003");
		}else if(dislist.getFlag()==false){
			throw new MyExecption("A006");
		}else if(!dislist.getESn().equals(sys_emp.getESn())){
			throw new MyExecption("A005");
		}
		DispatchResult disResult=isystem_service.findResultById(dislist.getDlId());
		if(disResult!=null&&!disResult.getCheckNext().equals(dislist.getESn())&&disResult.getCheckStatus()!=4){
			throw new MyExecption("A005");
		}
		idispatch_detail.save(detail);
		return rs;
	}

	public Result updateDispathList(String sn,DispatchList dlist) throws MyExecption {
		Result rs=new Result();
		if(dlist==null||MyMatcher.isEmpty(dlist.getDlId()+"")||MyMatcher.isEmpty(sn)){
			throw new MyExecption("A002");
		}
		DispatchList dislist=isystem_service.findByDlistId(dlist.getDlId());
		if(dislist==null){
			throw new MyExecption("A003");
		}else if(dislist.getFlag()==false){
			throw new MyExecption("A006");
		}else if(!dislist.getESn().equals(sn)){
			throw new MyExecption("A005");
		}
		DispatchResult disResult=isystem_service.findResultById(dislist.getDlId());
		if(disResult!=null&&!disResult.getCheckNext().equals(sn)&&disResult.getCheckStatus()!=4){
			throw new MyExecption("A005");
		}
		idispatch_list.save(dislist);
		return rs;
	}

	public Result updateDispathDetail(String sn,DispatchDetail detail) throws MyExecption {
		Result rs=new Result();
		if(detail==null||MyMatcher.isEmpty(sn)||MyMatcher.isEmpty(detail.getSheetId()+"")){
			throw new MyExecption("A002");
		}
		DispatchList dislist=isystem_service.findByDlistId(detail.getSheetId());
		if(dislist==null){
			throw new MyExecption("A003");
		}else if(dislist.getFlag()==false){
			throw new MyExecption("A006");
		}else if(!dislist.getESn().equals(sn)){
			throw new MyExecption("A005");
		}
		DispatchResult disResult=isystem_service.findResultById(dislist.getDlId());
		if(disResult!=null&&!disResult.getCheckNext().equals(dislist.getESn())&&disResult.getCheckStatus()!=4){
			throw new MyExecption("A005");
		}
		idispatch_detail.save(detail);
		return rs;
	}

	public Result commitDispathList(String sn,DispatchList dlist) throws MyExecption {
		Result rs=new Result();
		if(MyMatcher.isEmpty(sn)||dlist==null||MyMatcher.isEmpty(dlist.getDlId())){
			throw new MyExecption("A002");
		}
		SysPositions sys_position=isystem_service.findPositionBySn(sn);
		if(sys_position==null){
			throw new MyExecption("A003");
		}
		if(sys_position.getPId()!=3){
			throw new MyExecption("A005");
		}
		List list=isystem_service.findDetailById(dlist.getDlId());
		if(list==null||list.size()<0){
			throw new MyExecption("A010");
		}
		
		DispatchResult dis_result=new DispatchResult();
		dis_result.setSheetId(dlist.getDlId());
		dis_result.setCheckSn(sn);
		dis_result.setCheckTime(new Date());
		dis_result.setCheckStatus(1L);
		
		String sql="select * from hzy.sys_department where d_id=(select department_id from hzy.sys_employee where e_sn=?)";
		SQLQuery sqlQuery=isys_department.createSQLQuery(sql, sn).addEntity(SysDepartment.class);
		SysDepartment sys_depart=(SysDepartment) sqlQuery.uniqueResult();
		if(sys_depart==null){
			throw new MyExecption("A003");
		}
		dis_result.setCheckNext(sys_depart.getManageSn());
		idispatch_result.save(dis_result);
		
		return rs;
	}

	public Page findDispathList(BaseVO bv, String sn) throws MyExecption {
		if(bv==null||MyMatcher.isEmpty(sn)){
			throw new MyExecption("A002");
		}
		String sql = "select dl_id,e_sn,create_time,check_status,t4.dmoney from (select sum(dd.money) dmoney,dd.sheet_id from HZY.dispatch_detail dd group by dd.sheet_id) t4"
				+ " right join"
				+ " (select * from HZY.dispatch_list dl left join"
				+ " (select * from HZY.dispatch_Result dr where dr.check_time=("
				+ " select max(check_time) from HZY.dispatch_Result where sheet_id=dr.sheet_id)) t2 "
				+ " on dl.dl_id=t2.sheet_id where dl.e_sn=? and dl.flag=1)t3 on t4.sheet_id=t3.dl_id";
		return isys_employeeDao.findPageBySQL(bv, sql, sn);
	}
	
	public Result deleteDispathList(String sn,Long id) throws MyExecption {
		Result rs=new Result();
		if(MyMatcher.isEmpty(id+"")||MyMatcher.isEmpty(sn)){
			throw new MyExecption("A002");
		}
		DispatchList dislist=isystem_service.findByDlistId(id);
		if(dislist==null){
			throw new MyExecption("A003");
		}else if(dislist.getFlag()==false){
			throw new MyExecption("A006");
		}else if(!dislist.getESn().equals(sn)){
			throw new MyExecption("A005");
		}
		DispatchResult disResult=isystem_service.findResultById(id);
		if(disResult!=null&&!disResult.getCheckNext().equals(sn)&&disResult.getCheckStatus()!=4){
			throw new MyExecption("A005");
		}
	/*	List list=isystem_service.findDetailById(id);
		if(list==null||list.size()<0){
			throw new MyExecption("A010");
		}*/
		String sql="update hzy.dispatch_detail set flag=0 where sheet_id=?";
		idispatch_detail.createSQLQuery(sql, id).executeUpdate();
		
		String sql1="update hzy.dispatch_list set flag=0 where dl_id=?";
		idispatch_list.createSQLQuery(sql1, id);
		return rs;
	}

	public Result deleteDispathDetail(String sn,Long id) throws MyExecption {
		Result rs=new Result();
		if(MyMatcher.isEmpty(id+"")||MyMatcher.isEmpty(sn)){
			throw new MyExecption("A002");
		}
		DispatchList dislist=isystem_service.findByDlistId(id);
		if(dislist==null){
			throw new MyExecption("A003");
		}else if(dislist.getFlag()==false){
			throw new MyExecption("A006");
		}else if(!dislist.getESn().equals(sn)){
			throw new MyExecption("A005");
		}
		DispatchResult disResult=isystem_service.findResultById(id);
		if(disResult!=null&&!disResult.getCheckNext().equals(sn)&&disResult.getCheckStatus()!=4){
			throw new MyExecption("A005");
		}
		String sql="update hzy.dispatch_detail set flag=0 where ds_id=?";
//		DispatchList list=idispatch_list.get(id);
//		list.setFlag(false);
		idispatch_detail.createSQLQuery(sql, id).executeUpdate();
		return rs;
	}

	public LoginUser login_user(String sn) {
		// TODO Auto-generated method stub
		return null;
	}

	/*public long countSqlResult(final String sql, final Object... values) {
		
			*  *         
				String fromSql=sql;
									 * /
									* /
									* select子句与order by子句会影响count查询
									 * ,* 进行简单的排除
									 * . * fromSql ="from "+ StringUtils . substringAfter ( fromSql,"from");
											 fromSql =StringUtils . substringBefore (fromSql , "order by" );
											 String countSql ="select count(*) "+fromSql ;
										
		String countSql = "select count(1) from (" + sql + ")";
		Long count = 0L;
		try {
			count = ((Number) createSQLQuery(countSql, values).uniqueResult())
					.longValue();
		} catch (Exception e) {
			throw new RuntimeException("sql can't be auto count, hql is:"
					+ countSql, e);
		}
		return count;
	}*/
}
