package com.dongnao.autotest.common;

/**
 * Boolean 帮助类
 * 
 * @author Administrator
 *
 */
public final class BooleanUtil {
	/**
	 * 重载+1 字符串转换成boolean
	 * 
	 * @param value
	 * @return
	 */
	public static boolean toBoolean(String value) {
		return toBoolean(value, false);
	}
	
	/**
	 * 重载+2 字符串转换成boolean
	 * 
	 * @param value
	 * @return
	 */
	public static boolean toBoolean(String value, boolean defaultValue) {
		if (StringUtil.isNullOrWhiteSpace(value))
			return defaultValue;
		if ("0".equals(value) || "false".equals(value.toLowerCase()) || "off".equals(value.toLowerCase())
				|| "no".equals(value.toLowerCase()))
			return false;
		if ("1".equals(value) || "true".equals(value.toLowerCase()) || "on".equals(value.toLowerCase())
				|| "yes".equals(value.toLowerCase()))
			return true;
		return Boolean.parseBoolean(value);
	}

	/**
	 * 字符串转换成boolean
	 * 
	 * @param value
	 * @return
	 */
	public static boolean toBoolean(Integer value) {
		if (null == value)
			return false;
		if (value <= 0)
			return false;
		return true;
	}
}
