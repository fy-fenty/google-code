/**
 * 
 */
package org.tender.services;

import org.han.entity.DispatchList;
import org.han.exception.MyException;
import org.han.vo.BaseVo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author tender  
 * @date 2012-8-16   
 * @ClassName: EmpTest    
 * @Description: TODO   
 * @version    
 *  
 */
public class EmpTest {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "spring-hibernate.xml", "spring-beans.xml" });
		IEmpService iss=context.getBean("empservices",IEmpService.class);
		BaseVo vo=new BaseVo();
//		try {
//			System.out.println(iss.findDispatchByEmpId("10000000", vo).getTotalCount());
//		} catch (MyException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		DispatchList list=new DispatchList();
		list.setESn("10000000");
		list.setEventExplain("sssss");
		try {
			iss.commitDispatch(list, "10000000");
		} catch (MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
