package org.ymm.services.impl;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.ymm.services.IFinancialService;
import org.ymm.services.IManagerService;
import org.ymm.services.ISystemService;
import org.zjf.constant.AppConstant;
import org.zjf.dao.IDispatchResultDao;
import org.zjf.entity.DispatchResult;
import org.zjf.entity.SysEmployee;
import org.zjf.entity.SysPositions;
import org.zjf.exception.MyException;
import org.zjf.util.StringUtil;
import org.zjf.vo.BaseVo;
import org.zjf.vo.DispatchResultVo;
import org.zjf.vo.Page;
import org.zjf.vo.Result;

public class FinancialServiceImpl implements IFinancialService{

	private ISystemService iSystemService;
	private IDispatchResultDao iDispatchResultDao;
	
	public IDispatchResultDao getiDispatchResultDao() {
		return iDispatchResultDao;
	}

	public void setiDispatchResultDao(IDispatchResultDao iDispatchResultDao) {
		this.iDispatchResultDao = iDispatchResultDao;
	}

	public ISystemService getiSystemService() {
		return iSystemService;
	}

	public void setiSystemService(ISystemService iSystemService) {
		this.iSystemService = iSystemService;
	}

	@Override
	public Result applyClaims(SysEmployee sysEmployee, DispatchResultVo vo)
			throws MyException {
		Result result = new Result(true, AppConstant.OTHER_ERROR, "A001");
		if(sysEmployee==null||vo==null){
			return new Result(true, AppConstant.OTHER_ERROR, "A003");
		}
		if(StringUtil.isEmpty(vo.getSheetId()+"")==false){
			return new Result(true, AppConstant.OTHER_ERROR, "A003");
		}
		DispatchResult dr1 = iSystemService.findResultById(vo.getSheetId());
		if (dr1 == null) {
			return new Result(false, AppConstant.UPDATE_ERROR, "A013");
		}
		if(StringUtil.isEmpty(sysEmployee.getESn())==false
				||StringUtil.isEmpty(vo.getCheckStatus()+"")==false){
			return new Result(true, AppConstant.OTHER_ERROR, "A003");
		}
		
		if(!dr1.getCheckNext().equals(sysEmployee.getESn())){
			return new Result(false, AppConstant.UPDATE_ERROR, "A013");
		}
		
		DispatchResult saveDr2=new DispatchResult();
		saveDr2.setSheetId(dr1.getSheetId());
		saveDr2.setCheckTime(new Date());
		saveDr2.setCheckComment(vo.getCheckComment());
		saveDr2.setCheckSn(sysEmployee.getESn());
		
		if(vo.getCheckStatus()==2){
			saveDr2.setCheckStatus((long)5);
		}
		else if(vo.getCheckStatus()==4){
			saveDr2.setCheckStatus((long)4);
			saveDr2.setCheckNext(dr1.getCheckSn());
		}
		else{
			return new Result(false, AppConstant.UPDATE_ERROR, "A013");
		}

		iDispatchResultDao.save(saveDr2);
		return result;
	}

	public static void main(String[] args) throws Exception {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { "spring-sessinfactory.xml",
						"spring-dao-beans.xml", "spring-trans.xml" });
		IFinancialService ies = ac.getBean("financialServiceImpl", IFinancialService.class);
		SysEmployee se=new SysEmployee();
		se.setDepartmentId((long)3);
		se.setPId((long)3);
		se.setESn("xxxx1002");
		DispatchResultVo vo=new DispatchResultVo();
		vo.setDrId((long)41);
		vo.setSheetId((long)25);
		vo.setCheckStatus((long)2);//审批状态
		vo.setCheckComment("待付款xxx");
		System.out.println(ies.applyClaims(se,vo).getException());
		
		/*SysEmployee sysEmployee=new SysEmployee();
		sysEmployee.setDepartmentId((long)1);
		BaseVo bv=new BaseVo(0,100);
		System.out.println(ies.findMyDepartClaims(sysEmployee, bv).getResult().size());*/
	}
	
	@Override
	public SysPositions loginUser(String username, String pwd)
			throws MyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page lookClaims(SysEmployee emp, BaseVo vo) throws MyException {
		
		return null;
	}

	@Override
	public Result payMent(SysEmployee sysEmployee, DispatchResultVo vo)
			throws MyException {
		Result result = new Result(true, AppConstant.OTHER_ERROR, "A001");
		if(sysEmployee==null||vo==null){
			return new Result(true, AppConstant.OTHER_ERROR, "A003");
		}
		if(StringUtil.isEmpty(vo.getSheetId()+"")==false){
			return new Result(true, AppConstant.OTHER_ERROR, "A003");
		}
		DispatchResult dr1 = iSystemService.findResultById(vo.getSheetId());
		if (dr1 == null) {
			return new Result(false, AppConstant.UPDATE_ERROR, "A013");
		}
		if(StringUtil.isEmpty(sysEmployee.getESn())==false
				||StringUtil.isEmpty(dr1.getCheckStatus()+"")==false){
			return new Result(true, AppConstant.OTHER_ERROR, "A003");
		}
		if(dr1.getCheckStatus()!=5){
			return new Result(false, AppConstant.UPDATE_ERROR, "A013");
		}
		
		DispatchResult saveDr2=new DispatchResult();
		saveDr2.setSheetId(dr1.getSheetId());
		saveDr2.setCheckTime(new Date());
		saveDr2.setCheckComment(vo.getCheckComment());
		saveDr2.setCheckSn(sysEmployee.getESn());
		saveDr2.setCheckStatus((long)5);
		
		iDispatchResultDao.save(saveDr2);
		return result;
	}

	
	
	@Override
	public void GeneralExcel(SysEmployee emp) throws MyException {
		// TODO Auto-generated method stub
		
	}
	
	//查询当前审批状态是否是新建
	public Result getDispatchResultEnd(long dl_id) throws MyException{
		DispatchResult dr1 = iSystemService.findResultById(dl_id);
		if (dr1 != null) {
			if (dr1.getCheckStatus() == 4) {
				return new Result(false, AppConstant.UPDATE_ERROR, "A013");
			}
			return new Result(false, AppConstant.UPDATE_ERROR, "A013");
		}
		return new Result(true, AppConstant.DEFAULT_MSG, "A001");
	}

}
