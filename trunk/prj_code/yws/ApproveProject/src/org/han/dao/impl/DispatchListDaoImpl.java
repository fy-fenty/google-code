/**
 * 
 */
package org.han.dao.impl;

import org.han.dao.IDispatchListDao;
import org.han.entity.DispatchList;
import org.han.support.impl.BaseDaoImpl;
import org.hibernate.Session;

/**
 * @author tender
 * @date 2012-8-14
 * @ClassName: DispatchListDao
 * @extends BaseDAO
 * @Description: 对IDispatchListDao接口的实现
 * @version
 * 
 */
public class DispatchListDaoImpl extends BaseDaoImpl<DispatchList, Long>
		implements IDispatchListDao {

	public Long saveDispatch(DispatchList dis) {
		Session session = super.sessionFactory.getCurrentSession();
		Long result = (Long) session.save(dis);
		return result;
	}
}
