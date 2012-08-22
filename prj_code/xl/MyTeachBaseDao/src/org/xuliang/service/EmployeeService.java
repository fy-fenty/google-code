package org.xuliang.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.xuliang.constant.AppConstant;
import org.xuliang.dao.MyUserDao;
import org.xuliang.entity.MyUser;
import org.xuliang.exception.MyException;
import org.xuliang.utils.SessionListener;
import org.xuliang.vo.CurrentUser;
import org.xuliang.vo.LoginVo;
import org.xuliang.vo.Page;

@SuppressWarnings("unchecked")
public class EmployeeService implements IEmployeeService{
	public MyUserDao mud;
	
	public Map<String,Object> login(String uname,String upwd) throws MyException{
		if("xy".equals(uname)){
			throw new MyException("A001");
		}
		return null;
	}
	
	public Page findDispathList(){
		String sql = "";
		this.mud.countSqlResult(sql,(Map)null);

		return null;
	}

	@Override
	public String login(LoginVo lv) {
		// TODO Auto-generated method stub
		ServletActionContext.getRequest().setAttribute(AppConstant.CURRENT_UNAME, lv.getUname());
		HttpSession se = ServletActionContext.getRequest().getSession();
		
		se.setAttribute(AppConstant.CURRENT_USER, null);
		SessionListener.removeSession(se.getId());
		
		MyUser mu = new MyUser();
		mu.setMname("x");
		mu.setId("1");
		
		Map<String,HttpSession> map = SessionListener.getSessionMaps();
		for (String s : map.keySet()) {
			HttpSession se1 = map.get(s);
			CurrentUser cu = (CurrentUser)se1.getAttribute(AppConstant.CURRENT_USER);
			
			if(s.equals(se.getId())){
				continue;
			}
			
			if(mu.getId().equals(cu.getUid())){
				se1.setAttribute(AppConstant.CURRENT_USER, null);
				SessionListener.removeSession(se1.getId());
				break;
			}
		}
		
		CurrentUser cu = new CurrentUser();
		cu.setUid(mu.getId());
		//设置若干属性
		
		se.setAttribute(AppConstant.CURRENT_USER, cu);
		SessionListener.addSession(se.getId(),se);
		
		return null;
	}
}
