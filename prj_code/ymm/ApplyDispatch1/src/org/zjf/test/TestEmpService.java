package org.zjf.test;

import java.sql.SQLException;
import java.util.Date;

import oracle.sql.BLOB;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.ymm.entity.SysEmployee;
import org.ymm.exception.MyException;
import org.ymm.vo.BaseVo;
import org.ymm.vo.DispatchDetailVo;
import org.ymm.vo.DispatchListVo;
import org.ymm.vo.Page;
import org.ymm.vo.Result;
import org.zjf.services.IEmpService;

public class TestEmpService {

	public static void main(String[] args) throws SQLException, MyException {
		ApplicationContext con = new ClassPathXmlApplicationContext(
				"spring-service-bean.xml");
		IEmpService source = con.getBean("EmpService", IEmpService.class);
		Result res = null;
//		// 增加报销单
//		res = saveClaims(source);
//		System.out.println(res.getException() + res.getMsg());
//		
		// 修改报销单
//		res = updateClaims(source);
//		System.out.println(res.getException() + res.getMsg());
		
		// 增加报销单明细
//		res = saveDetail(source);
//		System.out.println(res.getException() + res.getMsg());
//		
//		// 修改报销单明细
//		res = updateDetail(source);
//		System.out.println(res.getException() + res.getMsg());
//		
//		// 删除报销单明细
//		res = deleteDetail(source);
//		System.out.println(res.getException() + res.getMsg());
//		
//		// 删除报销单
//		res = deleteClaims(source);
//		System.out.println(res.getException() + res.getMsg());
//		
//		//查询报销单
//		Page page=findEmpClaims(source);
//		System.out.println(page.getResult().size());
		
		System.out.println(commit(source).getMsg());
		
	}

	// 提交报销单
	public static Result commit(IEmpService source) throws SQLException {
		SysEmployee emp = new SysEmployee();
		emp.setESn("xxxx1004");
		emp.setPId(3L);
		emp.setDepartmentId(1L);

		DispatchListVo list = new DispatchListVo();
		list.setCreateTime(new Date());
		list.setFlag(true);
		list.setEventExplain("XXXX");
		list.setESn("xxxx1004");
		list.setDlId(25L);

		Result re = null;
		try {
			re = source.commitClaims(emp, list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			re = new Result();
			re.setException(e.getMessage());
		}
		return re;
	}

	// 保存报销单
	public static Result saveClaims(IEmpService source) throws MyException {
		DispatchListVo list = new DispatchListVo();
		list.setCreateTime(new Date());
		list.setFlag(true);
		list.setEventExplain("ertre");

		SysEmployee emp = new SysEmployee();
		emp.setESn("xxxx1004");
		emp.setPId(3L);
		emp.setDepartmentId(1L);

		return source.saveClaims(emp, list);
	}

	// 更新报销单
	public static Result updateClaims(IEmpService source) throws MyException {
		SysEmployee emp = new SysEmployee();
		emp.setESn("xxxx1004");
		emp.setPId(3L);
		emp.setDepartmentId(1L);

		DispatchListVo list = new DispatchListVo();
		list.setCreateTime(new Date());
		list.setFlag(true);
		list.setEventExplain("YYYYY");
		list.setESn("xxxx1004");
		list.setDlId(22L);

		return source.updateClaims(emp, list);
	}

	// 删除报销单
	public static Result deleteClaims(IEmpService source) throws MyException {
		SysEmployee emp = new SysEmployee();
		emp.setESn("xxxx1004");
		emp.setPId(3L);
		emp.setDepartmentId(1L);

		DispatchListVo list = new DispatchListVo();
		list.setCreateTime(new Date());
		list.setFlag(true);
		list.setEventExplain("咸鸭蛋");
		list.setESn("xxxx1004");
		list.setDlId(22L);
		return source.deleteClaims(emp, list);
	}

	// 保存报销单明细
	public static Result saveDetail(IEmpService source) throws SQLException,
			MyException {
		DispatchDetailVo detail = new DispatchDetailVo();
		detail.setAccessory(BLOB.empty_lob());
		detail.setCostExplain("我勒个擦");
		detail.setItemId(1L);
		detail.setMoney(Double.valueOf(5000));
		detail.setSheetId(25L);
		detail.setFlag(true);
		detail.setDsId(10L);

		SysEmployee emp = new SysEmployee();
		emp.setESn("xxxx1004");
		emp.setPId(3L);
		emp.setDepartmentId(1L);
		return source.saveDetail(emp, detail);
	}

	// 更新报销单明细
	public static Result updateDetail(IEmpService source) throws MyException,
			SQLException {
		DispatchDetailVo detail = new DispatchDetailVo();
		detail.setAccessory(BLOB.empty_lob());
		detail.setCostExplain("我勒个sdasds");
		detail.setItemId(1L);
		detail.setMoney(Double.valueOf(100));
		detail.setSheetId(22L);
		detail.setFlag(true);
		detail.setDsId(30L);

		SysEmployee emp = new SysEmployee();
		emp.setESn("xxxx1004");
		emp.setPId(3L);
		emp.setDepartmentId(1L);
		return source.updateDetail(emp, detail);
	}

	// 删除报销单明细
	public static Result deleteDetail(IEmpService source) throws MyException {
		DispatchDetailVo detail = new DispatchDetailVo();
		detail.setCostExplain("我勒个擦");
		detail.setItemId(1L);
		detail.setMoney(Double.valueOf(100));
		detail.setSheetId(22L);
		detail.setFlag(true);
		detail.setDsId(30L);

		SysEmployee emp = new SysEmployee();
		emp.setESn("xxxx1004");
		emp.setPId(3L);
		emp.setDepartmentId(1L);
		return source.deleteDetail(emp, detail);
	}

	public static Page findEmpClaims(IEmpService source) throws MyException {
		SysEmployee emp = new SysEmployee();
		emp.setESn("xxxx1004");
		emp.setPId(3L);
		emp.setDepartmentId(1L);
		BaseVo vo = new BaseVo();
		return source.findAllClaims(emp, vo);
	}
}
