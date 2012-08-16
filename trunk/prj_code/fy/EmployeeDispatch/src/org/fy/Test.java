package org.fy;

import java.util.Date;

import org.fy.entity.DispatchList;
import org.fy.exception.MyExecption;
import org.fy.service.ISysEmployeeService;
import org.fy.service.ISystemService;
import org.fy.vo.BaseVO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static void main(String[] args) throws MyExecption {
		ApplicationContext apc = new ClassPathXmlApplicationContext(
				new String[] { "hibernate-spring.xml", "beans.xml" });
		ISysEmployeeService sf = apc.getBean("sys_employee_service", ISysEmployeeService.class);
//		ISystemService is=apc.getBean("system_service", ISystemService.class);
		BaseVO bv=new BaseVO();
		bv.setStart(0);
		bv.setLimit(5);
//		System.out.println(sf.findDispathList(bv,"10000000").getResult().size());
		
		DispatchList dl=new DispatchList();
		dl.setESn("10000000");
		dl.setCreateTime(new Date());
		dl.setFlag(true);
		System.out.println(sf.saveDispathList(dl));
		
//		System.out.println(is.getMd5("123"));
		
//		System.out.println(sf.findlist(bv).getResult().size());
	}
}