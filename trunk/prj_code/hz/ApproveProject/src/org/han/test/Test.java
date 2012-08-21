package org.han.test;

import org.han.dao.ISysEmployeeDao;
import org.han.entity.SysEmployee;
import org.han.vo.Page;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tender.services.IEmpService;

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
		IEmpService ies=context.getBean("emps",IEmpService.class);
//		
//		Page page=ies.findDispatch();
//		System.out.println(page.getPageSize()+"++++++++++");
	}
}
