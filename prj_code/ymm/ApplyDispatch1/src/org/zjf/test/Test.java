package org.zjf.test;

import java.sql.SQLException;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.ymm.entity.DispatchList;
import org.ymm.entity.SysEmployee;
import org.ymm.exception.MyException;
import org.zjf.services.IEmpService;

public class Test {

	public static void main(String[] args) throws SQLException, MyException {
		ApplicationContext con = new ClassPathXmlApplicationContext("spring-service-bean.xml");
		IEmpService source = con.getBean("EmpService", IEmpService.class);
		SysEmployee emp=new SysEmployee();
		emp.setESn("10000   ");
		emp.setPId(3L);
		DispatchList list=new DispatchList();
		list.setCreateTime(new Date());
		list.setFlag(true);
		list.setEventExplain("茶饭的钱");
		list.setESn("10000   ");
		System.out.println(source.deleteClaims(emp,1));
	}
}
