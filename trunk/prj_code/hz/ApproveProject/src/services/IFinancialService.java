/**
 * 
 */
package org.tender.services;

import org.han.vo.Page;

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
	public Boolean pay();
	
	/**
	 * 
	 * @param listId
	 * @param empNo
	 * @return  
	 * @Description: TODO
	 * @return Boolean
	 */
	public Boolean approval(Long listId,String empNo);
	
	/**
	 * 
	 * @return  
	 * @Description: TODO
	 * @return Page
	 */
	public Page findList();
	
	/**
	 * 
	 * @return  
	 * @Description: TODO
	 * @return Boolean
	 */
	public Boolean generateReports();
}
