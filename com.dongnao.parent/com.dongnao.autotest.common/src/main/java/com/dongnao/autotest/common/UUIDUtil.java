/**
 * @title UUIDUtil.java
 * @description TODO
 * @package lm.com.framework
 * @author mrluo735
 * @since JDK1.7
 * @date 2016年11月4日下午4:55:52
 * @version v1.0
 */
package com.dongnao.autotest.common;

import java.util.UUID;

/**
 * UUID工具类
 * 
 * @author mrluo735
 *
 */
public class UUIDUtil {
	/**
	 * 随机生成一个新的UUID
	 * 
	 * @return
	 */
	public static UUID newUUID() {
		return UUID.randomUUID();
	}

	/**
	 * 随机生成一个新的32位UUID
	 * 
	 * @return
	 */
	public static String newUUID32() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 重载+1 字符串转UUID
	 * 
	 * @param value
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static UUID toUUID(String value) throws IllegalArgumentException {
		return UUID.fromString(value);
	}

	/**
	 * 重载+2 字符串转UUID
	 * 
	 * @param value
	 * @return
	 * @throws InternalError
	 */
	public static UUID toUUID(byte[] value) throws InternalError {
		return UUID.nameUUIDFromBytes(value);
	}

	/**
	 * 重载+3 字符串转UUID
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static UUID toUUID(String value, UUID defaultValue) {
		try {
			return UUID.fromString(value);
		} catch (IllegalArgumentException ex) {
			return defaultValue;
		}
	}
}
