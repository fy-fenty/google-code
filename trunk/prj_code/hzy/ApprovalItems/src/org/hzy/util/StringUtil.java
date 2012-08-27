package org.hzy.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;

/**
 * @author hzy
 * @date 2012-8-14
 * @extends Object
 * @class StringUtil
 * @description 字符串工具类
 */

public class StringUtil {
	public static boolean isEmpty(String str) {
		if (str == null) {
			return false;
		}
		if ("".equals((str + "").trim()))
			return false;
		return true;
	}

	public static String parseToJsonFromObject(Object obj) {
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		jc.registerJsonValueProcessor(java.sql.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		jc.registerJsonValueProcessor(java.sql.Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		return JSONSerializer.toJSON(obj, jc).toString();
	}

	public static void printObjectFromObject(Object obj) {
		HttpServletResponse res = ServletActionContext.getResponse();
		PrintWriter out = null;
		try {
			res.setCharacterEncoding("UTF-8");
			out = res.getWriter();
			out.print(parseToJsonFromObject(obj));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out == null) {
				return;
			}
			out.flush();
			out.close();
		}
	}
}
