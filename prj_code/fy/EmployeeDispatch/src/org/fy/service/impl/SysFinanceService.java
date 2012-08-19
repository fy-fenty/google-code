package org.fy.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.fy.constant.AppConstant;
import org.fy.dao.IDispatchResultDao;
import org.fy.entity.DispatchResult;
import org.fy.exception.MyExecption;
import org.fy.service.ISysEmployeeService;
import org.fy.service.ISysFinanceService;
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
 * @class SysFinanceService
 * @description 财务业务操作接口实现类
 */
@SuppressWarnings("unchecked")
public class SysFinanceService implements ISysFinanceService{
	private IDispatchResultDao idispatch_result;
	private ISysEmployeeService isys_empservice;
	
	public IDispatchResultDao getIdispatch_result() {
		return idispatch_result;
	}

	public void setIdispatch_result(IDispatchResultDao idispatch_result) {
		this.idispatch_result = idispatch_result;
	}
	public ISysEmployeeService getIsys_empservice() {
		return isys_empservice;
	}

	public void setIsys_empservice(ISysEmployeeService isys_empservice) {
		this.isys_empservice = isys_empservice;
	}

	public Result checkDispatchResult(DispatchResultVO drsvo) {
		if(drsvo==null||MyMatcher.isEmpty(drsvo.getEsn())||MyMatcher.isEmpty(drsvo.getSheetId())
				||MyMatcher.isEmpty(drsvo.getStatus())){
			return new Result(false,AppConstant.PARAM_ERROR,"A002");
		}
		String sql="select check_sn from (select * from hzy.dispatch_result dr where check_next=("
					+"select e_sn from hzy.sys_employee where e_sn=? and p_id=2 and department_id=3) and check_time="
					+"(select max(check_time) from hzy.dispatch_result where sheet_id=(select dl_id from hzy.dispatch_list where dl_id=? and flag=1))"
					+" and check_status=2)";
			SQLQuery sqlQuery=idispatch_result.createSQLQuery(sql,
					drsvo.getEsn(),drsvo.getSheetId()).addScalar("check_sn",Hibernate.STRING);
				sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				Map map=(Map)sqlQuery.uniqueResult();
			//Map map=idispatch_result.findUniqueBySQL(sql, drsvo.getEsn(),drsvo.getSheetId());
			
			if(map==null||map.size()==0||MyMatcher.isEmpty(map.get("check_sn"))){
				return new Result(false,AppConstant.NULL_ERROR,"A003");
			}
			DispatchResult dis_result=new DispatchResult();
			dis_result.setSheetId(drsvo.getSheetId());
			dis_result.setCheckSn(drsvo.getEsn());
			//dis_result.setCheckNext(checkNext);
			dis_result.setCheckComment(drsvo.getCheckComment());
			
			dis_result.setCheckTime(new Date());
			if(drsvo.getStatus()==1){
				dis_result.setCheckStatus(5L);
				dis_result.setCheckNext(null);
				idispatch_result.save(dis_result);
			}else if(drsvo.getStatus()==2){
				dis_result.setCheckStatus(4L);
				dis_result.setCheckNext(map.get("check_sn").toString());
				idispatch_result.save(dis_result);
			}else{
				return new Result(false,AppConstant.PARAM_ERROR,"A002");
			}
			return new Result(true,AppConstant.DEFAULT_ERROR,"A001");
	}

	public Page findDispatchList(BaseVO bv,String sn) throws MyExecption {
		if(bv==null||MyMatcher.isEmpty(sn)){
			throw new MyExecption("A002");
		}
		String sql=" select  dl_id,t3.e_sn,em.e_name,create_time,check_status,t4.dmoney from (select sum(dd.money) dmoney,dd.sheet_id from dispatch_detail dd group by dd.sheet_id) t4"
					+" right join"
					+"(select * from dispatch_list dl right join "
					+"(select * from dispatch_result dr where dr.check_time=("
					+"select max(check_time) from dispatch_result where sheet_id=dr.sheet_id) and check_status in(2,5) and "
					+"(select e_sn from hzy.sys_employee where e_sn=? and p_id=2 and department_id=3) in (check_sn,check_next)"    
					+") t2 on dl.dl_id=t2.sheet_id)"
					+"t3 on t4.sheet_id=t3.dl_id left join (select * from hzy.sys_employee) em on t3.E_SN=em.e_sn";
//		Map map=new HashMap();
//		map.put("sn", sn);
		return idispatch_result.findPageBySQL(bv, sql, sn);
	}

	public void generateTable(String sn) {
		// TODO Auto-generated method stub
		System.out.println("生成报表");
	}

	public Result pay(DispatchListVO dlistvo) {
		if(dlistvo==null||dlistvo.getSn()==null||dlistvo.getDlId()==null){
			return new Result(false,AppConstant.PARAM_ERROR,"A002");
		}
		String sql="update hzy.dispatch_result set check_status=6 where sheet_id=? and"
					+" check_sn=(select e_sn from hzy.sys_employee where e_sn=? and p_id=2 and department_id=3"
					+")and check_status=5";
		SQLQuery sqlQuery=idispatch_result.createSQLQuery(sql, dlistvo.getDlId(),dlistvo.getSn());
		System.out.println(sql);
		int u=sqlQuery.executeUpdate();
		System.out.println(u);
		if(u!=1){
			return new Result(false,AppConstant.UPDATE_ERROR,"A009");
		}
		return new Result(true,AppConstant.DEFAULT_ERROR,"A001");
	}

}
