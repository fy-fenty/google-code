package org.fy.dao;

import org.fy.entity.SysEmployee;
import org.fy.support.IBaseDAO;
import org.fy.vo.Page;

public interface ISysEmployeeDAO extends IBaseDAO<SysEmployee, Long> {
	public Page findDispathList();
}
