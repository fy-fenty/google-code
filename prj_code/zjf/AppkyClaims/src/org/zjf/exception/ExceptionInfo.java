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

	public final static Map<String,String> map=Collections.emptyMap();
	
	static{
		map.put("A001", AppConstant.DEFAULT_MSG);
		
		map.put("A002", AppConstant.PARAM_ERROR);
		map.put("A003", AppConstant.NULL_ERROR);
		map.put("A004", AppConstant.PATCH_ERROR);
		map.put("A005", AppConstant.SYS_ERROR);
		map.put("A006", AppConstant.FLAG_ERROR);
		
		map.put("A007", AppConstant.DELETE_ERROR);
		map.put("A008", AppConstant.SAVE_ERROR);
		map.put("A009", AppConstant.UPDATE_ERROR);
		
		map.put("A010", AppConstant.LIST_ERROR);
		map.put("A011", AppConstant.EXSIS_ERROR);
		
		map.put("A012", AppConstant.INEQUALITY_ERROR);
		map.put("A013", AppConstant.OTHER_ERROR);
		map.put("A014", AppConstant.SELECT_ERROR);
	}
}
