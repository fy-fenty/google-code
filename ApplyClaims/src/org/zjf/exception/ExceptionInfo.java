package org.zjf.exception;

import java.util.Collections;
import java.util.Map;

import org.zjf.constant.AppConstant;

public class ExceptionInfo {

	public final static Map<String,String> ERROR_MAP=Collections.emptyMap();
	
	static{
		ERROR_MAP.put("A000", AppConstant.DEFAULT_MSG);
		ERROR_MAP.put("A001", AppConstant.A001);
		ERROR_MAP.put("A002",AppConstant.A002);
	}
}
