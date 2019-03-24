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
 * byte 工具类
 * 
 * @author mrluo735
 *
 */
public class ByteUtil {
	/**
	 * 重载+1 字符串转成byte
	 * 
	 * @param value
	 * @return
	 * @throws NumberFormatException
	 */
	public static byte toByte(String value) {
		try {
			return Byte.parseByte(value);
		} catch (NumberFormatException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 重载+2 字符串转成byte
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static byte toByte(String value, byte defaultValue) {
		try {
			return Byte.parseByte(value);
		} catch (NumberFormatException ex) {
			return defaultValue;
		}
	}

	/**
	 * 重载+3 字符串转成byte
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static Byte toByte(String value, Byte defaultValue) {
		try {
			return Byte.valueOf(value);
		} catch (NumberFormatException ex) {
			return defaultValue;
		}
	}

	/**
	 * 字符串转成Byte
	 * 
	 * @param value
	 * @return
	 * @throws NumberFormatException
	 */
	public static Byte toByteOrNull(String value) {
		try {
			return Byte.valueOf(value);
		} catch (NumberFormatException ex) {
			return null;
		}
	}

	/**
	 * 重载+1 转换成byte
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static byte tryParse(String value, byte defaultValue) {
		if (StringUtil.isNullOrWhiteSpace(value))
			return defaultValue;
		for (char ch : value.toCharArray()) {
			if (!Character.isDigit(ch))
				return defaultValue;
		}
		try {
			return Byte.parseByte(value);
		} catch (NumberFormatException ex) {
			return defaultValue;
		}
	}

	/**
	 * 重载+2 转换成Byte
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static Byte tryParse(String value, Byte defaultValue) {
		if (StringUtil.isNullOrWhiteSpace(value))
			return defaultValue;
		for (char ch : value.toCharArray()) {
			if (!Character.isDigit(ch))
				return defaultValue;
		}
		try {
			return Byte.parseByte(value);
		} catch (NumberFormatException ex) {
			return defaultValue;
		}
	}

	/**
	 * 转换成Byte
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static Byte tryParseOrNull(String value) {
		try {
			return Byte.parseByte(value);
		} catch (NumberFormatException ex) {
			return null;
		}
	}
}
