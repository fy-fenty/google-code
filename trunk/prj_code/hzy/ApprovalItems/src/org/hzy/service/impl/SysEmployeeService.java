package org.hzy.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hzy.constant.AppConstant;
import org.hzy.dao.IDispatchListDao;
import org.hzy.dao.ISysEmployeeDao;
import org.hzy.entity.DispatchDetail;
import org.hzy.entity.DispatchList;
import org.hzy.entity.LoginUser;
import org.hzy.entity.SysPositions;
import org.hzy.exception.MyException;
import org.hzy.service.ISysEmployeeService;
import org.hzy.support.ISystemUtil;
import org.hzy.util.MyMatcher;
import org.hzy.vo.Result;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author fy
 * @date 2012-8-16
 * @class SysEmployeeService
 * @extends Object
 * @implements ISysEmployeeService
 * @description 员工业务的接口实现
 */
public class SysEmployeeService implements ISysEmployeeService {

	private ISysEmployeeDao iseDao;

	private IDispatchListDao idlDao;

	private ISystemUtil isu;

	@Override
	public List findAllDispatchListByESn(String eSn) throws MyException {
		if (MyMatcher.isEmpty(eSn)) {
			throw new MyException(AppConstant.A001);
		}
		String sql = "select d.dl_id dlId,d.e_sn ESn,d.create_time createTime,(select sum(dd.money) from hzy.DISPATCH_DETAIL dd where dd.sheet_id = d.dl_id)  totalMoney,(select d.check_status from hzy.dispatch_result d where d.dr_id = (select max(dr_id) from hzy.dispatch_result where sheet_id = d.dl_id)) currentStatus from hzy.dispatch_list d left join hzy.sys_employee s on d.e_sn = s.e_sn where s.e_sn = ? and d.flag = 1";
		SQLQuery sqlQuery = iseDao.createSQLQuery(sql, eSn);
		sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return sqlQuery.list();
	}

	@Override
	public Result saveDispatchList(Long uId, DispatchList dl) throws MyException {
		if (MyMatcher.isEmpty(uId)) {
			throw new MyException(AppConstant.A004);
		}
		if (MyMatcher.isEmpty(dl)) {
			throw new MyException(AppConstant.A005);
		}
		SysPositions sp = isu.findSysPositionsByUId(uId);
		if (sp.getPId().equals(AppConstant.EMPLOYEE) == false) {
			throw new MyException(AppConstant.A006);
		}
		Result rs = new Result();
		try {
			idlDao.save(dl);
			rs.setSuccess(true);
			rs.setMsg("保存报销单成功！");
		} catch (Exception e) {
			rs.setSuccess(false);
			rs.setMsg("保存报销单失败！");
			rs.setException(e.getMessage());
		}
		return rs;
	}

	@Override
	public Result updateDispatchList(Long UID, DispatchList dl) throws MyException {
		if (MyMatcher.isEmpty(UID)) {
			throw new MyException(AppConstant.A004);
		}
		if (MyMatcher.isEmpty(dl)) {
			throw new MyException(AppConstant.A005);
		}
		
		return null;
	}

	@Override
	public Result updateDispatchDetail(DispatchDetail dd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result deleteDispatchList(DispatchList dl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result deleteDispatchDetail(DispatchDetail dd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result addDispatchDetail(DispatchDetail dd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result submitDispatchList(DispatchList dl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result login(LoginUser lu) {
		// TODO Auto-generated method stub
		return null;
	}

	public ISysEmployeeDao getIseDao() {
		return iseDao;
	}

	public void setIseDao(ISysEmployeeDao iseDao) {
		this.iseDao = iseDao;
	}

	public IDispatchListDao getIdlDao() {
		return idlDao;
	}

	public void setIdlDao(IDispatchListDao idlDao) {
		this.idlDao = idlDao;
	}

	public ISystemUtil getIsu() {
		return isu;
	}

	public void setIsu(ISystemUtil isu) {
		this.isu = isu;
	}

	public static void main(String[] args) throws MyException {
		ApplicationContext actc = new ClassPathXmlApplicationContext(new String[] { "hibernate-spring.xml",
				"beans1.xml" });
		ISysEmployeeService is = actc.getBean("SysEmployeeService", ISysEmployeeService.class);

		/* 雇员查找所有当前状态的全部报销单 */
		// System.out.println(is.findAllDispatchListByESn("10000000"));

		/* 用户保存报销单 */
		Long uId = 1L;
		DispatchList dl = new DispatchList(null, "10000000", new Date(), "cc", true);
		is.saveDispatchList(uId, dl);
	}
}