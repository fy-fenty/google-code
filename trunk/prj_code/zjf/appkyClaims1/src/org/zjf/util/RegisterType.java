package org.zjf.util;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.Oracle10gDialect;

public class RegisterType extends Oracle10gDialect {
	public RegisterType() {  
        // TODO Auto-generated constructor stub  
        super();   
        registerHibernateType(Types.CHAR, Hibernate.STRING.getName());//将数据库的char类型转为String类型  
      //registerHibernateType(Types.DATE, Hibernate.TIMESTAMP.getName());  
      //registerHibernateType(Types.DECIMAL, Hibernate.BIG_DECIMAL.getName());    
        registerHibernateType(-1, Hibernate.STRING.getName());    
    }  
}	
