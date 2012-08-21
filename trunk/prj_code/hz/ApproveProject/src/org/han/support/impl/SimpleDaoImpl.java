package org.han.support.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.han.support.ISimpleDao;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * @author HanZhou
 * @className: SimpleDAO
 * @date 2012-8-14
 * @extends Object
 * @description: 底层DAO封装实现类
 */
@SuppressWarnings("unchecked")
public class SimpleDaoImpl<T, PK extends Serializable> implements
		ISimpleDao<T, PK> {
	protected SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Session getSession() {
		// TODO Auto-generated method stub
		return this.getSessionFactory().getCurrentSession();
	}

	protected Class<T> entityClass;

	/**
	 * @param clazz
	 * @param index
	 * @return 该对象的Class
	 * @description 获得泛型T的class
	 */
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

	public SimpleDaoImpl() {
		this.entityClass = this.getSuperClassType(this.getClass(), 0);
	}

	@Override
	public void save(final T entity) {
		// TODO Auto-generated method stub
		getSession().saveOrUpdate(entity);
	}

	@Override
	public T saveNew(final T entity) {
		// TODO Auto-generated method stub
		return (T) getSession().merge(entity);
	}

	@Override
	public void delete(final T entity) {
		// TODO Auto-generated method stub
		getSession().delete(entity);
	}

	@Override
	public void delete(final PK id) {
		// TODO Auto-generated method stub
		delete(this.get(id));
	}

	@Override
	public T get(final PK id) {
		// TODO Auto-generated method stub
		return (T) getSession().get(entityClass, id);
	}

	@Override
	public <X> X get(final Class<X> clazz, final Serializable id) {
		// TODO Auto-generated method stub
		return (X) getSession().get(clazz, id);
	}

	@Override
	public <X> List<X> find(final String hql) {
		// TODO Auto-generated method stub
		return createQuery(hql).list();
	}

	@Override
	public <X> List<X> find(final String hql, final Object... values) {
		// TODO Auto-generated method stub
		return createQuery(hql, values).list();
	}

	@Override
	public <X> List<X> find(final String hql, final Map<String, Object> values) {
		// TODO Auto-generated method stub
		return createQuery(hql, values).list();
	}

	@Override
	public <X> X findUnique(final String hql, final Object... values) {
		// TODO Auto-generated method stub
		return (X) createQuery(hql, values).uniqueResult();
	}

	@Override
	public <X> X findUnique(final String hql, final Map<String, Object> values) {
		// TODO Auto-generated method stub
		return (X) createQuery(hql, values).uniqueResult();
	}

	@Override
	public Query createQuery(String hql, Object... values) {
		// TODO Auto-generated method stub
		Query query = getSession().createQuery(hql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	@Override
	public Query createQuery(final String hql, final Map<String, Object> values) {
		// TODO Auto-generated method stub
		Query query = getSession().createQuery(hql);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}

	@Override
	public SQLQuery createSQLQuery(final String sql, final Object... values) {
		// TODO Auto-generated method stub
		SQLQuery query = getSession().createSQLQuery(sql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	@Override
	public SQLQuery createSQLQuery(final String sql,
			final Map<String, Object> values) {
		// TODO Auto-generated method stub
		SQLQuery query = getSession().createSQLQuery(sql);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}
}
