package org.fy.support;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * @author fy
 * @date 2012-8-14
 * @class SimpleDAO
 * @extends ISimpleDAO
 * @description
 */
@SuppressWarnings("unchecked")
public class SimpleDAO<T, PK extends Serializable> implements ISimpleDAO<T, PK> {

	private SessionFactory sessionFactory;

	private Class<T> klass;

	public SimpleDAO() {
		super();
		this.klass = this.getSuperClassType(this.getClass(), 0);
	}

	private Class getSuperClassType(Class klass, final int index) {
		Type type = klass.getGenericSuperclass();
		if (!(type instanceof ParameterizedType)) {
			return Object.class;
		}
		Type[] types = ((ParameterizedType) type).getActualTypeArguments();
		if (index == 0 || index >= types.length) {
			return Object.class;
		}
		return (Class) types[index];
	}

	@Override
	public Session getSession() {
		return getSessionFactory().getCurrentSession();
	}

	@Override
	public void save(T entity) {
		getSession().saveOrUpdate(entity);
	}

	@Override
	public T saveNew(T entity) {
		return (T) getSession().merge(entity);
	}

	@Override
	public void delete(T entity) {
		getSession().delete(entity);
	}

	@Override
	public void delete(PK id) {
		getSession().delete(get(id));
	}

	@Override
	public T get(PK id) {
		return (T) getSession().get(klass, id);
	}

	@Override
	public <X> X get(Class<X> klass, Serializable id) {
		return (X) getSession().get(klass, id);
	}

	@Override
	public <X> List<X> find(String hql) {
		return (List<X>) createQuery(hql).list();
	}

	@Override
	public <X> List<X> find(String hql, Object... values) {
		return (List<X>) createQuery(hql, values).list();
	}

	@Override
	public <X> List<X> find(String hql, Map<String, Object> values) {
		return (List<X>) createQuery(hql, values).list();
	}

	@Override
	public <X> X findUnique(String hql, Object... values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	@Override
	public <X> X findUnique(String hql, Map<String, Object> values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	@Override
	public Query createQuery(String hql, Object... values) {
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		return query;
	}

	@Override
	public Query createQuery(String hql, Map<String, Object> values) {
		Query query = getSession().createQuery(hql);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}

	@Override
	public SQLQuery createSQLQuery(String sql, Object... values) {
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		for (int i = 0; i < values.length; i++) {
			sqlQuery.setParameter(i, values[i]);
		}
		return sqlQuery;
	}

	@Override
	public SQLQuery createSQLQuery(String sql, Map<String, Object> values) {
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		if (values != null) {
			sqlQuery.setProperties(values);
		}
		return sqlQuery;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
