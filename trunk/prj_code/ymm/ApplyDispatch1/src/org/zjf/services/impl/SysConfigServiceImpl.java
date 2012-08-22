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

	@Override
	public SysConfig findSysConfig(long userid) {
		SysConfig config=configdao.findUnique("from SysConfig where UserId=?", userid+"");
		return config;
	}

}
