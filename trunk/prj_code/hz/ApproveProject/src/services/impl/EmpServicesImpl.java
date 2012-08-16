/**
 * 
 */
package org.tender.services.impl;

import java.util.Map;

import org.han.dao.IDispatchDetailDao;
import org.han.dao.IDispatchListDao;
import org.han.dao.ISysPositionsDao;
import org.han.entity.DispatchDetail;
import org.han.entity.DispatchList;
import org.han.exception.MyException;
import org.han.utils.StringUtil;
import org.han.vo.BaseVo;
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
	public BaseVo vo;
	private IDispatchListDao idd;
	private ISysService iss;
	private IDispatchDetailDao iddd;
	
	public IDispatchDetailDao getIddd() {
		return iddd;
	}
	public void setIddd(IDispatchDetailDao iddd) {
		this.iddd = iddd;
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
	public Result updateDispatch(DispatchList list,String empNo) throws MyException {
		// TODO Auto-generated method stub
		
		if(list==null||empNo.equals(""))
		{
			throw new MyException("A001");
		}
		Map<String,Object>map=iss.findListByListId(list.getDlId());
		Map<String,Object>emp=iss.findEmpByEmpNo(empNo);
		
		if(map==null||map.get("E_SN")!=(emp.get("E_SN")))
		{
			throw new MyException("A001");
		}
		Map<String,Object>statusList=iss.findStatusByListId(list.getDlId());
		if(statusList.get("check_next")!=empNo&& statusList!=null)
		{
			throw new MyException("A001");
		}
		if(map.get("flag").equals(0))
		{
			throw new MyException("A001");
		}
		this.idd.save(list);
		
		return new Result(true, "操作成功");
	}
	/* (non-Javadoc)
	 * @see org.tender.services.IEmpService#commitDispatch(org.han.entity.DispatchList)
	 */

	public Result commitDispatch(DispatchList list,String empNo) throws MyException {
		// TODO Auto-generated method stub
		if(list==null||empNo==null)
		{
			throw new MyException("A001");
		}
		Map<String,Object>emPosit=iss.findPostByUserId(empNo);
		if(!emPosit.get("P_ID").equals(3))
		{
			throw new MyException("A001");
		}
		Page detail=iss.findDetailByListId(list.getDlId(), vo);
		if(detail.getTotalCount()>1)
		{
			throw new MyException("A001");
		}
		this.idd.save(list);
		return new Result(true, "操作成功");
	}
	/* (non-Javadoc)
	 * @see org.tender.services.IEmpService#saveDispatch(org.han.entity.DispatchList, java.lang.String)
	 */
	@Override
	public Result saveDispatch(DispatchList list, String empNo)throws MyException  {
		// TODO Auto-generated method stub
		if(list==null||empNo==null)
		{
			throw new MyException("A001");
		}
		Map<String,Object>emPosit=iss.findPostByUserId(empNo);
		if(!emPosit.get("P_ID").equals(3))
		{
			throw new MyException("A001");
		}
		this.idd.saveNew(list);
		return new Result(true, "操作成功");
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
			throw new MyException("A001");
		}
		Map<String,Object>list=iss.findListByListId(listId);
		if(list.isEmpty())
		{
			throw new MyException("A001");
		}
		Map<String,Object>status=iss.findStatusByListId(listId);
		if(status.get("check_next")!=empNo&&status!=null)
		{
			throw new MyException("A001");
		}
		Map<String,Object>map=iss.findListByListId(listId);
		Map<String,Object>emp=iss.findEmpByEmpNo(empNo);
		
		if(map==null||map.get("E_SN")!=(emp.get("E_SN")))
		{
			throw new MyException("A001");
		}
		if(list.get("flag").equals(0))
		{
			throw new MyException("A001");
		}
		Page detail=iss.findDetailByListId(listId, vo);
		if(detail.getTotalCount()<1)
		{
			throw new MyException("A001");
		}
		/*
		 * 
		 */
		return null;
	}
	/* (non-Javadoc)
	 * @see org.tender.services.IEmpService#delDetail(java.lang.Long, java.lang.String)
	 */
	
	public  Result delDetail(Long listId,String empNo,Long detailId)throws MyException{
		// TODO Auto-generated method stub
		if(StringUtil.isEmpty(listId)||StringUtil.isEmpty(empNo)||StringUtil.isEmpty(detailId))
		{
			throw new MyException("A001");
		}
		Map<String,Object>status=iss.findStatusByListId(listId);
		if(status.get("check_next")!=empNo&&status!=null)
		{
			throw new MyException("A001");
		}
		Map<String,Object>map=iss.findListByListId(listId);
		
		
		if(map.get("E_SN").equals(empNo))
		{
			throw new MyException("A001");
		}
//		String sql="update hzy.dispatch_list set flag=0 where dl_id=1";
//		this.idd.createSQLQuery(sql, listId);
		this.iddd.delete(detailId);
		return new Result(true, "操作成功");
	}
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
		Map<String,Object>list=iss.findListByListId(listId);
		if(StringUtil.isEmpty(list))
		{
			throw new MyException("A001");
		}
		Map<String,Object>status=iss.findStatusByListId(listId);
		if(status.get("check_next")!=empNo&&status!=null)
		{
			throw new MyException("A001");
		}
		this.iddd.save(detail);
		return new Result(true, "操作成功");
	}
	/* (non-Javadoc)
	 * @see org.tender.services.IEmpService#delDispatch(org.han.entity.DispatchDetail, java.lang.Long, java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see org.tender.services.IEmpService#updateDetail(org.han.entity.DispatchDetail, java.lang.Long, java.lang.String)
	 */

	public Result updateDetail(DispatchDetail detail,Long listId,String empNo)throws MyException{
		// TODO Auto-generated method stub
		if(StringUtil.isEmpty(detail)||StringUtil.isEmpty(empNo)||StringUtil.isEmpty(listId))
		{
			throw new MyException("A001");
		}
		Map<String,Object>list=iss.findListByListId(listId);
		if(StringUtil.isEmpty(list))
		{
			throw new MyException("A001");
		}
		Map<String,Object>map=iss.findListByListId(listId);
		
		
		if(map.get("E_SN").equals(empNo))
		{
			throw new MyException("A001");
		}
		Map<String,Object>status=iss.findStatusByListId(listId);
		if(status.get("check_next")!=empNo&&status!=null)
		{
			throw new MyException("A001");
		}
		this.iddd.save(detail);
		return new Result(true, "操作成功");
	}
	
}
