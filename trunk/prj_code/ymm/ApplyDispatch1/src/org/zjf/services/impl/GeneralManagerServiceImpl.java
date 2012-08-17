package org.zjf.services.impl;

import org.ymm.entity.DispatchList;
import org.ymm.entity.DispatchResult;
import org.ymm.entity.SysEmployee;
import org.ymm.entity.SysPositions;
import org.ymm.exception.MyException;
import org.ymm.vo.Page;
import org.ymm.vo.Result;
import org.zjf.services.IGeneralManagerService;

public class GeneralManagerServiceImpl implements IGeneralManagerService {

	@Override
	public Result applyClaims(SysEmployee emp, DispatchResult result)
			throws MyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page findMyApply(SysEmployee emp, int start, int limit)
			throws MyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SysPositions loginUser(String username, String pwd)
			throws MyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result stopClaims(SysEmployee emp, DispatchList list) {
		// TODO Auto-generated method stub
		return null;
	}

}
