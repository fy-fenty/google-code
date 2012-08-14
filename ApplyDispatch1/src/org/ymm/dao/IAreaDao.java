package org.ymm.dao;

import org.ymm.entity.Area;
import org.ymm.support.IBaseDao;
/**
 * @author yingmingming 
 * @date 2012-8-14 下午4:01:02
 * @ClassName: IAreaDao 
 * @extends IBaseDao	 
 * @Description: 一个接口,对Area表需要功能的定义
 */
public interface IAreaDao extends IBaseDao<Area, Long> {
	public void say();
}
