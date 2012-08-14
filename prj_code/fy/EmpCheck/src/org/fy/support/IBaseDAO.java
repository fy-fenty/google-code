package org.fy.support;

import java.io.Serializable;
import java.util.Map;

import org.fy.vo.BaseVO;
import org.fy.vo.Page;
import org.hibernate.Query;

/**
 * @author fy
 * @date 2012-8-14
 * @class IBaseDAO
 * @extends ISimpleDAO
 * @description 基础DAO接口,扩展功能包括分页查询,按属性过滤条件列表查询.
 */
public interface IBaseDAO<T, PK extends Serializable> extends ISimpleDAO<T, PK> {

	/**
	 * 设置指定分页参数到 Query 对象中
	 * 
	 * @param query
	 *            要设置的 Query 对象
	 * @param first
	 *            开头条目索引
	 * @param limit
	 *            显示条目数量
	 * @return 设置好分页参数后的 Query 对象
	 */
	public abstract Query setPageParmeter(final Query query, final int first, final int limit);

	/**
	 * 得到执行 sql 语句和若干参数值得到的所有结果的条目数量
	 * <p>
	 * 本函数只能自动处理简单的sql语句,复杂的sql查询请另行编写count语句查询.
	 * </p>
	 * 
	 * @param sql
	 *            需要执行查询的 SQL
	 * @param values
	 *            需要传入的参数值集合
	 * @return 根据给定的参数查询得到的结果
	 */
	public abstract long countSqlResult(final String sql, final Object... values);

	/**
	 * 得到执行 sql 语句和若干键值对应的参数查询得到的所有结果的条目数量
	 * <p>
	 * 本函数只能自动处理简单的sql语句,复杂的sql查询请另行编写count语句查询.
	 * </p>
	 * 
	 * @param sql
	 *            需要执行查询的 SQL
	 * @param values
	 *            需要传入的若干键值对应的参数集合
	 * @return 根据给定的参数查询得到的结果
	 */
	public abstract long countSqlResult(final String sql, final Map<String, Object> values);

	/**
	 * 根据指定 sql 和若干参数值查询分页
	 * 
	 * @param bv
	 *            给定的分页参数
	 * @param sql
	 *            需要执行查询的 SQL
	 * @param values
	 *            需要传入的参数值集合
	 * @return 根据给定的参数查询得到的结果
	 */
	public abstract Page findPageBySql(final BaseVO bv, final String sql, final Object... values);

	/**
	 * 根据指定 sql 和若干键值对应的参数查询分页
	 * 
	 * @param bv
	 *            给定的分页参数
	 * @param sql
	 *            需要执行查询的 SQL
	 * @param values
	 *            需要传入的若干键值对应的参数
	 * @return 根据给定的参数查询得到的结果
	 */
	public abstract Page findPageBySql(final BaseVO bv, final String sql, final Map<String, Object> values);

	/**
	 * 根据指定 sql 和若干参数值得到的单个对象的 Map 形式
	 * 
	 * @param sql
	 *            需要执行查询的 SQL
	 * @param values
	 *            需要传入参数值集合
	 * @return 根据给定的参数查询得到的结果
	 */
	public abstract Map<String, Object> findUniqueBySql(final String sql, final Object... values);

	/**
	 * 根据指定 sql 和若干键值对应的参数得到的单个对象的 Map 形式
	 * 
	 * @param sql
	 *            需要执行查询的 SQL
	 * @param values
	 *            需要传入的若干键值对应的参数
	 * @return 根据给定的参数查询得到的结果
	 */
	public abstract Map<String, Object> findUniqueBySql(final String sql, Map<String, Object> values);
}