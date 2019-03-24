/**
 * @title TimestampUtil.java
 * @description TODO
 * @package lm.com.framework
 * @author mrluo735
 * @since JDK1.7
 * @date 2016年11月25日下午2:32:10
 * @version v1.0
 */
package com.dongnao.autotest.common;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author mrluo735
 *
 */
public class TimestampUtil {
	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static Timestamp getNow() {
		return Timestamp.valueOf(DateTimeUtil.getNow("yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 转成Timestamp
	 * 
	 * @param value
	 *            格式必须为yyyy-MM-dd HH:mm:ss[.fffffffff]
	 * @return
	 */
	public static Timestamp toTimestamp(String value) {
		if (StringUtil.isNullOrWhiteSpace(value))
			return null;
		try {
			return Timestamp.valueOf(value);
		} catch (IllegalArgumentException ex) {
			return null;
		}
	}

	/**
	 * 转成Timestamp
	 * 
	 * @param value
	 *            格式必须为yyyy-MM-dd HH:mm:ss[.fffffffff]
	 * @param defaultValue
	 * @return
	 */
	public static Timestamp toTimestamp(String value, Timestamp defaultValue) {
		if (StringUtil.isNullOrWhiteSpace(value))
			return defaultValue;
		try {
			return Timestamp.valueOf(value);
		} catch (IllegalArgumentException ex) {
			return defaultValue;
		}
	}

	/**
	 * 转成Timestamp
	 * 
	 * @param date
	 * @return
	 */
	public static Timestamp toTimestamp(Date date) {
		return Timestamp.valueOf(DateTimeUtil.toString(date, DateTimeUtil.DEFAULT_PATTERN));
	}

	/**
	 * 转成长整形
	 * 
	 * @param timestamp
	 * @return
	 */
	public static long toLong(Timestamp timestamp) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(timestamp);
		return cal.getTimeInMillis();
	}

	/**
	 * 时间转成String
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String toString(Timestamp timestamp, String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(timestamp);
	}
}
