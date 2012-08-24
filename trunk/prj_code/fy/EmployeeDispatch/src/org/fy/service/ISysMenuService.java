package org.fy.service;

import java.util.List;

import org.fy.exception.MyExecption;
import org.fy.vo.Page;

/**
 * @author hzy
 * @date 2012-8-22
 *	@extends Object
 * @class ISysMenuService
 * @description 生成菜单业务接口
 */
public interface ISysMenuService {
	/**
	 * @param 职位 pid
	 * @return Page
	 */
	public String findMenu(final Long pid) throws MyExecption;
}
