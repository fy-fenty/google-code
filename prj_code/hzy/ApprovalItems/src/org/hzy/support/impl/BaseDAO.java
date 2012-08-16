package org.hzy.support.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hzy.support.IBaseDAO;
import org.hzy.vo.BaseVo;
import org.hzy.vo.Page;

/**
 * @author hzy
 * @date 2012-8-14
 *	@extends  SimpleDAO
 * @class BaseDAO
 * @description 基础DAO,扩展功能包括分页查询,按属性过滤条件列表查询.
 */
@SuppressWarnings("unchecked")
public class BaseDAO<T,PK extends Serializable> extends SimpleDAO<T, PK> implements IBaseDAO<T, PK>{

	public Query setPageParameter(Query query, int first, int limit) {
		query.setFirstResult(first);
		query.setMaxResults(limit);
		return query;
	}

	public long countSqlResult(String sql, Object... values) {
		String countSql="select count(1) from ("+sql+")";
		Long count=0L;
		try {
			count = ((Number) createSQLQuery(countSql, values).uniqueResult())
					.longValue();
		} catch (Exception e) {
			throw new RuntimeException("sql can't be auto count, hql is:"
					+ countSql, e);
		}
		return count;
	}

	public long countSqlResult(String sql, Map<String, Object> values) {
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

	public Page findPageBySQL(BaseVo vo, String sql, Map<String, Object> values) {
		Page page=new Page();
		long totalCount=countSqlResult(sql, values);
		page.setTotalCount(totalCount);
		if(totalCount>0){
			SQLQuery query=createSQLQuery(sql, values);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			setPageParameter(query, vo.getStart(), vo.getLimit());
			List result = query.list();
			page.setResult(result);
		}
		return page;
	}

	public Page findPageBySQL(BaseVo vo, String sql, Object... values) {
		Page page=new Page();
		long totalCount=countSqlResult(sql, values);
		page.setTotalCount(totalCount);
		if (totalCount > 0) {
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
		SQLQuery query=createSQLQuery(sql, values);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (Map<String, Object>) query.uniqueResult();
	}

	public Map<String, Object> findUniqueBySQL(String sql, Object... values) {
		SQLQuery query = createSQLQuery(sql, values);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (Map<String, Object>) query.uniqueResult();
	}
	
}
