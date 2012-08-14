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
			final int limit) throws MyException {

		if (query == null || first < 0 || limit < 0)
			throw new MyException("A002");

		query.setFirstResult(first);
		query.setMaxResults(limit);
		return query;
	}

	@Override
	public long countSqlResult(final String sql, final Object... values)
			throws MyException {

		if (StringUtil.isEmpty(sql) == false)
			throw new MyException("A002");

		String countSql = "select count(1) from (" + sql + ")";
		Long count = 0L;

		try {
			count = (Long) createSQLQuery(countSql, values).uniqueResult();
		} catch (Exception e) {
			throw new MyException("A001");
		}
		return count;
	}

	@Override
	public long countSqlResult(String sql, Map<String, Object> values)
			throws MyException {

		if (StringUtil.isEmpty(sql) == false)
			throw new MyException("A002");

		String countSql = "select count(1) from (" + sql + ")";
		Long count = 0L;

		try {
			count = (Long) createSQLQuery(countSql, values).uniqueResult();
		} catch (Exception e) {
			throw new MyException("A001");
		}
		return count;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Page findPageBySql(BaseVO vo, String sql, Object... values)
			throws MyException {

		if (vo == null || StringUtil.isEmpty(sql) == false)
			throw new MyException("A002");

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
			throw new MyException("A001");
		}
		return page;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Page findPageBySql(BaseVO vo, String sql, Map<String, Object> values)
			throws MyException {

		if (vo == null || StringUtil.isEmpty(sql) == false)
			throw new MyException("A002");

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
			throw new MyException("A001");
		}
		return page;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> findUniqueBySQL(String sql,
			Map<String, Object> values) throws MyException {

		if (StringUtil.isEmpty(sql) == false)
			throw new MyException("A002");

		Map<String, Object> map = null;

		try {
			SQLQuery query = createSQLQuery(sql, values);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			map = (Map<String, Object>) query.uniqueResult();
		} catch (Exception e) {
			throw new MyException("A001");
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> findUniqueBySQL(String sql, Object... values)
			throws MyException {
		if (StringUtil.isEmpty(sql) == false)
			throw new MyException("A002");

		Map<String, Object> map = null;

		try {
			SQLQuery query = createSQLQuery(sql, values);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			map = (Map<String, Object>) query.uniqueResult();
		} catch (Exception e) {
			throw new MyException("A001");
		}
		return map;
	}

}
