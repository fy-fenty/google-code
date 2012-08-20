package org.zjf.util;

/**
 * @project:ApplyClaims
 * @author:zjf
 * @date:2012-8-13 下午10:44:36   
 * @class:StringUtil
 * @extends:
 * @description:字符串工具类
 */
public class StringUtil {

	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return 布尔值
	 */
	public static boolean isEmpty(String str){
		if(str==null)
			return false;
		if("null".equals(str))
			return false;
		if("".equals(str.trim()))
			return false;
		return true;
	}
}
