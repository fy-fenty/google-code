/**
 * 
 */
package org.tender.services.impl;

import java.util.List;
import java.util.Map;

import org.han.constant.AppConstant;
import org.han.dao.IDispatchDetailDao;
import org.han.dao.IDispatchListDao;
import org.han.dao.IDispatchResultDao;
import org.han.dao.IDispatchStatusDao;
import org.han.dao.ISysEmployeeDao;
import org.han.dao.ISysPositionsDao;
import org.han.entity.DispatchDetail;
import org.han.entity.DispatchList;
import org.han.entity.SysEmployee;
import org.han.exception.MyException;
import org.han.utils.StringUtil;
import org.han.vo.BaseVo;
import org.han.vo.DetailVo;
import org.han.vo.DispatchVo;
import org.han.vo.Page;
import org.han.vo.Result;
import org.tender.services.IEmpService;
import org.tender.services.ISysService;



/**
 * @author tender  
 * @date 2012-8-15   
 * @ClassName: EmpServices    
 * @Description: TODO   
 * @version    
 *  
 */
public   class EmpServicesImpl implements IEmpService  {
	public BaseVo vo=new BaseVo();
	private IDispatchListDao idd;
	private ISysService iss;
	public IDispatchDetailDao detaildao;
	public ISysEmployeeDao empdao;
	public ISysPositionsDao positiondao;
	public IDispatchResultDao resultdao;
	public IDispatchStatusDao statusdao;
	
	
	public IDispatchDetailDao getDetaildao() {
		return detaildao;
	}
	public void setDetaildao(IDispatchDetailDao detaildao) {
		this.detaildao = detaildao;
	}
	public ISysEmployeeDao getEmpdao() {
		return empdao;
	}
	public void setEmpdao(ISysEmployeeDao empdao) {
		this.empdao = empdao;
	}
	public ISysPositionsDao getPositiondao() {
		return positiondao;
	}
	public void setPositiondao(ISysPositionsDao positiondao) {
		this.positiondao = positiondao;
	}
	public IDispatchResultDao getResultdao() {
		return resultdao;
	}
	public void setResultdao(IDispatchResultDao resultdao) {
		this.resultdao = resultdao;
	}
	public IDispatchStatusDao getStatusdao() {
		return statusdao;
	}
	public void setStatusdao(IDispatchStatusDao statusdao) {
		this.statusdao = statusdao;
	}
	public ISysService getIss() {
		return iss;
	}
	public void setIss(ISysService iss) {
		this.iss = iss;
	}
	public IDispatchListDao getIdd() {
		return idd;
	}
	public void setIdd(IDispatchListDao idd) {
		this.idd = idd;
	}
	/* (non-Javadoc)
	 * @see org.tender.services.IEmpService#empLogin(java.lang.Long, java.lang.String)
	 */
	@Override
	public Map<String, Object> empLogin(Long uid, String upwd)
			throws MyException {
		// TODO Auto-generated method stub
	
		return null;
	}
	/* (non-Javadoc)
	 * @see org.tender.services.IEmpService#findDispatchByEmpId(java.lang.Long)
	 */
	@Override
	public Page findDispatchByEmpId(String empNo,BaseVo vo) {
		// TODO Auto-generated method stub
		String sql=" select * from hzy.dispatch_list where e_sn=?";
		return this.idd.findPageBySQL(vo, sql, empNo);
	}
	/* (non-Javadoc)
	 * @see org.tender.services.IEmpService#updateDispatch(org.han.entity.DispatchList)
	 */
	@Override
	public Result updateDispatch(DispatchVo dvo,String empNo) throws MyException {
		// TODO Auto-generated method stub
		
		if(StringUtil.isEmpty(dvo)||StringUtil.isEmpty(empNo))
		{
			throw new MyException("A001");
		}
		
		DispatchList map=idd.get(dvo.getListId());
		//||
		if(!map.getESn().equals((String)empNo)||StringUtil.isEmpty(map))
		{
			throw new MyException("A003");
		}
		//验证权限
		if(!iss.chackEmpPermi(dvo.getListId(), empNo))
		{
			throw new MyException("A004");
		}
		if(map.getFlag().equals(0))
		{
			throw new MyException("A004");
		}
		map.setEventExplain(dvo.getEventExplain());
		System.out.println("this is updataDispacth!");
		return new Result(true,  AppConstant.A000);
	}
	/* (non-Javadoc)
	 * @see org.tender.services.IEmpService#commitDispatch(org.han.entity.DispatchList)
	 */

