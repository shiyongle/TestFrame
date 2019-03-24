package com.dongnao.autotest.common;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import com.dongnao.autotest.common.enumerate.IBaseEnumInt;
import com.dongnao.autotest.common.tuple.Tuple3;

/**
 * 枚举帮助类
 * 
 * @author Administrator
 *
 */
public class EnumUtil {
	/**
	 * 根据枚举名称转换成枚举
	 * 
	 * @param enumType
	 * @param name
	 * @return
	 */
	public static <T extends Enum<T>> T toEnum(Class<T> enumType, String name) {
		return Enum.valueOf(enumType, name);
	}

	/**
	 * 根据枚举ordinal转换成枚举
	 * 
	 * @param enumType
	 * @param name
	 * @return
	 */
	public static <T extends Enum<T>> T toEnum(Class<T> enumType, int ordinal) {
		T[] enums = enumType.getEnumConstants();
		for (T t : enums) {
			if (ordinal == t.ordinal()) {
				return t;
			}
		}
		return enums[0];
	}

	/**
	 * 根据枚举值转换成枚举
	 * 
	 * @param enumType
	 * @param name
	 * @return
	 */
	public static <T extends IBaseEnumInt<?>> T toEnum(Class<T> enumType, Integer value) {
		T[] enums = enumType.getEnumConstants();
		for (T t : enums) {
			if (value.equals(t.getValue())) {
				return t;
			}
		}
		return enums[0];
	}

	/**
	 * 根据枚举ordinal获取名称,ordinal信息
	 * 
	 * @param enumType
	 * @param ordinal
	 * @return
	 */
	public static <T extends Enum<T>> List<SimpleEntry<String, Integer>> getNameOrdinal(Class<T> enumType,
			int ordinal) {
		List<SimpleEntry<String, Integer>> list = new ArrayList<>();
		T[] enums = enumType.getEnumConstants();
		for (T item : enums) {
			String name = item.name();
			if (ordinal == item.ordinal()) {
				SimpleEntry<String, Integer> entry = new SimpleEntry<String, Integer>(name, ordinal);
				list.add(entry);
			}
		}
		return list;
	}

	/**
	 * 根据枚举获取名称,ordinal信息
	 * 
	 * @param enumType
	 * @param ordinal
	 * @return
	 */
	public static <T extends Enum<T>> List<SimpleEntry<String, Integer>> getNameOrdinal(Class<T> enumType) {
		List<SimpleEntry<String, Integer>> list = new ArrayList<>();
		T[] enums = enumType.getEnumConstants();
		for (T item : enums) {
			String name = item.name();
			int ordinal = item.ordinal();
			SimpleEntry<String, Integer> entry = new SimpleEntry<String, Integer>(name, ordinal);
			list.add(entry);
		}
		return list;
	}

	/**
	 * 获取枚举描述
	 * 
	 * @param enumType
	 * @param name
	 * @return
	 */
	public static <T extends Enum<T>> String getDescription(Class<T> enumType, String name) {
		T[] enums = enumType.getEnumConstants();
		for (T item : enums) {
			String enumName = item.name();
			if (null == enumName || enumName != name)
				continue;

			Object descriptionObj = ReflectUtil.getValueByFieldName(item, "description");
			String description = null == descriptionObj ? "" : descriptionObj.toString();
			return description;
		}
		return null;
	}

	/**
	 * 获取枚举描述
	 * 
	 * @param enumType
	 * @param name
	 * @return
	 */
	public static <T extends Enum<T>> String getDescription(Class<T> enumType, Integer value) {
		T[] enums = enumType.getEnumConstants();
		for (T item : enums) {
			Integer valueObj = (Integer) ReflectUtil.getValueByFieldName(item, "value");
			if (null == valueObj || valueObj != value)
				continue;

			Object descriptionObj = ReflectUtil.getValueByFieldName(item, "description");
			String description = null == descriptionObj ? "" : descriptionObj.toString();
			return description;
		}
		return null;
	}

	/**
	 * 根据值获取枚举名称，值，描述信息
	 * 
	 * @param enumType
	 * @return
	 */
	public static <T extends Enum<T>> Tuple3<String, Integer, String> getNameValueDescription(Class<T> enumType,
			Integer value) {
		T[] enums = enumType.getEnumConstants();
		for (T item : enums) {
			String name = item.name();
			Integer valueObj = (Integer) ReflectUtil.getValueByFieldName(item, "value");
			if (null == valueObj || valueObj != value)
				continue;

			Object descriptionObj = ReflectUtil.getValueByFieldName(item, "description");
			String description = null == descriptionObj ? "" : descriptionObj.toString();
			return new Tuple3<String, Integer, String>(name, value, description);
		}
		return null;
	}

	/**
	 * 根据枚举获取枚举名称，值，描述信息
	 * 
	 * @param enumType
	 * @return
	 */
	public static <T extends Enum<T>> Tuple3<String, Integer, String> getNameValueDescription(Class<T> enumType,
			T value) {
		T[] enums = enumType.getEnumConstants();
		for (T item : enums) {
			if (!item.equals(value))
				continue;

			String name = item.name();
			Integer valueObj = (Integer) ReflectUtil.getValueByFieldName(item, "value");
			Object descriptionObj = ReflectUtil.getValueByFieldName(item, "description");
			String description = null == descriptionObj ? "" : descriptionObj.toString();
			return new Tuple3<String, Integer, String>(name, valueObj, description);
		}
		return null;
	}

	/**
	 * 获取枚举名称，值，描述信息
	 * 
	 * @param enumType
	 * @return
	 */
	public static <T extends Enum<T>> List<Tuple3<String, Integer, String>> getNameValueDescription(Class<T> enumType) {
		List<Tuple3<String, Integer, String>> list = new ArrayList<>();
		T[] enums = enumType.getEnumConstants();
		for (T item : enums) {
			String name = item.name();
			Integer valueObj = (Integer) ReflectUtil.getValueByFieldName(item, "value");
			Object descriptionObj = ReflectUtil.getValueByFieldName(item, "description");
			Integer value = null == valueObj ? 0 : valueObj;
			String description = null == descriptionObj ? "" : descriptionObj.toString();
			list.add(new Tuple3<String, Integer, String>(name, value, description));
		}
		return list;
	}
}
