package org.hzy.service;

import org.hzy.exception.MyException;
import org.hzy.vo.Page;

/**
 * @author fy
 * @date 2012-8-22
 * @class IMenuService
 * @extends Object
 * @description 管理菜单的接口
 */
public interface IMenuService {
	public Page findMEnuByEsn(String esn) throws MyException;
}
