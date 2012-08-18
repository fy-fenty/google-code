package org.zjf.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.ymm.entity.SysEmployee;
import org.ymm.exception.MyException;
import org.ymm.vo.BaseVo;
import org.ymm.vo.DispatchResultVo;
import org.ymm.vo.Page;
import org.ymm.vo.Result;
import org.zjf.services.IFinancialService;

public class TestFinancialService {

	public static void main(String[] args) throws MyException {
		ApplicationContext con = new ClassPathXmlApplicationContext(
				"spring-service-bean.xml");
		IFinancialService source = con.getBean("FinancialService", IFinancialService.class);
		Result res = null;
//		
//		res=applyClaims(source);
//		System.out.println(res.getMsg());
//		res=payMent(source);
//		System.out.println(res.getMsg());
		Page page=lookClaims(source);
		System.out.println(page.getResult().size());
	}
	
	public static Result applyClaims(IFinancialService source) throws MyException{
		SysEmployee emp = new SysEmployee();
		emp.setESn("xxxx1001");
		emp.setPId(3L);
		emp.setDepartmentId(3L);

		DispatchResultVo vo =new DispatchResultVo();
		vo.setCheckComment("同意");
		vo.setDrId(31L);
		return source.applyClaims(emp, vo);
	}
	
	public static Result payMent(IFinancialService source) throws MyException{
		
		SysEmployee emp = new SysEmployee();
		emp.setESn("xxxx1001");
		emp.setPId(3L);
		emp.setDepartmentId(3L);

		DispatchResultVo vo =new DispatchResultVo();
		vo.setCheckComment("同意");
		vo.setDrId(31L);
		return source.payMent(emp, vo);
	}
	
	public static Page lookClaims(IFinancialService source) throws MyException{
		SysEmployee emp = new SysEmployee();
		emp.setESn("xxxx1001");
		emp.setPId(3L);
		emp.setDepartmentId(3L);
		
		BaseVo vo =new BaseVo();
		
		return source.lookClaims(emp, vo);
	}
}
