package org.zjf.services.impl;

import org.hibernate.SQLQuery;
import org.ymm.dao.IDispatchDetailDao;
import org.ymm.dao.IDispatchListDao;
import org.ymm.dao.IDispatchResultDao;
import org.ymm.dao.ILoginUserDao;
import org.ymm.dao.ISysPositionsDao;
import org.ymm.entity.DispatchList;
import org.ymm.entity.DispatchResult;
import org.ymm.entity.LoginUser;
import org.ymm.entity.SysPositions;
import org.ymm.exception.MyException;
import org.ymm.util.MD5;
import org.ymm.util.StringUtil;
import org.ymm.vo.BaseVo;
import org.ymm.vo.Page;
import org.zjf.services.ISystemService;

/**
 * @project:ApplyDispatch1
 * @author:zjf
 * @date:2012-8-15 下午2:11:55   
 * @class:SystemServiceImpl
 * @extends:Object
 * @description:系统服务实现类
 */
public class SystemServiceImpl implements ISystemService{

	private IDispatchListDao listdao;
	private IDispatchResultDao resultdao;
	private IDispatchDetailDao detaildao;
	private ISysPositionsDao posdao;
	private ILoginUserDao userdao;
	public ILoginUserDao getUserdao() {
		return userdao;
	}

	public void setUserdao(ILoginUserDao userdao) {
		this.userdao = userdao;
	}

	public ISysPositionsDao getPosdao() {
		return posdao;
	}

	public void setPosdao(ISysPositionsDao posdao) {
		this.posdao = posdao;
	}

	public IDispatchDetailDao getDetaildao() {
		return detaildao;
	}

	public void setDetaildao(IDispatchDetailDao detaildao) {
		this.detaildao = detaildao;
	}

	public IDispatchResultDao getResultdao() {
		return resultdao;
	}

	public void setResultdao(IDispatchResultDao resultdao) {
		this.resultdao = resultdao;
	}

	public IDispatchListDao getListdao() {
		return listdao;
	}

	public void setListdao(IDispatchListDao listdao) {
		this.listdao = listdao;
	}

	@Override
	public String getMD5(final String pwd) {
		return MD5.ecodeByMD5(pwd);
	}

	@Override
	public DispatchList findById(final long cid){
		return listdao.get(cid);
	}

	@Override
	public DispatchResult findResultById(final long cid) throws MyException{
		String sql="select r.* from(select * from dispatch_result where sheet_id=?  order by CHECK_TIME desc nulls last)r where rownum=1";
		SQLQuery query=resultdao.createSQLQuery(sql, cid+"").addEntity(DispatchResult.class);
		return (DispatchResult)query.uniqueResult();
	}

	@Override
	public Page findDetailById(final long id,final int start,final int limit) throws MyException {
		String sql="select * from dispatch_detail where sheet_Id=?";
		BaseVo vo=new BaseVo();
		vo.setStart(start);
		vo.setLimit(limit);
		return detaildao.findPageBySQL(vo, sql, id+"");
	}

	@Override
	public Page findResultListById(final long id,final int start,final int limit) throws MyException {
		String sql="select * from dispatch_result where sheet_Id=?";
		BaseVo vo=new BaseVo();
		vo.setLimit(limit);
		vo.setStart(start);
		return resultdao.findPageBySQL(vo, sql, id+"");
	}

	@Override
	public SysPositions findPositionById(final long id) throws MyException {
		return posdao.get(id);
	}

	@Override
	public LoginUser findUserBySn(String sn) throws MyException {
		String sql="from LoginUser where ESn=?";
		return userdao.findUnique(sql, sn);
	}

	@Override
	public boolean checkSys(String sn, long cid) throws MyException {
		if(!StringUtil.isEmpty(sn))
			return false;
		DispatchResult result= findResultById(cid);
		if(result==null)
			return false;
		if(!result.getCheckNext().equals(sn))
			return false;
		return true;
	}
}
