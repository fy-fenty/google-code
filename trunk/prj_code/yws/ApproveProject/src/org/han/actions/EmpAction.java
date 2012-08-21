package org.han.actions;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.han.services.IEmpService;

import com.opensymphony.xwork2.ActionSupport;

@Namespace("/emp")
public class EmpAction extends ActionSupport {

	private IEmpService empBiz;

	public IEmpService getEmpBiz() {
		return empBiz;
	}

	public void setEmpBiz(IEmpService empBiz) {
		this.empBiz = empBiz;
	}

	@Action(value = "dis")
	public String getDispatchByEmpSn() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		String str = session.getId();
		System.out.println(session.getValue(str));
		System.out.println(str);
		session.invalidate();
		System.out.println(session.getId());
		return null;
	}
}
