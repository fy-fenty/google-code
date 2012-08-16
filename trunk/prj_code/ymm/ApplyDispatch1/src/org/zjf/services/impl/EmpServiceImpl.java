package org.zjf.services.impl;

import java.util.List;

import org.ymm.dao.IAreaDao;
import org.ymm.dao.IDispatchDetailDao;
import org.ymm.dao.IDispatchListDao;
import org.ymm.dao.IDispatchResultDao;
import org.ymm.dao.IDispatchStatusDao;
import org.ymm.dao.ISysEmployeeDao;
import org.ymm.entity.DispatchDetail;
import org.ymm.entity.DispatchList;
import org.ymm.entity.DispatchResult;
import org.ymm.entity.DispatchStatus;
import org.ymm.entity.LoginUser;
import org.ymm.entity.SysEmployee;
import org.ymm.entity.SysPositions;
import org.ymm.exception.MyException;
import org.ymm.util.MD5;
import org.ymm.util.StringUtil;
import org.ymm.vo.BaseVo;
import org.ymm.vo.Page;
import org.ymm.vo.Result;
import org.zjf.services.IEmpService;
import org.zjf.services.ISystemService;

/**
 * @project:ApplyDispatch1
 * @author:zjf
 * @date:2012-8-14 下午8:30:09
 * @class:EmpServiceImpl
 * @extends:IEmpService
 * @description:雇员业务接口实现
 */
public class EmpServiceImpl implements IEmpService {

	private ISysEmployeeDao empdao;
	private IAreaDao areadao;
	private IDispatchListDao listdao;
	private IDispatchResultDao resultdao;
	private ISystemService system;
	private IDispatchDetailDao detaildao;
	private IDispatchStatusDao statusdao;

	public IDispatchStatusDao getStatusdao() {
		return statusdao;
	}

	public void setStatusdao(IDispatchStatusDao statusdao) {
		this.statusdao = statusdao;
	}

	public IDispatchDetailDao getDetaildao() {
		return detaildao;
	}

	public void setDetaildao(IDispatchDetailDao detaildao) {
		this.detaildao = detaildao;
	}

	public ISystemService getSystem() {
		return system;
	}

	public void setSystem(ISystemService system) {
		this.system = system;
	}

	public ISysEmployeeDao getEmpdao() {
		return empdao;
	}

	public void setEmpdao(ISysEmployeeDao empdao) {
		this.empdao = empdao;
	}

	public IAreaDao getAreadao() {
		return areadao;
	}

	public void setAreadao(IAreaDao areadao) {
		this.areadao = areadao;
	}

	public IDispatchListDao getListdao() {
		return listdao;
	}

	public void setListdao(IDispatchListDao listdao) {
		this.listdao = listdao;
	}

	public IDispatchResultDao getResultdao() {
		return resultdao;
	}

	public void setResultdao(IDispatchResultDao resultdao) {
		this.resultdao = resultdao;
	}

	@Override
	public Page findAllClaims(final SysEmployee emp, final int start,
			final int limit) throws MyException {
		if (StringUtil.isEmpty(emp.getESn()) == false)
			throw new MyException("A002");
		BaseVo vo = new BaseVo();
		vo.setLimit(limit);
		vo.setStart(start);
		String sql = "select x.E_SN as E_SN,x.E_name as Ename,y.DL_ID as sheet_id,y.create_time as time,NVL(z.money,0) as money,NVL(j.DA_STATUS,'新建')as status"
				+ " from sys_employee x left join"
				+ " DisPATCH_LIST y on x.E_sn=y.E_SN left join (select sheet_id,sum(money)as money from"
				+ " dispatch_detail group by sheet_id) z"
				+ " on y.DL_ID=z.sheet_id left join"
				+ " (select kk.* from(select dr.sheet_id,dr.DR_ID,s.DA_STATUS from (select r.sheet_id,r.DR_ID,r.CHECK_STATUS from ( "
				+ "    select sheet_id,DR_ID,CHECK_STATUS,row_number() over (partition by e.sheet_id order by e.DR_ID desc) rn"
				+ "             from dispatch_result e    "
				+ " ) r where rn =1) dr left join dispatch_status s on dr.CHECK_STATUS=s.DA_ID) kk) j"
				+ " on z.sheet_id=j.sheet_id where x.E_SN=:name and y.flag=1";
		Page page = empdao.findPageBySQL(vo, sql, emp.getESn());
		if (page == null)
			throw new MyException("A003");
		return page;
	}

	@Override
	public SysEmployee findBySn(String SN) throws MyException {
		if (StringUtil.isEmpty(SN) == false)
			throw new MyException("A002");
		String sql = "from SysEmployee where e_sn=?";
		SysEmployee emp = (SysEmployee) empdao.findUnique(sql, SN);
		if (emp == null)
			throw new MyException("A003");
		return emp;
	}

	@Override
	public Result deleteClaims(final SysEmployee emp, long cid)
			throws MyException {
		if (StringUtil.isEmpty(emp.getESn()) == false)
			throw new MyException("A002");
		DispatchList dis = system.findById(cid);
		if (dis == null)
			throw new MyException("A003");
		if (dis.getFlag() == false)
			throw new MyException("A006");
		if (!dis.getESn().equals(emp.getESn()))
			throw new MyException("A005");

		DispatchResult obj = system.findResultById(cid);
		if (obj != null)
			throw new MyException("A005");
		List<DispatchDetail> list = system.findDetailById(cid);
		if (list == null || list.size() <= 0 || deleteDetail(list))
			try {
				dis.setFlag(false);
				listdao.delete(dis);
			} catch (Exception e) {
				throw new MyException("A007");
			}
		Result result=new Result();
		return result;
	}

