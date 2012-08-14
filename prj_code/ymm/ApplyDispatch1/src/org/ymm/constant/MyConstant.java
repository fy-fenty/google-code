package org.ymm.constant;

import java.util.HashMap;
import java.util.Map;

public class MyConstant {
	public static final Map<String,String> map=new HashMap();
	static{
		map.put("A001", AppConstant.ACTION_ERROR_MSG_A001);
		map.put("A002", AppConstant.ACTION_ERROR_MSG_A002);
	}
}
