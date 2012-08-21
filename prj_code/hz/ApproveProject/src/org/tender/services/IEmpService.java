/**
 * 
 */
package org.tender.services;

import java.util.Map;

import org.han.entity.DispatchDetail;
import org.han.entity.DispatchList;
import org.han.exception.MyException;
import org.han.vo.BaseVo;
import org.han.vo.DetailVo;
import org.han.vo.DispatchVo;
import org.han.vo.Page;
import org.han.vo.Result;

/**
 * @author tender  
 * @date 2012-8-15   
 * @ClassName: IEmpService    
 * @Description: 定义员工所有的接口   
 * @version    
 *  
 */
public interface IEmpService {
	/**
	 * 
	 * @param uid
	 * @param upwd
	 * @return
	 * @throws MyException  
	 * @Description: TODO
	 * @return Map<String,Object>
	 */
	public  Map<String,Object>  empLogin(Long uid,String upwd )throws MyException;

	/**
	 * 
	 * @param empId
	 * @return  
	 * @Description: 查询雇员报销单
	 * @return Page
	 */
	public  Page findDispatchByEmpId(String empNo,BaseVo vo)throws MyException;
	
	/**
	 * 
	 * @param list
	 * @return  
	 * @Description: 修改报销单
	 * @return Boolean
	 */
	public Result updateDispatch(DispatchVo dvo,String empNo)throws MyException;
	/**
	 * 
	 * @param list
	 * @return  
	 * @Description: 提交报销单
	 * @return Boolean
	 */
	public Result commitDispatch(DispatchList list,String empNo)throws MyException;
	
	/**
	 * 
	 * @param list
	 * @return  
	 * @Description: 保持报销单
	 * @return Boolean
	 */
	public Result saveDispatch(DispatchList list,String empNo)throws MyException ;

	/**
	 * 
	 * @param list
	 * @return  
	 * @Description: 删除报销单
	 * @return Boolean
	 */
	public Result delDispatch(Long listId,String empNo)throws MyException;
	
	/**
	 * 
	 * @param detail
	 * @return  
	 * @Description: 修改报销单明细
	 * @return Boolean
	 */
	public Result updateDetail(DetailVo dvo,Long listId,String empNo)throws MyException;
	
	/**
	 * 
	 * @param detail
	 * @return  
	 * @Description: 删除报销单明细
	 * @return Boolean
	 */
	public Result delDetail(Long listId,String empNo)throws MyException;
	
	/**
	 * 
	 * @param detail
	 * @return  
	 * @Description: 增加报销单明细
	 * @return Boolean
	 */
	public Result addDetail(DispatchDetail detail,String empNo,Long listId)throws MyException;
}
