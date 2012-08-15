package org.ymm.services;

import org.zjf.vo.Page;

public interface IEmployeeService {
	/**
	 * @Title: selectDis_listByE_SN 
	 * @param @param E_SN
	 * @param @return
	 * @return Page     
	 * @Description: 通过员工编号查询员工报销单
	 * @throws
	 */
	public Page selectDis_listByE_SN(final String E_SN,final int start,final int limit);
	
	
}
