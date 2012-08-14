package org.ymm.support;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
	public Class getSuperClassType(final Class claz,final int index){
		Type type=claz.getGenericSuperclass();
		if(type instanceof ParameterizedType){
			return Object.class;
		}
		Type[] ts = ((ParameterizedType) type).getActualTypeArguments();
		if(index<0||index>=ts.length){
			return Object.class;
		}
		return (Class) ts[index];
	}
	
	@Override
	public Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	public void save(final T entity) {
		getSession().saveOrUpdate(entity);
	}

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

	@Override
	public T get(final PK id) {
		return (T)getSession().get(entityClass, id);
	}

	@Override
	public <X> X get(Class<X> claz,Serializable id) {
		return (X) getSession().get(claz,id);
	}

	@Override
	public <X> List<X> find(final String hql) {
		return createQuery(hql).list();
	}

	@Override
	public <X> List<X> find(final String hql, Object... values) {
		return createQuery(hql, values).list();
	}
	
	@Override
	public <X> List<X> find(final String hql, final Map<String, Object> values) {
		return createQuery(hql, values).list();
	}

	@Override
	public <X> X findUnique(String hql, Object... values) {
		return (X)createQuery(hql, values).uniqueResult();
	}

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
	public SQLQuery createSQLQuery(final String sql,final Object... values) {
		SQLQuery query = getSession().createSQLQuery(sql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	@Override
	public SQLQuery createSQLQuery(String hql, Map<String, Object> values) {
		SQLQuery query = getSession().createSQLQuery(hql);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}
	
	
	public Query createQuery(final String hql, final Object... values) {
		Query query = getSession().createQuery(hql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}
	
}
