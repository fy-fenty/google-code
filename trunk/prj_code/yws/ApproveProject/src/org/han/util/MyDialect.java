package org.han.util;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.Oracle10gDialect;

public class MyDialect extends Oracle10gDialect {
	public MyDialect() {
		super();
		// TODO Auto-generated constructor stub
		registerHibernateType(Types.DATE, Hibernate.TIMESTAMP.getName());
		registerHibernateType(Types.CHAR, Hibernate.STRING.getName());
	}
}
