/**
 * 
 */
package org.tender.services.impl;

import java.util.Map;

import org.han.dao.IDispatchListDao;
import org.han.exception.MyException;
import org.han.vo.BaseVo;
import org.han.vo.Page;
import org.han.vo.Result;
import org.tender.services.ISysService;

/**
 * @author tender  
 * @date 2012-8-15   
 * @ClassName: SysServiceImpl    
 * @Description: TODO   
 * @version    
 *  
 */
public class SysServiceImpl implements ISysService {
	public IDispatchListDao idl;
	public IDispatchListDao getIdl() {
		return idl;
	}

	public void setIdl(IDispatchListDao idl) {
		this.idl = idl;
	}
	/* (non-Javadoc)
	 * @see org.tender.services.ISysService#checkPermissionsByUserId(java.lang.Long)
	 */

	public Result checkPermissionsByUserId(String empNo,long sheetId) throws MyException {
		// TODO Auto-generated method stub
		String sql=" select * from hzy.dispatch_result where check_next=? and sheet_id=?";
		Map<String,Object>map=this.idl.findUniqueBySQL(sql,empNo,sheetId );
		if(map==null)
		{
			throw new MyException("A001");
		}
		Result re=new Result();
		return re;
//		if(map!=null)
//		{
//			return true;
//		}
//		return false;
	}
	/* (non-Javadoc)
	 * @see org.tender.services.ISysService#findDetailByListId(java.lang.Long)
	 */

	public Page findDetailByListId(Long listId,BaseVo vo)throws MyException {
		// TODO Auto-generated method stub
		String sql=" select * from hzy.dispatch_detail where sheet_id=?";
		return this.idl.findPageBySQL(vo, sql, listId);
	}
	/* (non-Javadoc)
	 * @see org.tender.services.ISysService#findEmpByEmpNo(java.lang.String)
	 */
	
	public Map<String, Object> findEmpByEmpNo(String empNo)throws MyException {
		// TODO Auto-generated method stub
		String sql=" select * from hzy.sys_employee where e_sn=?";
		return this.idl.findUniqueBySQL(sql, empNo);
		
	}
	/* (non-Javadoc)
	 * @see org.tender.services.ISysService#findListByListId(java.lang.Long)
	 */
	
	public Map<String, Object> findListByListId(Long listId) {
		// TODO Auto-generated method stub
		String sql="select * from hzy.dispatch_list where dl_id=?";
		return this.idl.findUniqueBySQL(sql, listId);
	}
	/* (non-Javadoc)
	 * @see org.tender.services.ISysService#findPostByUserId(java.lang.Long)
	 */

	public  Map<String,Object>findPostByUserId(String empNo) throws MyException {
		// TODO Auto-generated method stub
		String sql=" select b.p_name_cn from hzy.sys_employee a left join hzy.sys_positions b on a.p_id=b.p_id where a.e_id=?";
		return this.idl.findUniqueBySQL(sql, empNo);
	}
	/* (non-Javadoc)
	 * @see org.tender.services.ISysService#findResultByListId(java.lang.Long)
	 */

	public Page findResultByListId(Long listId,BaseVo vo) {
		// TODO Auto-generated method stub
		String sql="select * from hzy.dispatch_result where sheet_id=?";
		
		
		return this.idl.findPageBySQL(vo, sql, listId);
	}
	/* (non-Javadoc)
	 * @see org.tender.services.ISysService#findStatusByListId(java.lang.Long)
	 */
	
	public Map<String, Object> findStatusByListId(Long listId) {
		// TODO Auto-generated method stub
		String sql="select * from hzy.dispatch_result b where b.check_time=( select max(check_time) from hzy.dispatch_result a  where a.sheet_id=?)";
		return this.idl.findUniqueBySQL(sql, listId);
	}
	/* (non-Javadoc)
	 * @see org.tender.services.ISysService#findUserByUserId(java.lang.Long)
	 */

	public Map<String, Object> findUserByUserId(Long userId) {
		// TODO Auto-generated method stub
		String sql=" select * from login_user where uid=?";
		return this.idl.findUniqueBySQL(sql, userId);
	}
	/* (non-Javadoc)
	 * @see org.tender.services.ISysService#MD5(java.lang.String)
	 */
	
	public String MD5(String md) {
		// TODO Auto-generated method stub
		byte[] source = md.getBytes();
		String s = null;
		char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
				'e', 'f' };
		try {
			java.security.MessageDigest ss = java.security.MessageDigest
					.getInstance("MD5");
			ss.update(source);
			byte tmp[] = ss.digest(); // MD5 的计算结果是一个 128 位的长整数，
			// 用字节表示就是 16 个字节
			char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
			// 所以表示成 16 进制需要 32 个字符
			int k = 0; // 表示转换结果中对应的字符位置
			for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
				// 转换成 16 进制字符的转换
				byte byte0 = tmp[i]; // 取第 i 个字节
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
				// >>> 为逻辑右移，将符号位一起右移
				str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
			}
			s = new String(str); // 换后的结果转换为字符串

		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
	
	
}

