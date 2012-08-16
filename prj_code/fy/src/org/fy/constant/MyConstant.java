package org.fy.constant;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class MyConstant {
	private static Map<String, String> errors;

	static {
		if (errors == null) {
			errors = new HashMap();
		}
		errors.put("A001", AppConstant.AOO1);
	}

	/**
	 * @author fy
	 * @param key
	 *            错误码
	 * @description 根据错误码返回错误消息，错误码参考@AppConstant
	 * @return 错误消息
	 */
	public static String getErrorsByCode(String key) {
		return errors.get(key);
	}

}