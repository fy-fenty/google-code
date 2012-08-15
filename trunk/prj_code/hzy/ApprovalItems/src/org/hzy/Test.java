package org.hzy;

import org.hzy.exception.MyException;
import org.hzy.service.ISysEmployeeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static void main(String[] args) throws MyException {
		ApplicationContext actc = new ClassPathXmlApplicationContext(new String[]{
				"hibernate-spring.xml","beans1.xml"
		});
		ISysEmployeeService is = actc.getBean("SysEmployeeService",ISysEmployeeService.class);
		System.out.println(is.findAllDispatch(10000L));
	}
}