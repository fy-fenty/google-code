package org.fy.util;

import java.util.List;

import org.fy.vo.Page;
import org.fy.vo.Result;

import net.sf.json.JSONArray;
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
	public static String RsJson(Result rs){
		JSONObject json=null;
		 if(rs!=null){
			 json=JSONObject.fromObject(rs);
		 }
		 return json.toString();
	}
	public static String ArrayJson(List<?> data){
		JSONArray json=null;
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor()); 
		jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class, new JsonDateValueProcessor());
		 if(data!=null){
			 json=JSONArray.fromObject(data,jsonConfig);
		 }
		 return json.toString();
	}
	public static String ObjectJson(Object data){
		JSONArray json=null;
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor()); 
		jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class, new JsonDateValueProcessor());
		 if(data!=null){
			 json=JSONArray.fromObject(data,jsonConfig);
		 }
		 return json.toString();
	}
	
	public static List JsonToBean(String str){
		JSONArray jsonArrary=JSONArray.fromObject(str);
		return jsonArrary;
	}
}
