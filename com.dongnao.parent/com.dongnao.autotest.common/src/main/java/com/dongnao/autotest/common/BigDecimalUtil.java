/**
 * @title BigDecimalUtil.java
 * @description TODO
 * @package lm.com.framework
 * @author mrluo735
 * @since JDK1.7
 * @date 2016年12月30日下午12:52:42
 * @version v1.0
 */
package com.dongnao.autotest.common;

import java.math.BigDecimal;

/**
 * BigDecimal工具类
 * 
 * @author mrluo735
 *
 */
public class BigDecimalUtil {
	/**
	 * 重载+1 字符串转成BigDecimal
	 * 
	 * @param value
	 * @return
	 */
	public static BigDecimal toBigDecimal(String value) throws NumberFormatException {
		double doubleValue = DoubleUtil.toDouble(value);
		return BigDecimal.valueOf(doubleValue);
	}

	/**
	 * 重载+2 字符串转成BigDecimal
	 * 
	 * @param value
	 * @return
	 */
	public static BigDecimal toBigDecimal(String value, BigDecimal defaultValue) {
		try {
			double doubleValue = DoubleUtil.toDouble(value);
			return BigDecimal.valueOf(doubleValue);
		} catch (NumberFormatException ex) {
			return defaultValue;
		}
	}

	/**
	 * 字符串转成BigDecimal
	 * 
	 * @param value
	 * @return
	 */
	public static BigDecimal toBigDecimalOrNull(String value) throws NumberFormatException {
		try {
			double doubleValue = DoubleUtil.toDouble(value);
			return BigDecimal.valueOf(doubleValue);
		} catch (NumberFormatException ex) {
			return null;
		}
	}

	/**
	 * 重载+3 字符串转成BigDecimal
	 * 
	 * @param value
	 * @return
	 */
	public static BigDecimal toBigDecimal(long value) {
		return BigDecimal.valueOf(value);
	}

	/**
	 * 重载+4 字符串转成BigDecimal
	 * 
	 * @param value
	 * @return
	 */
	public static BigDecimal toBigDecimal(double value) {
		return BigDecimal.valueOf(value);
	}

	/**
	 * 重载+5 字符串转成BigDecimal
	 * 
	 * @param value
	 * @throws NullPointerException
	 * @return
	 */
	public static BigDecimal toBigDecimal(Long value) throws NullPointerException {
		return BigDecimal.valueOf(value);
	}

	/**
	 * 重载+6 字符串转成BigDecimal
	 * 
	 * @param value
	 * @throws NullPointerException
	 * @return
	 */
	public static BigDecimal toBigDecimal(Double value) throws NullPointerException {
		return BigDecimal.valueOf(value);
	}

	/**
	 * 重载+7 字符串转成BigDecimal
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static BigDecimal toBigDecimal(Long value, BigDecimal defaultValue) {
		if (null == value)
			return defaultValue;
		return BigDecimal.valueOf(value);
	}

	/**
	 * 重载+8 字符串转成BigDecimal
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static BigDecimal toBigDecimal(Double value, BigDecimal defaultValue) {
		if (null == value)
			return defaultValue;
		return BigDecimal.valueOf(value);
	}
}
