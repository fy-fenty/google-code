package org.fy;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static void main(String[] args) {
		ApplicationContext apc = new ClassPathXmlApplicationContext(
				new String[] { "hibernate-spring.xml", "beans.xml" });
		SessionFactory sf = apc.getBean("session_factory", SessionFactory.class);
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		SQLQuery sqlQuery = session.createSQLQuery("select * from HZY.AREA");
		sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = sqlQuery.list();
		tx.commit();
		System.out.println(list);
	}
}