package org.hzy.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hzy.dao.ISysEmployeeDao;
import org.hzy.entity.SysEmployee;
import org.hzy.support.BaseDAO;

/**
 * @author hzy
 * @date 2012-8-14
 * @extends BaseDAO
 * @class SysEmployeeDaoImpl
 * @description 雇员实现类
 */
public class SysEmployeeDaoImpl extends BaseDAO<SysEmployee, Long> implements ISysEmployeeDao {

	@Override
	public List findAllDispatch(Long dlId) {
		if (dlId == null) {
			return null;
		}
		String sql = "select d.dl_id,d.e_sn,d.create_time,(select sum(dd.money) from hzy.DISPATCH_DETAIL dd where dd.sheet_id = d.dl_id) money,(select d.check_status from hzy.dispatch_result d where d.dr_id = (select max(dr_id) from hzy.dispatch_result where sheet_id = d.dl_id)) status from hzy.dispatch_list d left join hzy.sys_employee s on d.e_sn = s.e_sn where s.e_sn = ?";
		SQLQuery sqlQuery = super.createSQLQuery(sql, dlId);
		sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return sqlQuery.list();
	}
}