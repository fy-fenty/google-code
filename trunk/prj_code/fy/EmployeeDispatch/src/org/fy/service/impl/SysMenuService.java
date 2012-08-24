package org.fy.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.fy.dao.ISysMenuDao;
import org.fy.dao.ISysRoleDao;
import org.fy.entity.SysMenu;
import org.fy.entity.SysRole;
import org.fy.exception.MyExecption;
import org.fy.service.ISysEmployeeService;
import org.fy.service.ISysMenuService;
import org.fy.util.MyMatcher;
import org.fy.vo.Page;
import org.fy.vo.TreeBean;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author hzy
 * @date 2012-8-22
 *	@extends Object
 * @class SysMenuService
 * @description 生成菜单业务接口实现类
 */
public class SysMenuService implements ISysMenuService{
	private ISysMenuDao isys_menudao;
	private ISysRoleDao isys_roledao;
	public ISysMenuDao getIsys_menudao() {
		return isys_menudao;
	}
	public void setIsys_menudao(ISysMenuDao isys_menudao) {
		this.isys_menudao = isys_menudao;
	}
	public ISysRoleDao getIsys_roledao() {
		return isys_roledao;
	}
	public void setIsys_roledao(ISysRoleDao isys_roledao) {
		this.isys_roledao = isys_roledao;
	}
	
	public String findMenu(Long pid) throws MyExecption {
		if(MyMatcher.isEmpty(pid)){
			throw new MyExecption("A002");
		}
		SysRole sr=isys_roledao.findUnique("From SysRole where PId=?", pid);
		if(sr==null||MyMatcher.isEmpty(sr.getMenu())){
			throw new MyExecption("A003");
		}
		String sql="select * from hzy.sys_menu where m_id in ("+sr.getMenu()+")";
		SQLQuery sqlQuery=isys_menudao.createSQLQuery(sql).addEntity(SysMenu.class);
		List<SysMenu> list=sqlQuery.list();
		List<TreeBean> treeNodeArray = new ArrayList<TreeBean>();
		TreeBean root=new TreeBean();//根
		root.setCls("folder");
		root.setId(4L);
		root.setText("雇员");
		root.setLeaf(false);
		for (int i = 0; i < list.size(); i++) {
			TreeBean tb=new TreeBean();
			SysMenu sm=list.get(i);
			tb.setId(sm.getMId());
			tb.setText(sm.getMenuName());
			tb.setUrl(sm.getUrl());
			tb.setLeaf(true);
			treeNodeArray.add(tb);
		}
		root.setChildren(treeNodeArray);
		JSONArray ja=JSONArray.fromObject(root);
		return ja.toString();
	}
	
	public static void main(String[] args) {
		ApplicationContext apc = new ClassPathXmlApplicationContext(
				new String[] { "hibernate-spring.xml", "beans.xml" });
		ISysMenuService sf = apc.getBean("sys_menu_service", ISysMenuService.class);
		try {
			System.out.println(sf.findMenu(3L));
		} catch (MyExecption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
