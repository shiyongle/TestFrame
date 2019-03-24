/**
 * @title ByteUitl.java
 * @description TODO
 * @package lm.com.framework
 * @author mrluo735
 * @since JDK1.7
 * @date 2016年12月30日上午11:44:31
 * @version v1.0
 */
package com.dongnao.autotest.common;

/**
 * short 工具类
 * 
 * @author mrluo735
 *
 */
public class ShortUtil {
	/**
	 * 重载+1 字符串转成short
	 * 
	 * @param value
	 * @return
	 * @throws NumberFormatException
	 */
	public static short toShort(String value) {
		try {
			return Short.parseShort(value);
		} catch (NumberFormatException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 重载+2 字符串转成short
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static short toShort(String value, short defaultValue) {
		try {
			return Short.parseShort(value);
		} catch (NumberFormatException ex) {
			return defaultValue;
		}
	}

	/**
	 * 重载+3 字符串转成Short
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static Short toShort(String value, Short defaultValue) {
		try {
			return Short.valueOf(value);
		} catch (NumberFormatException ex) {
			return defaultValue;
		}
	}

	/**
	 * 字符串转成Short
	 * 
	 * @param value
	 * @return
	 * @throws NumberFormatException
	 */
	public static Short toShortOrNull(String value) {
		try {
			return Short.valueOf(value);
		} catch (NumberFormatException ex) {
			return null;
		}
	}

	/**
	 * 重载+1 转换成short
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static short tryParse(String value, short defaultValue) {
		if (StringUtil.isNullOrWhiteSpace(value))
			return defaultValue;
		for (char ch : value.toCharArray()) {
			if (!Character.isDigit(ch))
				return defaultValue;
		}
		try {
			return Short.parseShort(value);
		} catch (NumberFormatException ex) {
			return defaultValue;
		}
	}

	/**
	 * 重载+2 转换成Short
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static Short tryParse(String value, Short defaultValue) {
		if (StringUtil.isNullOrWhiteSpace(value))
			return defaultValue;
		for (char ch : value.toCharArray()) {
			if (!Character.isDigit(ch))
				return defaultValue;
		}
		try {
			return Short.parseShort(value);
		} catch (NumberFormatException ex) {
			return defaultValue;
		}
	}

	/**
	 * 转换成Short
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static Short tryParseOrNull(String value) {
		try {
			return Short.parseShort(value);
		} catch (NumberFormatException ex) {
			return null;
		}
	}
}
