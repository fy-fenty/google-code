package org.han.services.impl;

import java.util.Date;
import java.util.List;

import org.han.constant.AppConstant;
import org.han.dao.IDispatchDetailDao;
import org.han.dao.IDispatchListDao;
import org.han.dao.IDispatchResultDao;
import org.han.dao.ISysConfigDao;
import org.han.dao.ISysEmployeeDao;
import org.han.entity.DispatchDetail;
import org.han.entity.DispatchList;
import org.han.entity.DispatchResult;
import org.han.entity.LoginUser;
import org.han.entity.SysDepartment;
import org.han.entity.SysPositions;
import org.han.exception.MyException;
import org.han.services.IEmpService;
import org.han.services.ISystemServices;
import org.han.util.StringUtil;
import org.han.vo.BaseVo;
import org.han.vo.DispatchDetailVo;
import org.han.vo.DispatchListVo;
import org.han.vo.LoginVo;
import org.han.vo.Page;
import org.han.vo.Result;

/**
 * @author HanZhou
 * @className: EmpServiceImpl
 * @date 2012-8-16
 * @extends Object
 * @description: 雇员业务逻辑实现类
 */
public class EmpServiceImpl implements IEmpService {
	private ISysEmployeeDao empDao;
	private IDispatchListDao disListDao;
	private IDispatchResultDao disResultDao;
	private IDispatchDetailDao disDetailDao;
	private ISysConfigDao configDao;
	private ISystemServices systemBiz;

	public ISysConfigDao getConfigDao() {
		return configDao;
	}

	public void setConfigDao(ISysConfigDao configDao) {
		this.configDao = configDao;
	}

	public IDispatchDetailDao getDisDetailDao() {
		return disDetailDao;
	}

	public void setDisDetailDao(IDispatchDetailDao disDetailDao) {
		this.disDetailDao = disDetailDao;
	}

	public IDispatchResultDao getDisResultDao() {
		return disResultDao;
	}

	public void setDisResultDao(IDispatchResultDao disResultDao) {
		this.disResultDao = disResultDao;
	}

	public ISystemServices getSystemBiz() {
		return systemBiz;
	}

	public void setSystemBiz(ISystemServices systemBiz) {
		this.systemBiz = systemBiz;
	}

	public IDispatchListDao getDisListDao() {
		return disListDao;
	}

	public void setDisListDao(IDispatchListDao disListDao) {
		this.disListDao = disListDao;
	}

	public ISysEmployeeDao getEmpDao() {
		return empDao;
	}

	public void setEmpDao(ISysEmployeeDao empDao) {
		this.empDao = empDao;
	}

	/**
	 * @param empSN
	 * @return true该编号是雇员
	 * @Description: 判断该雇员是否为雇员
	 */
	private boolean isEmployee(String empSN) {
		SysPositions pos = systemBiz.getSysPositionsBySN(empSN);
		if (pos == null) {
			return false;
		}
		return pos.getPId() == 3;
	}

	/**
	 * @param disId
	 * @return true有效报销单且创建人是该雇员
	 * @Description: 判断报销单是否有效且创建人是否是该雇员
	 */
	private boolean isValidDispatch(Long disId, String empSN) {
		DispatchList disList = systemBiz.getDisListById(disId);
		if (null == disList) {// 判断报销单是否有效
			return false;
		}
		if (!empSN.equals(disList.getESn())) {// 判断创建者是不是该雇员
			return false;
		}
		return true;
	}

	/**
	 * @param disId
	 * @param empSN
	 * @return true有权限操作
	 * @Description: 判断是否有权限操作
	 */
	private boolean havePurview(Long disId, String empSN) {
		return systemBiz.checkEmpPermi(disId, empSN);
	}

	public Result saveDispatch(String empSN, DispatchList dis) throws Exception {
		if (null == dis || StringUtil.isEmpty(empSN)) {
			throw new MyException("A003");
		}
		if (!isEmployee(empSN)) {// 如果如果不是雇员
			throw new MyException("A004");
		}
		disListDao.save(dis);
		return new Result(true, AppConstant.A000);
	}

	@Override
	public Result updateDispatch(String empSN, DispatchListVo dis)
			throws Exception {
		// TODO Auto-generated method stub
		if (null == dis || StringUtil.isEmpty(empSN)) {
			throw new MyException("A003");
		}
		if (!isEmployee(empSN)) {// 如果如果不是雇员
			throw new MyException("A004");
		}
		if (!isValidDispatch(dis.getDlId(), empSN)) {
			throw new MyException("A007");
		}
		if (!havePurview(dis.getDlId(), empSN)) {// 是否有权限操作
			throw new MyException("A007");
		}
		DispatchList disList = disListDao.get(dis.getDlId());
		if (null == disList) {
			throw new MyException("A007");
		}
		disList.setEventExplain(dis.getEventExplain());
		return new Result(true, AppConstant.A000);
	}

	@Override
	public Result updateDisDetail(String empSN, DispatchDetailVo disDetail)
			throws Exception {
		// TODO Auto-generated method stub
		if (StringUtil.isEmpty(empSN) || null == disDetail) {
			throw new MyException("A003");
		}
		if (!isEmployee(empSN)) {// 如果如果不是雇员
			throw new MyException("A004");
		}
		if (!isValidDispatch(disDetail.getSheetId(), empSN)) {
			throw new MyException("A007");
		}
		DispatchDetail disD = disDetailDao.get(disDetail.getDsId());
		if (null == disD || !disD.getFlag()
				|| !disD.getSheetId().equals(disDetail.getSheetId())) {
			throw new MyException("A007");
		}
		if (!havePurview(disDetail.getSheetId(), empSN)) {// 是否有权限操作
			throw new MyException("A007");
		}
		disD.setAccessory(disDetail.getAccessory());
		disD.setCostExplain(disDetail.getCostExplain());
		disD.setItemId(disDetail.getItemId());
		disD.setMoney(disDetail.getMoney());
		return new Result(true, AppConstant.A000);
	}

