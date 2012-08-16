package org.fy.support;

import java.io.Serializable;
import java.util.Map;

import org.fy.vo.BaseVO;
import org.fy.vo.Page;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

/**
 * @author fy
 * @date 2012-8-14
 * @class BaseDAO
 * @extends IBaseDAO
 * @description 基础DAO,扩展功能包括分页查询,按属性过滤条件列表查询.
 */
@SuppressWarnings("unchecked")
public abstract class BaseDAO<T, PK extends Serializable> extends SimpleDAO<T, PK> implements IBaseDAO<T, PK> {

	@Override
	public Query setPageParmeter(Query query, int first, int limit) {
		query.setFirstResult(first);
		query.setMaxResults(limit);
		return query;
	}

	@Override
	public long countSqlResult(String sql, Object... values) {
		String sqlCount = "select count(1) from (" + sql + ")";
		Long count = 0L;
		try {
			count = ((Number) createSQLQuery(sqlCount, values).uniqueResult()).longValue();
		} catch (Exception e) {
			throw new RuntimeException("sql can't be auto count, sql is" + sqlCount, e);
		}
		return count;
	}

	@Override
	public long countSqlResult(String sql, Map<String, Object> values) {
		String sqlCount = "select count(1) from (" + sql + ")";
		Long count = 0L;
		try {
			count = ((Number) createSQLQuery(sqlCount, values).uniqueResult()).longValue();
		} catch (Exception e) {
			throw new RuntimeException("sql can't be auto count, sql is" + sqlCount, e);
		}
		return count;
	}

	@Override
	public Page findPageBySql(BaseVO bv, String sql, Object... values) {
		Page pb = new Page();
		Long count = countSqlResult(sql, values);
		if (count == 0) {
			return pb;
		}
		SQLQuery sqlQuery = createSQLQuery(sql, values);
		sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		setPageParmeter(sqlQuery, bv.getStart(), bv.getLimit());
		pb.setResult(sqlQuery.list());
		return pb;
	}

	@Override
	public Page findPageBySql(BaseVO bv, String sql, Map<String, Object> values) {
		Page pb = new Page();
		Long count = countSqlResult(sql, values);
		if (count == 0) {
			return pb;
		}
		SQLQuery sqlQuery = createSQLQuery(sql, values);
		sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		setPageParmeter(sqlQuery, bv.getStart(), bv.getLimit());
		pb.setResult(sqlQuery.list());
		return pb;
	}

	@Override
	public Map<String, Object> findUniqueBySql(String sql, Object... values) {
		SQLQuery sqlQuery = createSQLQuery(sql, values);
		sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (Map<String, Object>) sqlQuery.uniqueResult();
	}

	@Override
	public Map<String, Object> findUniqueBySql(String sql, Map<String, Object> values) {
		SQLQuery sqlQuery = createSQLQuery(sql, values);
		sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (Map<String, Object>) sqlQuery.uniqueResult();
	}

}
