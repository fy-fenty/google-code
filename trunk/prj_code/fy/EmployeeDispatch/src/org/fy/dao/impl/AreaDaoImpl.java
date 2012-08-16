package org.fy.dao.impl;

import java.util.List;

import org.fy.dao.IAreaDao;
import org.fy.entity.Area;
import org.fy.support.BaseDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author fy
 * @date 2012-8-14
 *	@extends BaseDAO
 * @class AreaDaoImpl
 * @description 区域接口实现类
 */
public class AreaDaoImpl extends BaseDAO<Area, Long> implements IAreaDao{
}
