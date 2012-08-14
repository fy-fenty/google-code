package org.zjf.exception;

import java.util.Collections;
import java.util.Map;

import org.zjf.constant.AppConstant;

/**
 * @project:ApplyClaims
 * @author:zjf
 * @date:2012-8-14 下午12:37:54   
 * @class:ExceptionInfo
 * @extends:Object
 * @description:存放异常以及异常信息的集合
 */
public class ExceptionInfo {

	public final static Map<String,String> ERROR_MAP=Collections.emptyMap();
	
	static{
		ERROR_MAP.put("A000", AppConstant.DEFAULT_MSG);
		ERROR_MAP.put("A001", AppConstant.A001);
		ERROR_MAP.put("A002",AppConstant.A002);
	}
}
