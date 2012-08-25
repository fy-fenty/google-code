package org.zjf.services;

import java.util.List;

import org.ymm.entity.SysMenu;

/**
 * @project:ApplyDispatch1
 * @author:zjf
 * @date:2012-8-22 上午11:24:20   
 * @class:IRoleService
 * @extends:Object
 * @description:角色服务接口
 */
public interface ISysMenuService {

	/**
	 * 根据职位查询菜单
	 * @param pid
	 * @return
	 */
	public List<SysMenu> findMenuByPID(long pid);

}
