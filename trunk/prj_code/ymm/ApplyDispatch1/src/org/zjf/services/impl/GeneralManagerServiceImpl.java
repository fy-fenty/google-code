package org.zjf.services.impl;

import java.util.Date;
import java.util.List;

import org.ymm.constant.MyConstant;
import org.ymm.dao.IDispatchListDao;
import org.ymm.dao.IDispatchResultDao;
import org.ymm.dao.ISysEmployeeDao;
import org.ymm.entity.DispatchDetail;
import org.ymm.entity.DispatchList;
import org.ymm.entity.DispatchResult;
import org.ymm.entity.SysEmployee;
import org.ymm.entity.SysPositions;
import org.ymm.exception.MyException;
import org.ymm.util.StringUtil;
import org.ymm.vo.BaseVo;
import org.ymm.vo.DispatchListVo;
import org.ymm.vo.DispatchResultVo;
import org.ymm.vo.Page;
import org.ymm.vo.Result;
import org.zjf.services.IGeneralManagerService;
import org.zjf.services.ISystemService;

public class GeneralManagerServiceImpl implements IGeneralManagerService {

	private ISysEmployeeDao empdao;
	private ISystemService system;
	private IDispatchResultDao resultdao;
	private IDispatchListDao listdao;
	
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

	public ISysEmployeeDao getEmpdao() {
		return empdao;
	}

	public void setEmpdao(ISysEmployeeDao empdao) {
		this.empdao = empdao;
	}

	public ISystemService getSystem() {
		return system;
	}

	public void setSystem(ISystemService system) {
		this.system = system;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result applyClaims(SysEmployee emp, DispatchResultVo cla)
			throws MyException {
		if (emp == null || emp.getPId() == null)
			return getResult("A002");
		if (cla == null || cla.getSheetId() == null)
			return getResult("A002");
		if (StringUtil.isEmpty(emp.getESn()) == false
				|| StringUtil.isEmpty(cla.getCheckNext()) == false)
			return getResult("A002");

		Result res=checkEmpPos(emp.getDepartmentId());
		if(res.getSuccess()==false)
		return res;

		List<DispatchDetail> list = system.findDetailById(cla.getSheetId(), 0,
				999).getResult();
		if (list == null || list.size() <= 0)
			return getResult("A010");

		
		try {
			DispatchResult result=resultdao.get(cla.getSheetId());
			result.setCheckNext(emp.getESn());
			result.setCheckStatus(1L);
			result.setCheckNext(null);
			result.setCheckStatus(2L);
			result.setCheckSn(emp.getESn());
			result.setCheckTime(new Date());
			result.setSheetId(cla.getSheetId());
		} catch (Exception e) {
			return getResult("A008");
		}
		Result result = new Result();
		return result;
	}

	@Override
	public Page findMyApply(SysEmployee emp,BaseVo vo)
			throws MyException {
		
		
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
	public SysPositions loginUser(String username, String pwd)
			throws MyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result stopClaims(SysEmployee emp, DispatchListVo list) throws MyException {
		if(emp==null||emp.getPId()==null)
			return this.getResult("A002");
		if(list.getDlId()==null)
			return this.getResult("A002");
		Result result=this.checkEmpPos(emp.getPId());
		if(result.getSuccess()==false)
			return this.getResult("A005");
		DispatchList dl=listdao.get(list.getDlId());
		dl.setFlag(false);
		Result res=new Result();
		return res;
	}

	private Result checkEmpPos(long p_id) throws MyException {
		SysPositions pos = system.findPositionById(p_id);
		if (pos == null)
			return this.getResult("A003");
		if (!pos.getPNameCn().equals("总经理"))
			return this.getResult("A005");
		Result result=new Result();
		return result;
	}
	private Result getResult(String str){
		Result res=new Result();
		res.setException(str);
		res.setSuccess(false);
		res.setMsg(MyConstant.map.get(str));
		return res;
	}
}
