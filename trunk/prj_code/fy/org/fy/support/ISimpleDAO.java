package org.fy.support;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 * @author fy
 * @date 2012-8-14
 * @class ISimpleDAO
 * @extends Object
 * @description 底层DAO封装接口类
 */
public interface ISimpleDAO<T, PK extends Serializable> {
	/**
	 * 取得当前 session
	 * 
	 * @return 当前 session
	 */
	public abstract Session getSession();

	/**
	 * 保存或新增或修改目标对象
	 * 
	 * @param entity
	 *            需要操作的对象
	 */
	public abstract void save(final T entity);

	/**
	 * 保存目标对象并返回保存之后的持久对象
	 * 
	 * @param entity
	 *            需要保存的瞬时对象
	 * @return 修改后的持久对象
	 */
	public abstract T saveNew(final T entity);

	/**
	 * 删除目标对象
	 * 
	 * @param entity
	 *            需要删除的对象，目标必须包含在 session 中或者持有 ID 属性的瞬时对象
	 */
	public abstract void delete(final T entity);

	/**
	 * 删除指定 ID 的对象
	 * 
	 * @param id
	 *            需要删除的对象 ID
	 */
	public abstract void delete(final PK id);

	/**
	 * 返回指定 ID 的对象
	 * 
	 * @param id
	 *            需要返回的对象 ID
	 * @return 根据给定的参数查询得到的结果
	 */
	public abstract T get(final PK id);

	/**
	 * 按照类和 ID 获得对象
	 * 
	 * @param klass
	 *            需要查找的对象的 Class
	 * @param id
	 *            需要查找的对象的 ID
	 * @return 根据给定的参数查询得到的结果
	 */
	public abstract <X> X get(final Class<X> klass, final Serializable id);

	/**
	 * 根据指定 HQL 查询对象集合
	 * 
	 * @param hql
	 *            需要执行查询的 HQL
	 * @return 根据给定的参数查询得到的结果
	 */
	public abstract <X> List<X> find(final String hql);

	/**
	 * 根据指定 HQL 和若干参数值查询对象集合
	 * 
	 * @param hql
	 *            需要执行查询的 HQL
	 * @param values
	 *            需要传入的参数值集合
	 * @return 根据给定的参数查询得到的结果
	 */
	public abstract <X> List<X> find(final String hql, final Object... values);

	/**
	 * 根据指定 HQL 和若干键值对应的参数查询对象集合
	 * 
	 * @param hql
	 *            需要执行查询的 HQL
	 * @param values
	 *            需要传入的键值对应的参数集合
	 * @return 根据给定的参数查询得到的结果
	 */
	public abstract <X> List<X> find(final String hql, final Map<String, Object> values);

	/**
	 * 根据指定 HQL 和若干参数值查询单个对象
	 * 
	 * @param hql
	 *            需要执行查询的 HQL
	 * @param values
	 *            需要传入的参数值集合
	 * @return 根据给定的参数查询得到的结果
	 */
	public abstract <X> X findUnique(final String hql, Object... values);

	/**
	 * 根据指定 HQL 和若干键值对应的参数查询单个集合
	 * 
	 * @param hql
	 *            需要执行查询的 HQL
	 * @param values
	 *            需要传入的键值对应的参数集合
	 * @return 根据给定的参数查询得到的结果
	 */
	public abstract <X> X findUnique(final String hql, final Map<String, Object> values);

	/**
	 * 根据指定 HQL 和若干参数值创建 Query
	 * 
	 * @param hql
	 *            需要执行查询的 Hql
	 * @param values
	 *            需要传入的参数值集合
	 * @return 根据指定条件创建的 Query
	 */
	public abstract Query createQuery(final String hql, final Object... values);

	/**
	 * 根据指定 HQL 和若干键值对应的参数创建 Query
	 * 
	 * @param hql
	 *            需要执行查询的 Hql
	 * @param values
	 *            需要传入的键值对应的参数值集合
	 * @return 根据指定条件创建的 Query
	 */
	public abstract Query createQuery(final String hql, final Map<String, Object> values);

	/**
	 * 根据指定 HQL 和若干参数值创建 SQLQuery
	 * 
	 * @param sql
	 *            需要执行查询的 Sql
	 * @param values
	 *            需要传入的参数值集合
	 * @return 根据指定条件创建的 SQLQuery
	 */
	public abstract SQLQuery createSQLQuery(final String sql, Object... values);

	/**
	 * 根据指定 HQL 和若干键值对应的参数创建 SQLQuery
	 * 
	 * @param sql
	 *            需要执行查询的 Sql
	 * @param values
	 *            需要传入的键值对应的参数值集合
	 * @return 根据指定条件创建的 SQLQuery
	 */
	public abstract SQLQuery createSQLQuery(final String sql, Map<String, Object> values);
}