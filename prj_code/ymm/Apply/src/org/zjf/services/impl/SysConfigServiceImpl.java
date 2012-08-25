package org.zjf.services.impl;

import org.ymm.dao.ISysConfigDao;
import org.ymm.entity.SysConfig;
import org.zjf.services.ISysConfigService;

public class SysConfigServiceImpl implements ISysConfigService {

	private ISysConfigDao configdao;

	public ISysConfigDao getConfigdao() {
		return configdao;
	}

	public void setConfigdao(ISysConfigDao configdao) {
		this.configdao = configdao;
	}

	
	public SysConfig findSysConfig(long userid) {
		SysConfig config=configdao.findUnique("select u from SysConfig u where User_Id=?", userid+"");
		System.out.println(config);
		return config;
	}

}
