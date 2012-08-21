/**
 * 
 */
package org.tender.services.impl;

import java.util.Date;
import java.util.Map;

import org.han.constant.AppConstant;
import org.han.dao.IDispatchListDao;
import org.han.dao.IDispatchResultDao;
import org.han.dao.ILoginUserDao;
import org.han.dao.ISysEmployeeDao;
import org.han.entity.DispatchResult;
import org.han.entity.SysEmployee;
import org.han.exception.MyException;
import org.han.utils.StringUtil;
import org.han.vo.BaseVo;
import org.han.vo.Page;
import org.han.vo.Result;
import org.han.vo.UserVo;
import org.hibernate.SQLQuery;
import org.tender.services.IManagerService;
import org.tender.services.ISysService;

/**
 * @author tender  
 * @date 2012-8-18   
 * @ClassName: ManagerServiceImpl    
 * @Description: TODO   
 * @version    
 *  
 */
public class ManagerServiceImpl implements IManagerService {
	public BaseVo vo=new BaseVo();
	private ISysService iss;
	private ISysEmployeeDao empdao;
	private IDispatchListDao listdao;
	private ILoginUserDao userdao;
	private IDispatchResultDao resultdao;
	public IDispatchResultDao getResultdao() {
		return resultdao;
	}
	public void setResultdao(IDispatchResultDao resultdao) {
		this.resultdao = resultdao;
	}
	public ILoginUserDao getUserdao() {
		return userdao;
	}
	public void setUserdao(ILoginUserDao userdao) {
		this.userdao = userdao;
	}
	public IDispatchListDao getListdao() {
		return listdao;
	}
	public void setListdao(IDispatchListDao listdao) {
		this.listdao = listdao;
	}
	public ISysEmployeeDao getEmpdao() {
		return empdao;
	}
	public void setEmpdao(ISysEmployeeDao empdao) {
		this.empdao = empdao;
	}
	public ISysService getIss() {
		return iss;
	}
	public void setIss(ISysService iss) {
		this.iss = iss;
	}
	/* (non-Javadoc)
	 * @see org.tender.services.IManagerService#Approval(java.lang.Long, java.lang.String)
	 */
	@Override
	public Result Approval(Long listId, String empNo,Long checkStatus) throws MyException {
		// TODO Auto-generated method stub
		if(StringUtil.isEmpty(listId)||StringUtil.isEmpty(empNo))
		{
			throw new MyException("A003");
		}
		
		String sql = "select t1.m,t2.check_sn c_sn,(select manage_sn from hzy.SYS_DEPARTMENT where d_id=3) f_sn,(select e_sn from hzy.SYS_EMPLOYEE where p_id=1) z_sn from (select sum(money) m,sheet_id from hzy.DISPATCH_DETAIL where flag=1 " +
		"group by sheet_id) t1 right join"+ 
				"(select * from hzy.DISPATCH_RESULT dr where check_next=? and check_status=1 and  dr.check_time="+ 
				"(select max(check_time) from hzy.DISPATCH_RESULT where sheet_id=? and sheet_id=dr.sheet_id)) t2  on t1.sheet_id=t2.sheet_id";
		Map data = listdao.findUniqueBySQL(sql, empNo,listId);
		if (null == data || data.size() != 4) 
		{
			throw new MyException("A007");
		}
		String[] ret = null;
		if (1 == checkStatus) 
		{// 同意
			if (Double.valueOf(data.get("M") + "") > 5000) 
			{
				ret = new String[] { data.get("Z_SN") + "", "1" };
			} 
			else {ret = new String[] { data.get("F_SN") + "", "2" };
			}
			} 
		else if (checkStatus == 2) 
			{// 打回
				ret = new String[] { data.get("C_SN") + "", "4" };
			}
		else if (checkStatus == 3) 
			{// 终止
				ret = new String[] { null, "3" };
			}
		DispatchResult dResult = new DispatchResult();
		dResult.setSheetId(listId);
		dResult.setCheckSn(empNo);
		dResult.setCheckNext(ret[0]);
		dResult.setCheckStatus(Long.valueOf(ret[1]));
		dResult.setCheckTime(new Date());
		dResult.setCheckComment("sss");
		resultdao.save(dResult);
		return new Result(true, AppConstant.A000);
			
	}
	/* (non-Javadoc)
	 * @see org.tender.services.IManagerService#findDepartmentList(java.lang.String)
	 */
	@Override
	public Page findDepartmentList(String empNo,BaseVo vo) throws MyException  {
		// TODO Auto-generated method stub
		if(StringUtil.isEmpty(empNo))
		{
			throw new MyException("A003");
		}
		SysEmployee emp=iss.findEmpByEmpNo(empNo);
//		String sqlfindemp="select * from hzy.dispatch_list a left join hzy.sys_employee b on a.e_sn=b.e_sn where b.department_id=?";
//		Page page=empdao.findPageBySQL(vo, sqlfindemp, emp.getDepartmentId());
//		String sqlfindlist="select t1.* from (select  dl.* from hzy.SYS_EMPLOYEE e right join hzy.DISPATCH_LIST dl on e.e_sn=dl.e_sn  where dl.flag=1 and e.department_id="
//				+ "(select department_id from hzy.SYS_EMPLOYEE where e_sn=? and p_id=2)) t1 where t1.dl_id "
//						+ " in (select sheet_id from hzy.DISPATCH_RESULT group by sheet_id)";
		String sql="select t3.*,t4.money from (select sum(dd.money) money,dd.sheet_id from hzy.dispatch_detail dd group by dd.sheet_id) t4"+ 
				" right join (select dl.dl_id,dl.e_sn,e.e_name,dl.create_time,t2.check_status from hzy.dispatch_list dl "+ 
				" right join  (select * from hzy.dispatch_result dr where dr.check_time=("+ 
				" select max(check_time) from hzy.dispatch_result where sheet_id=dr.sheet_id)) t2 "+ 
				" on dl.dl_id=t2.sheet_id left join hzy.SYS_EMPLOYEE e on dl.e_sn=e.e_sn "+ 
				" where e.department_id=(select department_id from hzy.SYS_EMPLOYEE where e_sn=?  and p_id=2)  and dl.flag=1)t3 "+ 
				" on t4.sheet_id=t3.dl_id ";
		Page listPage=listdao.findPageBySQL(vo, sql, empNo);
		if(StringUtil.isEmpty(listPage))
		{
			throw new MyException("A003");
			
		}
		return listPage;
	}
	/* (non-Javadoc)
	 * @see org.tender.services.IManagerService#findWaitMe(java.lang.String)
	 */
	@Override
	public Page findWaitMe(String empNo,BaseVo vo) throws MyException  {
		// TODO Auto-generated method stub
		if(StringUtil.isEmpty(empNo))
		{
			throw new MyException("A003");
		}
		
//		String sql="select t1.* from (select  dl.* from hzy.SYS_EMPLOYEE e right join hzy.DISPATCH_LIST dl on e.e_sn=dl.e_sn  where dl.flag=1 and e.department_id="
//				+ "(select department_id from hzy.SYS_EMPLOYEE where e_sn=? and p_id=2)) t1 where t1.dl_id "
//				+ " in (select sheet_id from hzy.DISPATCH_RESULT cc where cc.check_next=?)";
		String sql="select t3.*,t4.money from (select sum(dd.money) money,dd.sheet_id from hzy.dispatch_detail dd group by dd.sheet_id) t4"+ 
				" right join (select dl.dl_id,dl.e_sn,e.e_name,dl.create_time,t2.check_status from hzy.dispatch_list dl "+ 
				" right join (select * from hzy.dispatch_result dr where dr.check_next=(select e_sn from hzy.SYS_EMPLOYEE where e_sn=? and p_id=2) and dr.check_status=1 and dr.check_time=("+ 
				" select max(check_time) from hzy.dispatch_result where sheet_id=dr.sheet_id)) t2 "+ " on dl.dl_id=t2.sheet_id left join hzy.SYS_EMPLOYEE e on dl.e_sn=e.e_sn  where dl.flag=1)t3 "+ 
				" on t4.sheet_id=t3.dl_id  ";
		Page listPage=listdao.findPageBySQL(vo, sql, empNo);
		if(StringUtil.isEmpty(listPage))
		{
			throw new MyException("A003");
			
		}
		return listPage;
	}
	
	/* (non-Javadoc)
	 * @see org.tender.services.IManagerService#resetPwd(java.lang.String, java.lang.String)
	 */
	@Override
	public Result resetPwd(String managerNo, UserVo uvo) throws MyException {
		// TODO Auto-generated method stub
		if(StringUtil.isEmpty(managerNo)||StringUtil.isEmpty(vo))
		{
			throw new MyException("A003");
		}
		String sql=" select *  from hzy.sys_employee m where m.e_sn =? and m.department_id="
				+"(select emp.department_id  from hzy.sys_employee emp where emp.e_sn =? and emp.p_id=3)";
		SysEmployee emp=(SysEmployee)empdao.createSQLQuery(sql, managerNo,uvo.geteSn()).addEntity(SysEmployee.class).uniqueResult();
		if(StringUtil.isEmpty(emp))
		{
			throw new MyException("A004");
		}
		
		SQLQuery ss=userdao.createSQLQuery("update hzy.login_user set u_pwd=? where e_sn=?",uvo.getUpwd(),uvo.geteSn());
		ss.executeUpdate();
		System.out.println(ss);
		System.out.println("this is resetPwd!");
		return new Result(true,  AppConstant.A000);
	}	
}
