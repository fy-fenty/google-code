package org.fy.service.impl;

import java.util.Date;
import java.util.Map;

import org.fy.constant.AppConstant;
import org.fy.dao.IDispatchDetailDao;
import org.fy.dao.IDispatchListDao;
import org.fy.dao.IDispatchResultDao;
import org.fy.entity.DispatchList;
import org.fy.entity.DispatchResult;
import org.fy.entity.LoginUser;
import org.fy.exception.MyExecption;
import org.fy.service.ISysEmployeeService;
import org.fy.service.ISysManagerService;
import org.fy.util.MyMatcher;
import org.fy.vo.BaseVO;
import org.fy.vo.DispatchListVO;
import org.fy.vo.DispatchResultVO;
import org.fy.vo.Page;
import org.fy.vo.Result;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

/**
 * @author hzy
 * @date 2012-8-18
 *	@extends Object
 * @class SysManagerService
 * @description 总经理业务接口实现类
 */
public class SysManagerService implements ISysManagerService{
	private IDispatchResultDao idispatch_result;
	private IDispatchListDao idispatch_list;
	private IDispatchDetailDao idispatch_detail;
	
	public IDispatchResultDao getIdispatch_result() {
		return idispatch_result;
	}
	public void setIdispatch_result(IDispatchResultDao idispatch_result) {
		this.idispatch_result = idispatch_result;
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
	
	public Result checkDispatchResult(DispatchResultVO drsvo) {
		if(drsvo==null||MyMatcher.isEmpty(drsvo.getEsn())||MyMatcher.isEmpty(drsvo.getSheetId())
				||MyMatcher.isEmpty(drsvo.getStatus())){
			return new Result(false,AppConstant.PARAM_ERROR,"A002");
		}
		String sql="select check_sn,check_next,(select e_sn from hzy.sys_employee where department_id=3 and p_id=2) as cai"
					+" from (select * from hzy.dispatch_result dr where check_next=("
					+"select e_sn from hzy.sys_employee where e_sn=? and p_id=1) and check_time="
					+"(select max(check_time) from hzy.dispatch_result where sheet_id=(select dl_id from hzy.dispatch_list where dl_id=? and flag=1))"
					+" and check_status=1)";
		SQLQuery sqlQuery=idispatch_result.createSQLQuery(sql,drsvo.getEsn(),drsvo.getSheetId());
//			SQLQuery sqlQuery=idispatch_result.createSQLQuery(sql,
//				drsvo.getEsn(),drsvo.getSheetId()).addScalar("check_sn",Hibernate.STRING).addScalar("cai",Hibernate.STRING);
			sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			Map map=(Map)sqlQuery.uniqueResult();
			//Map map=idispatch_result.findUniqueBySQL(sql, drsvo.getEsn(),drsvo.getSheetId());
			System.out.println(map);
			if(map==null||map.size()==0||MyMatcher.isEmpty(map.get("check_sn"))||MyMatcher.isEmpty(map.get("cai"))){
				return new Result(false,AppConstant.NULL_ERROR,"A003");
			}
			System.out.println(map.get("check_sn"));
			System.out.println(map.get("cai"));
			
			DispatchResult dis_result=new DispatchResult();
			dis_result.setSheetId(drsvo.getSheetId());
			dis_result.setCheckSn(drsvo.getEsn());
			//dis_result.setCheckNext(checkNext);
			dis_result.setCheckComment(drsvo.getCheckComment());
			
			dis_result.setCheckTime(new Date());
			if(drsvo.getStatus()==1){
				dis_result.setCheckStatus(2L);
				dis_result.setCheckNext(map.get("cai").toString());
				idispatch_result.save(dis_result);	
			}else if(drsvo.getStatus()==2){
				dis_result.setCheckStatus(4L);
				dis_result.setCheckNext(map.get("check_sn").toString());
				idispatch_result.save(dis_result);
			}else if(drsvo.getStatus()==3){
				dis_result.setCheckStatus(3L);
				dis_result.setCheckNext(null);
				idispatch_result.save(dis_result);
				
				DispatchList dlist=idispatch_list.get(dis_result.getSheetId());
				if(dlist==null||MyMatcher.isEmpty(dlist.getESn())){
					return new Result(false,AppConstant.NULL_ERROR,"A003");
				}
				
				String sql2="update hzy.dispatch_detail set flag=0 where sheet_id=?";
				int u=idispatch_detail.createSQLQuery(sql2, dis_result.getSheetId()).executeUpdate();
				System.out.println(u);
				if(u<1){
					return new Result(false,AppConstant.UPDATE_ERROR,"A009");
				}
				String sql3="update hzy.dispatch_list set flag=0 where dl_id=?";
				int u2=idispatch_list.createSQLQuery(sql3, dis_result.getSheetId()).executeUpdate();
				if(u2!=1){
					return new Result(false,AppConstant.UPDATE_ERROR,"A009");
				}
				
			}else{
				return new Result(false,AppConstant.PARAM_ERROR,"A002");
			}
			return new Result(true,AppConstant.DEFAULT_ERROR,"A001");
	}

	public Page findWaitCheckDisList(BaseVO bv, String sn) throws MyExecption {
		if(bv==null||MyMatcher.isEmpty(sn)){
			throw new MyExecption("A002");
		}
		String sql="select  dl_id,e_sn,create_time,check_status,t4.dmoney from (select sum(dd.money) dmoney,dd.sheet_id from dispatch_detail dd group by dd.sheet_id) t4"
					+" right join"
					+"(select * from dispatch_list dl right join "
					+"(select * from dispatch_result dr where dr.check_time=("
					+"select max(check_time) from dispatch_result where sheet_id=dr.sheet_id) and check_next=("
					+"select e_sn from hzy.sys_employee where e_sn=? and p_id=1) and check_status=1) t2" 
					+" on dl.dl_id=t2.sheet_id)"
					+"t3 on t4.sheet_id=t3.dl_id";
		return idispatch_list.findPageBySQL(bv, sql, sn);
	}

	public LoginUser loginUser(String sn) {
		// TODO Auto-generated method stub
		return null;
	}

	public Result stopAlreadyCheck(DispatchListVO dlistvo) {
		if(dlistvo==null||MyMatcher.isEmpty(dlistvo.getDlId())||MyMatcher.isEmpty(dlistvo.getSn())){
			return new Result(false,AppConstant.PARAM_ERROR,"A002");
		}
		String sql="update hzy.dispatch_result set check_status=3 where sheet_id=? and check_sn=("
					+"select e_sn from hzy.sys_employee where e_sn=? and p_id=2"
					+") and check_status in (1,2)";
		SQLQuery sqlQuery=idispatch_result.createSQLQuery(sql, dlistvo.getDlId(),dlistvo.getSn());
		int u=sqlQuery.executeUpdate();
		if(u!=1){
			return new Result(false,AppConstant.UPDATE_ERROR,"A009");
		}
		return new Result(true,AppConstant.DEFAULT_ERROR,"A001");
	}

}
