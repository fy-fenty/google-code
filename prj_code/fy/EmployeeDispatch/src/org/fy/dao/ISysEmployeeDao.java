package org.fy.dao;

import org.fy.entity.SysEmployee;
import org.fy.support.IBaseDAO;

/**
 * @author fy
 * @date 2012-8-14
 *	@extends IBaseDAO
 * @class ISysEmployeeDao
 * @description 雇员接口
 */
public interface ISysEmployeeDao extends IBaseDAO<SysEmployee, Long> {
	public abstract SysEmployee getgEmpById(Long id);
}
