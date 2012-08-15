package org.ymm.services.impl;

import java.util.List;

import org.ymm.services.IEmployeeService;
import org.zjf.entity.SysEmployee;
import org.zjf.support.impl.BaseDao;
import org.zjf.vo.BaseVO;
import org.zjf.vo.Page;

public class EmployeeServiceImpl implements IEmployeeService {

	protected  BaseDao baseDao;
	
	@Override
	public Page selectDis_listByE_SN(final String E_SN,final int start,final int limit) {
		String sql="select t1.*,t2.money,t3.cs as status from " +
				" (select dl.dl_id,dl.e_sn,se.e_name,dl.create_time,dl.flag from dispatch_list dl left join sys_employee se on dl.e_sn=se.e_sn) t1 "+
				" left join "+
				" (select dd.sheet_id, sum(money) as money from dispatch_detail dd group by dd.sheet_id) t2 "+
				"  on t1.dl_id=t2.sheet_id "+
				" left join "+
				" (select d1.sheet_id,(select ds1.da_status from dispatch_status ds1 where ds1.da_id=d1.check_status) as cs from dispatch_result d1 where check_time =(select max(check_time) from dispatch_result d where d.sheet_id=d1.sheet_id)) t3 "+
				"  on t3.sheet_id=t2.sheet_id where t1.e_sn=?";
		BaseVO vo=new BaseVO();
		vo.setLimit(limit);
		vo.setStart(start);
		Page page= baseDao.findPageBySql(vo, sql, E_SN);
		return page;
	}
	
	
	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	
}
