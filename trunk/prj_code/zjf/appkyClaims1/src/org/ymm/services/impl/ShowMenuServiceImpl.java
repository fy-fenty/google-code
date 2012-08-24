package org.ymm.services.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.ymm.services.IShowMenuService;
import org.zjf.dao.ISysMenuDao;
import org.zjf.dao.ISysRoleDao;
import org.zjf.entity.SysMenu;
import org.zjf.entity.SysRole;
import org.zjf.exception.MyException;
import org.zjf.util.StringUtil;
import org.zjf.vo.TreeVo;

public class ShowMenuServiceImpl implements IShowMenuService {

	private ISysRoleDao iSysRoleDao;
	private ISysMenuDao iSysMenuDao;
	
	public ISysMenuDao getiSysMenuDao() {
		return iSysMenuDao;
	}

	public void setiSysMenuDao(ISysMenuDao iSysMenuDao) {
		this.iSysMenuDao = iSysMenuDao;
	}

	public ISysRoleDao getiSysRoleDao() {
		return iSysRoleDao;
	}

	public void setiSysRoleDao(ISysRoleDao iSysRoleDao) {
		this.iSysRoleDao = iSysRoleDao;
	}

	public String findEmpShowMenu(long p_id) throws MyException {
		if(StringUtil.isEmpty(p_id+"")==false){
			throw new MyException("A003");
		}
		String sql=" from SysRole where p_id=? ";
		SysRole sysRole = iSysRoleDao.findUniqueByHQL(sql, p_id);
		System.out.println(sysRole.getMenu());
		String sql1=" from SysMenu where m_id in ("+sysRole.getMenu()+") "; 
		List list=iSysMenuDao.findByHQL(sql1);
		
		TreeVo tv=null;
		List l=new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			SysMenu sysMenu =(SysMenu)list.get(i);
			tv=new TreeVo();
			tv.setText(sysMenu.getMenuName());
			tv.setCls("folder");
			tv.setLeaf(true);
			tv.setUrl(sysMenu.getUrl());
			l.add(tv);
		}
		
		JSONArray ja1=new JSONArray().fromObject(l);
		return ja1.toString();
	}
	
	public static void main(String[] args) throws MyException {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { "spring-sessinfactory.xml",
						"spring-dao-beans.xml" });
		IShowMenuService ies = ac.getBean("showMenuServiceImpl", IShowMenuService.class);
		String str= ies.findEmpShowMenu((long)3);
		System.out.println(str);
	}
}
