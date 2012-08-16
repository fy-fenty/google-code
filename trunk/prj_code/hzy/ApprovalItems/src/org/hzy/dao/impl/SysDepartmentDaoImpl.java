package org.hzy.dao.impl;

import org.hzy.dao.IAreaDao;
import org.hzy.dao.ISysDepartmentDao;
import org.hzy.entity.SysDepartment;
import org.hzy.support.impl.BaseDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author hzy
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
