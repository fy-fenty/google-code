/**
 * 
 */
package org.tender.services;

import java.util.Map;

import org.han.exception.MyException;
import org.han.vo.BaseVo;
import org.han.vo.Page;
import org.han.vo.Result;

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
	public Result Approval(String empNo,Long listNo,Long checkStatus)throws MyException;
	
	/**
	 * 
	 * @param empNo
	 * @return  
	 * @Description: 查询待我审核的保险单
	 * @return Page
	 */
	public Page findWaitMe(String empNo,BaseVo vo)throws MyException;
	
	
	
	/**
	 * 
	 * @param listNo
	 * @return  
	 * @Description: 终止已审批报销单
	 * @return Boolean
	 */
	public Result terminationList(Long listNo,String empNo)throws MyException;
//	
//	/**
//	 * 
//	 * @param empNo
//	 * @param pwd
//	 * @return  
//	 * @Description: 总经理登陆
//	 * @return Map<String,Object>
//	 */
//	public Map<String,Object>managerLogin(Long empNo,String pwd);
}
