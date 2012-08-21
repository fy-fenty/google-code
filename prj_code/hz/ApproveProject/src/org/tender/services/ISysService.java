/**
 * 
 */
package org.tender.services;

import java.util.Map;

import org.han.entity.DispatchList;
import org.han.entity.DispatchResult;
import org.han.entity.DispatchStatus;
import org.han.entity.LoginUser;
import org.han.entity.SysEmployee;
import org.han.entity.SysPositions;
import org.han.exception.MyException;
import org.han.vo.BaseVo;
import org.han.vo.Page;
import org.han.vo.Result;

/**
 * @author tender  
 * @date 2012-8-15   
 * @ClassName: ISysService    
 * @Description: TODO   
 * @version    
 *  
 */
public interface ISysService {
	/**
	 * 
	 * @param listId(报销单id)
	 * @return  
	 * @Description: 查询制定报销单
	 * @return Map<String,Object>
	 */
	public DispatchList findListByListId(Long listId) throws MyException;
	
	/**
	 * 
	 * @param listId(报销单id)
	 * @return  
	 * @Description: 查询制定报销单明细
	 * @return Map<String,Object>
	 */
	public Page findDetailByListId(Long listId,BaseVo vo) throws MyException;
	
	/**
	 * 
	 * @param listId
	 * @return  
	 * @Description: 查询制定报销单流程
	 * @return Map<String,Object>
	 */
	public Page findResultByListId(Long listId,BaseVo vo) throws MyException;
	
	/**
	 * 
	 * @param listId
	 * @return  
	 * @Description: 查询制定报销单当前审批记录
	 * @return Map<String,Object>
	 */
	public DispatchResult findStatusByListId(Long listId) throws MyException;
	
	/**
	 * 
	 * @param userId
	 * @return  
	 * @Description: 验证报销单权限
	 * @return Boolean
	 */
	public boolean chackEmpPermi(Long listId, String empNo);
	
	/**
	 * 
	 * @param md
	 * @return  
	 * @Description: MD5加密
	 * @return String
	 */
	public String MD5(String md);
	
	/**
	 * 
	 * @param userId
	 * @return  
	 * @Description: 根据用户编号查询用户
	 * @return Map<String,Object>
	 */
	public LoginUser findUserByUserId(Long userId) throws MyException;
	
	/**
	 * 
	 * @param userId
	 * @return  
	 * @Description: 根据用户id查询用户职位
	 * @return Map<String,Object>
	 */
	public SysEmployee findPostByUserId(String empNo) throws MyException;
	
	/**
	 * 
	 * @param empId
	 * @return  
	 * @Description: 根据雇员编号查询雇员
	 * @return Map<String,Object>
	 */
	public SysEmployee findEmpByEmpNo(String empNo) throws MyException;

	/**
	 * @param empNo
	 * @return  
	 * @Description: TODO
	 * @return Map<String,Object> 
	 */
	
}
