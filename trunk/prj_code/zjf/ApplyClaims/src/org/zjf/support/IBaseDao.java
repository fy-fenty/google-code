package org.zjf.support;

import java.io.Serializable;
import java.util.Map;

import org.hibernate.Query;
import org.zjf.exception.MyException;
import org.zjf.vo.BaseVO;
import org.zjf.vo.Page;

/**
 * @project:ApplyClaims
 * @author:zjf
 * @date:2012-8-14 上午9:17:51   
 * @class:IBaseDao
 * @extends:ISimpleDao
 * @description:基础DAo接口，扩展功能包括分页查询，按属性过滤条件列表查询
 */
public interface IBaseDao<T,PK extends Serializable> extends ISimpleDao<T, PK>{

	/**
	 * 设置分页参数到Query对象
	 * @param query
	 * 			query对象
	 * @param first
	 * 			起始位置
	 * @param limit
	 * 			条数
	 * @return Query
	 * 			设置好分页参数的Query	
	 */
	public abstract Query setPageParameter(final Query query,final int first,final int limit);
	
	/**
	 * 查询本次SQL所获得的记录总数
	 * @param sql
	 * 			SQL语句
	 * @param values
	 * 			参数	，按顺序设置
	 * @return long
	 * 			查询到的记录总数
	 */
	public abstract long countSqlResult(final String sql,final Object...values);
	
	/**
	 * 查询本次SQL所获得的记录总数
	 * @param sql
	 * 			SQL语句
	 * @param values
	 * 			map形式的SQL语句参数，按名称设置
	 * @return long
	 * 			查询到的记录总数
	 */
	public abstract long countSqlResult(final String sql,final Map<String,Object> valuesO);
	
	/**
	 * 通过SQL分页查询
	 * @param vo
	 * 			分页参数对象
	 * @param sql
	 * 			SQL语句
	 * @param values
	 * 			参数	，按顺序设置
	 * @return Page
	 * 			封装好了的Pahe对象
	 */
	public abstract Page findPageBySql(final BaseVO vo ,final String sql,final Object...values);
	
	/**
	 * 通过SQL分页查询
	 * @param vo
	 * 			分页参数对象
	 * @param sql
	 * 			SQL语句
	 * @param values
	 * 			map形式的SQL语句参数，按名称设置
	 * @return Page
	 * 			封装好了的Pahe对象
	 */
	public abstract Page findPageBySql(final BaseVO vo ,final String sql,final Map<String,Object> values);
	
	/**
	 * 根据sql查询唯一结果，返回Map对象
	 * 
	 * @param sql
	 *            sql语句.
	 * @param values
	 *          命名参数,按名称绑定.
	 * @return Map 
	 * 			Map对象.
	 */
	public abstract Map<String, Object> findUniqueBySQL(String sql,
			Map<String, Object> values);
	
	/**
	 * 根据sql查询唯一结果，返回Map对象
	 * 
	 * @param sql
	 *          sql语句.
	 * @param values
	 *          数量可变的查询参数,按顺序绑定.
	 * @return Map 
	 * 			Map对象
	 */
	public abstract Map<String, Object> findUniqueBySQL(final String sql,
			final Object... values);
}
