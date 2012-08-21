package org.fy.util;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.Oracle10gDialect;

/**
 * @author hzy
 * @date 2012-8-20
 *	@extends Oracle10gDialect 
 * @class RegisterType
 * @description 注册自定义类型
 */
public class RegisterType extends Oracle10gDialect{
	public RegisterType() {  
        // TODO Auto-generated constructor stub  
        super();   
        registerHibernateType(Types.CHAR, Hibernate.STRING.getName());//将数据库的char类型转为String类型  
        registerHibernateType(Types.DATE, Hibernate.TIMESTAMP.getName());  
    }  
}