	private boolean deleteDetail(List<DispatchDetail> list) throws MyException {
		try {
			for (DispatchDetail dispatchDetail : list) {
				dispatchDetail.setFlag(false);
				detaildao.delete(dispatchDetail);
			}
		} catch (Exception e) {
			throw new MyException("A007");
		}
		return true;
	}

	@Override
	public Result saveClaims(final SysEmployee emp, DispatchList cla)
			throws MyException {
		if (cla == null)
			throw new MyException("A003");
		SysEmployee emp1 = empdao.findUnique("from SysEmployee where ESn=?",
				cla.getESn());
		if (emp == null)
			throw new MyException("A003");
		if (!emp.getESn().equals(emp1.getESn()))
			throw new MyException("A005");
		checkEmpPos(emp.getPId());
		try {
			listdao.saveNew(cla);
		} catch (Exception e) {
			throw new MyException("A008");
		}
		Result result=new Result();
		return result;
	}

	@Override
	public Result updateClaims(final SysEmployee emp, DispatchList cla)
			throws MyException {
		if (cla == null)
			throw new MyException("A002");
		DispatchList list = system.findById(cla.getDlId());
		if (list == null)
			throw new MyException("A010");
		if (list.getFlag() == false)
			throw new MyException("A006");
		if (!list.getESn().equals(emp.getESn()))
			throw new MyException("A005");
		DispatchResult result = system.findResultById(cla.getDlId());
		checkStatus(result);
		try {
			listdao.save(cla);
		} catch (Exception e) {
			throw new MyException("A009");
		}
		Result result1=new Result();
		return result1;
	}

	@Override
	public Result updateDetail(final SysEmployee emp, DispatchDetail detail)
			throws MyException {
		if (detail == null)
			throw new MyException("A002");
		DispatchList dis = system.findById(detail.getDsId());
		if (dis.getFlag() == false)
			throw new MyException("A006");
		if (!dis.getESn().equals(emp.getESn()))
			throw new MyException("A005");
		DispatchResult result = system.findResultById(detail.getSheetId());
		checkStatus(result);
		try {
			detaildao.save(detail);
		} catch (Exception e) {
			throw new MyException("A008");
		}
		Result result2=new Result();
		return result2;
	}

	@Override
	public Result deleteDetail(final SysEmployee emp, DispatchDetail detail)
			throws MyException {
		if (detail == null)
			throw new MyException("A002");
		DispatchDetail dd = detaildao.get(detail.getDsId());
		if (dd == null)
			throw new MyException("A003");
		if (dd.getFlag() == false)
			throw new MyException("A006");
		DispatchList dis = system.findById(detail.getDsId());
		if (dis.getFlag() == false)
			throw new MyException("A006");
		if (!dis.getESn().equals(emp.getESn()))
			throw new MyException("A005");
		DispatchResult result = system.findResultById(detail.getSheetId());
		checkStatus(result);
		try {
			detail.setFlag(false);
			detaildao.delete(detail);
		} catch (Exception e) {
			throw new MyException("A007");
		}
		Result result3=new Result();
		return result3;
	}

	@Override
	public Result saveDetail(final SysEmployee emp, DispatchDetail detail)
			throws MyException {
		if (detail == null)
			throw new MyException("A002");
		checkEmpPos(emp.getPId());
		DispatchResult result = system.findResultById(detail.getSheetId());
		checkStatus(result);
		try {
			detaildao.saveNew(detail);
		} catch (Exception e) {
			throw new MyException("A009");
		}
		Result result4=new Result();
		return result4;
	}

	@Override
	public Result commitClaims(final SysEmployee emp, final DispatchResult cla)
			throws Exception {
		
		if (cla == null)
			throw new Exception("A002");
		checkEmpPos(emp.getPId());
		List<DispatchDetail> list = system.findDetailById(cla.getSheetId());
		if (list == null || list.size() <= 0)
			throw new MyException("A010");
		checkEmpPos(emp.getPId());
		try {
			resultdao.save(cla);
		} catch (Exception e) {
			throw new Exception("A008");
		}
		Result result=new Result();
		return result;
	}

	@Override
	public SysPositions loginUser(String username, String pwd)
			throws MyException {
		if (!StringUtil.isEmpty(username) || !StringUtil.isEmpty(pwd))
			throw new MyException("A002");
		LoginUser user = system.findUserBySn(username);
		if (user == null)
			throw new MyException("A003");
		if (!user.getUPwd().equals(MD5.ecodeByMD5(pwd)))
			throw new MyException("A005");
		SysEmployee emp = findBySn(user.getESn());
		SysPositions pos = system.findPositionById(emp.getPId());
		if (pos == null)
			throw new MyException("A003");
		return pos;
	}

	private void checkStatus(DispatchResult result) throws MyException {
		String sql = "from DispatchStatus where daStatus='已打回'";
		DispatchStatus status = statusdao.findUnique(sql);
		if (result != null) {
			if (result.getCheckStatus() != status.getDaId())
				throw new MyException("A005");
		}
	}

	private void checkEmpPos(long p_id) throws MyException {
		SysPositions pos = system.findPositionById(p_id);
		if (pos == null)
			throw new MyException("A003");
		if (!pos.getPNameCn().equals("雇员"))
			throw new MyException("A005");
	}
}
