package org.zjf.support;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 * @project:ApplyClaims
 * @author:zjf
 * @date:2012-8-13 下午10:53:25   
 * @class:ISimpleDao
 * @extends:Object
 * @description:底层DAO封装接口
 */
public interface ISimpleDao<T,PK extends Serializable> {
	
	/**
	 * 得到当前Session
	 * @return Session
	 * 				Hibernate的Session
	 */
	public abstract Session getSession();
	
	/**
	 * 保存或修改对象
	 * @param entity 
	 * 			实体类对象
	 */
	public abstract void save(final T entity);
	
	/**
	 * 保存新增的对象
	 * @param entity
	 * 			实体	类对象
	 */
	public abstract T saveNew(final T entity);
	
	/**
	 * 删除对象
	 * @param entity
	 * 			对象必须是Sessin中的对象或含有id的瞬时对象
	 */
	public abstract void delete(final T entity);
	
	/**
	 * 通过id删除对象
	 * @param id
	 * 			对象在数据库中的id
	 */
	public abstract void delete(final PK id);
	
	/**
	 * 通过id获取对象
	 * @param id
	 * @return T
	 * 			获取的对象
	 */
	public abstract T get(final PK id);
	
	/**
	 * 按类和id查询对象
	 * @param clazz
	 * 				类的class
	 * @param id
	 * 			要获取对象的id
	 * @return A
	 * 			获取的对象
	 */
	public abstract <A> A get(final Class<A> clazz,final PK id);
	
	/**
	 * 通过HQL查询对象列表
	 * @param hql
	 * 			HQL查询语句
	 * @return List
	 * 			对象列表
	 */
	public abstract <H> List<H> findByHQL(final String hql);
	
	/**
	 * 通过HQL查询对象列表
	 * @param hql
	 * 			HQL查询语句
	 * @param values
	 * 			数量可变的参数，按顺序绑定
	 * @return List
	 * 			对象列表
	 */
	public abstract <H> List<H> findByHQL(final String hql,final Object... values);
	
	/**
	 * 通过HQL查询对象列表
	 * @param hql
	 * 			HQL查询语句
	 * @param values
	 * 			命名参数，按参数名称绑定
	 * @return List
	 * 			对象列表
	 */
	public abstract <H> List<H> findByHQL(final String hql,final Map<String,Object> values);
	
	/**
	 * 通过HQL查询唯一对象
	 * @param sql
	 * 			HQL查询语句
	 * @param values
	 * 			数量可变的参数，按顺序绑定
	 * @return H
	 * 			对象
	 */
	public abstract <H> H findUniqueByHQL(final String hql,final Object... values);
	
	/**
	 * 通过HQL查询唯一对象
	 * @param hql
	 * 			HQL查询语句
	 * @param values
	 * 			命名参数，按参数名称绑定
	 * @return List
	 * 			对象
	 */
	public abstract <H> H findUniqueByHQL(final String hql,final Map<String,Object> values);
	
	/**
	 * 通过HQL查询与参数列表创建Query
	 * @param sql
	 * 			HQL语句
	 * @param values
	 * 			数量可变的参数，按顺序绑定
	 * @return Query
	 * 			Query对象
	 */
	public abstract Query createHQLQuery(final String hql,final Object... values);
	
	/**
	 * 通过HQL查询与参数列表创建Query
	 * @param sql
	 * 			HQL语句
	 * @param values
	 * 			命名参数，按参数名称绑定
	 * @return Query
	 * 			Query对象
	 */
	public abstract Query createHQLQuery(final String hql,final Map<String,Object> values);
	
	/**
	 * 通过SQL语句和参数列表创建SQLQuery
	 * @param sql
	 * 			SQL语句
	 * @param values
	 * 			数量可变的参数，按顺序绑定
	 * @return SQLQuery
	 * 			SQLQuery对象	
	 */
	public abstract SQLQuery createSQLQuery(final String sql,final Object...values); 
	
	/**
	 * 通过SQL语句和参数列表创建SQLQuery
	 * @param sql
	 * 			SQL语句
	 * @param values
	 * 			命名参数，按参数名称绑定
	 * @return SQLQuery
	 * 			SQLQuery对象
	 */
	public abstract SQLQuery createSQLQuery(final String sql,final Map<String,Object> values); 
	
	
	
}
