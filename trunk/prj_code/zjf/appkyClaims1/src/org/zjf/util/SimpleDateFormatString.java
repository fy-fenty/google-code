package org.zjf.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleDateFormatString {
	public static String sfString(Date d){
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return sf.format(d);
	}
}
