package org.hzy.service.impl;

import java.util.ArrayList;
import java.util.Map;

import org.hzy.constant.AppConstant;
import org.hzy.dao.ISysMenuDao;
import org.hzy.dao.ISysRoleDao;
import org.hzy.exception.MyException;
import org.hzy.service.IMenuService;
import org.hzy.support.ISystemUtil;
import org.hzy.util.MyMatcher;
import org.hzy.vo.BaseVo;
import org.hzy.vo.Page;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author fy
 * @date 2012-8-22
 * @class MenuService
 * @implements IMenuService
 * @description 管理菜单的接口
 */
public class MenuService implements IMenuService {

	private ISystemUtil isu;
	private ISysRoleDao isrDao;
	private ISysMenuDao ismDao;

	@Override
	public Page findMEnuByEsn(String esn) throws MyException {
		if (MyMatcher.isEmpty(esn)) {
			throw new MyException(AppConstant.A001);
		}
		Map map = isu.findUniqueBySQL("select menu from hzy.sys_role where p_id = ?", isu.findSysPositionsByESn(esn).getPId());
		return isu.findPageBySQL(new BaseVo(), "select * from hzy.sys_menu where m_id in (" + map.get("MENU") + ")");
	}

	public ISystemUtil getIsu() {
		return isu;
	}

	public void setIsu(ISystemUtil isu) {
		this.isu = isu;
	}

	public ISysRoleDao getIsrDao() {
		return isrDao;
	}

	public void setIsrDao(ISysRoleDao isrDao) {
		this.isrDao = isrDao;
	}

	public ISysMenuDao getIsmDao() {
		return ismDao;
	}

	public void setIsmDao(ISysMenuDao ismDao) {
		this.ismDao = ismDao;
	}

	public static void main(String[] args) throws MyException {
		ApplicationContext actc = new ClassPathXmlApplicationContext(new String[] { "hibernate-spring.xml", "beans1.xml" });
		IMenuService is = actc.getBean("MenuService", IMenuService.class);
		Page p = is.findMEnuByEsn("10000000");
		System.out.println(p.getResult());
	}
}
