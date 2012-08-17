package org.zjf.test;

import java.sql.SQLException;
import java.util.Date;

import oracle.sql.BLOB;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.ymm.constant.MyConstant;
import org.ymm.entity.DispatchDetail;
import org.ymm.entity.DispatchList;
import org.ymm.entity.DispatchResult;
import org.ymm.entity.SysEmployee;
import org.ymm.vo.Result;
import org.zjf.services.IEmpService;

public class Test {

	public static void main(String[] args) throws SQLException{
		ApplicationContext con = new ClassPathXmlApplicationContext("spring-service-bean.xml");
		IEmpService source = con.getBean("EmpService", IEmpService.class);
		Result res=commit(source);
		System.out.println(res.getException()+res.getMsg());
	}
	
	public static Result commit(IEmpService source) throws SQLException{
		SysEmployee emp=new SysEmployee();
		emp.setESn("xxxx1004");
		emp.setPId(3L);
		emp.setDepartmentId(1L);
		
		DispatchList list=new DispatchList();
		list.setCreateTime(new Date());
		list.setFlag(true);
		list.setEventExplain("咸鸭蛋");
		list.setESn("xxxx1004");
		list.setDlId(11L);
		
		DispatchResult result=new DispatchResult();
		result.setCheckComment("这是吃饭的钱");
		result.setCheckSn(emp.getESn());
		result.setCheckStatus(5L);
		result.setCheckTime(new Date());
		result.setSheetId(6L);
		
		DispatchDetail detail=new DispatchDetail();
		detail.setAccessory(BLOB.empty_lob());
		detail.setCostExplain("我勒个擦");
		detail.setItemId(1L);
		detail.setMoney(Double.valueOf(100));
		detail.setSheetId(11L);
		detail.setFlag(true);
		detail.setDsId(10L);
		
		Result res=new Result();
		try {
			source.commitClaims(emp, list);
		} catch (Exception e) {
			res.setException(e.getMessage());
			res.setSuccess(false);
			res.setMsg(MyConstant.map.get(e.getMessage()));
		}
		
		return res;
	}
}
