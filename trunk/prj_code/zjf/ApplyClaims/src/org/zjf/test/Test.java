package org.zjf.test;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.ymm.services.IEmployeeService;

/**
 * @project:ApplyClaims
 * @author:zjf
 * @date:2012-8-14 下午12:36:53   
 * @class:Test
 * @extends:Object
 * @description:测试类
 */
public class Test{
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws SQLException {
		ApplicationContext con=new ClassPathXmlApplicationContext(new String[]{"spring-sessinfactory.xml","spring-dao-beans.xml","spring-trans.xml"});
		IEmployeeService  dao=con.getBean("employeeServiceDaoImpl",IEmployeeService.class);
		System.out.println(dao.selectDis_listByE_SN("10000   ", 0, 10).getTotalCount());
	}
}
