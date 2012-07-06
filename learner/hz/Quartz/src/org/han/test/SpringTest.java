package org.han.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class SpringTest {
	public static void main(String[] args) {
		System.out.println("aaaaaaaaaaaaaaa");
		ApplicationContext context=new ClassPathXmlApplicationContext("Spring-beans.xml");
			System.out.println("bbbbbbbbbbbbbbbbbbbb");
	}
}
