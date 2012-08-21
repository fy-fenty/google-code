package org.han.test;

import org.han.dao.ISysEmployeeDao;
import org.han.entity.SysEmployee;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @ClassName: Test
 * @Description: TODO
 * @author HanZhou
 * @date 2012-8-13
 */
public class Test {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "spring-hibernate.xml", "spring-beans.xml" });
		ISysEmployeeDao empDao = context.getBean("empdao",
				ISysEmployeeDao.class);
		SysEmployee emp = empDao.get(1L);
		System.out.println(emp.getEName());
	}
}
