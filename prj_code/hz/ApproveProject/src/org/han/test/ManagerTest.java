/**
 * 
 */
package org.han.test;

import org.han.exception.MyException;
import org.han.vo.BaseVo;
import org.han.vo.UserVo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tender.services.IManagerService;
import org.tender.services.ISysService;

/**
 * @author tender  
 * @date 2012-8-18   
 * @ClassName: ManagerTest    
 * @Description: TODO   
 * @version    
 *  
 */
public class ManagerTest {

	/**
	 * @param args  
	 * @Description: TODO
	 * @return void 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "spring-hibernate.xml", "spring-beans.xml" });
		BaseVo vo=new BaseVo();
		IManagerService manger=context.getBean("managerservice",IManagerService.class);
		try {
//			System.out.println(manger.findDepartmentList("10000003", vo).getTotalCount());;
			System.out.println(manger.findWaitMe("10000003", vo).getTotalCount());
//			UserVo uvo=new UserVo();
//			uvo.seteSn("10000000");
//			uvo.setUpwd("yangwenshou");
//			manger.resetPwd("10000003", uvo);
		} catch (MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

	}

}
