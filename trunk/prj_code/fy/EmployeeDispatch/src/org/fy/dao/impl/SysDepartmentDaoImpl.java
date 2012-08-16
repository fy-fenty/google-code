package org.fy.dao.impl;

import org.fy.dao.IAreaDao;
import org.fy.dao.ISysDepartmentDao;
import org.fy.entity.SysDepartment;
import org.fy.support.BaseDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author fy
 * @date 2012-8-14
 *	@extends  BaseDAO
 * @class SysDepartmentImpl
 * @description 部门接口实现类
 */
public class SysDepartmentDaoImpl extends BaseDAO<SysDepartment, Long> implements ISysDepartmentDao{
	public static void main(String[] args){
		ApplicationContext ctx=new ClassPathXmlApplicationContext("beans1.xml");
		ISysDepartmentDao tu=ctx.getBean("SysDepartmentDao",ISysDepartmentDao.class);
		System.out.println(tu.find("From DispatchStatus").size());
	}
}
