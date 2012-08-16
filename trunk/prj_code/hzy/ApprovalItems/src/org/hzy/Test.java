package org.hzy;

import org.hzy.exception.MyException;
import org.hzy.support.ISystemUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static void main(String[] args) throws MyException {
		Long l1 = 1L;
		Long l2 = 1L;
		System.out.println(l1 != l2);
	}
}