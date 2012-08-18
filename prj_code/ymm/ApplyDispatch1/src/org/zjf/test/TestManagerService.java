package org.zjf.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.ymm.entity.SysEmployee;
import org.ymm.exception.MyException;
import org.ymm.vo.DispatchResultVo;
import org.ymm.vo.Page;
import org.ymm.vo.Result;
import org.zjf.services.IManagerService;

public class TestManagerService {
	public static void main(String[] args) throws MyException {
		ApplicationContext con = new ClassPathXmlApplicationContext(
				"spring-service-bean.xml");
		IManagerService source = con.getBean("ManagerService", IManagerService.class);
		Result res = null;
//		//审批报销单
		res=applyClaims(source);
		System.out.println(res.getMsg());
//		//查询部门报销单
//		Page page=fiadMine(source);
//		System.out.println(page.getResult().size());
//		Page page1=findMyDepartClaims(source);
//		System.out.println(page1.getResult().size());
	}
	
	public static Result applyClaims(IManagerService source) throws MyException{
		SysEmployee emp = new SysEmployee();
		emp.setESn("xxxx1000");
		emp.setPId(2L);
		emp.setDepartmentId(1L);

		DispatchResultVo vo =new DispatchResultVo();
		vo.setCheckComment("同意");
		vo.setSheetId(25L);
		vo.setCheckStatus(2L);
		return source.applyClaims(emp, vo);
	}
	
	public static Page fiadMine(IManagerService source) throws MyException{
		SysEmployee emp = new SysEmployee();
		emp.setESn("xxxx1000");
		emp.setPId(2L);
		emp.setDepartmentId(1L);

		DispatchResultVo vo =new DispatchResultVo();
		vo.setCheckComment("同意");
		vo.setSheetId(23L);
		return source.findMyApplyClaims(emp, vo);
	}
	
	public static Page  findMyDepartClaims(IManagerService source) throws MyException{
		SysEmployee emp = new SysEmployee();
		emp.setESn("xxxx1000");
		emp.setPId(2L);
		emp.setDepartmentId(1L);

		DispatchResultVo vo =new DispatchResultVo();
		vo.setCheckComment("同意");
		vo.setSheetId(23L);
		vo.setCheckStatus(2L);
		return source.findMyDepartClaims(emp, vo);
		
	}
}
