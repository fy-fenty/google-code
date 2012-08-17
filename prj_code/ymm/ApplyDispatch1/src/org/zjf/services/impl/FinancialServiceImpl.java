package org.zjf.services.impl;

import org.ymm.entity.DispatchResult;
import org.ymm.entity.SysEmployee;
import org.ymm.entity.SysPositions;
import org.ymm.exception.MyException;
import org.ymm.vo.Page;
import org.ymm.vo.Result;
import org.zjf.services.IFinancialService;

public class FinancialServiceImpl implements IFinancialService{


	@Override
	public SysPositions loginUser(String username, String pwd)
			throws MyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page lookClaims(SysEmployee emp, int start, int limit)
			throws MyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void GeneralExcel(SysEmployee emp) throws MyException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Result applyClaims(SysEmployee emp, DispatchResult result)
			throws MyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result payMent(SysEmployee emp, DispatchResult result)
			throws MyException {
		// TODO Auto-generated method stub
		return null;
	}

}