	@Override
	public Result deleteDispatch(String empSN, Long disId) throws Exception {
		// TODO Auto-generated method stub
		if (StringUtil.isEmpty(empSN) || null == disId) {
			throw new MyException("A003");
		}
		if (!isEmployee(empSN)) {// 如果不是雇员
			throw new MyException("A004");
		}
		if (!havePurview(disId, empSN)) {// 是否有权限操作
			throw new MyException("A007");
		}
		int delDetail = disDetailDao
				.createSQLQuery(
						"update hzy.dispatch_detail set flag=0 where sheet_id=?",
						disId).executeUpdate();
		if (delDetail <= 0) {
			throw new MyException("A008");
		}
		int delDis = disListDao.createSQLQuery(
				"update hzy.dispatch_list set flag=0 where dl_id=?", disId)
				.executeUpdate();
		if (delDis <= 0) {
			throw new MyException("A008");
		}
		return new Result(true, AppConstant.A000);
	}

	@Override
	public Result deleteDisDetail(String empSN, Long disId, Long detailId)
			throws Exception {
		// TODO Auto-generated method stub
		if (StringUtil.isEmpty(empSN) || null == disId) {
			throw new MyException("A003");
		}
		if (!isEmployee(empSN)) {// 如果如果不是雇员
			throw new MyException("A004");
		}
		if (!isValidDispatch(disId, empSN)) {
			throw new MyException("A007");
		}
		if (!havePurview(disId, empSN)) {// 是否有权限操作
			throw new MyException("A007");
		}
		int result = disDetailDao
				.createSQLQuery(
						"update hzy.dispatch_detail set flag=0 where ds_id=? and sheet_id=?",
						detailId, disId).executeUpdate();
		if (result <= 0) {
			throw new MyException("A008");
		}
		return new Result(true, AppConstant.A000);
	}

	public Result saveDisDetail(String empSN, DispatchDetail disDetail)
			throws Exception {
		if (StringUtil.isEmpty(empSN) || null == disDetail) {
			throw new MyException("A003");
		}
		if (!isEmployee(empSN)) {// 如果如果不是雇员
			throw new MyException("A004");
		}
		if (!isValidDispatch(disDetail.getSheetId(), empSN)) {
			throw new MyException("A007");
		}
		if (!havePurview(disDetail.getSheetId(), empSN)) {// 是否有权限操作
			throw new MyException("A007");
		}
		disDetailDao.save(disDetail);
		return new Result(true, AppConstant.A000);
	}

	public Result commitDispatch(String empSN, Long disId) throws Exception {
		if (StringUtil.isEmpty(empSN) || null == disId) {
			throw new MyException("A003");
		}
		String sql = "select * from hzy.SYS_DEPARTMENT where d_id=(select department_id from hzy.SYS_EMPLOYEE where e_sn=?)";
		SysDepartment dept = (SysDepartment) disResultDao.createSQLQuery(sql,
				empSN).addEntity(SysDepartment.class).uniqueResult();
		if (null == dept) {
			throw new MyException("A004");
		}
		List<?> details = systemBiz.getDispatchDetails(disId);
		if (null == details || details.size() <= 0) {// 报销单集合
			throw new MyException("A003");
		}
		DispatchResult dr = new DispatchResult();
		dr.setSheetId(disId);
		dr.setCheckSn(empSN);
		dr.setCheckNext(dept.getManageSn());
		dr.setCheckStatus(1L);
		dr.setCheckTime(new Date());
		disResultDao.save(dr);
		return new Result(true, AppConstant.A000);
	}

	public Page findDisByEmpId(BaseVo vo, String empSN) throws Exception {
		if (StringUtil.isEmpty(empSN) || null == vo) {
			throw new MyException("A003");
		}
		String sql = "select t3.*,t4.money from (select sum(dd.money) money,dd.sheet_id from hzy.dispatch_detail dd group by dd.sheet_id) t4"
				+ " right join "
				+ "(select dl.dl_id,dl.e_sn,e.e_name,dl.create_time,NVL((select da_status from hzy.DISPATCH_STATUS where da_id=t2.check_status),'草稿') status from hzy.dispatch_list dl left join "
				+ " (select * from hzy.dispatch_result dr where dr.check_time=("
				+ " select max(check_time) from hzy.dispatch_result where sheet_id=dr.sheet_id)) t2 "
				+ " on dl.dl_id=t2.sheet_id left join hzy.SYS_EMPLOYEE e on dl.e_sn=e.e_sn  where dl.e_sn=?  and dl.flag=1)t3 "
				+ " on t4.sheet_id=t3.dl_id";
		return empDao.findPageBySQL(vo, sql, empSN);
	}

	public LoginUser login(LoginVo vo) throws Exception {
		if (vo == null || StringUtil.isEmpty(vo.getUname())
				|| StringUtil.isEmpty(vo.getPwd())) {
			throw new MyException("A003");
		}
		LoginUser user = systemBiz.getLoginUserBySN(vo.getUname());
		if (null == user) {
			throw new MyException("A003");
		}
		// SysConfig config = configDao.findUnique(
		// " from SysConfig where user_id=?", user.getUId());
		// Date begin = config.getBegintime();
		// Date end = config.getEndtime();
		// Long center = new Date().getTime();
		// if (begin.getTime() > center || center > end.getTime()) {
		// throw new MyException("A003");
		// }
		if (user.getUPwd().equals(systemBiz.getMD5Encryption(vo.getPwd()))) {
			return user;
		}
		return null;
	}
}
