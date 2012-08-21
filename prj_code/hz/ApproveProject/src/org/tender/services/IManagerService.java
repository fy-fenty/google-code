/**
 * 
 */
package org.tender.services;

import java.util.Map;

import org.han.exception.MyException;
import org.han.vo.BaseVo;
import org.han.vo.Page;
import org.han.vo.Result;
import org.han.vo.UserVo;

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
	public Result Approval(Long listId,String empNo,Long checkStatus)throws MyException;
	
	/**
	 * 
	 * @param empNo
	 * @return  
	 * @Description: 查询部门报销单
	 * @return Page
	 */
	public Page findDepartmentList(String empNo,BaseVo vo) throws MyException ;
	
	/**
	 * 
	 * @param empNo
	 * @return  
	 * @Description: 查询待我审核报销单
	 * @return Page
	 */
	public Page findWaitMe(String empNo,BaseVo vo) throws MyException ;
	
	/**
	 * 
	 * @param managerNo
	 * @param empNo
	 * @return  
	 * @Description: 部门经理重置雇员密码
	 * @return Boolean
	 */
	public Result resetPwd(String managerNo,UserVo uvo)throws MyException;
//	
//	/**
//	 * 
//	 * @param empNo
//	 * @param pwd
//	 * @return  
//	 * @Description: 登录
//	 * @return Map<String,Object>
//	 */
//	public Map<String,Object>managerLogin(Long empNo,String pwd);
}
