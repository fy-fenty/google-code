package org.xuliang.support;

import java.io.Serializable;
import java.util.Map;

import org.hibernate.Query;
import org.xuliang.vo.BaseVO;
import org.xuliang.vo.Page;

/**
 * @author xuliang
 * @date 2012-08-12
 * @class BaseDAO
 * @extends SimpleDAO
 * @description 基础DAO接口,扩展功能包括分页查询,按属性过滤条件列表查询.
 */
public interface IBaseDAO<T, PK extends Serializable> extends ISimpleDAO<T, PK> {
	/**
	 * 设置分页参数到Query对象
	 * 
	 * @param query
	 *            Query对象
	 * @param fisrt
	 *            起始
	 * @param limit
	 *            条数
	 * 
	 * @return Query Query对象
	 */
	public abstract Query setPageParameter(final Query query, final int fisrt,
			final int limit);

	/**
	 * 执行count查询获得本次Sql查询所能获得的对象总数.
	 * <p>
	 * 本函数只能自动处理简单的sql语句,复杂的sql查询请另行编写count语句查询.
	 * 
	 * @param sql
	 *            hql语句
	 * @param values
	 *            参数
	 * 
	 * @return long 条数
	 */
	public abstract long countSqlResult(final String sql,
			final Object... values);

	/**
	 * 执行count查询获得本次Sql查询所能获得的对象总数.
	 * <p>
	 * 本函数只能自动处理简单的sql语句,复杂的sql查询请另行编写count语句查询.
	 * 
	 * @param sql
	 *            hql语句
	 * @param values
	 *            map参数
	 * 
	 * @return long 条数
	 */
	public abstract long countSqlResult(final String sql,
			final Map<String, Object> values);

	/**
	 * 按SQL分页查询
	 * 
	 * @param vo
	 *            分页参数.
	 * @param sql
	 *            sql语句.
	 * @param values
	 *            命名参数,按名称绑定.
	 * 
	 * @return Page 分页查询结果.result里面是Map对象.
	 */
	public abstract Page findPageBySQL(final BaseVO vo, final String sql,
			final Map<String, Object> values);

	/**
	 * 按SQL分页查询
	 * 
	 * @param vo
	 *            分页参数.
	 * @param sql
	 *            sql语句.
	 * @param values
	 *            数量可变的查询参数,按顺序绑定.
	 * 
	 * @return Page 分页查询结果.result里面是Map对象.
	 */
	public abstract Page findPageBySQL(final BaseVO vo, final String sql,
			final Object... values);

	/**
	 * 根据sql查询唯一结果，返回Map对象
	 * 
	 * @param sql
	 *            sql语句.
	 * @param values
	 *            命名参数,按名称绑定.
	 * 
	 * @return Map Map对象.
	 */
	public Map<String, Object> findUniqueBySQL(String sql,
			Map<String, Object> values);

	/**
	 * 根据sql查询唯一结果，返回Map对象
	 * 
	 * @param sql
	 *            sql语句.
	 * @param values
	 *            数量可变的查询参数,按顺序绑定.
	 * 
	 * @return Map Map对象.
	 */
	public abstract Map<String, Object> findUniqueBySQL(final String sql,
			final Object... values);

}