package org.hzy.web.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONSerializer;

import org.apache.struts2.ServletActionContext;
import org.hzy.constant.AppConstant;
import org.hzy.exception.MyException;
import org.hzy.service.IMenuService;
import org.hzy.vo.CurrentUser;
import org.hzy.vo.Page;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class GetMenuAction {
	private IMenuService imService;

	public String getMenu() {
		CurrentUser esn = (CurrentUser) ServletActionContext.getRequest().getSession().getAttribute(AppConstant.CURRENT_USER);
		esn = new CurrentUser(1L, "10000000");
		try {
			// if (esn == null) {
			// return null;
			// }
			Page pg = imService.findMEnuByEsn(esn.getUname());
			List<Map<String, String>> maps = new ArrayList();
			for (int i = 0; i < pg.getResult().size(); i++) {
				Map<String, String> mp = (Map<String, String>) pg.getResult().get(i);
				Map mps = new HashMap();
				mps.put("text", mp.get("MENU_NAME"));
				mps.put("leaf", true);
				mps.put("url", mp.get("URL"));
				maps.add(mps);
			}
			ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
			ServletActionContext.getResponse().getWriter().print(JSONSerializer.toJSON(maps));
		} catch (MyException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public IMenuService getImService() {
		return imService;
	}

	public void setImService(IMenuService imService) {
		this.imService = imService;
	}

}