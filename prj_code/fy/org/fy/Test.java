package org.fy;

import org.fy.service.ISysEmployeeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static void main(String[] args) {
		ApplicationContext apc = new ClassPathXmlApplicationContext(
				new String[] { "hibernate-spring.xml", "beans.xml" });
		ISysEmployeeService sf = apc.getBean("sys_employee_service", ISysEmployeeService.class);
		sf.findDispathList();
	}
}