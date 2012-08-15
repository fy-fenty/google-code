package org.ymm.services;
/**
 * @author yingmingming 
 * @date 2012-8-15 下午9:24:35
 * @ClassName: ISystemService 
 * @extends 	 
 * @Description: 系统服务接口
 */
public interface ISystemService {
	/**
	 * @Title: getMd5 
	 * @param @param pwd
	 * @param @return
	 * @return String     
	 * @Description: md5加密
	 * @throws
	 */
	public String getMd5(String pwd);
}
