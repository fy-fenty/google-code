package org.zjf.services;

import org.ymm.entity.SysConfig;

/**
 * @project:ApplyDispatch1
 * @author:zjf
 * @date:2012-8-22 上午11:28:51   
 * @class:ISysConfigService
 * @extends:Object
 * @description:系统配置服务接口
 */
public interface ISysConfigService {

	/**
	 * 通过用户id查询系统配置
	 * @param userid
	 * @return
	 */
	public SysConfig findSysConfig(long userid);
}
