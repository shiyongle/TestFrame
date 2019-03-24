/**
 * @title TimeUtil.java
 * @description TODO
 * @package lm.com.framework
 * @author mrluo735
 * @since JDK1.7
 * @date 2016年11月25日下午2:23:52
 * @version v1.0
 */
package com.dongnao.autotest.common;

import java.sql.Time;
import java.text.SimpleDateFormat;

/**
 * Time 工具类
 * 
 * @author mrluo735
 *
 */
public class TimeUtil {
	/**
	 * 最小值: 00:00:00
	 */
	public static final Time MIN = Time.valueOf("00:00:00");

	/**
	 * 默认格式: HH:mm:ss
	 */
	public static final String DEFAULT_PATTERN = "HH:mm:ss";

	/**
	 * 转成Time类型
	 * 
	 * @param value
	 *            格式: HH:mm:ss
	 * @return
	 */
	public static Time toTime(String value) {
		if (StringUtil.isNullOrWhiteSpace(value))
			return null;
		return Time.valueOf(value);
	}

	/**
	 * 转成Time类型
	 * 
	 * @param value
	 *            格式: HH:mm:ss
	 * @param defaultValue
	 * @return
	 */
	public static Time toTime(String value, Time defaultValue) {
		if (StringUtil.isNullOrWhiteSpace(value))
			return defaultValue;
		try {
			return Time.valueOf(value);
		} catch (Exception ex) {
			return defaultValue;
		}
	}

	/**
	 * 时间转成String
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String toString(Time time, String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(time);
	}
}
