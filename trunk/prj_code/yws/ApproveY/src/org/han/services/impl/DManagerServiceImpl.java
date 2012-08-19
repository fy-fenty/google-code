package org.han.services.impl;

import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.han.services.IDManagerService;
import org.han.services.ISystemServices;
import org.tender.constant.AppConstant;
import org.tender.dao.IDispatchListDao;
import org.tender.dao.IDispatchResultDao;
import org.tender.dao.ISysEmployeeDao;
import org.tender.entity.DispatchResult;
import org.tender.exception.MyException;
import org.tender.util.StringUtil;
import org.tender.vo.ApprovalVo;
import org.tender.vo.BaseVo;
import org.tender.vo.Page;
import org.tender.vo.Result;

public class DManagerServiceImpl implements IDManagerService {

	private IDispatchListDao disListDao;
	private ISysEmployeeDao empDao;
	private IDispatchResultDao disResultDao;
	private ISystemServices sysBiz;

	public IDispatchResultDao getDisResultDao() {
		return disResultDao;
	}

	public void setDisResultDao(IDispatchResultDao disResultDao) {
		this.disResultDao = disResultDao;
	}

	public ISystemServices getSysBiz() {
		return sysBiz;
	}

	public void setSysBiz(ISystemServices sysBiz) {
		this.sysBiz = sysBiz;
	}

	public ISysEmployeeDao getEmpDao() {
		return empDao;
	}

	public void setEmpDao(ISysEmployeeDao empDao) {
		this.empDao = empDao;
	}

	public IDispatchListDao getDisListDao() {
		return disListDao;
	}

	public void setDisListDao(IDispatchListDao disListDao) {
		this.disListDao = disListDao;
	}

	@Override
	public Result approval(ApprovalVo vo) throws Exception {
		// TODO Auto-generated method stub
		if (null == vo || StringUtil.isEmpty(vo.getEmpSN())
				|| !StringUtil.isNaN(vo.getDisId() + "")
				|| !StringUtil.isNaN(vo.getDisResul() + "")) {
			throw new MyException("A003");
		}
		String sql = "select t1.m,t2.check_sn c_sn,(select manage_sn from hzy.SYS_DEPARTMENT where d_id=3) f_sn,(select e_sn from hzy.SYS_EMPLOYEE where p_id=1) z_sn from (select sum(money) m,sheet_id from hzy.DISPATCH_DETAIL where flag=1 group by sheet_id) t1 right join"
				+ "(select * from hzy.DISPATCH_RESULT dr where check_next=? and check_status=1 and  dr.check_time="
				+ "(select max(check_time) from hzy.DISPATCH_RESULT where sheet_id=? and sheet_id=dr.sheet_id)) t2  on t1.sheet_id=t2.sheet_id";
		Map data = disListDao
				.findUniqueBySQL(sql, vo.getEmpSN(), vo.getDisId());
		if (null == data || data.size() != 4) {
			throw new MyException("A007");
		}
		String[] result = null;
		if (1 == vo.getDisResul()) {// 同意
			if (Double.valueOf(data.get("M") + "") > 5000) {
				result = new String[] { data.get("Z_SN") + "", "1" };
			} else {
				result = new String[] { data.get("F_SN") + "", "2" };
			}
		} else if (vo.getDisResul() == 2) {// 打回
			result = new String[] { data.get("C_SN") + "", "4" };
		} else if (vo.getDisResul() == 3) {// 终止
			result = new String[] { null, "3" };
		}
		DispatchResult dResult = new DispatchResult();
		dResult.setSheetId(vo.getDisId());
		dResult.setCheckSn(vo.getEmpSN());
		dResult.setCheckNext(result[0]);
		dResult.setCheckStatus(Long.valueOf(result[1]));
		dResult.setCheckTime(new Date());
		dResult.setCheckComment(vo.getComment());
		disResultDao.save(dResult);
		return new Result(true, AppConstant.A000);
	}

	@Override
	public Page findDeptDispatch(String empSN, BaseVo vo) throws Exception {
		// TODO Auto-generated method stub
		if (StringUtil.isEmpty(empSN) || null == vo) {
			throw new MyException("A003");
		}
		String sql = "select t3.*,t4.money from (select sum(dd.money) money,dd.sheet_id from hzy.dispatch_detail dd group by dd.sheet_id) t4"
				+ " right join (select dl.dl_id,dl.e_sn,e.e_name,dl.create_time,t2.check_status from hzy.dispatch_list dl "
				+ " right join  (select * from hzy.dispatch_result dr where dr.check_time=("
				+ " select max(check_time) from hzy.dispatch_result where sheet_id=dr.sheet_id)) t2 "
				+ " on dl.dl_id=t2.sheet_id left join hzy.SYS_EMPLOYEE e on dl.e_sn=e.e_sn "
				+ " where e.department_id=(select department_id from hzy.SYS_EMPLOYEE where e_sn=?  and p_id=2)  and dl.flag=1)t3 "
				+ " on t4.sheet_id=t3.dl_id";
		return disListDao.findPageBySQL(vo, sql, empSN);
	}

	@Override
	public Page findMeApproval(String empSN, BaseVo vo) throws Exception {
		// TODO Auto-generated method stub
		if (StringUtil.isEmpty(empSN) || null == vo) {
			throw new MyException("A003");
		}
		String sql = "select t3.*,t4.money from (select sum(dd.money) money,dd.sheet_id from hzy.dispatch_detail dd group by dd.sheet_id) t4"
				+ " right join (select dl.dl_id,dl.e_sn,e.e_name,dl.create_time,t2.check_status from hzy.dispatch_list dl "
				+ " right join (select * from hzy.dispatch_result dr where dr.check_next=(select e_sn from hzy.SYS_EMPLOYEE where e_sn=? and p_id=2) and dr.check_status=1 and dr.check_time=("
				+ " select max(check_time) from hzy.dispatch_result where sheet_id=dr.sheet_id)) t2 "
				+ " on dl.dl_id=t2.sheet_id left join hzy.SYS_EMPLOYEE e on dl.e_sn=e.e_sn  where dl.flag=1)t3 "
				+ " on t4.sheet_id=t3.dl_id";
		return disListDao.findPageBySQL(vo, sql, empSN);
	}

	public Result resetPwd(String dSN, String empSN) throws Exception {
		// TODO Auto-generated method stub
		if (StringUtil.isEmpty(dSN) || StringUtil.isEmpty(empSN)
				|| dSN.equals(empSN)) {
			throw new MyException("A003");
		}
		String pwdStr = this.getCharacterAndNumber(10);
		System.out.println(pwdStr);
		String pwd = sysBiz.getMD5Encryption(pwdStr);
		String sql = "update hzy.LOGIN_USER set u_pwd=?  where e_sn= (select e_sn from hzy.SYS_EMPLOYEE where e_sn=? and p_id=3 and department_id="
				+ "(select department_id from hzy.SYS_EMPLOYEE where e_sn=?  and p_id=2))";
		int result = empDao.createSQLQuery(sql, pwd, empSN, dSN)
				.executeUpdate();
		if (result != 1) {
			throw new MyException("A009");
		}
		return new Result(true, AppConstant.A000);
	}

	private String getCharacterAndNumber(int length) {
		String val = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字
			if ("char".equalsIgnoreCase(charOrNum)) // 字符串
			{
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
				val += (char) (choice + random.nextInt(26));
			} else if ("num".equalsIgnoreCase(charOrNum)) // 数字
			{
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}
}
