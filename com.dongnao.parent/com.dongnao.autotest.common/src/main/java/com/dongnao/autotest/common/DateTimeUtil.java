package com.dongnao.autotest.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期时间帮助类
 * 
 * @author Administrator
 *
 */
public final class DateTimeUtil {
	/**
	 * 默认时间格式
	 * <p>
	 * yyyy-MM-dd HH:mm:ss
	 * </p>
	 */
	public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static Date getNow() {
		return new Date();
	}

	/**
	 * 获取当前时间
	 * 
	 * @param pattern
	 *            时间格式：例：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getNow(String pattern) {
		// 设置日期格式
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new Date());
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static long getNowAsLong() {
		try {
			String now = getNow(DEFAULT_PATTERN);
			return toLong(now, DEFAULT_PATTERN);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取当前时间
	 * 
	 * @param defaultValue
	 * @return
	 */
	public static long getNowAsLong(long defaultValue) {
		try {
			String now = getNow(DEFAULT_PATTERN);
			return toLong(now, DEFAULT_PATTERN);
		} catch (ParseException e) {
			return defaultValue;
		}
	}

	/**
	 * 转成日期
	 * 
	 * @param source
	 * @return
	 */
	public static Date toDate(String source) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_PATTERN);
			return sdf.parse(source);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 转成日期
	 * 
	 * @param source
	 * @return
	 */
	public static Date toDate(String source, Date defaultValue) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_PATTERN);
			return sdf.parse(source);
		} catch (ParseException e) {
			return defaultValue;
		}
	}

	/**
	 * 转成日期
	 * 
	 * @param source
	 * @param pattern
	 * @return
	 */
	public static Date toDate(String source, String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.parse(source);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 转成日期
	 * 
	 * @param source
	 * @param pattern
	 * @return
	 */
	public static Date toDate(String source, String pattern, Date defaultValue) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.parse(source);
		} catch (ParseException e) {
			return defaultValue;
		}
	}

	/**
	 * 时间字符串转成长整型
	 * 
	 * @param dateStr
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static long toLong(String dateStr, String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = sdf.parse(dateStr);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.getTimeInMillis();
	}

	/**
	 * 长整型时间转成String
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String toString(long date, String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}

	/**
	 * 时间转成String
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String toString(Date date, String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}

	/**
	 * 返回一个新的 System.DateTime，它将指定的毫秒数加到此实例的值上。
	 * 
	 * @param date
	 * @param millisecond
	 * @return
	 */
	public static Date addMilliseconds(Date date, int millisecond) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MILLISECOND, millisecond);
		return calendar.getTime();
	}

	/**
	 * 返回一个新的 System.DateTime，它将指定的秒数加到此实例的值上。
	 * 
	 * @param date
	 * @param second
	 * @return
	 */
	public static Date addSeconds(Date date, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, second);
		return calendar.getTime();
	}

	/**
	 * 返回一个新的 System.DateTime，它将指定的分钟数加到此实例的值上。
	 * 
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date addMinutes(Date date, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minute);
		return calendar.getTime();
	}

	/**
	 * 返回一个新的 System.DateTime，它将指定的小时数加到此实例的值上。
	 * 
	 * @param date
	 * @param hour
	 * @return
	 */
	public static Date addHours(Date date, int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, hour);
		return calendar.getTime();
	}

	/**
	 * 返回一个新的 System.DateTime，它将指定的天数加到此实例的值上。
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDays(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}

	/**
	 * 返回一个新的 System.DateTime，它将指定的月数加到此实例的值上。
	 * 
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date addMonths(Date date, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, month);
		return calendar.getTime();
	}

	/**
	 * 返回一个新的 System.DateTime，它将指定的月数加到此实例的值上。
	 * 
	 * @param date
	 * @param year
	 * @return
	 */
	public static Date addYears(Date date, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, year);
		return calendar.getTime();
	}

	/**
	 * 获取此实例所表示的毫秒数
	 * 
	 * @param date
	 * @return
	 */
	public static int getMillisecond(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MILLISECOND);
	}

	/**
	 * 获取此实例所表示的秒数
	 * 
	 * @param date
	 * @return
	 */
	public static int getSecond(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.SECOND);
	}

	/**
	 * 获取此实例所表示的分钟数
	 * 
	 * @param date
	 * @return
	 */
	public static int getMinute(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * 获取此实例所在一天中的小时数
	 * 
	 * @param date
	 * @return
	 */
	public static int getHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取此实例所在月份中的一天
	 * <p>
	 * 表示为一个在1和31之间的值
	 * </p>
	 * 
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取此实例所表示的星期几
	 * <p>
	 * 表示为一个在0和6之间的值
	 * </p>
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * 获取此实例所在年份中的一天
	 * <p>
	 * 表示为一个在1和365之间的值
	 * </p>
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 获取指定年和月的天数
	 * 
	 * @param year
	 * @param month
	 *            1-12
	 * @return
	 * @throws ParseException
	 */
	public static int getDaysInMonth(int year, int month) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		String source = year + "-" + month;
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(format.parse(source));
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取此实例所表示的月份
	 * <p>
	 * 表示为一个在1和12之间的值
	 * </p>
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH);
	}

	/**
	 * 获取此实例所在月份的第一天的日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstInMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = getDay(date);
		return addDays(date, -day + 1);
	}

	/**
	 * 获取此实例所在月份的最后一天的日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastInMonth(Date date) {
		Date first = getFirstInMonth(date);
		return addDays(addMonths(first, 1), -1);
	}

	/**
	 * 获取此实例所表示的年份
	 * 
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 返回两个时间相差的天数
	 * 
	 * @param firstDate
	 * @param secondDate
	 * @return
	 */
	public static int subtractDays(Date firstDate, Date secondDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(firstDate);
		int day1 = calendar.get(Calendar.DAY_OF_YEAR);

		calendar.setTime(secondDate);
		int day2 = calendar.get(Calendar.DAY_OF_YEAR);

		return day2 - day1;
	}

	/**
	 * 获取mysql最小时间
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Date getMinDatetime() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_PATTERN);
		return sdf.parse("0001-01-01 00:00:00");
	}

	/**
	 * 获取mysql最大时间
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Date getMaxDatetime() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_PATTERN);
		return sdf.parse("9999-12-31 23:59:59");
	}

	/**
	 * 转成用户友好的字符串
	 * <p>
	 * 与当前时间相比的时间差，如刚刚，5分钟前，今天XXX，昨天XXX<br />
	 * from AndroidUtilCode
	 * <p>
	 */
	public static String toStringFriendly(Date date) {
		return toStringFriendly(date.getTime());
	}

	/**
	 * 转成用户友好的字符串
	 * <p>
	 * 与当前时间相比的时间差，如刚刚，5分钟前，今天XXX，昨天XXX<br />
	 * from AndroidUtilCode
	 * <p>
	 * 
	 * @param timeStampMillis
	 */
	public static String toStringFriendly(long timeStampMillis) {
		final long MILLIS_PER_SECOND = 1000;
		final long MILLIS_PER_MINUTE = 60 * MILLIS_PER_SECOND;
		final long MILLIS_PER_HOUR = 60 * MILLIS_PER_MINUTE;
		final long MILLIS_PER_DAY = 24 * MILLIS_PER_HOUR;

		long now = System.currentTimeMillis();
		long timespan = now - timeStampMillis;
		if (timespan < 0) {
			// 'c' 日期和时间，被格式化为 "%ta %tb %td %tT %tZ %tY"，例如 "Sun Jul 20 16:17:00
			// EDT 1969"。
			return String.format("%tc", timeStampMillis);
		}
		if (timespan < MILLIS_PER_SECOND) {
			return "刚刚";
		} else if (timespan < MILLIS_PER_MINUTE) {
			return String.format("%d秒前", timespan / MILLIS_PER_SECOND);
		} else if (timespan < MILLIS_PER_HOUR) {
			return String.format("%d分钟前", timespan / MILLIS_PER_MINUTE);
		}
		// 获取当天00:00:00
		long wee = toDate(getNow("yyyy-MM-dd 00:00:00")).getTime();
		if (timeStampMillis >= wee) {
			// 'R' 24 小时制的时间，被格式化为 "%tH:%tM"
			return String.format("今天%tR", timeStampMillis);
		} else if (timeStampMillis >= wee - MILLIS_PER_DAY) {
			return String.format("昨天%tR", timeStampMillis);
		} else {
			// 'F' ISO 8601 格式的完整日期，被格式化为 "%tY-%tm-%td"。
			return String.format("%tF", timeStampMillis);
		}
	}
}
