/**
 * @title ObjectUtil.java
 * @description TODO
 * @package lm.com.framework
 * @author mrluo735
 * @since JDK1.7
 * @date 2016年12月29日上午10:57:05
 * @version v1.0
 */
package com.dongnao.autotest.common;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.UUID;

import com.dongnao.autotest.common.enumerate.IBaseEnumInt;

/**
 * Object 工具类
 * 
 * @author mrluo735
 *
 */
public class ObjectUtil {
	/**
	 * isNull
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNull(Object value) {
		if (null == value)
			return true;
		return false;
	}

	/**
	 * isNullOrWhiteSpace
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNullOrWhiteSpace(Object value) {
		if (null == value || StringUtil.isNullOrWhiteSpace(value.toString()))
			return true;
		return false;
	}

	/**
	 * 转成数据类型
	 * 
	 * @param clazz
	 * @param object
	 * @param defaultValue
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T toDataType(Class<T> clazz, Object object) {
		Object value = null == object ? "" : object;
		if (clazz.equals(boolean.class) || clazz.equals(Boolean.class))
			return (T) ((Boolean) BooleanUtil.toBoolean(value.toString()));
		if (clazz.equals(Time.class))
			return (T) TimeUtil.toTime(value.toString(), null);
		if (clazz.equals(Date.class))
			return (T) DateTimeUtil.toDate(value.toString());
		if (clazz.equals(Timestamp.class))
			return (T) TimestampUtil.toTimestamp(value.toString());
		if (clazz.equals(java.util.Date.class))
			return (T) DateTimeUtil.toDate(value.toString());
		if (clazz.equals(byte.class) || clazz.equals(Byte.class))
			return (T) ByteUtil.toByteOrNull(value.toString());
		if (clazz.equals(short.class) || clazz.equals(Short.class))
			return (T) ShortUtil.toShortOrNull(value.toString());
		if (clazz.equals(int.class) || clazz.equals(Integer.class))
			return (T) IntegerUtil.toIntegerOrNull(value.toString());
		if (clazz.equals(long.class) || clazz.equals(Long.class))
			return (T) LongUtil.toLongOrNull(value.toString());
		if (clazz.equals(float.class) || clazz.equals(Float.class))
			return (T) FloatUtil.toFloatOrNull(value.toString());
		if (clazz.equals(double.class) || clazz.equals(Double.class))
			return (T) DoubleUtil.toDoubleOrNull(value.toString());
		if (clazz.equals(BigInteger.class))
			return (T) BigIntegerUtil.toBigInteger(value.toString());
		if (clazz.equals(BigDecimal.class))
			return (T) BigDecimalUtil.toBigDecimal(value.toString());
		if (clazz.equals(UUID.class))
			return (T) UUIDUtil.toUUID(value.toString());
		if (clazz.equals(Enum.class) || clazz.isEnum()) {
			Class cls = clazz;
			Class<?>[] interfaces = clazz.getInterfaces();
			for (Class<?> item : interfaces) {
				if (item.equals(IBaseEnumInt.class)) {
					return (T) EnumUtil.toEnum(cls, (Integer) object);
				}
			}
			return (T) Enum.valueOf(cls, value.toString());
		}
		// TODO 其他类型再补充
		return (T) object;
	}

	/**
	 * 转成数据类型
	 * 
	 * @param clazz
	 * @param object
	 * @param defaultValue
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T toDataType(Class<T> clazz, Object object, T defaultValue) {
		Object value = null == object ? "" : object;
		if (clazz.equals(boolean.class) || clazz.equals(Boolean.class))
			return (T) ((Boolean) BooleanUtil.toBoolean(value.toString(), (Boolean) defaultValue));
		if (clazz.equals(Time.class))
			return (T) TimeUtil.toTime(value.toString(), (Time) defaultValue);
		if (clazz.equals(Date.class))
			return (T) DateTimeUtil.toDate(value.toString(), (Date) defaultValue);
		if (clazz.equals(Timestamp.class))
			return (T) TimestampUtil.toTimestamp(value.toString(), (Timestamp) defaultValue);
		if (clazz.equals(java.util.Date.class))
			return (T) DateTimeUtil.toDate(value.toString(), (java.util.Date) defaultValue);
		if (clazz.equals(byte.class) || clazz.equals(Byte.class))
			return (T) ByteUtil.toByte(value.toString(), (Byte) defaultValue);
		if (clazz.equals(short.class) || clazz.equals(Short.class))
			return (T) ShortUtil.toShort(value.toString(), (Short) defaultValue);
		if (clazz.equals(int.class) || clazz.equals(Integer.class))
			return (T) IntegerUtil.toInteger(value.toString(), (Integer) defaultValue);
		if (clazz.equals(long.class) || clazz.equals(Long.class))
			return (T) LongUtil.toLong(value.toString(), (Long) defaultValue);
		if (clazz.equals(float.class) || clazz.equals(Float.class))
			return (T) FloatUtil.toFloat(value.toString(), (Float) defaultValue);
		if (clazz.equals(double.class) || clazz.equals(Double.class))
			return (T) DoubleUtil.toDouble(value.toString(), (Double) defaultValue);
		if (clazz.equals(BigInteger.class))
			return (T) BigIntegerUtil.toBigInteger(value.toString(), (BigInteger) defaultValue);
		if (clazz.equals(BigDecimal.class))
			return (T) BigDecimalUtil.toBigDecimal(value.toString(), (BigDecimal) defaultValue);
		if (clazz.equals(UUID.class))
			return (T) UUIDUtil.toUUID(value.toString(), (UUID) defaultValue);
		if (clazz.equals(Enum.class) || clazz.isEnum()) {
			Class cls = clazz;
			Class<?>[] interfaces = clazz.getInterfaces();
			for (Class<?> item : interfaces) {
				if (item.equals(IBaseEnumInt.class)) {
					return (T) EnumUtil.toEnum(cls, (Integer) object);
				}
			}
			return (T) Enum.valueOf(cls, value.toString());
		}
		// TODO 其他类型再补充
		return (T) object;
	}
}
