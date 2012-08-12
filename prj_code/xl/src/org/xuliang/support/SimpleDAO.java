package org.xuliang.support;

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
 * @author xuliang
 * @date 2012-08-12
 * @class ISimpleDAO
 * @extends Object
 * @description 底层DAO封装类
 */
@SuppressWarnings("unchecked")
public class SimpleDAO<T, PK extends Serializable> implements ISimpleDAO<T, PK> {
	protected SessionFactory sessionFactory;

	protected Class<T> entityClass;

	public SimpleDAO() {
		this.entityClass = this.getSuperClassType(getClass(), 0);
	}

	private Class getSuperClassType(final Class clazz, final int index) {
		Type type = clazz.getGenericSuperclass();
		if (type instanceof ParameterizedType == false) {
			return Object.class;
		}
		Type[] ts = ((ParameterizedType) type).getActualTypeArguments();
		if (index < 0 || index >= ts.length) {
			return Object.class;
		}
		return (Class) ts[index];
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void save(final T entity) {
		getSession().saveOrUpdate(entity);
	}

	public T saveNew(final T entity) {
		return (T) getSession().merge(entity);

	}

	public void delete(final T entity) {
		getSession().delete(entity);
	}

	public void delete(final PK id) {
		delete(get(id));
	}

	public T get(final PK id) {
		return (T) getSession().get(entityClass, id);
	}

	public <X> X get(Class<X> clazz, Serializable id) {
		return (X) getSession().get(clazz, id);
	}

	public <X> List<X> find(final String hql) {
		return createQuery(hql).list();
	}

	public <X> List<X> find(final String hql, final Object... values) {
		return createQuery(hql, values).list();
	}

	public <X> List<X> find(final String hql, final Map<String, Object> values) {
		return createQuery(hql, values).list();
	}

	public <X> X findUnique(final String hql, final Object... values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	public <X> X findUnique(final String hql, final Map<String, Object> values) {
		return (X) createQuery(hql, values).uniqueResult();
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

	public Query createQuery(final String hql, final Map<String, Object> values) {
		Query query = getSession().createQuery(hql);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}

	public SQLQuery createSQLQuery(final String sql, final Object... values) {
		SQLQuery query = getSession().createSQLQuery(sql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	public SQLQuery createSQLQuery(final String sql,
			final Map<String, Object> values) {
		SQLQuery query = getSession().createSQLQuery(sql);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}

}
