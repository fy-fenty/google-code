package org.han.services.impl;

import java.util.List;

import org.han.services.ISystemServices;
import org.tender.dao.IDispatchDetailDao;
import org.tender.dao.IDispatchListDao;
import org.tender.dao.IDispatchResultDao;
import org.tender.dao.ILoginUserDao;
import org.tender.dao.ISysPositionsDao;
import org.tender.entity.DispatchList;
import org.tender.entity.DispatchResult;
import org.tender.entity.LoginUser;
import org.tender.entity.SysEmployee;
import org.tender.entity.SysPositions;
import org.tender.util.MD5;

public class SystemServiceImpl implements ISystemServices {
	private IDispatchListDao disListDao;
	private IDispatchResultDao disResultDao;
	private IDispatchDetailDao disDetail;
	private ILoginUserDao logUserDao;
	private ISysPositionsDao positionsDao;

	public ISysPositionsDao getPositionsDao() {
		return positionsDao;
	}

	public void setPositionsDao(ISysPositionsDao positionsDao) {
		this.positionsDao = positionsDao;
	}

	public ILoginUserDao getLogUserDao() {
		return logUserDao;
	}

	public void setLogUserDao(ILoginUserDao logUserDao) {
		this.logUserDao = logUserDao;
	}

	public IDispatchDetailDao getDisDetail() {
		return disDetail;
	}

	public void setDisDetail(IDispatchDetailDao disDetail) {
		this.disDetail = disDetail;
	}

	public IDispatchResultDao getDisResultDao() {
		return disResultDao;
	}

	public void setDisResultDao(IDispatchResultDao disResultDao) {
		this.disResultDao = disResultDao;
	}

	public IDispatchListDao getDisListDao() {
		return disListDao;
	}

	public void setDisListDao(IDispatchListDao disListDao) {
		this.disListDao = disListDao;
	}

	@Override
	public DispatchList getDisListById(Long disId) {
		return disListDao.findUnique(
				"from DispatchList where dl_id=? and flag=1", disId);
	}

	@Override
	public boolean checkEmpPermi(Long disId, String empSN) {
		// TODO Auto-generated method stub
		DispatchResult disResult = this.getDisResultByDisId(disId);
		if (null == disResult) {
			SysPositions pos = getSysPositionsBySN(empSN);
			if (pos.getPId() == 3) {// 是否是雇员
				return true;
			}
		} else if (empSN.equals(disResult.getCheckNext())) {
			return true;
		}
		return false;
	}

	@Override
	public DispatchResult getDisResultByDisId(Long disId) {
		// TODO Auto-generated method stubs
		String sql = "select * from hzy.dispatch_result dr where check_time=(select max(check_time) from hzy.dispatch_result where sheet_id=? and dr.sheet_id=sheet_id)";
		DispatchResult dr = (DispatchResult) disResultDao.createSQLQuery(sql,
				disId).addEntity(DispatchResult.class).uniqueResult();
		return dr;
	}

	@Override
	public List getDispatchDetails(Long disId) {
		// TODO Auto-generated method stub
		String hql = "from DispatchDetail where sheet_id=? and flag=1";
		return disDetail.find(hql, disId);
	}

	@Override
	public List getDispatchResults(Long disId) {
		// TODO Auto-generated method stub
		String hql = "from DispatchResult where sheet_id=?";
		return disResultDao.find(hql, disId);
	}

	@Override
	public LoginUser getLoginUserBySN(String empSN) {
		// TODO Auto-generated method stub
		return logUserDao.findUnique("from LoginUser where e_sn=?", empSN);
	}

	@Override
	public String getMD5Encryption(String pwd) {
		// TODO Auto-generated method stub
		return MD5.getMD5(pwd);
	}

	@Override
	public SysEmployee getSysEmployeeBySN(String empSN) {
		// TODO Auto-generated method stub
		return logUserDao.findUnique("from SysEmployee where e_sn=?", empSN);
	}

	@Override
	public SysPositions getSysPositionsBySN(String empSN) {
		// TODO Auto-generated method stub
		SysEmployee emp = this.getSysEmployeeBySN(empSN);
		if (null == emp) {
			return null;
		}
		SysPositions obj = positionsDao.findUnique(
				"from SysPositions where p_id=?", emp.getPId());
		return obj;
	}

}
