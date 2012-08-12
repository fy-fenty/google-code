package org.jston.util;

public class StringUtil {

	public static boolean isEmpty(String str){
		if(str==null)
			return false;
		if("".equals((str+"").trim()))
				return false;
		return true;
	}
}
