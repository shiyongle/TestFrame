/**
 * @title IntegerUtil.java
 * @description TODO
 * @package lm.com.framework
 * @author mrluo735
 * @since JDK1.7
 * @date 2016年11月9日下午5:15:25
 * @version v1.0
 */
package com.dongnao.autotest.common;

/**
 * Integer 帮助类
 * 
 * @author mrluo735
 *
 */
public final class IntegerUtil {
	/**
	 * 重载+1 字符串转成int
	 * 
	 * @param value
	 * @return
	 * @throws NumberFormatException
	 */
	public static int toInteger(String value) throws NumberFormatException {
		return Integer.parseInt(value);
	}

	/**
	 * 重载+2 字符串转成int
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static int toInteger(String value, int defaultValue) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException ex) {
			return defaultValue;
		}
	}

	/**
	 * 重载+3 字符串转成Integer
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static Integer toInteger(String value, Integer defaultValue) {
		try {
			return Integer.valueOf(value);
		} catch (NumberFormatException ex) {
			return defaultValue;
		}
	}

	/**
	 * 字符串转成Integer
	 * 
	 * @param value
	 * @return
	 * @throws NumberFormatException
	 */
	public static Integer toIntegerOrNull(String value) {
		try {
			return Integer.valueOf(value);
		} catch (NumberFormatException ex) {
			return null;
		}
	}

	/**
	 * 重载+1 转换成int
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static int tryParse(String value, int defaultValue) {
		if (StringUtil.isNullOrWhiteSpace(value))
			return defaultValue;
		for (char ch : value.toCharArray()) {
			if (!Character.isDigit(ch))
				return defaultValue;
		}
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException ex) {
			return defaultValue;
		}
	}

	/**
	 * 重载+2 转换成Integer
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static Integer tryParse(String value, Integer defaultValue) {
		if (StringUtil.isNullOrWhiteSpace(value))
			return defaultValue;
		for (char ch : value.toCharArray()) {
			if (!Character.isDigit(ch))
				return defaultValue;
		}
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException ex) {
			return defaultValue;
		}
	}

	/**
	 * 转换成Integer
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static Integer tryParseOrNull(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException ex) {
			return null;
		}
	}
}
