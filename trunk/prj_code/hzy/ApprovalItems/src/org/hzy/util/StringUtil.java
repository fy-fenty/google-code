package org.hzy.util;

/**
 * @author hzy
 * @date 2012-8-14
 *	@extends Object
 * @class StringUtil
 * @description  字符串工具类
 */
public class StringUtil {
	public static boolean isEmpty(String str){
		if(str==null){
			return false;
		}
		if("".equals((str+"").trim()))
			return false;
		return true;
	}
}
