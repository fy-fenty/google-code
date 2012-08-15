package org.zjf.support.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.zjf.exception.MyException;
import org.zjf.support.IBaseDao;
import org.zjf.util.StringUtil;
import org.zjf.vo.BaseVO;
import org.zjf.vo.Page;

/**
 * @project:ApplyClaims
 * @author:zjf
 * @date:2012-8-14 上午9:48:20
 * @class:BaseDao
 * @extends:
 * @description:
 */
public class BaseDao<T,PK extends Serializable> extends SimpleDao<T, PK> implements IBaseDao<T,PK> {

	@Override
	public Query setPageParameter(final Query query, final int first,
			final int limit){
		query.setFirstResult(first);
		query.setMaxResults(limit);
		return query;
	}

	@Override
	public long countSqlResult(final String sql, final Object... values) {
		String countSql = "select count(1) from (" + sql + ")";
		Long count = 0L;
		System.out.println(countSql);
		try {
			count = ((Number)createSQLQuery(countSql, values).uniqueResult()).longValue();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return count;
	}

	@Override
	public long countSqlResult(String sql, Map<String, Object> values) {


		String countSql = "select count(1) from (" + sql + ")";
		Long count = 0L;

		try {
			count = (Long) createSQLQuery(countSql, values).uniqueResult();
		} catch (Exception e) {
			
		}
		return count;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Page findPageBySql(BaseVO vo, String sql, Object... values) {

		Page page = null;

		try {
			long totalcount = countSqlResult(sql, values);

			if (totalcount > 0) {
				page = new Page();
				page.setTotalCount(totalcount);
				SQLQuery query = createSQLQuery(sql, values);
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				setPageParameter(query, vo.getStart(), vo.getLimit());
				List result = query.list();
				page.setResult(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Page findPageBySql(BaseVO vo, String sql, Map<String, Object> values){
		Page page = null;
			long totalcount = countSqlResult(sql, values);
			if (totalcount > 0) {
				page = new Page();
				page.setTotalCount(totalcount);
				SQLQuery query = createSQLQuery(sql, values);
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				setPageParameter(query, vo.getStart(), vo.getLimit());
				List result = query.list();
				page.setResult(result);
			}
		return page;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> findUniqueBySQL(String sql,
			Map<String, Object> values){
		Map<String, Object> map = null;
		try {
			SQLQuery query = createSQLQuery(sql, values);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			map = (Map<String, Object>) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> findUniqueBySQL(String sql, Object... values) {
		Map<String, Object> map = null;
		try {
			SQLQuery query = createSQLQuery(sql, values);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			map = (Map<String, Object>) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

}
