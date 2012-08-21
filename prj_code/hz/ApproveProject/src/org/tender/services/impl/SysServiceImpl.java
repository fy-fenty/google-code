/**
 * 
 */
package org.tender.services.impl;

import java.util.Map;

import org.han.dao.IDispatchDetailDao;
import org.han.dao.IDispatchListDao;
import org.han.dao.IDispatchResultDao;
import org.han.dao.IDispatchStatusDao;
import org.han.dao.ISysEmployeeDao;
import org.han.dao.ISysPositionsDao;
import org.han.entity.DispatchDetail;
import org.han.entity.DispatchList;
import org.han.entity.DispatchResult;
import org.han.entity.LoginUser;
import org.han.entity.SysEmployee;
import org.han.entity.SysPositions;
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
	public IDispatchDetailDao detaildao;
	public ISysEmployeeDao empdao;
	public ISysPositionsDao positiondao;
	public IDispatchResultDao resultdao;
	public IDispatchStatusDao statusdao;
	
	public IDispatchDetailDao getDetaildao() {
		return detaildao;
	}

	public void setDetaildao(IDispatchDetailDao detaildao) {
		this.detaildao = detaildao;
	}

	public ISysEmployeeDao getEmpdao() {
		return empdao;
	}

	public void setEmpdao(ISysEmployeeDao empdao) {
		this.empdao = empdao;
	}

	public ISysPositionsDao getPositiondao() {
		return positiondao;
	}

	public void setPositiondao(ISysPositionsDao positiondao) {
		this.positiondao = positiondao;
	}

	public IDispatchResultDao getResultdao() {
		return resultdao;
	}

	public void setResultdao(IDispatchResultDao resultdao) {
		this.resultdao = resultdao;
	}

	public IDispatchStatusDao getStatusdao() {
		return statusdao;
	}

	public void setStatusdao(IDispatchStatusDao statusdao) {
		this.statusdao = statusdao;
	}


	public IDispatchListDao getIdl() {
		return idl;
	}

	public void setIdl(IDispatchListDao idl) {
		this.idl = idl;
	}
	/* (non-Javadoc)
	 * @see org.tender.services.ISysService#checkPermissionsByUserId(java.lang.Long)
	 */
	public boolean chackEmpPermi(Long listId, String empNo) {
		// TODO Auto-generated method stub
		DispatchResult disResult = this.findStatusByListId(listId);
		if (null == disResult) {
			SysEmployee pos=null;
			try {
				pos = findPostByUserId(empNo);
			} catch (MyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (pos.getPId() == 3) {//是否是雇员
				return true;
			}
		} else if (empNo.equals(disResult.getCheckNext())) {
			return true;
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see org.tender.services.ISysService#findDetailByListId(java.lang.Long)
	 */

	public Page findDetailByListId(Long listId,BaseVo vo)throws MyException {
		// TODO Auto-generated method stub
		String sql=" select * from hzy.dispatch_detail where sheet_id=?";
		return this.idl.findPageBySQL(vo, sql, listId);
	}
	
	
	public SysEmployee findEmpByEmpNo(String empNo)throws MyException {
		// TODO Auto-generated method stub
		String sql=" select * from hzy.sys_employee where e_sn=?";
		return (SysEmployee)this.empdao.createSQLQuery(sql,empNo).addEntity(SysEmployee.class).uniqueResult();
		
	}
	/* (non-Javadoc)
	 * @see org.tender.services.ISysService#findListByListId(java.lang.Long)
	 */
	
	public DispatchList findListByListId(Long listId) {
		// TODO Auto-generated method stub
		//and flag=1
		return this.idl.findUnique(
				"from DispatchList where dl_id=? ", listId);
	 	
		
	}
	/* (non-Javadoc)
	 * @see org.tender.services.ISysService#findPostByUserId(java.lang.Long)
	 */

	public SysEmployee findPostByUserId(String empNo) throws MyException {
		// TODO Auto-generated method stub
		//String sql=" select b.p_name_cn from hzy.sys_employee a left join hzy.sys_positions b on a.p_id=b.p_id where a.e_sn=?";
		String sql="select * from hzy.sys_employee a where a.e_sn=?";
		return (SysEmployee)this.empdao.createSQLQuery(sql, empNo).addEntity(SysEmployee.class).uniqueResult();
	}
	/* (non-Javadoc)
	 * @see org.tender.services.ISysService#findResultByListId(java.lang.Long)
	 */

	public Page findResultByListId(Long listId,BaseVo vo) {
		// TODO Auto-generated method stub
		String sql="select * from hzy.dispatch_result where sheet_id=?";
		
		
		return this.resultdao.findPageBySQL(vo, sql, listId);
	}
	/* (non-Javadoc)
	 * @see org.tender.services.ISysService#findStatusByListId(java.lang.Long)
	 */
	
	public DispatchResult findStatusByListId(Long listId) {
		// TODO Auto-generated method stub
		String sql="select * from hzy.dispatch_result b where b.check_time=( select max(check_time) from hzy.dispatch_result a  where a.sheet_id=?)";
		return (DispatchResult)this.statusdao.createSQLQuery(sql, listId).addEntity(DispatchResult.class).uniqueResult();
	}
	/* (non-Javadoc)
	 * @see org.tender.services.ISysService#findUserByUserId(java.lang.Long)
	 */

	public LoginUser findUserByUserId(Long userId) {
		// TODO Auto-generated method stub
		String sql=" select * from login_user where uid=?";
		return (LoginUser)this.idl.createSQLQuery(sql, userId).addEntity(LoginUser.class).uniqueResult();
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

