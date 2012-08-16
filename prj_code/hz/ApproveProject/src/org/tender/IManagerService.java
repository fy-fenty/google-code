/**
 * 
 */
package org.tender.services;

import java.util.Map;

import org.han.vo.Page;

/**
 * @author tender  
 * @date 2012-8-15   
 * @ClassName: IManagerService    
 * @Description: 部门经理service   
 * @version    
 *  
 */
public interface IManagerService {
	
	/**
	 * 
	 * @param listId
	 * @param empNo
	 * @return  
	 * @Description: 部门经理审批
	 * @return Boolean
	 */
	public Boolean Approval(Long listId,String empNo);
	
	/**
	 * 
	 * @param empNo
	 * @return  
	 * @Description: 查询部门报销单
	 * @return Page
	 */
	public Page findDepartmentList(String empNo);
	
	/**
	 * 
	 * @param empNo
	 * @return  
	 * @Description: 查询待我审核报销单
	 * @return Page
	 */
	public Page findWaitMe(String empNo);
	
	/**
	 * 
	 * @param managerNo
	 * @param empNo
	 * @return  
	 * @Description: 部门经理重置雇员密码
	 * @return Boolean
	 */
	public Boolean resetPwd(String managerNo,String empNo);
	
	/**
	 * 
	 * @param empNo
	 * @param pwd
	 * @return  
	 * @Description: 登录
	 * @return Map<String,Object>
	 */
	public Map<String,Object>managerLogin(Long empNo,String pwd);
}
