package org.tender.test;

import org.tender.dao.IAreaDao;
import org.tender.dao.ISysEmployeeDao;
import org.tender.entity.Area;
import org.tender.entity.SysEmployee;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author tender  
 * @date 2012-8-14   
 * @ClassName: MyTest    
 * @Description: TODO   
 * @version    
 *
 */
public class MyTest {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "spring-hibernate.xml", "applicationContext.xml" });
		IAreaDao ad=context.getBean("areadao",IAreaDao.class);
		Area a=ad.get(1L);
		System.out.println(a.getAreaName());
	}
}
