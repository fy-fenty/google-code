/**
 * 
 */
package org.tender.services.impl;

import java.util.Date;
import java.util.Map;

import org.han.constant.AppConstant;
import org.han.dao.IDispatchListDao;
import org.han.dao.IDispatchResultDao;
import org.han.dao.ISysEmployeeDao;
import org.han.entity.DispatchResult;
import org.han.entity.SysEmployee;
import org.han.exception.MyException;
import org.han.utils.StringUtil;
import org.han.vo.BaseVo;
import org.han.vo.Page;
import org.han.vo.Result;
import org.hibernate.SQLQuery;
import org.springframework.jdbc.object.SqlQuery;
import org.tender.services.IFinancialService;
import org.tender.services.ISysService;

/**
 * @author tender  
 * @date 2012-8-18   
 * @ClassName: FinancialServiceImpl    
 * @Description: TODO   
 * @version    
 *  
 */
public class FinancialServiceImpl implements IFinancialService{
	
	public BaseVo vo=new BaseVo();
	private ISysService iss;
	private IDispatchListDao listdao;
	private IDispatchResultDao resultdao;
	private ISysEmployeeDao empdao;
	

	public ISysService getIss() {
		return iss;
	}
	public void setIss(ISysService iss) {
		this.iss = iss;
	}
	public ISysEmployeeDao getEmpdao() {
		return empdao;
	}
	public void setEmpdao(ISysEmployeeDao empdao) {
		this.empdao = empdao;
	}
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
	/* (non-Javadoc)
	 * @see org.tender.services.IFinancialService#approval(java.lang.Long, java.lang.String)
	 */
	@Override
	public Result approval(Long listId, String empNo,Long checkStatus) throws MyException {
		// TODO Auto-generated method stub
		if(StringUtil.isEmpty(listId)||StringUtil.isEmpty(empNo))
		{
			throw new MyException("A003");
		}
		String sql = "select t1.m,t2.check_sn c_sn,(select manage_sn from hzy.SYS_DEPARTMENT where d_id=3) f_sn," +
				"(select e_sn from hzy.SYS_EMPLOYEE where p_id=1) z_sn from (select sum(money) m,sheet_id from hzy.DISPATCH_DETAIL where flag=1 " +
				"group by sheet_id) t1 right join (select * from hzy.DISPATCH_RESULT dr where check_next=? and check_status=1 and  dr.check_time="+ 
				"(select max(check_time) from hzy.DISPATCH_RESULT where sheet_id=? and sheet_id=dr.sheet_id)) t2  on t1.sheet_id=t2.sheet_id";
		Map data = listdao.findUniqueBySQL(sql, empNo,listId);
		if (null == data || data.size() != 4) 
		{
			throw new MyException("A007");
		}
		String[] ret = null;
		if (1 == checkStatus) 
		{
			// 同意
			ret = new String[] { null, "3" };
		} 
		else if (checkStatus == 2) 
		{
			// 打回
			if (Double.valueOf(data.get("M") + "") > 5000) 
			{
				ret = new String[] { data.get("Z_SN") + "", "1" };
			} 
			else 
			{
				ret = new String[] { data.get("F_SN") + "", "2" };
			
			} 
			
		}
		
		DispatchResult dr=new DispatchResult();
		dr.setSheetId(listId);
		dr.setCheckSn(empNo);
		dr.setCheckNext(ret[0]);
		dr.setCheckStatus(Long.valueOf(ret[1]));
		dr.setCheckTime(new Date());
		dr.setCheckComment("sss");
		resultdao.save(dr);
		return new Result(true, AppConstant.A000);
	}
	/* (non-Javadoc)
	 * @see org.tender.services.IFinancialService#findList(java.lang.String)
	 */
	@Override
	public Page findList(String empNo,BaseVo vo) throws MyException {
		// TODO Auto-generated method stub
		if(StringUtil.isEmpty(empNo))
		{
			throw new MyException("A003");
		}
		SysEmployee emp=iss.findPostByUserId(empNo);
		if(!emp.getDepartmentId().equals(3L))
		{
			throw new MyException("A003");
		}
		String sql="select * from hzy.dispatch_result where check_sn=? and check_status in(5,2)";
		Page page=resultdao.findPageBySQL(vo, sql, empNo);
		if(page.getTotalCount()<1)
		{
			throw new MyException("A003");
		}
		return page;
	}
	/* (non-Javadoc)
	 * @see org.tender.services.IFinancialService#generateReports()
	 */
	@Override
	public Result generateReports() {
		// TODO Auto-generated method stub
		
		return null;
	}
	/* (non-Javadoc)
	 * @see org.tender.services.IFinancialService#pay(java.lang.Long)
	 */
	@Override
	public Result pay(Long listId,String empNo) throws MyException {
		// TODO Auto-generated method stub
		if(StringUtil.isEmpty(listId)||StringUtil.isEmpty(empNo))
		{
			throw new MyException("A003");
		}
		DispatchResult result=iss.findStatusByListId(listId);
		if(!result.getCheckStatus().equals(5))
		{
			throw new MyException("A004");
		}
		String sql="update hzy.dispatch_result set check_status=6 where sheet_id=?";
		SQLQuery query=resultdao.createSQLQuery(sql,listId);
		query.executeUpdate();
		return new Result(true,  AppConstant.A000);
	}

}
