package org.ymm.util;

import java.text.SimpleDateFormat;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class Json {

	public static String JSON_List(Object obj) {
		JSONArray array = JSONArray.fromObject(obj);
		return array.toString();
	}

	public static String JSON_Object(Object obj) {
		JsonConfig jf = new JsonConfig();  
        //jf.registerJsonValueProcessor(java.sql.Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));  
        jf.registerJsonValueProcessor(java.sql.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));  
        jf.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));  
		JSONObject job = JSONObject.fromObject(obj,jf);
		return job.toString();
	}

	public static String JSON_List(Map map) {
		JSONArray array = JSONArray.fromObject(map);
		return array.toString();
	}
}
class DateJsonValueProcessor implements JsonValueProcessor  
{  
      
    private String format;  
    public DateJsonValueProcessor(String format){  
        this.format = format;  
    }  
      
    public Object processArrayValue(Object value, JsonConfig jsonConfig)  
    {  
        return null;  
    }  
  
    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig)  
    {  
        if(value == null)  
        {  
            return "";  
        }  
        if(value instanceof java.sql.Timestamp)  
        {  
            String str = new SimpleDateFormat(format).format((java.sql.Timestamp)value);  
            return str;  
        }  
        if (value instanceof java.util.Date)  
        {  
            String str = new SimpleDateFormat(format).format((java.util.Date) value);  
            return str;  
        }  
          
        return value.toString();  
    }  
}  
