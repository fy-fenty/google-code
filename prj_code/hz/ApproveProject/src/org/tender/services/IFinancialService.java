/**
 * 
 */
package org.tender.services;

import org.han.exception.MyException;
import org.han.vo.BaseVo;
import org.han.vo.Page;
import org.han.vo.Result;

/**
 * @author tender  
 * @date 2012-8-15   
 * @ClassName: IFinancialService    
 * @Description: 财务的接口   
 * @version    
 *  
 */
public interface IFinancialService {
	
	/**
	 * 
	 * @return  
	 * @Description: TODO
	 * @return Boolean
	 */
	public Result pay(Long listId,String empNo)throws MyException;
	
	/**
	 * 
	 * @param listId
	 * @param empNo
	 * @return  
	 * @Description: TODO
	 * @return Boolean
	 */
	public Result approval(Long listId,String empNo,Long checkStatus)throws MyException;
	
	/**
	 * 
	 * @return  
	 * @Description: TODO
	 * @return Page
	 */
	public Page findList(String empNo,BaseVo vo)throws MyException;
	
	/**
	 * 
	 * @return  
	 * @Description: TODO
	 * @return Boolean
	 */
	public Result generateReports();
}
