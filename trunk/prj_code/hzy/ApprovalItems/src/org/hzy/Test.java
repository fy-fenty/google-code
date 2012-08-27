package org.hzy;

import org.hzy.exception.MyException;
import org.hzy.vo.HandleDispatchVo;
import org.hzy.web.action.EmployeeAction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static void main(String[] args) throws MyException {
		ApplicationContext actc = new ClassPathXmlApplicationContext(new String[] { "hibernate-spring.xml", "beans1.xml" });
		EmployeeAction ea = actc.getBean("org.hzy.web.action.EmployeeAction", EmployeeAction.class);
		String json = "[{'money':'4564.15','costExplain':'123123大法官','itemId':3},{'money':'345','costExplain':'123','itemId':3},{'money':'1231','costExplain':'1232','itemId':2}]";
		ea.setDds(json);
		HandleDispatchVo hd = new HandleDispatchVo();
		hd.setEsn("10000000");
		ea.setHdVo(hd);
		ea.commitDispatch();
	}
}