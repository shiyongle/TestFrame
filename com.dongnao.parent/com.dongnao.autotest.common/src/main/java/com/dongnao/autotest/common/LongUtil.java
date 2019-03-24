package com.dongnao.autotest.common;

/**
 * Long 帮助类
 * 
 * @author Administrator
 *
 */
public final class LongUtil {
	/**
	 * 重载+1 字符串转成long
	 * 
	 * @param value
	 * @return
	 * @throws NumberFormatException
	 */
	public static long toLong(String value) throws NumberFormatException {
		return Long.parseLong(value);
	}

	/**
	 * 重载+2 字符串转成long
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static long toLong(String value, long defaultValue) {
		try {
			return Long.parseLong(value);
		} catch (NumberFormatException ex) {
			return defaultValue;
		}
	}

	/**
	 * 重载+3 字符串转成Long
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static Long toLong(String value, Long defaultValue) {
		try {
			return Long.valueOf(value);
		} catch (NumberFormatException ex) {
			return defaultValue;
		}
	}

	/**
	 * 字符串转成Long
	 * 
	 * @param value
	 * @return
	 * @throws NumberFormatException
	 */
	public static Long toLongOrNull(String value) {
		try {
			return Long.valueOf(value);
		} catch (NumberFormatException ex) {
			return null;
		}
	}

	/**
	 * 重载+1 转换成long
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static long tryParse(String value, long defaultValue) {
		if (StringUtil.isNullOrWhiteSpace(value))
			return defaultValue;
		for (char ch : value.toCharArray()) {
			if (!Character.isDigit(ch))
				return defaultValue;
		}
		try {
			return Long.parseLong(value);
		} catch (NumberFormatException ex) {
			return defaultValue;
		}
	}

	/**
	 * 重载+2 转换成Long
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static Long tryParse(String value, Long defaultValue) {
		if (StringUtil.isNullOrWhiteSpace(value))
			return defaultValue;
		for (char ch : value.toCharArray()) {
			if (!Character.isDigit(ch))
				return defaultValue;
		}
		try {
			return Long.parseLong(value);
		} catch (NumberFormatException ex) {
			return defaultValue;
		}
	}

	/**
	 * 转换成Long
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static Long tryParseOrNull(String value) {
		try {
			return Long.parseLong(value);
		} catch (NumberFormatException ex) {
			return null;
		}
	}
}
