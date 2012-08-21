package org.fy;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.fy.entity.DispatchDetail;
import org.fy.entity.DispatchList;
import org.fy.entity.SysEmployee;
import org.fy.exception.MyExecption;
import org.fy.service.ISysDepartManagerService;
import org.fy.service.ISysEmployeeService;
import org.fy.service.ISysFinanceService;
import org.fy.service.ISysManagerService;
import org.fy.service.ISystemService;
import org.fy.util.JsonUtil;
import org.fy.vo.BaseVO;
import org.fy.vo.DispatchDetailVO;
import org.fy.vo.DispatchListVO;
import org.fy.vo.DispatchResultVO;
import org.fy.vo.Page;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static void main(String[] args){
		ApplicationContext apc = new ClassPathXmlApplicationContext(
				new String[] { "hibernate-spring.xml", "beans.xml" });
		ISysEmployeeService sf = apc.getBean("sys_employee_service", ISysEmployeeService.class);
		String str="this is project";
		System.out.println(str.length());
		String[] s=new String[str.length()];
		for (int i = 0; i < str.length(); i++) {
						
		}
		
		
//		ISysDepartManagerService sf = apc.getBean("sys_departManage_service", ISysDepartManagerService.class);
		
//		ISysManagerService sf = apc.getBean("sys_ZManage_service", ISysManagerService.class);
		
//		ISysFinanceService sf = apc.getBean("sys_Finance_service", ISysFinanceService.class);
		
//		ISystemService is=apc.getBean("system_service", ISystemStotalCountervice.class);
/*		BaseVO bv=new BaseVO();
		bv.setStart(2);
		bv.setLimit(2);
		try {
			Page page=sf.findDispathList(bv,"10000000");
			System.out.println(page.getResult().size());
			System.out.println(JsonUtil.PageJson(page).toString());
//			Map map=(Map) sf.findDispathList(bv,"10000000").getResult().get(0);
//			System.out.println(map.get("E_SN"));
		} catch (MyExecption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
//		
/*		DispatchList dl=new DispatchList();
		dl.setCreateTime(new Date());
		dl.setEventExplain("money");
		System.out.println(sf.saveDispathList("10000000",dl).getMsg());*/
		
/*		DispatchDetail detail=new DispatchDetail();
		detail.setSheetId(61L);
		detail.setMoney(550D);
		detail.setCostExplain("SOS");
		detail.setItemId(1L);
			System.out.println(sf.saveDispathDetail("10000000", detail).getMsg());*/

		
/*		DispatchListVO dlistvo=new DispatchListVO();
		dlistvo.setDlId(60L);
		dlistvo.setEventExplain("money12345");
		dlistvo.setSn("10000000");
		System.out.println(sf.updateDispathList(dlistvo).getMsg());*/
		
/*		DispatchDetailVO detailvo=new DispatchDetailVO();
		detailvo.setDsId(62L);
		detailvo.setSheetId(61L);
		detailvo.setMoney(5533D);
//		detailvo.setCostExplain("123");
		detailvo.setItemId(2L);
		detailvo.setSn("10000000");
		System.out.println(sf.updateDispathDetail(detailvo).getMsg());*/
		
		
//		System.out.println(sf.deleteDispathList("10000000", 61L).getMsg());
		
/*		DispatchDetailVO detail=new DispatchDetailVO();
//		detail.setDsId(62L);
		detail.setSheetId(61L);
		detail.setSn("10000000");
		System.out.println(sf.deleteDispathDetail(detail).getMsg());*/
		
/*		DispatchListVO dl=new DispatchListVO();
		dl.setDlId(61L);
		dl.setSn("10000000");
		System.out.println(sf.commitDispathList(dl).getMsg());*/
		
		
//		System.out.println(is.getMd5("123456"));
//		Random rd=new Random(3);
//		System.out.println(rd.nextInt());
		
		
		
		
		//部门经理测试
//		System.out.println(sf.pwdReset("10000003", "10000000").getMsg());
//		BaseVO bv=new BaseVO();
//		bv.setStart(0);
//		bv.setLimit(5);
		
	/*	DispatchResultVO drsvo=new DispatchResultVO();
		drsvo.setCheckComment("同意");
		drsvo.setEsn("10000003");
		drsvo.setSheetId(60L);
		drsvo.setStatus(1);
		System.out.println(sf.checkDispatchResult(drsvo).getMsg());*/
/*		try {
//			System.out.println(sf.findDepartDisList(bv, "10000003").getResult().get(0));
			System.out.println(sf.findWaitCheckDisList(bv, "10000003").getResult().get(0));	
		} catch (MyExecption e) {
				System.out.println(e.getMessage());
		}*/
		
		
		//总经理测试
		
/*		try {
			System.out.println(sf.findWaitCheckDisList(bv,"10000002").getResult().size());
		} catch (MyExecption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

/*		DispatchResultVO drsvo=new DispatchResultVO();
		drsvo.setCheckComment("同意");
		drsvo.setEsn("10000002");
		drsvo.setSheetId(60L);
		drsvo.setStatus(1);
		System.out.println(sf.checkDispatchResult(drsvo).getMsg());*/
		
/*		DispatchListVO dlistvo=new DispatchListVO();
		dlistvo.setDlId(23L);
		dlistvo.setSn("10000003");
		System.out.println(sf.stopAlreadyCheck(dlistvo).getMsg());*/
		
		//财务测试
	/*	DispatchResultVO drsvo=new DispatchResultVO();
		drsvo.setCheckComment("待付款");
		drsvo.setEsn("10000001");
		drsvo.setSheetId(61L);
		drsvo.setStatus(1);
		System.out.println(sf.checkDispatchResult(drsvo).getMsg());*/
		
/*		try {
			System.out.println(sf.findDispatchList(bv, "10000001").getResult().size());
		} catch (MyExecption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	/*	DispatchListVO dlistvo=new DispatchListVO();
		dlistvo.setDlId(60L);
		dlistvo.setSn("10000001");
		System.out.println(sf.pay(dlistvo).getMsg());*/
//		System.out.println(sf.findlist(bv).getResult().size());
	}
}