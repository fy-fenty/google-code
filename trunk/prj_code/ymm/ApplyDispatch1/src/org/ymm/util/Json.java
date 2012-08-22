package org.ymm.util;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Json {

	public static String JSON_List(List list) {
		JSONArray array = JSONArray.fromObject(list);
		return array.toString();
	}

	public static String JSON_Object(Object obj) {
		JSONObject job = JSONObject.fromObject(obj);
		return job.toString();
	}

	public static String JSON_List(Map map) {
		JSONArray array = JSONArray.fromObject(map);
		return array.toString();
	}
}
