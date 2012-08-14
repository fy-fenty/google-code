package org.ymm.support;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.ymm.vo.BaseVo;
import org.ymm.vo.Page;

/** 
 * @author yingmingming 
 * @date 2012-8-14 下午2:42:16
 * @ClassName: BaseDaoImpl 
 * @extends 	 
 * @Description: 基础DAO,扩展功能包括分页查询,按属性过滤条件列表查询.
 */
public class BaseDaoImpl<T,PK extends Serializable> extends SimpleDaoImpl<T, PK> 
	implements IBaseDao<T, PK>{

	@Override
	public Query setPageParameter(final Query query,final int fisrt, int limit) {
		query.setFirstResult(fisrt);
		query.setMaxResults(limit);
		return query;
	}

	@Override
	public long countSqlResult(final String sql,final Object... values) {
		String countSql="select count(1) form ("+sql+")";
		Long count =0L;
		try{
			count=((Number) createSQLQuery(countSql, values).uniqueResult()).longValue();
		}catch(Exception e){
			throw new RuntimeException("sql can't be auto count, hql is:"
					+ countSql, e);
		}
		return count;
	}

	@Override
	public long countSqlResult(final String sql,final Map<String, Object> values) {
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
	public Page findPageBySQL(final BaseVo vo,final String sql,final Map<String, Object> values) {
		Page page=new Page();
		long totalCount=countSqlResult(sql,values);
		page.setTotalCount(totalCount);
		if(totalCount>0){
			//按分页查询结果集
			SQLQuery query=createSQLQuery(sql, values);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			setPageParameter(query, vo.getStart(), vo.getLimit());
			List result=query.list();
			page.setResult(result);
		}
		
		return page;
	}

	@Override
	public Page findPageBySQL(BaseVo vo, String sql, Object... values) {
		
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

	@Override
	public Map<String, Object> findUniqueBySQL(String sql,
			Map<String, Object> values) {
		SQLQuery query =createSQLQuery(sql,values);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (Map<String, Object>)query.uniqueResult();
	}

	@Override
	public Map<String, Object> findUniqueBySQL(String sql, Object... values) {
		SQLQuery query = createSQLQuery(sql, values);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (Map<String, Object>) query.uniqueResult();
	}
}
