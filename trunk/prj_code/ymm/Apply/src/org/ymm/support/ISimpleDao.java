package org.ymm.support;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 * 
 * @author yingmingming 
 * @date 2012-8-14 上午10:46:44
 * @ClassName: ISimpleDao 
 * @extends Object	 
 * @Description: 底层DAO封装接口类
 * @param <T>
 * @param <PK>
 */

public interface ISimpleDao<T,PK extends Serializable> {

	/**
	 * @Title: getSession 
	 * @param @return
	 * @return Session     
	 * @Description: TODO(取得当前Session) 
	 * @throws
	 */
	
	public abstract Session getSession(); 
	
	/**
	 * @Title: save 
	 * @param @param entity
	 * @return void     
	 * @Description: 保存新增或修改的对象
	 * @throws
	 */
	public abstract void save(final T entity); 
	
	/**
	 * @Title: saveNew 
	 * @param @param entity
	 * @param @return
	 * @return T     
	 * @Description: 保存新增的对象 
	 * @throws
	 */
	public abstract T saveNew(final T entity);
	
	/**
	 * @Title: delete 
	 * @param @param entity
	 * @return void     
	 * @Description: 删除对象  注:对象必须是session中的对象含id属性的transient对象 
	 * @throws
	 */
	public abstract void delete(final T entity);
	
	/**
	 * @Title: delete 
	 * @param @param id
	 * @return void     
	 * @Description: 按id删除对象 
	 * @throws
	 */
	public abstract void delete(final PK id);
	
	/**
	 * @Title: get 
	 * @param @param id
	 * @param @return
	 * @return T     
	 * @Description: 按id得到对象
	 * @throws
	 */
	public abstract T get(final PK id);
	
	/**
	 * @Title: get 
	 * @param @param claz
	 * @param @param id
	 * @param @return
	 * @return X     
	 * @Description: 按类和id获取对象 
	 * @throws
	 */
	public abstract <X> X get(final Class<X> claz,final Serializable id);
	
	/**
	 * @Title: find 
	 * @param @param sql
	 * @param @return
	 * @return List<X>     
	 * @Description: 按HQL查询对象列表.
	 * @throws
	 */
	public abstract <X> List<X> find(final String sql);
	
	/**
	 * @Title: find 
	 * @param @param sql
	 * @param @param values
	 * @return List<X>     
	 * @Description: 按HQL查询对象列表. 注：数量可变的参数，按顺序绑定
	 */
	public abstract <X> List<X> find(final String sql,final String...values);
	
	/**
	 * @Title: find 
	 * @param @param hql
	 * @param @param values
	 * @return List<X>     
	 * @Description: 按HQL查询对象列表.注： 命名参数,按名称绑定.
	 * @throws
	 */
	public abstract <X> List<X> find(final String hql,final Map<String, Object> values);
	
	/**
	 * @Title: findUnique 
	 * @param @param hql
	 * @param @param values
	 * @return X     
	 * @Description: 按 HQL查询唯一对象,注： 数量可变的参数,按顺序绑定.
	 */
	public abstract <X> X findUnique(final String hql, final String... values);
	
	/**
	 * @Title: findUnique 
	 * @param @param hql
	 * @param @param values
	 * @return X     
	 * @Description: 按HQL查询唯一对象.注： 命名参数,按名称绑定.
	 */
	public abstract <X> X findUnique(final String hql,final Map<String, Object> values);
	
	/**
	 * @Title: createQuery 
	 * @param @param hql
	 * @param @param values
	 * @return Query     
	 * @Description: 根据查询HQL与参数列表创建Query对象.注：数量可变的参数,按顺序绑定. 
	 * @throws
	 */
	public abstract Query createQuery(final String hql, final String... values);
	
	/**
	 * @Title: createQuery 
	 * @param @param sql
	 * @param @param values
	 * @return Query     
	 * @Description: 根据查询HQL与参数列表创建Query对象. 注：名称参数,按名称绑定.
	 */
	
	public abstract Query createQuery(final String sql,final Map<String,Object> values);
	
	/**
	 * @Title: createSQLQuery 
	 * @param @param sql
	 * @param @param values
	 * @return SQLQuery     
	 * @Description: 根据查询SQL与参数列表创建SQLQuery对象.注： 数量可变的参数,按顺序绑定.
	 */
	public abstract SQLQuery createSQLQuery(final String sql,final String... values);
	
	/**
	 * @Title: createSQLQuery 
	 * @param @param sql
	 * @param @param values
	 * @param @return
	 * @return SQLQuery     
	 * @Description: 根据查询SQL与参数列表创建SQLQuery对象.注： 命名参数,按名称绑定.
	 * @throws
	 */
	public abstract SQLQuery createSQLQuery(final String sql,final Map<String, Object> values);
}
