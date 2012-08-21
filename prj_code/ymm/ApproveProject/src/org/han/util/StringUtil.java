package org.han.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class StringUtil {
	private static Map<String, String> checks;

	static {
		checks = new HashMap<String, String>();
		checks.put("isNaN", "^\\d+$");
	}

	/**
	 * 验证参数是否为空，null 或者空字符串
	 * 
	 * @param input
	 *            需要验证的参数
	 * @return 空为True，反之False
	 */
	public static boolean isEmpty(Object input) {
		return input == null || "".equals(input.toString().trim()) ? true
				: false;
	}

	/**
	 * 验证是否非数字
	 * 
	 * @param input
	 *            需要验证的参数
	 * @return 数字为True，反之False
	 */
	public static boolean isNaN(String input) {
		return Pattern.matches(checks.get("isNaN"), input);
	}
}
