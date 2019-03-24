/**
 * @title BigIntegerUtil.java
 * @description TODO
 * @package lm.com.framework
 * @author mrluo735
 * @since JDK1.7
 * @date 2016年12月30日下午12:52:42
 * @version v1.0
 */
package com.dongnao.autotest.common;

import java.math.BigInteger;

/**
 * BigInteger工具类
 * 
 * @author mrluo735
 *
 */
public class BigIntegerUtil {
	/**
	 * 重载+1 字符串转成BigInteger
	 * 
	 * @param value
	 * @return
	 */
	public static BigInteger toBigInteger(String value) throws NumberFormatException {
		long longValue = LongUtil.toLong(value);
		return BigInteger.valueOf(longValue);
	}

	/**
	 * 重载+2 字符串转成BigInteger
	 * 
	 * @param value
	 * @return
	 */
	public static BigInteger toBigInteger(String value, BigInteger defaultValue) {
		try {
			long longValue = LongUtil.toLong(value);
			return BigInteger.valueOf(longValue);
		} catch (NumberFormatException ex) {
			return defaultValue;
		}
	}

	/**
	 * 字符串转成BigInteger
	 * 
	 * @param value
	 * @return
	 */
	public static BigInteger toBigIntegerOrNull(String value) throws NumberFormatException {
		try {
			long longValue = LongUtil.toLong(value);
			return BigInteger.valueOf(longValue);
		} catch (NumberFormatException ex) {
			return null;
		}
	}

	/**
	 * 重载+3 字符串转成BigInteger
	 * 
	 * @param value
	 * @return
	 */
	public static BigInteger toBigInteger(long value) {
		return BigInteger.valueOf(value);
	}

	/**
	 * 重载+4 字符串转成BigInteger
	 * 
	 * @param value
	 * @throws NullPointerException
	 * @return
	 */
	public static BigInteger toBigInteger(Long value) throws NullPointerException {
		return BigInteger.valueOf(value);
	}

	/**
	 * 重载+5 字符串转成BigInteger
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static BigInteger toBigInteger(Long value, BigInteger defaultValue) {
		if (null == value)
			return defaultValue;
		return BigInteger.valueOf(value);
	}
}
