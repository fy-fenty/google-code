package org.fy.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.fy.constant.AppConstant;
import org.fy.constant.MyConstant;
import org.fy.dao.IDispatchDetailDao;
import org.fy.dao.IDispatchListDao;
import org.fy.dao.IDispatchResultDao;
import org.fy.dao.ILoginUserDao;
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
import org.fy.utils.AppUtils;
import org.fy.utils.SessionListener;
import org.fy.vo.BaseVO;
import org.fy.vo.DispatchDetailVO;
import org.fy.vo.DispatchListVO;
import org.fy.vo.Page;
import org.fy.vo.Result;
import org.fy.vo.UserVO;
import org.hibernate.SQLQuery;
/**
 * @author hzy
 * @date 2012-8-15
 * @extends Object
 * @class SysEmployeeService
 * @description  员工业务接口实现类
 */
public class SysEmployeeService implements ISysEmployeeService {

	private ISysEmployeeDao isys_employeeDao;
	private ISystemService isystem_service;	
	private IDispatchListDao idispatch_list;
	private IDispatchDetailDao idispatch_detail;
	private IDispatchResultDao idispatch_result;
	private ISysDepartmentDao isys_department;
	private ILoginUserDao ilog_userDao;
	
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
	public ILoginUserDao getIlog_userDao() {
		return ilog_userDao;
	}
	public void setIlog_userDao(ILoginUserDao ilog_userDao) {
		this.ilog_userDao = ilog_userDao;
	}

	public Result saveDispathList(String sn,DispatchList dlist){
		Result rs=null;
		if(dlist==null||MyMatcher.isEmpty(sn)){
			return new Result(false,AppConstant.PARAM_ERROR,"A002");
//			throw new MyExecption("A002");
		}
		SysPositions sys_postion=isystem_service.findPositionBySn(sn);
		if(sys_postion==null||MyMatcher.isEmpty(sys_postion.getPId())){
			return new Result(false,AppConstant.PARAM_ERROR,"A002");
//			throw new MyExecption("A002");
		}
		if(sys_postion.getPId()!=3){
			return new Result(false,AppConstant.SYS_ERROR,"A005");
//			throw new MyExecption("A005");
		}
		dlist.setESn(sn);
		dlist.setFlag(true);
		idispatch_list.save(dlist);
		rs=new Result(true,AppConstant.DEFAULT_ERROR,"A001");
		return rs;
	}

	public Result saveDispathDetail(String sn,DispatchDetail detail) {
		Result rs=null;
		if(detail==null||sn==null||MyMatcher.isEmpty(detail.getSheetId())){
			rs=new Result(false,AppConstant.PARAM_ERROR,"A002");
//			throw new MyExecption("A002");
		}
		rs=checkingDispatch(rs,sn, detail.getSheetId());
		if(rs!=null){
			return rs;
		}
		detail.setFlag(true);
		idispatch_detail.save(detail);
		rs=new Result(true,AppConstant.DEFAULT_ERROR,"A001");
		return rs;
	}

	public Result updateDispathList(DispatchListVO dlistvo){
		Result rs=null;
		if(dlistvo==null||MyMatcher.isEmpty(dlistvo.getDlId())||MyMatcher.isEmpty(dlistvo.getSn())){
			rs=new Result(false, AppConstant.PARAM_ERROR, "A002");
//			throw new MyExecption("A002");
		}
		rs=checkingDispatch(rs, dlistvo.getSn(), dlistvo.getDlId());
		if(rs!=null){
			return rs;
		}
		DispatchList dl=idispatch_list.get(dlistvo.getDlId());
		if(dl==null){
			return new Result(false,AppConstant.NULL_ERROR,"A003");
		}
		dl.setEventExplain(dlistvo.getEventExplain());
		rs=new Result(true,AppConstant.DEFAULT_ERROR,"A001");
		return rs;
	}

