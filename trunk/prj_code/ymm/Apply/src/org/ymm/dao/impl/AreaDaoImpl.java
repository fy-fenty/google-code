package org.ymm.dao.impl;

import org.ymm.dao.IAreaDao;
import org.ymm.entity.Area;
import org.ymm.support.BaseDaoImpl;

/**
 * @author yingmingming 
 * @date 2012-8-14 下午3:25:27
 * @ClassName: AreaDaoImpl 
 * @extends BaseDaoImpl   implements：IAreaDao
 * @Description: IAreaDao接口的实现类
 */
public class AreaDaoImpl extends BaseDaoImpl<Area, Long> 
	implements IAreaDao{


	public void say() {
		// TODO Auto-generated method stub
		System.out.println("ss");
	}
}
