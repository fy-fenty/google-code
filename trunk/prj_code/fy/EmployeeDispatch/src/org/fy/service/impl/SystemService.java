package org.fy.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.fy.dao.IDetailItemDao;
import org.fy.dao.IDispatchDetailDao;
import org.fy.dao.IDispatchListDao;
import org.fy.dao.IDispatchResultDao;
import org.fy.dao.ILoginUserDao;
import org.fy.dao.ISysEmployeeDao;
import org.fy.dao.ISysPositionsDao;
import org.fy.entity.DispatchList;
import org.fy.entity.DispatchResult;
import org.fy.entity.LoginUser;
import org.fy.entity.SysEmployee;
import org.fy.entity.SysPositions;
import org.fy.service.ISystemService;
import org.fy.util.MD5;
import org.fy.utils.AppUtils;
import org.fy.vo.BaseVO;
import org.fy.vo.Page;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

/**
 * @author hzy
 * @date 2012-8-15
 *	@extends Object
 * @class SystemService
 * @description 系统类接口实现类
 */
public class SystemService implements ISystemService{
	private IDispatchListDao idispatch_list;
	private IDispatchResultDao idispatch_result;
	private IDispatchDetailDao idispatch_detail;
	private ISysPositionsDao isys_position;
	private ILoginUserDao ilogin_user;
	private ISysEmployeeDao isys_employee;
	private IDetailItemDao idetail_item;
	

	public IDispatchListDao getIdispatch_list() {
		return idispatch_list;
	}
	public void setIdispatch_list(IDispatchListDao idispatch_list) {
		this.idispatch_list = idispatch_list;
	}
	public IDispatchResultDao getIdispatch_result() {
		return idispatch_result;
	}
	public void setIdispatch_result(IDispatchResultDao idispatch_result) {
		this.idispatch_result = idispatch_result;
	}
	public IDispatchDetailDao getIdispatch_detail() {
		return idispatch_detail;
	}
	public void setIdispatch_detail(IDispatchDetailDao idispatch_detail) {
		this.idispatch_detail = idispatch_detail;
	}
	public ISysPositionsDao getIsys_position() {
		return isys_position;
	}
	public void setIsys_position(ISysPositionsDao isys_position) {
		this.isys_position = isys_position;
	}
	public ILoginUserDao getIlogin_user() {
		return ilogin_user;
	}
	public void setIlogin_user(ILoginUserDao ilogin_user) {
		this.ilogin_user = ilogin_user;
	}
	public ISysEmployeeDao getIsys_employee() {
		return isys_employee;
	}
	public void setIsys_employee(ISysEmployeeDao isys_employee) {
		this.isys_employee = isys_employee;
	}
	public IDetailItemDao getIdetail_item() {
		return idetail_item;
	}
	public void setIdetail_item(IDetailItemDao idetail_item) {
		this.idetail_item = idetail_item;
	}
	
	public String getMd5(final String pwd){
		return MD5.getMD5(pwd);
	}

	public DispatchList findByDlistId(final Long id) {
		return idispatch_list.get(id);
	}

	public DispatchResult findResultById(final Long id) {
		String sql="select * from hzy.dispatch_result where check_time=(select max(check_time) from hzy.dispatch_result where sheet_id=?)";
		SQLQuery squery=idispatch_result.createSQLQuery(sql, id).addEntity(DispatchResult.class);
		return (DispatchResult) squery.uniqueResult();
	}

	public List findDetailById(final Long id) {
		return idispatch_detail.find("From DispatchDetail where sheetId=? and flag=1", id);
	}

	public List findDlistResultById(final Long id) {
		return idispatch_result.find("From DispatchResult where sheetId=?", id);
	}

	public SysPositions findPositionBySn(final String sn) {
		String sql="select * from hzy.sys_positions where p_id=(select p_id from hzy.sys_employee where e_sn=?)";
//		return isys_position.findUnique("From SysPositions", values);
		SQLQuery squery=isys_position.createSQLQuery(sql, sn).addEntity(SysPositions.class);
//		isys_position.findUniqueBySql(sql, values)
		return (SysPositions) squery.uniqueResult();
	}

	public LoginUser findUserBySn(final String sn) {
		return ilogin_user.findUnique("From LoginUser where ESn=?", sn);
	}

	public SysEmployee findEmployeeBySn(final String sn) {
		return isys_employee.findUnique("From SysEmployee where ESn=?", sn);
	}

	public boolean checkDlist(final Long id,final String sn) {
		DispatchResult dl=findResultById(id);
		if(dl==null){
			return false;
		}
		if(sn.equals(dl.getCheckNext())){
			return true;
		};
		return false;
	}

	public Page findDetailItem(BaseVO bv) {
		String sql="select * from hzy.detail_item";
		return idetail_item.findPageBySQL(bv, sql);
	}

}