	public Result updateDispathDetail(DispatchDetailVO detailvo){
		Result rs=null;
		if(detailvo==null||MyMatcher.isEmpty(detailvo.getSn())||MyMatcher.isEmpty(detailvo.getSheetId())||MyMatcher.isEmpty(detailvo.getDsId())){
			return new Result(false, AppConstant.PARAM_ERROR, "A002");
//			throw new MyExecption("A002");
		}
		DispatchDetail dis_detail=idispatch_detail.get(detailvo.getDsId());
		if(dis_detail==null){
			return new Result(false,AppConstant.NULL_ERROR,"A003");
		}else if(!dis_detail.getSheetId().equals(detailvo.getSheetId())){
			return new Result(false, AppConstant.PARAM_ERROR, "A002");
		}else if(dis_detail.getFlag()==false){
			return new Result(false,AppConstant.FLAG_ERROR,"A006");
		}
		rs=checkingDispatch(rs,detailvo.getSn(),dis_detail.getSheetId());
		if(rs!=null){
			return rs;
		}
		dis_detail.setCostExplain(detailvo.getCostExplain());
		dis_detail.setItemId(detailvo.getItemId());
		dis_detail.setMoney(detailvo.getMoney());
		dis_detail.setAccessory(detailvo.getAccessory());
		rs=new Result(true,AppConstant.DEFAULT_ERROR,"A001");
		return rs;
	}

	public Result commitDispathList(DispatchListVO dlistvo){
		Result rs=null;
		if(MyMatcher.isEmpty(dlistvo.getSn())||dlistvo==null||MyMatcher.isEmpty(dlistvo.getDlId())){
			return new Result(false, AppConstant.PARAM_ERROR, "A002");
//			throw new MyExecption("A002");
		}
		SysPositions sys_position=isystem_service.findPositionBySn(dlistvo.getSn());
		if(sys_position==null){
			return new Result(false,AppConstant.NULL_ERROR,"A003");
//			throw new MyExecption("A003");
		}
		if(sys_position.getPId()!=3){
			return new Result(false,AppConstant.SYS_ERROR,"A005");
//			throw new MyExecption("A005");
		}
		rs=checkingDispatch(rs, dlistvo.getSn(),dlistvo.getDlId());
		if(rs!=null){
			return rs;
		}
		List list=isystem_service.findDetailById(dlistvo.getDlId());
		if(list==null||list.size()==0){
			return new Result(false,AppConstant.LIST_ERROR,"A005");
//			throw new MyExecption("A010");
		}	
		DispatchResult dis_result=new DispatchResult();
		dis_result.setSheetId(dlistvo.getDlId());
		dis_result.setCheckSn(dlistvo.getSn());
		dis_result.setCheckTime(new Date());
		dis_result.setCheckStatus(1L);
		
		String sql="select * from hzy.sys_department where d_id=(select department_id from hzy.sys_employee where e_sn=?)";
		SQLQuery sqlQuery=isys_department.createSQLQuery(sql,dlistvo.getSn()).addEntity(SysDepartment.class);
//		Map map=isys_department.findUniqueBySQL(sql, sn);
		SysDepartment sys_depart=(SysDepartment) sqlQuery.uniqueResult();
		if(sys_depart==null||MyMatcher.isEmpty(sys_depart.getManageSn())){
			return new Result(false,AppConstant.NULL_ERROR,"A003");
//			throw new MyExecption("A003");
		}
		dis_result.setCheckNext(sys_depart.getManageSn());
//		System.out.println(map.get("MANAGE_SN"));
		idispatch_result.save(dis_result);
		rs=new Result(true,AppConstant.DEFAULT_ERROR,"A001");
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
				+ " on dl.dl_id=t2.sheet_id where dl.e_sn=? and dl.flag=1)t3 on t4.sheet_id=t3.dl_id order by create_time desc";
		return isys_employeeDao.findPageBySQL(bv, sql, sn);
	}
	
	public Result deleteDispathList(String sn,Long id){
		Result rs=null;
		if(MyMatcher.isEmpty(id)||MyMatcher.isEmpty(sn)){
			return new Result(false, AppConstant.PARAM_ERROR, "A002");
		}
		rs=checkingDispatch(rs, sn,id);
		if(rs!=null){
			return rs;
		}
	/*	List list=isystem_service.findDetailById(id);
		if(list==null||list.size()==0){
			throw new MyExecption("A010");
		}*/
		String sql="update hzy.dispatch_detail set flag=0 where sheet_id=?";
		int u=idispatch_detail.createSQLQuery(sql, id).executeUpdate();
		if(u<1){
			return new Result(false,AppConstant.UPDATE_ERROR,"A009");
		}
		String sql1="update hzy.dispatch_list set flag=0 where dl_id=?";
		int u2=idispatch_list.createSQLQuery(sql1, id).executeUpdate();
		if(u2!=1){
			return new Result(false,AppConstant.UPDATE_ERROR,"A009");
		}
		rs=new Result(true,AppConstant.DEFAULT_ERROR,"A001");
		return rs;
	}

