/**
 * 
 */
package org.tender.services;

import java.util.Map;

import org.han.vo.Page;

/**
 * @author tender  
 * @date 2012-8-15   
 * @ClassName: IGeneralManagerService    
 * @Description: 总经理接口   
 * @version    
 *  
 */
public interface IGeneralManagerService {
	
	/**
	 * 
	 * @param empNo
	 * @param listNo
	 * @return  
	 * @Description: 总经理审批
	 * @return Boolean
	 */
	public Boolean Approval(String empNo,Long listNo);
	
	/**
	 * 
	 * @param empNo
	 * @return  
	 * @Description: 查询待我审核的保险单
	 * @return Page
	 */
	public Page findWaitMe(String empNo);
	
	
	
	/**
	 * 
	 * @param listNo
	 * @return  
	 * @Description: 终止已审批报销单
	 * @return Boolean
	 */
	public Boolean terminationList(Long listNo);
	
	/**
	 * 
	 * @param empNo
	 * @param pwd
	 * @return  
	 * @Description: 总经理登陆
	 * @return Map<String,Object>
	 */
	public Map<String,Object>managerLogin(Long empNo,String pwd);
}
