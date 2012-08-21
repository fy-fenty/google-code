/**
 * 
 */
package org.tender.services.impl;

import java.util.Date;
import java.util.Map;

import org.han.constant.AppConstant;
import org.han.dao.IDispatchListDao;
import org.han.dao.IDispatchResultDao;
import org.han.entity.DispatchResult;
import org.han.entity.SysEmployee;
import org.han.exception.MyException;
import org.han.utils.StringUtil;
import org.han.vo.BaseVo;
import org.han.vo.Page;
import org.han.vo.Result;
import org.hibernate.SQLQuery;
import org.tender.services.IGeneralManagerService;
import org.tender.services.ISysService;

/**
 * @author tender  
 * @date 2012-8-18   
 * @ClassName: GeneralManagerServiceImpl    
 * @Description: TODO   
 * @version    
 *  
 */
public class GeneralManagerServiceImpl implements IGeneralManagerService {
	public BaseVo vo=new BaseVo();
	private ISysService iss;
	private IDispatchListDao listdao;
	private IDispatchResultDao resultdao;
	public IDispatchListDao getListdao() {
		return listdao;
	}

	public void setListdao(IDispatchListDao listdao) {
		this.listdao = listdao;
	}

	public IDispatchResultDao getResultdao() {
		return resultdao;
	}

	public void setResultdao(IDispatchResultDao resultdao) {
		this.resultdao = resultdao;
	}

	public ISysService getIss() {
		return iss;
	}

	public void setIss(ISysService iss) {
		this.iss = iss;
	}
	
	/* (non-Javadoc)
	 * @see org.tender.services.IGeneralManagerService#Approval(java.lang.String, java.lang.Long)
	 */
	@Override
	public Result Approval(String empNo, Long listNo,Long checkStatus)throws MyException {
		// TODO Auto-generated method stub
		if(StringUtil.isEmpty(listNo)||StringUtil.isEmpty(empNo))
		{
			throw new MyException("A003");
		}
		
		
		String sql = "select t1.m,t2.check_sn c_sn,(select manage_sn from hzy.SYS_DEPARTMENT where d_id=3) f_sn," +
				"(select e_sn from hzy.SYS_EMPLOYEE where p_id=1) z_sn from (select sum(money) m,sheet_id from hzy.DISPATCH_DETAIL where flag=1 " +
				"group by sheet_id) t1 right join (select * from hzy.DISPATCH_RESULT dr where check_next=? and check_status=1 and  dr.check_time="+ 
				"(select max(check_time) from hzy.DISPATCH_RESULT where sheet_id=? and sheet_id=dr.sheet_id)) t2  on t1.sheet_id=t2.sheet_id";
		Map data = listdao.findUniqueBySQL(sql, empNo,listNo);
		if (null == data || data.size() != 4) 
		{
			throw new MyException("A007");
		}
		String[] ret = null;
		if (1 == checkStatus) 
		{// 同意
			
			ret = new String[] { data.get("F_SN") + "", "2" };
			
		}	
		else if (checkStatus == 2) 
		{
			// 打回
			ret = new String[] { data.get("F_SN") + "", "2" };
		}
		else if (checkStatus == 3) 
		{
			// 终止
				ret = new String[] { null, "3" };
		}
		DispatchResult dResult = new DispatchResult();
		dResult.setSheetId(listNo);
		dResult.setCheckSn(empNo);
		dResult.setCheckNext(ret[0]);
		dResult.setCheckStatus(Long.valueOf(ret[1]));
		dResult.setCheckTime(new Date());
		dResult.setCheckComment("sss");
		resultdao.save(dResult);
		return new Result(true, AppConstant.A000);
		
	}
	/* (non-Javadoc)
	 * @see org.tender.services.IGeneralManagerService#findWaitMe(java.lang.String)
	 */
	@Override
	public Page findWaitMe(String empNo,BaseVo vo)throws MyException {
		// TODO Auto-generated method stub
		if(StringUtil.isEmpty(empNo))
		{
			throw new MyException("A003");
		}
		
//		String sql="select t1.* from (select  dl.* from hzy.SYS_EMPLOYEE e right join hzy.DISPATCH_LIST dl on e.e_sn=dl.e_sn  where dl.flag=1 and e.department_id="
//				+ "(select department_id from hzy.SYS_EMPLOYEE where e_sn=?)) t1 where t1.dl_id "
//				+ " in (select sheet_id from hzy.DISPATCH_RESULT cc where cc.check_next=?)";
		String sql="select t3.*,t4.money from (select sum(dd.money) money,dd.sheet_id from hzy.dispatch_detail dd group by dd.sheet_id) t4"+ 
				" right join (select dl.dl_id,dl.e_sn,e.e_name,dl.create_time,t2.check_status from hzy.dispatch_list dl "+ 
				" right join (select * from hzy.dispatch_result dr where dr.check_next=(select e_sn from hzy.SYS_EMPLOYEE where e_sn=?) and dr.check_status=1 and dr.check_time=("+ 
				" select max(check_time) from hzy.dispatch_result where sheet_id=dr.sheet_id)) t2 "+ 
				" on dl.dl_id=t2.sheet_id left join hzy.SYS_EMPLOYEE e on dl.e_sn=e.e_sn  where dl.flag=1)t3 "+ 
				" on t4.sheet_id=t3.dl_id ";
		Page listPage=listdao.findPageBySQL(vo, sql, empNo);
		if(StringUtil.isEmpty(listPage))
		{
			throw new MyException("A003");
			
		}
		return listPage;
	}
	/* (non-Javadoc)
	 * @see org.tender.services.IGeneralManagerService#terminationList(java.lang.Long)
	 */
	@Override
	public Result terminationList(Long listNo,String empNo) throws MyException {
		// TODO Auto-generated method stub
		if(StringUtil.isEmpty(listNo)||StringUtil.isEmpty(empNo))
		{
			throw new MyException("A003");
		}
		SysEmployee emp=iss.findPostByUserId(empNo);
		if(!emp.getPId().equals(1))
		{
			throw new MyException("A004");
		}
		DispatchResult result=iss.findStatusByListId(listNo);
		if(!result.getCheckStatus().equals(1)||!result.getCheckStatus().equals(2))
		{
			throw new MyException("A004");
		}
		SysEmployee manger=iss.findPostByUserId(result.getCheckSn());
		if(!manger.getPId().equals(2))
		{
			throw new MyException("A004");
		}
		String sql="update hzy.dispatch_result set status=3 where check_next not in(?,'10000001') and sheet_id=?";
		SQLQuery query=resultdao.createSQLQuery(sql, empNo,listNo);
		int i=query.executeUpdate();
		if(i<1)
		{
			throw new MyException("A003");
		}
		return new Result(true, AppConstant.A000);
	}
}
