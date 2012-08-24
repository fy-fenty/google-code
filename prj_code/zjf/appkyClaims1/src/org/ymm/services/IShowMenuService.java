package org.ymm.services;

import java.util.List;

import org.zjf.exception.MyException;

/**
 * @author yingmingming 
 * @date 2012-8-23 下午4:41:34
 * @ClassName: IShowMenuService 
 * @extends 	 
 * @Description: 显示menu的接口
 */
public interface IShowMenuService {
	public String findEmpShowMenu(final long p_id)throws MyException;
}
