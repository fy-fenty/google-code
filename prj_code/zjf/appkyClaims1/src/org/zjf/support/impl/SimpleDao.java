package org.zjf.support.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.zjf.support.ISimpleDao;

import sun.reflect.generics.reflectiveObjects.TypeVariableImpl;

/**
 * @project:ApplyClaims
 * @author:zjf
 * @date:2012-8-13 下午11:44:20   
 * @class:SimpleDao
 * @extends:Object
 * @implement:ISimpleDao
 * @description:底层DAO封装实现类
 */
public class SimpleDao<T,PK extends Serializable> implements ISimpleDao<T, PK> {

	protected SessionFactory sessionfactory;
	protected Class<T> entityClass;
	
	@SuppressWarnings("unchecked")
	public SimpleDao(){
		this.entityClass=this.getSuperClassType(getClass(), 0);
	}
	public SessionFactory getSessionfactory() {
		return sessionfactory;
	}

	public void setSessionfactory(SessionFactory sessionfactory) {
		this.sessionfactory = sessionfactory;
	}

	@SuppressWarnings({"rawtypes" })
	private Class getSuperClassType(final Class clazz,final int index){
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

	
	public Session getSession() {
		return this.sessionfactory.getCurrentSession();
	}

	
	public void save(final T entity) {
		this.getSession().saveOrUpdate(entity);
		
	}

	@SuppressWarnings("unchecked")
	
	public T saveNew(final T entity) {
		return (T)this.getSession().merge(entity);	
	}

	
	public void delete(final T entity) {
		this.getSession().delete(entity);
	}

	
	public void delete(final PK id) {
		delete(get(id));
	}

	@SuppressWarnings("unchecked")
	
	public T get(final PK id) {
		return (T)this.getSession().get(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	
	public <A> A get(final Class<A> clazz, final PK id) {
		return (A)this.getSession().get(clazz, id);
	}

	@SuppressWarnings("unchecked")
	
	public <H> List<H> findByHQL(final String hql) {
		return createHQLQuery(hql).list();
	}

	@SuppressWarnings("unchecked")
	
	public <H> List<H> findByHQL(final String hql, final Object... values) {
		return createHQLQuery(hql, values).list();
	}

	@SuppressWarnings("unchecked")
	
	public <H> List<H> findByHQL(final String hql, final Map<String, Object> values){
		return createHQLQuery(hql, values).list();
	}

	@SuppressWarnings("unchecked")
	
	public <H> H findUniqueByHQL(final String hql, final Object... values) {
		return (H)createHQLQuery(hql, values).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	
	public <H> H findUniqueByHQL(final String hql, final Map<String, Object> values){
		return (H)createHQLQuery(hql, values).uniqueResult();
	}

	
	public Query createHQLQuery(final String hql, final Object... values) {
		Query query=getSession().createQuery(hql);
		if(values!=null&&values.length>0){
			for (int q = 0; q < values.length; q++) {
				query.setParameter(q, values[q]);
			}
		}
		return query;
	}

	
	public Query createHQLQuery(final String hql,final Map<String, Object> values){
		Query query=getSession().createQuery(hql);
		if(values!=null)
			query.setProperties(values);
		return query;
	}

	
	public SQLQuery createSQLQuery(final String sql,final Object... values){
		SQLQuery query=getSession().createSQLQuery(sql);
		if(values!=null&&values.length>0){
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	
	public SQLQuery createSQLQuery(final String sql,final Map<String, Object> values){	
		SQLQuery query=getSession().createSQLQuery(sql);
		if(values!=null)
			query.setProperties(values);
		return query;
	}
}
