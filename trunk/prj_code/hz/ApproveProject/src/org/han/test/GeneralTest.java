/**
 * 
 */
package org.han.test;

import org.han.exception.MyException;
import org.han.vo.BaseVo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tender.services.IGeneralManagerService;

/**
 * @author tender  
 * @date 2012-8-19   
 * @ClassName: GeneralTest    
 * @Description: TODO   
 * @version    
 *  
 */
public class GeneralTest {

	/**
	 * @param args  
	 * @Description: TODO
	 * @return void 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
			ApplicationContext context = new ClassPathXmlApplicationContext(
					new String[] { "spring-hibernate.xml", "spring-beans.xml" });
			IGeneralManagerService iss=context.getBean("generalservice",IGeneralManagerService.class);
			BaseVo vo=new BaseVo();
			try {
				iss.findWaitMe("10000003", vo);
			} catch (MyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

}
