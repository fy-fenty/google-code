package org.ymm.test;

import java.sql.SQLException;

import org.hibernate.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.ymm.dao.IAreaDao;

public class Test {
	public static void main(String[] args) throws SQLException {
		ApplicationContext cont = new ClassPathXmlApplicationContext(
				new String[] { "hibernate.xml", "beans.xml" });
		IAreaDao iad = cont.getBean("areaDaoImpl", IAreaDao.class);
		System.out.println(iad.getClass());
	}
}
