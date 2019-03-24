/**
 * @title DoubleUtil.java
 * @description TODO
 * @package lm.com.framework
 * @author mrluo735
 * @since JDK1.7
 * @date 2016年12月30日下午12:49:32
 * @version v1.0
 */
package com.dongnao.autotest.common;

/**
 * Double 工具类
 * 
 * @author mrluo735
 *
 */
public class DoubleUtil {
	/**
	 * 重载+1 字符串转成double
	 * 
	 * @param value
	 * @return
	 * @throws NumberFormatException
	 */
	public static double toDouble(String value) throws NumberFormatException {
		return Double.parseDouble(value);
	}

	/**
	 * 重载+2 字符串转成double
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static double toDouble(String value, double defaultValue) {
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException ex) {
			return defaultValue;
		}
	}

	/**
	 * 重载+3 字符串转成Double
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static Double toDouble(String value, Double defaultValue) {
		try {
			return Double.valueOf(value);
		} catch (NumberFormatException ex) {
			return defaultValue;
		}
	}

	/**
	 * 字符串转成Double
	 * 
	 * @param value
	 * @return
	 * @throws NumberFormatException
	 */
	public static Double toDoubleOrNull(String value) {
		try {
			return Double.valueOf(value);
		} catch (NumberFormatException ex) {
			return null;
		}
	}

	/**
	 * 重载+1 转换成double
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static double tryParse(String value, double defaultValue) {
		if (StringUtil.isNullOrWhiteSpace(value))
			return defaultValue;
		for (char ch : value.toCharArray()) {
			if (!Character.isDigit(ch))
				return defaultValue;
		}
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException ex) {
			return defaultValue;
		}
	}

	/**
	 * 重载+2 转换成Double
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static Double tryParse(String value, Double defaultValue) {
		if (StringUtil.isNullOrWhiteSpace(value))
			return defaultValue;
		for (char ch : value.toCharArray()) {
			if (!Character.isDigit(ch))
				return defaultValue;
		}
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException ex) {
			return defaultValue;
		}
	}

	/**
	 * 转换成Double
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static Double tryParseOrNull(String value) {
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException ex) {
			return null;
		}
	}
}
