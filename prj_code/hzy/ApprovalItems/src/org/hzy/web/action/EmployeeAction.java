package org.hzy.web.action;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import org.hzy.entity.DispatchDetail;
import org.hzy.entity.DispatchList;
import org.hzy.exception.MyException;
import org.hzy.service.IEmployeeService;
import org.hzy.support.ISystemUtil;
import org.hzy.util.StringUtil;
import org.hzy.vo.HandleDispatchVo;
import org.hzy.vo.Page;
import org.hzy.vo.Result;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class EmployeeAction {
	private IEmployeeService ieService;
	private HandleDispatchVo hdVo = new HandleDispatchVo();
	private String dds;
	private ISystemUtil isu;

	public String findAllDispatchListByESn() {
		if (hdVo.getEsn() == null) {
			return null;
		}
		try {
			StringUtil.printObjectFromObject(ieService.findAllDispatchListByESn(hdVo));
		} catch (MyException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String findAllDetailItem() {
		if (hdVo.getEsn() == null) {
			return null;
		}
		try {
			Page pg = isu.getAllDetailItem(hdVo);
			StringUtil.printObjectFromObject(pg);
		} catch (MyException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String commitDispatch() {
		if (hdVo.getEsn() == null) {
			return null;
		}
		Result rs_dl = ieService.saveDispatchList(hdVo);
		if (rs_dl.getSuccess() == false) {
			StringUtil.printObjectFromObject(rs_dl);
			return null;
		}
		Serializable id = rs_dl.getSer();
		Long lid = (Long) id;
		if (lid == null) {
			rs_dl.setSuccess(false);
			rs_dl.setMsg("无效的报销单标识");
			StringUtil.printObjectFromObject(rs_dl);
			return null;
		}
		hdVo.setDlId(lid);
		if (dds.startsWith("[") == false) {
			dds = "[" + dds + "]";
		}
		JSONArray jsonArray = JSONArray.fromObject(dds);
		DispatchDetail[] ddss = (DispatchDetail[]) JSONArray.toArray(jsonArray, DispatchDetail.class);
		if (ddss == null) {
			rs_dl.setSuccess(false);
			rs_dl.setMsg("无效的报销单明细");
			StringUtil.printObjectFromObject(rs_dl);
			return null;
		}
		Result rs_dd = ieService.addDispatchDetail(hdVo, ddss);
		if (rs_dd.getSuccess() == false) {
			StringUtil.printObjectFromObject(rs_dd);
			return null;
		}
		Result rs_sub_dl = ieService.submitDispatchList(hdVo);
		if (rs_sub_dl.getSuccess() == false) {
			StringUtil.printObjectFromObject(rs_sub_dl);
			return null;
		}
		StringUtil.printObjectFromObject(rs_sub_dl);
		return null;
	}

	public String findDd() {
		if (hdVo.getEsn() == null) {
			return null;
		}
		try {
			List<DispatchDetail> dds = isu.findDispatchDetailByDlId(hdVo.getDlId());
			Map map = new HashMap();
			map.put("dds", JSONSerializer.toJSON(dds));
			StringUtil.printObjectFromObject(map);
		} catch (MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String findDl() {
		if (hdVo.getEsn() == null) {
			return null;
		}
		try {
			DispatchList dl = isu.findDispatchListByDlId(hdVo.getDlId());
			Map map = new HashMap();
			Map data = new HashMap();
			data.put("hdVo.comment", dl.getEventExplain());
			map.put("data", data);
			map.put("success", true);
			StringUtil.printObjectFromObject(map);
		} catch (MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String updateDl() {
		if (hdVo.getEsn() == null) {
			return null;
		}
		StringUtil.printObjectFromObject(ieService.updateDispatchList(hdVo));
		return null;
	}

	public String updateDd() {
		if (hdVo.getEsn() == null) {
			return null;
		}
		StringUtil.printObjectFromObject(ieService.updateDispatchDetail(hdVo));
		return null;
	}

	public String deleteDd() {
		if (hdVo.getEsn() == null) {
			return null;
		}
		StringUtil.printObjectFromObject(ieService.deleteDispatchDetail(hdVo));
		return null;
	}

	public IEmployeeService getIeService() {
		return ieService;
	}

	public void setIeService(IEmployeeService ieService) {
		this.ieService = ieService;
	}

	public HandleDispatchVo getHdVo() {
		return hdVo;
	}

	public void setHdVo(HandleDispatchVo hdVo) {
		this.hdVo = hdVo;
	}

	public String getDds() {
		return dds;
	}

	public void setDds(String dds) {
		this.dds = dds;
	}

	public ISystemUtil getIsu() {
		return isu;
	}

	public void setIsu(ISystemUtil isu) {
		this.isu = isu;
	}

	public static void main(String[] args) {
		ApplicationContext actc = new ClassPathXmlApplicationContext(new String[] { "hibernate-spring.xml", "beans1.xml" });
		EmployeeAction ea = actc.getBean("org.hzy.web.action.EmployeeAction", EmployeeAction.class);
		// /* 直接提交报销单 */
		// ea.hdVo = new HandleDispatchVo();
		// ea.hdVo.setComment("aaaaaaa");
		// ea.hdVo.setEsn("10000000");
		// DispatchDetail[] dd = new Dispatchz;
		// dd.setCostExplain("bbbbbbbb");
		// dd.setItemId(2L);
		// dd.setMoney(222D);
		// ea.dds = ll;
		// ea.commitDispatch();

		/* 看报销单所有类别 */
		ea.findAllDetailItem();

	}
}
