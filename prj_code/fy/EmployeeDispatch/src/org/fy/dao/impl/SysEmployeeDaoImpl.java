package org.fy.dao.impl;

import org.fy.dao.ISysEmployeeDao;
import org.fy.entity.SysEmployee;
import org.fy.support.BaseDAO;

/**
 * @author fy
 * @date 2012-8-14
 *	@extends BaseDAO
 * @class SysEmployeeDaoImpl
 * @description 雇员实现类
 */
public class SysEmployeeDaoImpl extends BaseDAO<SysEmployee, Long> implements ISysEmployeeDao{

	public SysEmployee getgEmpById(Long id) {
		return super.get(id);
	}

}