	public Result deleteDispathDetail(DispatchDetailVO detailvo){
		Result rs=null;
		if(detailvo==null||MyMatcher.isEmpty(detailvo.getDsId())||MyMatcher.isEmpty(detailvo.getSheetId())||MyMatcher.isEmpty(detailvo.getSn())){
			return new Result(false, AppConstant.PARAM_ERROR, "A002");
		}
		DispatchDetail dis_detail=idispatch_detail.get(detailvo.getDsId());
		if(dis_detail==null){
			return new Result(false,AppConstant.NULL_ERROR,"A003");
		}else if(!dis_detail.getSheetId().equals(detailvo.getSheetId())){
			return new Result(false, AppConstant.PARAM_ERROR, "A002");
		}else if(dis_detail.getFlag()==false){
			return new Result(false,AppConstant.FLAG_ERROR,"A006");
		}
		rs=checkingDispatch(rs, detailvo.getSn(),detailvo.getSheetId());
		if(rs!=null){
			return rs;
		}
		String sql="update hzy.dispatch_detail set flag=0 where ds_id=?";
//		DispatchDetail dis_detail=idispatch_detail.get(detailvo.getDsId());
//		list.setFlag(false);
		if(MyMatcher.isEmpty(detailvo.getDsId())){
			return new Result(false, AppConstant.PARAM_ERROR, "A002");
		}
		int u=idispatch_detail.createSQLQuery(sql, detailvo.getDsId()).executeUpdate();
		System.out.println(u);
		if(u!=1){
			return new Result(false,AppConstant.UPDATE_ERROR,"A009");
		}
		rs=new Result(true,AppConstant.DEFAULT_ERROR,"A001");
		return rs;
	}

	public LoginUser login_user(UserVO uvo) throws MyExecption, NoSuchAlgorithmException {
		if(uvo==null||MyMatcher.isEmpty(uvo.getEsn())){
			throw new MyExecption("A002");
		}
		LoginUser lu=(LoginUser) ilog_userDao.createQuery("From LoginUser lu where lu.ESn=?", uvo.getEsn());
//		
//		if(lu==null){
//			throw new MyExecption("A003");
//		}
/*		if(!lu.getUPwd().equals(AppUtils.encodeByMD5(uvo.getPwd()))){
			throw new MyExecption("A012");
		}*/
		if(uvo.getPwd().equals("123")){
			return lu;
		}
		return lu;
	}
	
	private Result checkingDispatch(Result rs,String sn,Long Dispatch_id){
		SysPositions sys_postion=isystem_service.findPositionBySn(sn);
		if(sys_postion==null||MyMatcher.isEmpty(sys_postion.getPId())){
			return new Result(false,AppConstant.PARAM_ERROR,"A002");
		}
		if(sys_postion.getPId()!=3){
			return new Result(false,AppConstant.SYS_ERROR,"A005");
		}
		DispatchList dislist=isystem_service.findByDlistId(Dispatch_id);
		if(dislist==null){
			return new Result(false,AppConstant.NULL_ERROR,"A003");
//			throw new MyExecption("A003");
		}else if(dislist.getFlag()==null||dislist.getFlag()==false){
			return new Result(false,AppConstant.FLAG_ERROR,"A006");
//			throw new MyExecption("A006");
		}else if(!dislist.getESn().equals(sn)){
			return new Result(false,AppConstant.SYS_ERROR,"A005");
//			throw new MyExecption("A005");
		}
		DispatchResult disResult=isystem_service.findResultById(dislist.getDlId());
		if(disResult!=null&&!disResult.getCheckNext().equals(sn)&&disResult.getCheckStatus()!=4){
			return new Result(false,AppConstant.SYS_ERROR,"A005");
//			throw new MyExecption("A005");
		}
		return rs;
	}
}
