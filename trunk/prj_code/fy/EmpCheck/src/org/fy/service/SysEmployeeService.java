package org.fy.service;

import org.fy.dao.ISysEmployeeDAO;
import org.fy.vo.Page;

public class SysEmployeeService implements ISysEmployeeService {

	private ISysEmployeeDAO sysEmployeeDao;

	@Override
	public Page findDispathList() {
		return sysEmployeeDao.findDispathList();
	}

	public ISysEmployeeDAO getSysEmployeeDao() {
		return sysEmployeeDao;
	}

	public void setSysEmployeeDao(ISysEmployeeDAO sysEmployeeDao) {
		this.sysEmployeeDao = sysEmployeeDao;
	}

}
