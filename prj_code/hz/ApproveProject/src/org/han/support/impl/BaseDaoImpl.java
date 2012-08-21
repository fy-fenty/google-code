package org.han.support.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.han.support.IBaseDao;
import org.han.vo.BaseVo;
import org.han.vo.Page;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

/**
 * @author HanZhou
 * @className: BaseDAO
 * @date 2012-8-14
 * @extends SimpleDAO<T, PK>
 * @description: 基础DAO,扩展功能包括分页查询,按属性过滤条件列表查询.
 */
@SuppressWarnings("unchecked")
public class BaseDaoImpl<T, PK extends Serializable> extends SimpleDaoImpl<T, PK>
		implements IBaseDao<T, PK> {

	@Override
	public long countSqlResult(final String sql, final Object... values) {
		// TODO Auto-generated method stub
		String countSql = "select count(1) from (" + sql + ")";
		Long count = 0L;
		try {
			count = ((Number) createSQLQuery(countSql, values).uniqueResult())
					.longValue();
		} catch (Exception e) {
			throw new RuntimeException("sql can't be auto count, hql is:"
					+ countSql, e);
		}
		return count;
	}

	@Override
	public long countSqlResult(final String sql,final  Map<String, Object> values) {
		// TODO Auto-generated method stub
		String countSql = "select count(1) from (" + sql + ")";
		Long count = 0L;
		try {
			count = ((Number) createSQLQuery(countSql, values).uniqueResult())
					.longValue();
		} catch (Exception e) {
			throw new RuntimeException("sql can't be auto count, hql is:"
					+ countSql, e);
		}
		return count;
	}

	@Override
	public Page findPageBySQL(final BaseVo vo, final String sql, final Map<String, Object> values) {
		// TODO Auto-generated method stub
		Page page = new Page();
		// count查询
		long totalCount = countSqlResult(sql, values);
		page.setTotalCount(totalCount);

		if (totalCount > 0) {
			// 按分页查询结果集
			SQLQuery query = createSQLQuery(sql, values);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			setPageParameter(query, vo.getStart(), vo.getLimit());
			List result = query.list();
			page.setData(result);
		}
		return page;
	}

	@Override
	public Page findPageBySQL(final BaseVo vo, final String sql, final Object... values) {
		// TODO Auto-generated method stub
		Page page = new Page();
		// count查询
		long totalCount = countSqlResult(sql, values);
		page.setTotalCount(totalCount);

		if (totalCount > 0) {
			// 按分页查询结果集
			SQLQuery query = createSQLQuery(sql, values);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			setPageParameter(query, vo.getStart(), vo.getLimit());
			List result = query.list();
			page.setData(result);
		}
		return page;
	}

	@Override
	public Map<String, Object> findUniqueBySQL(final String sql,
			final Map<String, Object> values) {
		// TODO Auto-generated method stub
		SQLQuery query = createSQLQuery(sql, values);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (Map<String, Object>) query.uniqueResult();
	}

	@Override
	public Map<String, Object> findUniqueBySQL(final String sql, final Object... values) {
		// TODO Auto-generated method stub
		SQLQuery query = createSQLQuery(sql, values);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (Map<String, Object>) query.uniqueResult();
	}

	@Override
	public Query setPageParameter(final Query query, final int fisrt, final int limit) {
		// hibernate的firstResult的序号从0开始
		query.setFirstResult(fisrt);
		query.setMaxResults(limit);
		return query;
	}

}
