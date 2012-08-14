package org.fy.dao;

import org.fy.entity.SysEmployee;
import org.fy.support.BaseDAO;
import org.fy.vo.Page;

public class SysEmployeeDAO extends BaseDAO<SysEmployee, Long> implements ISysEmployeeDAO {

	@Override
	public Page findDispathList() {
		return null;
	}

}
