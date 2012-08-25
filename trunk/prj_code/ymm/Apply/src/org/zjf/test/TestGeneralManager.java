package org.zjf.test;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.ymm.entity.SysEmployee;
import org.ymm.exception.MyException;
import org.ymm.vo.DispatchListVo;
import org.ymm.vo.DispatchResultVo;
import org.ymm.vo.Page;
import org.ymm.vo.Result;
import org.zjf.services.IGeneralManagerService;

public class TestGeneralManager {

	public static void main(String[] args) throws MyException {
		ApplicationContext con = new ClassPathXmlApplicationContext(
				"spring-service-bean.xml");
		IGeneralManagerService source = con.getBean("GeneralManagerService", IGeneralManagerService.class);
		Result res = null;
//		Page page=findMyApply(source);
//		System.out.println(page.getResult().size());
		res=appalyClaims(source);
		System.out.println(res.getMsg());
//		res=stopClaims(source);
//		System.out.println(res.getMsg());
	}
	public static Page findMyApply(IGeneralManagerService source) throws MyException{
		
		SysEmployee emp = new SysEmployee();
		emp.setESn("xxxx1006");
		emp.setPId(1L);
		emp.setDepartmentId(1L);

		DispatchResultVo vo =new DispatchResultVo();
		vo.setCheckComment("同意");
		vo.setSheetId(23L);
		return source.findMyApply(emp, vo); 
	}
	
	public static Result appalyClaims(IGeneralManagerService source) throws MyException{
		
		SysEmployee emp = new SysEmployee();
		emp.setESn("xxxx1006");
		emp.setPId(1L);
		emp.setDepartmentId(1L);

		DispatchResultVo vo =new DispatchResultVo();
		vo.setCheckComment("同意");
		vo.setSheetId(25L);
		vo.setCheckStatus(2L);
		return source.applyClaims(emp, vo);
	}
	
	public static Result stopClaims(IGeneralManagerService source) throws MyException{
		SysEmployee emp = new SysEmployee();
		emp.setESn("xxxx1006");
		emp.setPId(1L);
		emp.setDepartmentId(1L);

		DispatchListVo list = new DispatchListVo();
		list.setCreateTime(new Date());
		list.setFlag(true);
		list.setEventExplain("XXXX");
		list.setDlId(24L);
		return source.stopClaims(emp, list);
	}
}
