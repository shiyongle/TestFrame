/**
 * @title FloatUtil.java
 * @description TODO
 * @package lm.com.framework
 * @author mrluo735
 * @since JDK1.7
 * @date 2016年12月30日下午12:49:32
 * @version v1.0
 */
package com.dongnao.autotest.common;

/**
 * Float 工具类
 * @author mrluo735
 *
 */
public class FloatUtil {
	/**
	 * 重载+1 字符串转成float
	 * 
	 * @param value
	 * @return
	 * @throws NumberFormatException
	 */
	public static float toFloat(String value) throws NumberFormatException {
		return Float.parseFloat(value);
	}

	/**
	 * 重载+2 字符串转成float
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static float toFloat(String value, float defaultValue) {
		try {
			return Float.parseFloat(value);
		} catch (NumberFormatException ex) {
			return defaultValue;
		}
	}

	/**
	 * 重载+3 字符串转成Float
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static Float toFloat(String value, Float defaultValue) {
		try {
			return Float.valueOf(value);
		} catch (NumberFormatException ex) {
			return defaultValue;
		}
	}

	/**
	 * 字符串转成Float
	 * 
	 * @param value
	 * @return
	 * @throws NumberFormatException
	 */
	public static Float toFloatOrNull(String value) {
		try {
			return Float.valueOf(value);
		} catch (NumberFormatException ex) {
			return null;
		}
	}

	/**
	 * 重载+1 转换成float
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static float tryParse(String value, float defaultValue) {
		if (StringUtil.isNullOrWhiteSpace(value))
			return defaultValue;
		for (char ch : value.toCharArray()) {
			if (!Character.isDigit(ch))
				return defaultValue;
		}
		try {
			return Float.parseFloat(value);
		} catch (NumberFormatException ex) {
			return defaultValue;
		}
	}

	/**
	 * 重载+2 转换成Float
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static Float tryParse(String value, Float defaultValue) {
		if (StringUtil.isNullOrWhiteSpace(value))
			return defaultValue;
		for (char ch : value.toCharArray()) {
			if (!Character.isDigit(ch))
				return defaultValue;
		}
		try {
			return Float.parseFloat(value);
		} catch (NumberFormatException ex) {
			return defaultValue;
		}
	}

	/**
	 * 转换成Float
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static Float tryParseOrNull(String value) {
		try {
			return Float.parseFloat(value);
		} catch (NumberFormatException ex) {
			return null;
		}
	}
}
