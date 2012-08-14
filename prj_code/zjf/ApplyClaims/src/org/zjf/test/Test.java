package org.zjf.test;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zjf.support.impl.SimpleDao;

public class Test{

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws SQLException {
		ApplicationContext con=new ClassPathXmlApplicationContext("spring-dao-beans.xml");
		SimpleDao  dao=con.getBean("SimpleDao",SimpleDao.class);
		System.out.println(dao.getSessionfactory().getCurrentSession());
	}
}