	public Result commitDispatch(DispatchList list,String empNo) throws MyException {
		// TODO Auto-generated method stub
		if(list==null||empNo==null)
		{
			throw new MyException("A003");
		}
		SysEmployee emPosit=iss.findPostByUserId(empNo);
		if(emPosit.getPId()!=3)
		{
			throw new MyException("A004");
		}
		Page detail=iss.findDetailByListId(list.getDlId(), vo);
		if(detail.getTotalCount()<1)
		{
			throw new MyException("A003");
		}
		
		this.idd.save(list);
		return new Result(true,  AppConstant.A000);
	}
	/* (non-Javadoc)
	 * @see org.tender.services.IEmpService#saveDispatch(org.han.entity.DispatchList, java.lang.String)
	 */
	@Override
	public Result saveDispatch(DispatchList list, String empNo)throws MyException  {
		// TODO Auto-generated method stub
		if(list==null||empNo==null)
		{
			throw new MyException("A003");
		}
		SysEmployee emPosit=iss.findPostByUserId(empNo);
		if(emPosit.getPId()!=3)
		{
			throw new MyException("A004");
		}
		this.idd.saveNew(list);
		System.out.println("this is savaDispatch!");
		return new Result(true,  AppConstant.A000);
	}
	public  Result delDetail(Long listId,String empNo)throws MyException{
		// TODO Auto-generated method stub
		if(StringUtil.isEmpty(listId)||StringUtil.isEmpty(empNo))
		{
			throw new MyException("A003");
		}
		if(!iss.chackEmpPermi(listId, empNo))
		{
			throw new MyException("A004");
		}
		DispatchList list=iss.findListByListId(listId);
		if(list.getESn().equals(empNo))
		{
			throw new MyException("A004");
		}
		int result = detaildao
				.createSQLQuery(
						"update hzy.dispatch_detail set flag=0 where ds_id=?",
						listId).executeUpdate();
		if (result <= 0) {
			throw new MyException("A008");
		}

		return new Result(true,  AppConstant.A000);
	}
	/* (non-Javadoc)
	 * @see org.tender.services.IEmpService#delDispatch(org.han.entity.DispatchList, java.lang.String)
	 */
	@Override
	public Result delDispatch(Long listId, String empNo)
			throws MyException {
		// TODO Auto-generated method stub
		if(listId==null||empNo==null)
		{
			throw new MyException("A003");
		}
		DispatchList list=iss.findListByListId(listId);
		if(StringUtil.isEmpty(list)||list.getFlag().equals(1))
		{
			throw new MyException("A004");
		}
		if(!iss.chackEmpPermi(list.getDlId(), empNo))
		{
			throw new MyException("A004");
		}

		if(!list.getESn().equals(empNo))
		{
			throw new MyException("A001");
		}
	
		Page detail=iss.findDetailByListId(listId, vo);
		if(detail.getTotalCount()<1)
		{
			throw new MyException("A001");
		}
		int delDetail = detaildao
				.createSQLQuery(
						"update hzy.dispatch_detail set flag=0 where sheet_id=?",
						listId).executeUpdate();
		if (delDetail <= 0) {
			throw new MyException("A008");
		}
		int delDis = idd.createSQLQuery(
				"update hzy.dispatch_list set flag=0 where sheet_id=?", listId)
				.executeUpdate();
		if (delDis <= 0) {
			throw new MyException("A008");
		}
		
		/*
		 * 
		 */
		return new Result(true,  AppConstant.A000);
	}
	/* (non-Javadoc)
	 * @see org.tender.services.IEmpService#delDetail(java.lang.Long, java.lang.String)
	 */
	
	
	/* (non-Javadoc)
	 * @see org.tender.services.IEmpService#addDetail(org.han.entity.DispatchDetail, java.lang.String)
	 */
	
	public Result addDetail(DispatchDetail detail, String empNo,Long listId)
			throws MyException {
		// TODO Auto-generated method stub
		if(StringUtil.isEmpty(detail)||StringUtil.isEmpty(empNo)||StringUtil.isEmpty(listId))
		{
			throw new MyException("A001");
		}
		DispatchList list=iss.findListByListId(listId);
		
		if(!list.getESn().equals(empNo))
		{
			throw new MyException("A001");
		}
		if(!iss.chackEmpPermi(list.getDlId(), empNo))
		{
			throw new MyException("A004");
		}
		this.detaildao.save(detail);
		System.out.println("this is addDetail");
		return new Result(true,  AppConstant.A000);
	}
	/* (non-Javadoc)
	 * @see org.tender.services.IEmpService#delDispatch(org.han.entity.DispatchDetail, java.lang.Long, java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see org.tender.services.IEmpService#updateDetail(org.han.entity.DispatchDetail, java.lang.Long, java.lang.String)
	 */

	public Result updateDetail(DetailVo dvo,Long listId,String empNo)throws MyException{
		// TODO Auto-generated method stub
		if(StringUtil.isEmpty(dvo)||StringUtil.isEmpty(empNo)||StringUtil.isEmpty(listId))
		{
			throw new MyException("A001");
		}
		DispatchDetail detail=detaildao.get(dvo.getDetailId());
		DispatchList list=iss.findListByListId(detail.getSheetId());
		System.out.println(detail);
		if(list.getESn().equals(empNo))
		{
			throw new MyException("A001");
		}

		if(!iss.chackEmpPermi(list.getDlId(), empNo))
		{
			throw new MyException("A004");
		}
		
		detail.setMoney(dvo.getMoney());
		detail.setItemId(dvo.getItemId());
		detail.setCostExplain(dvo.getCostExplain());
		detail.setAccessory(dvo.getAccessory());
		return new Result(true,  AppConstant.A000);
	}
	
}
