package org.ymm.services.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.ymm.services.ISystemService;
import org.zjf.dao.ILoginUserDao;
import org.zjf.dao.ISysPositionsDao;
import org.zjf.entity.DispatchList;
import org.zjf.entity.DispatchResult;
import org.zjf.entity.LoginUser;
import org.zjf.entity.SysEmployee;
import org.zjf.entity.SysPositions;
import org.zjf.exception.MyException;
import org.zjf.support.impl.BaseDao;
import org.zjf.util.MD5;
import org.zjf.vo.BaseVO;
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

	public static void main(String[] args) throws MyException {
		ApplicationContext ac=new ClassPathXmlApplicationContext(new String[]{"spring-sessinfactory.xml","spring-trans.xml","spring-dao-beans.xml"});
		ISystemService iss= ac.getBean("systemServiceImpl",ISystemService.class);
		System.out.println(iss.checkSys("xxxx1004",2).getSuccess());
	}
	
	private BaseDao baseDao;
	private ISysPositionsDao posdao;
	private ILoginUserDao logindao;
	
	public ILoginUserDao getLogindao() {
		return logindao;
	}

	public void setLogindao(ILoginUserDao logindao) {
		this.logindao = logindao;
	}

	public ISysPositionsDao getPosdao() {
		return posdao;
	}

	public void setPosdao(ISysPositionsDao posdao) {
		this.posdao = posdao;
	}

	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public String getMD5(final String pwd) {
		String MD5Pwd="";
		if(pwd!=null&&!"".equals(pwd)){
			MD5Pwd =MD5.MD5Encode(pwd);
		}
		return MD5Pwd;
	}

	@Override
	public DispatchList findById(final long dl_id) {
		String sql=" from DispatchList where dl_id=?";
		DispatchList dispatchList= (DispatchList)baseDao.findUniqueByHQL(sql, dl_id);
		return dispatchList;
	}
	
	@Override
	public DispatchResult findResultById(final long sheet_id) {
		String sql="select dr1.* from dispatch_result dr1 where dr1.sheet_id=? "+
					" and check_time=(select max(check_time) from dispatch_result dr2 where dr2.sheet_id= dr1.sheet_id )";
		
		DispatchResult dispatchResult =  (DispatchResult) baseDao.createSQLQuery(sql, sheet_id).addEntity(DispatchResult.class).uniqueResult();
		return dispatchResult;
	}

	@Override
	public Page findDetailById(final long sheet_id,final int start,final int limit) {
		String sql="select * from dispatch_detail where sheet_id=?";
		BaseVO vo=new BaseVO();
		vo.setLimit(limit);
		vo.setStart(start);
		Page page= baseDao.findPageBySql(vo, sql,sheet_id);
		return page;
	}

	@Override
	public Page findResultListById(final long sheet_id,final int start,final int limit) {
		String sql=" select * from dispatch_result where sheet_id=? order by check_time";
		BaseVO vo=new BaseVO();
		vo.setLimit(limit);
		vo.setStart(start);
		Page page=baseDao.findPageBySql(vo, sql,sheet_id);
		return page;
	}

	@Override
	public SysPositions findPositionById(final long id) {
		return posdao.get(id);
	}

	@Override
	public LoginUser findUserBySn(String E_SN) {
		String sql=" from LoginUser where ESn=?";
		return logindao.findUniqueByHQL(sql, E_SN);
	}

	@Override
	public Result checkSys(String E_SN, long dl_id) {
		DispatchResult dispatchResult = this.findResultById(dl_id);
		Result result=new Result();
		if(!dispatchResult.getCheckNext().equals(E_SN)){
			result.setSuccess(false);
		}
		return result;
	}
	
}
