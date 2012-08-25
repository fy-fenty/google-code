package org.zjf.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.ymm.dao.ISysMenuDao;
import org.ymm.dao.ISysRoleDao;
import org.ymm.entity.SysMenu;
import org.ymm.entity.SysRole;
import org.zjf.services.ISysMenuService;

public class SysMenuServiceImpl implements ISysMenuService {

	private ISysMenuDao menudao;
	private ISysRoleDao roledao;

	public ISysMenuDao getMenudao() {
		return menudao;
	}

	public void setMenudao(ISysMenuDao menudao) {
		this.menudao = menudao;
	}

	public ISysRoleDao getRoledao() {
		return roledao;
	}

	public void setRoledao(ISysRoleDao roledao) {
		this.roledao = roledao;
	}

	
	public List<SysMenu> findMenuByPID(long pid) {
		SysRole role=roledao.findUnique("from SysRole where P_Id=?", pid+"");
		String [] sts=role.getMenu().split(",");
		List<SysMenu> list=new ArrayList<SysMenu>();
		for (int i = 0; i < sts.length; i++) {
			long mui=Integer.parseInt(sts[i]);
			list.add(menudao.get(mui));
		}
		return list;
	}
}
