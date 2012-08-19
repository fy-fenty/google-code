package org.fy.service.impl;

import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.fy.constant.AppConstant;
import org.fy.dao.IDispatchListDao;
import org.fy.dao.IDispatchResultDao;
import org.fy.dao.ILoginUserDao;
import org.fy.entity.DispatchList;
import org.fy.entity.DispatchResult;
import org.fy.entity.LoginUser;
import org.fy.entity.SysEmployee;
import org.fy.exception.MyExecption;
import org.fy.service.ISysDepartManagerService;
import org.fy.service.ISystemService;
import org.fy.util.MD5;
import org.fy.util.MyMatcher;
import org.fy.vo.BaseVO;
import org.fy.vo.DispatchResultVO;
import org.fy.vo.Page;
import org.fy.vo.Result;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

/**
 * @author hzy
 * @date 2012-8-17
 *	@extends Object
 * @class SysDepartManagerService
 * @description 部门经理业务接口实现类
 */
@SuppressWarnings("unchecked")
public class SysDepartManagerService implements ISysDepartManagerService{
	private IDispatchResultDao idispatch_result;
	private IDispatchListDao idispatch_list;
	private ILoginUserDao ilog_user;
	
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

	public ILoginUserDao getIlog_user() {
		return ilog_user;
	}

	public void setIlog_user(ILoginUserDao ilog_user) {
		this.ilog_user = ilog_user;
	}
	public Result checkDispatchResult(DispatchResultVO drsvo) {
		if(drsvo==null||MyMatcher.isEmpty(drsvo.getEsn())||MyMatcher.isEmpty(drsvo.getSheetId())
				||MyMatcher.isEmpty(drsvo.getStatus())){
			return new Result(false,AppConstant.PARAM_ERROR,"A002");
		}
		String sql="select check_sn,check_next,(select e_sn from hzy.sys_employee where p_id=1) as ma,"
					+"(select e_sn from hzy.sys_employee where department_id=3 and p_id=2) as cai,de.sm zm"
					+" from (select * from hzy.dispatch_result dr where check_next=("
					+"select e_sn from hzy.sys_employee where e_sn=? and p_id=2) and check_time="
					+"(select max(check_time) from hzy.dispatch_result where sheet_id=(select dl_id from hzy.dispatch_list where dl_id=? and flag=1))"
					+"and check_status=1) ddr left join (select sum(money) sm,sheet_id from hzy.dispatch_detail where flag=1 group by sheet_id) de"
					+" on ddr.sheet_id=de.sheet_id";
		SQLQuery sqlQuery=idispatch_result.createSQLQuery(sql,
							drsvo.getEsn(),drsvo.getSheetId()).addScalar("check_sn",Hibernate.STRING).addScalar("check_next",Hibernate.STRING).addScalar("ma",Hibernate.STRING)
							.addScalar("cai",Hibernate.STRING).addScalar("zm");
		sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Map map=(Map)sqlQuery.uniqueResult();
//		Map map=idispatch_result.findUniqueBySQL(sql, drsvo.getEsn(),drsvo.getSheetId());
		
		if(map==null||map.size()==0||MyMatcher.isEmpty(map.get("check_sn"))||MyMatcher.isEmpty("ma")
				||MyMatcher.isEmpty(map.get("cai"))||MyMatcher.isEmpty(map.get("zm"))){
			return new Result(false,AppConstant.NULL_ERROR,"A003");
		}
/*		System.out.println(map.get("check_sn"));
		System.out.println(map.get("ma"));
		System.out.println(map.get("cai"));
		System.out.println(map.get("zm"));*/	
		DispatchResult dis_result=new DispatchResult();
		dis_result.setSheetId(drsvo.getSheetId());
		dis_result.setCheckSn(drsvo.getEsn());
//		dis_result.setCheckNext(checkNext);
		dis_result.setCheckComment(drsvo.getCheckComment());
		
		dis_result.setCheckTime(new Date());
		if(drsvo.getStatus()==1){
			if(Double.valueOf(map.get("zm").toString())>5000){
				dis_result.setCheckStatus(1L);
				dis_result.setCheckNext(map.get("ma").toString());
			}else{
				dis_result.setCheckStatus(2L);
				dis_result.setCheckNext(map.get("cai").toString());
			}
			idispatch_result.save(dis_result);
		}else if(drsvo.getStatus()==2){
			dis_result.setCheckStatus(4L);
			dis_result.setCheckNext(map.get("check_sn").toString());
			idispatch_result.save(dis_result);
		}else if(drsvo.getStatus()==3){
			dis_result.setCheckStatus(3L);
			dis_result.setCheckNext(null);
			idispatch_result.save(dis_result);
	/*		DispatchList dlist=idispatch_list.get(dis_result.getSheetId());
			if(dlist==null){
				return new Result(false,AppConstant.NULL_ERROR,"A003");
			}
			dlist.setFlag(false);*/
		}else{
			return new Result(false,AppConstant.PARAM_ERROR,"A002");
		}
		return new Result(true,AppConstant.DEFAULT_ERROR,"A001");
	}

