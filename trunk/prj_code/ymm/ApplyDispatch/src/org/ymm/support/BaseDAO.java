package org.ymm.support;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.ymm.vo.Page;

/**
 * @author xuliang
 * @date 2012-08-12
 * @class BaseDAO
 * @extends SimpleDAOT
 * @description 基础DAO,扩展功能包括分页查询,按属性过滤条件列表查询.
 */

@SuppressWarnings("unchecked")
public class BaseDAO<T, PK extends Serializable> extends SimpleDAO<T, PK>
		implements IBaseDAO<T, PK> {

	public Query setPageParameter(final Query query, final int fisrt,
			final int limit) {
		// hibernate的firstResult的序号从0开始
		query.setFirstResult(fisrt);
		query.setMaxResults(limit);
		return query;
	}

	public long countSqlResult(final String sql, final Object... values) {

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

	public long countSqlResult(final String sql,
			final Map<String, Object> values) {
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

	public Page findPageBySQL(BaseVO vo, String sql, Map<String, Object> values) {
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
			page.setResult(result);
		}

		return page;
	}

	public Page findPageBySQL(BaseVO vo, String sql, Object... values) {
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
			page.setResult(result);
		}

		return page;
	}

	public Map<String, Object> findUniqueBySQL(String sql,
			Map<String, Object> values) {
		SQLQuery query = createSQLQuery(sql, values);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (Map<String, Object>) query.uniqueResult();
	}

	public Map<String, Object> findUniqueBySQL(String sql, Object... values) {
		SQLQuery query = createSQLQuery(sql, values);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (Map<String, Object>) query.uniqueResult();
	}
}
