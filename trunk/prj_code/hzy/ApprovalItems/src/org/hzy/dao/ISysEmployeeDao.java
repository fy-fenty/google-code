package org.hzy.dao;

import java.util.List;

import org.hzy.entity.SysEmployee;
import org.hzy.support.IBaseDAO;

/**
 * @author hzy
 * @date 2012-8-14
 *	@extends IBaseDAO
 * @class ISysEmployeeDao
 * @description 雇员接口
 */
public interface ISysEmployeeDao {
	public abstract List findAllDispatch(Long dlId);
}