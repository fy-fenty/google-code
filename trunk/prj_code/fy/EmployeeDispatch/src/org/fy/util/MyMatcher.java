package org.fy.util;

import java.util.Map;
import java.util.regex.Pattern;

public class MyMatcher {
	private static Map<String, String> checks;

	static {
		checks.put("isNaN", "^\\d+$");
	}

	/**
	 * 验证参数是否为空，null 或者空字符串
	 * @param input 需要验证的参数
	 * @return 空为True，反之Fasle
	 */
	public static boolean isEmpty(String input) {
		return "".equals(input.trim()) || input == null ? true : false;
	}

	/**
	 * 验证是否非数字
	 * @param input 需要验证的参数
	 * @return 数字为True，反之False
	 */
	public static boolean isNaN(String input) {
		return Pattern.matches(checks.get("isNaN"), input);
	}
}
