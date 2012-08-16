package org.ymm.support;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import sun.reflect.generics.reflectiveObjects.TypeVariableImpl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author yingmingming 
 * @date 2012-8-14 上午11:27:24
 * @ClassName: SimpleDao 
 * @extends 	 
 * @Description: 底层DAO封装类
 */
public class SimpleDaoImpl<T,PK extends Serializable> implements ISimpleDao<T, PK> {
	
	protected SessionFactory sessionFactory;
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Class<T> entityClass;
	
	public SimpleDaoImpl(){
		this.entityClass = this.getSuperClassType(getClass(), 0);
	}
	
	@SuppressWarnings("rawtypes")
	public Class getSuperClassType(final Class clazz,final int index){
		Type type=clazz.getGenericSuperclass();
		if(type instanceof ParameterizedType==false)
			return Object.class;
		Type[] types=((ParameterizedType)type).getActualTypeArguments();
		if(types.length<0||index>=types.length)
			return Object.class;
		if(types[index] instanceof TypeVariableImpl)
			return Object.class;
		return (Class)types[index];
	}
	
	@Override
	public Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	public void save(final T entity) {
		getSession().saveOrUpdate(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T saveNew(final T entity) {
		return (T)getSession().merge(entity);
	}

	@Override
	public void delete(final T entity) {
		getSession().delete(entity);
	}

	@Override
	public void delete(final PK id) {
		delete(get(id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(final PK id) {
		return (T)getSession().get(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <X> X get(Class<X> claz,Serializable id) {
		return (X) getSession().get(claz,id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <X> List<X> find(final String hql) {
		return createQuery(hql).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <X> List<X> find(final String hql, final String... values) {
		return createQuery(hql, values).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <X> List<X> find(final String hql, final Map<String, Object> values) {
		return createQuery(hql, values).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <X> X findUnique(final String hql, final String... values) {
		return (X)createQuery(hql, values).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <X> X findUnique(final String hql,final Map<String, Object> values) {
		return (X)createQuery(hql, values).uniqueResult();
	}

	@Override
	public Query createQuery(final String hql,final Map<String, Object> values) {
		Query query =getSession().createQuery(hql);
		if(values!=null){
			query.setProperties(values);
		}
		return query;
	}

	@Override
	public SQLQuery createSQLQuery(final String sql,final String... values) {
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(sql);
		if (values != null&&values.length>0) {
			for (int i = 0; i < values.length; i++) {
				query.setString(i, values[i]);
			}
		}
		return query;
	}

	@Override
	public SQLQuery createSQLQuery(final String hql,final  Map<String, Object> values) {
		SQLQuery query = getSession().createSQLQuery(hql);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}
	
	
	public Query createQuery(final String hql, final String... values) {
		Query query = getSession().createQuery(hql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setString(i, values[i]);
			}
		}
		return query;
	}
	
}
