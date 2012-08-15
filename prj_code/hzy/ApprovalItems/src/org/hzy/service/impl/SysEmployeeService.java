package org.hzy.service.impl;

import java.util.List;

import org.hzy.constant.AppConstant;
import org.hzy.dao.ISysEmployeeDao;
import org.hzy.exception.MyException;
import org.hzy.service.ISysEmployeeService;

public class SysEmployeeService implements ISysEmployeeService {

	private ISysEmployeeDao ised;

	@Override
	public List findAllDispatch(Long dlId) throws MyException {
		if (dlId == null) {
			throw new MyException(AppConstant.A001);
		}
		return ised.findAllDispatch(dlId);
	}

	public ISysEmployeeDao getIsed() {
		return ised;
	}

	public void setIsed(ISysEmployeeDao ised) {
		this.ised = ised;
	}

}