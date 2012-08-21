package org.ymm.services.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.ymm.services.ISystemService;
import org.zjf.constant.AppConstant;
import org.zjf.dao.IDispatchDetailDao;
import org.zjf.dao.IDispatchListDao;
import org.zjf.dao.IDispatchResultDao;
import org.zjf.dao.ILoginUserDao;
import org.zjf.dao.ISysPositionsDao;
import org.zjf.entity.DispatchList;
import org.zjf.entity.DispatchResult;
import org.zjf.entity.LoginUser;
import org.zjf.entity.SysPositions;
import org.zjf.exception.MyException;
import org.zjf.support.impl.BaseDao;
import org.zjf.util.MD5;
import org.zjf.util.StringUtil;
import org.zjf.vo.BaseVo;
import org.zjf.vo.Page;
import org.zjf.vo.Result;

/**
 * @author yingmingming 
 * @date 2012-8-15 下午9:23:21
 * @ClassName: SystemServiceImpl 
 * @extends Object	 
 * @Description: 系统接口实现类
 */
public class SystemServiceImpl implements ISystemService {

	
	
	private BaseDao baseDao;
	private ISysPositionsDao iSysPositionsDao;
	private ILoginUserDao iLoginUserDao;
	private IDispatchListDao iDispatchListDao;
	private IDispatchResultDao iDispatchResultDao;
	private IDispatchDetailDao iDispatchDetailDao;
	

	public IDispatchDetailDao getiDispatchDetailDao() {
		return iDispatchDetailDao;
	}

	public void setiDispatchDetailDao(IDispatchDetailDao iDispatchDetailDao) {
		this.iDispatchDetailDao = iDispatchDetailDao;
	}

	public IDispatchResultDao getiDispatchResultDao() {
		return iDispatchResultDao;
	}

	public void setiDispatchResultDao(IDispatchResultDao iDispatchResultDao) {
		this.iDispatchResultDao = iDispatchResultDao;
	}

	public IDispatchListDao getiDispatchListDao() {
		return iDispatchListDao;
	}

	public void setiDispatchListDao(IDispatchListDao iDispatchListDao) {
		this.iDispatchListDao = iDispatchListDao;
	}

	public ILoginUserDao getiLoginUserDao() {
		return iLoginUserDao;
	}

	public void setiLoginUserDao(ILoginUserDao iLoginUserDao) {
		this.iLoginUserDao = iLoginUserDao;
	}

	public ISysPositionsDao getiSysPositionsDao() {
		return iSysPositionsDao;
	}

	public void setiSysPositionsDao(ISysPositionsDao iSysPositionsDao) {
		this.iSysPositionsDao = iSysPositionsDao;
	}

	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	
	public String getMD5(final String pwd) {
		String MD5Pwd="";
		if(StringUtil.isEmpty(pwd)){
			MD5Pwd =MD5.MD5Encode(pwd);
		}
		return MD5Pwd;
	}

	
	public DispatchList findById(final long dl_id) {
		//String sql=" from DispatchList where dl_id=?";
		//DispatchList dispatchList= (DispatchList)baseDao.findUniqueByHQL(sql, dl_id);
		return iDispatchListDao.get(dl_id);
	}
	
	
	public DispatchResult findResultById(final long sheet_id) {
		
		String sql="select dr1.* from dispatch_result dr1 where dr1.sheet_id=? "+
					" and check_time=(select max(check_time) from dispatch_result dr2 where dr2.sheet_id= dr1.sheet_id )";
		
		DispatchResult dispatchResult=(DispatchResult) iDispatchResultDao.createSQLQuery(sql, sheet_id).addEntity(DispatchResult.class).uniqueResult();
		return dispatchResult;
	}
	

	
	public Page findDetailById(final long sheet_id,final int start,final int limit) {
		String sql="select * from dispatch_detail where sheet_id=?";
		BaseVo vo=new BaseVo(start,limit);
		Page page= iDispatchDetailDao.findPageBySql(vo, sql,sheet_id);
		return page;
	}
	
	
	
	public Page findResultListById(final long sheet_id,final int start,final int limit) {
		String sql=" select * from dispatch_result where sheet_id=? order by check_time";
		BaseVo vo=new BaseVo(start,limit);
		Page page=iDispatchListDao.findPageBySql(vo, sql,sheet_id);
		return page;
	}

	
	
	public SysPositions findPositionById(final long id) {
		return iSysPositionsDao.get(id);
	}
	
	
	
	public LoginUser findUserBySn(String E_SN) {
		String sql=" from LoginUser where ESn=?";
		return iLoginUserDao.findUniqueByHQL(sql, E_SN);
	}
	

	
	public Result checkSys(String E_SN, long dl_id) {
		
		Result result=new Result(true,AppConstant.DEFAULT_MSG,"A001");
		
		DispatchResult dispatchResult = this.findResultById(dl_id);
		if(dispatchResult==null){
			return new Result(false,AppConstant.SYS_ERROR,"A003");
		}
		if(!dispatchResult.getCheckNext().equals(E_SN)){
			return new Result(false,AppConstant.SYS_ERROR,"A005");
		}
		return result;
	}
	
	/*
	 public static void main(String[] args) throws MyException {
		ApplicationContext ac=new ClassPathXmlApplicationContext(new String[]{"spring-sessinfactory.xml","spring-trans.xml","spring-dao-beans.xml"});
		ISystemService iss= ac.getBean("systemServiceImpl",ISystemService.class);
		System.out.println(iss.checkSys("xxxx1000",3).getMsg());
	 }
	*/
	
}
