package org.fy.util;

import org.fy.vo.Page;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class JsonUtil {
	public static String PageJson(Page page){
		JSONObject json=null;
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor()); 
		jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class, new JsonDateValueProcessor());
		if(page!=null){
			json=JSONObject.fromObject(page,jsonConfig);
		}
		return json.toString();
	}
//	public static String ListJson(List<?> list){
//		JSONArray json=null;
//		 if(list.size()>0&list!=null){
//			 json=JSONArray.fromObject(list);
//		 }
//		 return json.toString();
//	}
}
