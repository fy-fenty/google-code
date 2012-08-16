package org.tender.test;

import org.han.services.IEmpService;
import org.han.services.ISystemServices;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tender.entity.DispatchDetail;

/**
 * 
 * @author tender
 * @date 2012-8-14
 * @ClassName: MyTest
 * @Description: TODO
 * @version
 */
public class MyTest {
	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "spring-hibernate.xml", "spring-dao-beans.xml",
						"spring-services-bean.xml" });
		// ISystemServices sysBiz = context.getBean("sysBiz",
		// ISystemServices.class);
		// System.out.println(sysBiz.getDisListById(1l));
		IEmpService empBiz = context.getBean("empBiz", IEmpService.class);
		DispatchDetail detail = new DispatchDetail();
		detail.setSheetId(1L);
		detail.setFlag(true);
		detail.setMoney(60d);
		detail.setItemId(1L);
		System.out.println(empBiz.saveDisDetail("10000000", detail)
				.getSuccess());
	}
}
