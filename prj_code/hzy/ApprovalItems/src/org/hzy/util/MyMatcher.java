package org.hzy.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class MyMatcher {
	private static Map<String, String> checks;

	static {
		checks = new HashMap();
		checks.put("isNaN", "^\\d+$");
	}

	/**
	 * 验证参数是否为空，null 或者空字符串
	 * 
	 * @param input
	 *            需要验证的参数
	 * @return 空为True，反之Fasle
	 */
	public static boolean isEmpty(String input) {
		return input == null || "".equals(input.trim()) ? true : false;
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
