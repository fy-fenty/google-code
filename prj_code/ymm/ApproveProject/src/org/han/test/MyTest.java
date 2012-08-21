package org.han.test;

import java.util.Date;

import net.sf.json.JSONObject;

import org.han.entity.DispatchDetail;
import org.han.entity.DispatchList;
import org.han.services.IDManagerService;
import org.han.services.IEmpService;
import org.han.services.IFinancialService;
import org.han.services.IGManagerService;
import org.han.services.ISystemServices;
import org.han.util.JsonValueProcessorImpl;
import org.han.vo.ApprovalVo;
import org.han.vo.BaseVo;
import org.han.vo.Page;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author tender
 * @date 2012-8-14
 * @ClassName: MyTest
 * @Description: TODO
 * @version
 */
public class MyTest {
	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "spring-hibernate.xml", "spring-dao-beans.xml",
						"spring-services-bean.xml" });
		// ISystemServices sysBiz = context.getBean("sysBiz",
		// ISystemServices.class);
		// System.out.println(sysBiz.getDisListById(1l));
		IEmpService empBiz = context.getBean("empBiz", IEmpService.class);
		Page page = empBiz.findDisByEmpId(new BaseVo(0, 10), "10000000");

		System.out.println(page.getData().size());
		String str = new JsonValueProcessorImpl("yyyy-MM-dd HH:mm:ss")
				.getStrByObject(page);

		System.out.println(str);
		// DispatchDetail detail = new DispatchDetail();
		// detail.setDsId(20L);
		// detail.setSheetId(23L);
		// detail.setMoney(null);
		// detail.setItemId(1L);

		// detail.setFlag(true);
		// System.out.println(empBiz.updateDisDetail("10000000", detail)
		// .getSuccess());
		// System.out.println(empBiz.saveDisDetail("10000000", detail));
		// DispatchList dl = new DispatchList();
		// dl.setDlId(23L);
		// dl.setEventExplain("123");
		// dl.setCreateTime(new Date());
		// dl.setESn("10000000");
		// System.out.println(empBiz.updateDispatch("10000000",
		// dl).getSuccess());
		// System.out.println(empBiz.saveDispatch("10000000", dl).getSuccess());
		// System.out.println(dl.getDlId());

		// System.out.println(empBiz.deleteDispatch("10000000", 3L));emp
		// System.out.println(empBiz.commitDispatch("10000000", 3L));

		// System.out.println(empBiz.findDisByEmpId(new BaseVo(0, 2),
		// "10000000")
		// .getData().size());

		IGManagerService gm = context.getBean("gmBiz", IGManagerService.class);
		// System.out.println(gm.approval(
		// new ApprovalVo("10000002", 60L, 3, "终止")).getSuccess());
		// System.out.println(gm.findMeApproval("10000002", new BaseVo(0, 2))
		// .getData().size());
		// System.out.println(gm.finishDispatch(
		// new ApprovalVo("10000002", 21L, "挂了")).getSuccess());

		IDManagerService dm = context.getBean("dmBiz", IDManagerService.class);
		// System.out.println(dm.resetPwd("10000003", "10000000"));
		// System.out.println(dm.findMeApproval("10000003", new BaseVo(0, 20))
		// .getData().size());
		// System.out.println(dm.approval(new ApprovalVo("10000003", 23L, 1,
		// "同")));

		IFinancialService fbiz = context.getBean("finaBiz",
				IFinancialService.class);
		// fbiz.approval(new ApprovalVo("10000001", 23L, 1,
		// "1111111111111111111111"));
		// System.out.println(fbiz.pay(new ApprovalVo("10000001", 23L, "付款"))
		// .getSuccess());
		// System.out.println(fbiz.findApproval("10000001", new BaseVo(0, 2))
		// .getData().size());
	}
}
