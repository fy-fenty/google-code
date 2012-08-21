/**
 * 
 */
package org.han.test;

import java.util.Map;

import org.han.entity.DispatchList;
import org.han.entity.DispatchResult;
import org.han.entity.SysEmployee;
import org.han.exception.MyException;
import org.han.vo.BaseVo;
import org.han.vo.Page;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tender.services.ISysService;

/**
 * @author tender  
 * @date 2012-8-15   
 * @ClassName: SysTest    
 * @Description: TODO   
 * @version    
 *  
 */
public class SysTest {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "spring-hibernate.xml", "spring-beans.xml" });
		ISysService iss=context.getBean("sysservices",ISysService.class);
		BaseVo vo=new BaseVo();
//		Map<String, Object> map = null;
//		try {
//			map = iss.findListByListId((long) 2);
//		} catch (MyException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
	//	Map<String,Object> map=iss.findPostByUserId((long)2);
		
//		Page p=iss.findDetailByListId((long)1, vo);
//		System.out.println(p.getTotalCount());
	//	Map<String,Object>map=iss.findStatusByListId((long)2);
//		DispatchList list=null;
//		try {
//			 list= iss.findListByListId((long)2);
//		} catch (MyException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		System.out.println(list.getESn()+"____________");
//		try {
//			System.out.println(iss.findResultByListId((long)2, vo));
//		} catch (MyException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	//	System.out.println(iss.checkPermissionsByUserId("10000001", (long)1));
		
			System.out.println(iss.chackEmpPermi((long)21,"10000000" ));
		
//		SysEmployee emp=null;
//		try {
//			 emp= iss.findPostByUserId("10000000");
//			} catch (MyException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		System.out.println(emp.getPId()+"____________");
	}

}
