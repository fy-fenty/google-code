package org.hzy.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hzy
 * @date 2012-8-14
 *	@extends Object
 * @class MyConstant
 * @description 存储异常码
 */
public class MyConstant {
	public static final Map<String,String> map = new HashMap();
	
	static{
		map.put("A001", AppConstant.A001);
//		map.put("A002", AppConstant.A002);
	}
}