	public Result pwdReset(String dsn,String esn) {
		if(MyMatcher.isEmpty(dsn)||MyMatcher.isEmpty(esn)){
			return new Result(false,AppConstant.PARAM_ERROR,"A002");
		}
		Random rd=new Random(10);
		Integer id=rd.nextInt();
		System.out.println(id);
		String pwd=MD5.getMD5(id+"");
		System.out.println(pwd);
		
		String sql="update hzy.login_user set u_pwd=? where e_sn=("
					+"select e_sn from hzy.sys_employee where e_sn=? and p_id=3 and department_id=("
					+"select d_id from hzy.sys_department where d_id=("
					+"select department_id from hzy.sys_employee where e_sn=? and p_id=2)))";
		int u=ilog_user.createSQLQuery(sql, pwd,esn,dsn).executeUpdate();
		if(u!=1){
			return new Result(false,AppConstant.UPDATE_ERROR,"A009");
		}
		return new Result(true,AppConstant.DEFAULT_ERROR,"A001");
	}

	public Page findWaitCheckDisList(BaseVO bv, String sn) throws MyExecption {
		if(bv==null||MyMatcher.isEmpty(sn)){
			throw new MyExecption("A002");
		}
		String sql="select  dl_id,e_sn,create_time,check_status,t4.dmoney from (select sum(dd.money) dmoney,dd.sheet_id from dispatch_detail dd group by dd.sheet_id) t4"
					+" right join "
					+"(select * from dispatch_list dl right join "
					+"(select * from dispatch_result dr where dr.check_time=("
					+"select max(check_time) from dispatch_result where sheet_id=dr.sheet_id) and check_next=("
                    +"select e_sn from hzy.sys_employee where e_sn=? and p_id=2) and check_status=1) t2"
                    +" on dl.dl_id=t2.sheet_id)"
                    +"t3 on t4.sheet_id=t3.dl_id";
		return idispatch_list.findPageBySQL(bv, sql, sn);
	}

	public Page findDepartDisList(BaseVO bv, String sn) throws MyExecption {
		if(bv==null||MyMatcher.isEmpty(sn)){
			throw new MyExecption("A002");
		}
		
		String sql="select  dl_id,e_sn,create_time,check_status,t4.dmoney from (select sum(dd.money) dmoney,dd.sheet_id from dispatch_detail dd group by dd.sheet_id) t4"
					+" right join"
					+"(select * from dispatch_list dl right join"
					+"(select * from dispatch_result dr where dr.check_time=("
					+"select max(check_time) from dispatch_result where sheet_id=dr.sheet_id)) t2 "
                    +" on dl.dl_id=t2.sheet_id where dl.e_sn in("
                    +"select e_sn from hzy.sys_employee where department_id in(select d_id from hzy.sys_department where d_id=("
                    +"select department_id from hzy.sys_employee where e_sn=? and p_id=2"
                    +"))) )"
                    +"t3 on t4.sheet_id=t3.dl_id";
		
		return idispatch_list.findPageBySQL(bv, sql, sn);
	}

	public LoginUser loginUser(String sn) {
		// TODO Auto-generated method stub
		return null;
	}

}
