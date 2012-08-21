/**
 * 
 */
package org.han.test;

import org.han.entity.DispatchDetail;
import org.han.entity.DispatchList;
import org.han.entity.SysEmployee;
import org.han.exception.MyException;
import org.han.vo.BaseVo;
import org.han.vo.DetailVo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tender.services.IEmpService;

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
		//SysEmployee  emp=iss.
//		DispatchList list=new DispatchList();
//		list.setDlId((long)21);
//		list.setESn("10000000");
//		list.setEventExplain("ddd3333");
//		try {
//			iss.updateDispatch(list, "10000000");
//		} catch (MyException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		DetailVo detail=new DetailVo();
		detail.setAccessory("ssssssssssss");
		detail.setMoney(1111d);
		detail.setDetailId(22L);
		try {
			iss.updateDetail(detail,22L,"10000000" );
		} catch (MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
