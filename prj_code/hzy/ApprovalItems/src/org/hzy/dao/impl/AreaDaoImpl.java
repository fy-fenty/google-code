package org.hzy.dao.impl;

import java.util.List;

import org.hzy.dao.IAreaDao;
import org.hzy.entity.Area;
import org.hzy.support.impl.BaseDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author hzy
 * @date 2012-8-14
 *	@extends BaseDAO
 * @class AreaDaoImpl
 * @description 区域接口实现类
 */
public class AreaDaoImpl extends BaseDAO<Area, Long> implements IAreaDao{
}
