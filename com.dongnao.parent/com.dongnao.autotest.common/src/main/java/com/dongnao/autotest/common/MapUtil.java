/**
 * @title MapUtil.java
 * @description TODO
 * @package lm.com.framework.map
 * @author mrluo735
 * @since JDK1.7
 * @date 2016年12月5日下午6:45:07
 * @version v1.0
 */
package com.dongnao.autotest.common;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import com.dongnao.autotest.common.EnumUtil;
import com.dongnao.autotest.common.ObjectUtil;
import com.dongnao.autotest.common.enumerate.IBaseEnumInt;

/**
 * map工具类
 * 
 * @author easy
 *
 */
public class MapUtil {
	/**
	 * 判断是否为空.
	 */
	public static boolean isEmpty(final Map<?, ?> map) {
		return (map == null) || map.isEmpty();
	}

	/**
	 * 判断是否不为空.
	 */
	public static boolean isNotEmpty(final Map<?, ?> map) {
		return (map != null) && !map.isEmpty();
	}

	/**
	 * 转换Map的键类型
	 * 
	 * @param map
	 * @return
	 */
	public static <K1, K2, V> Map<K1, V> key2Key(final Map<K2, V> map, Class<K1> clazz) {
		Map<K1, V> mapResult = new HashMap<>();
		if (isEmpty(map))
			return mapResult;

		for (Entry<K2, V> item : map.entrySet()) {
			mapResult.put(ObjectUtil.toDataType(clazz, item.getKey()), item.getValue());
		}
		return mapResult;
	}

	/**
	 * 实例化一个Map
	 */
	public static <K, V> Map<K, V> newMap() {
		return new HashMap<K, V>();
	}
	
	/**
	 * 复制Map
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> clone(final HashMap<K, V> map) {
		Map<K, V> mapResult = new HashMap<>();
		if (isEmpty(map))
			return mapResult;
		
		return (Map<K, V>) map.clone();
	}

	/**
	 * 获取布尔的值
	 * 
	 * @param map
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static <K> boolean getBoolean(final Map<? super K, ?> map, final K key, boolean defaultValue) {
		return ObjectUtil.toDataType(Boolean.class, map.get(key), defaultValue);
	}

	/**
	 * 获取整型的值
	 * 
	 * @param map
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static <K> byte getByte(final Map<? super K, ?> map, final K key, byte defaultValue) {
		return ObjectUtil.toDataType(Byte.class, map.get(key), defaultValue);
	}

	/**
	 * 获取整型的值
	 * 
	 * @param map
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static <K> short getShort(final Map<? super K, ?> map, final K key, short defaultValue) {
		return ObjectUtil.toDataType(Short.class, map.get(key), defaultValue);
	}

	/**
	 * 获取整型的值
	 * 
	 * @param map
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static <K> int getInteger(final Map<? super K, ?> map, final K key, int defaultValue) {
		return ObjectUtil.toDataType(Integer.class, map.get(key), defaultValue);
	}

	/**
	 * 获取长整型的值
	 * 
	 * @param map
	 * @param key
	 * @return
	 */
	public static <K> Long getLong(final Map<? super K, ?> map, final K key) {
		return ObjectUtil.toDataType(Long.class, map.get(key), null);
	}

	/**
	 * 获取长整型的值
	 * 
	 * @param map
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static <K> long getLong(final Map<? super K, ?> map, final K key, long defaultValue) {
		return ObjectUtil.toDataType(Long.class, map.get(key), defaultValue);
	}

	/**
	 * 获取单精度的值
	 * 
	 * @param map
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static <K> float getFloat(final Map<? super K, ?> map, final K key, float defaultValue) {
		return ObjectUtil.toDataType(Float.class, map.get(key), defaultValue);
	}

	/**
	 * 获取双精度的值
	 * 
	 * @param map
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static <K> double getDouble(final Map<? super K, ?> map, final K key, double defaultValue) {
		return ObjectUtil.toDataType(Double.class, map.get(key), defaultValue);
	}

	/**
	 * 获取BigInteger的值
	 * 
	 * @param map
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static <K> BigInteger getBigInteger(final Map<? super K, ?> map, final K key, BigInteger defaultValue) {
		return ObjectUtil.toDataType(BigInteger.class, map.get(key), defaultValue);
	}

	/**
	 * 获取BigDecimal的值
	 * 
	 * @param map
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static <K> BigDecimal getBigDecimal(final Map<? super K, ?> map, final K key, BigDecimal defaultValue) {
		return ObjectUtil.toDataType(BigDecimal.class, map.get(key), defaultValue);
	}

	/**
	 * 获取时间类型的值
	 * 
	 * @param map
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static <K> Time getTime(final Map<? super K, ?> map, final K key, Time defaultValue) {
		return ObjectUtil.toDataType(Time.class, map.get(key), defaultValue);
	}

	/**
	 * 获取日期类型的值
	 * 
	 * @param map
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static <K> Date getDate(final Map<? super K, ?> map, final K key, Date defaultValue) {
		return ObjectUtil.toDataType(Date.class, map.get(key), defaultValue);
	}

	/**
	 * 获取Timestamp类型的值
	 * 
	 * @param map
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static <K> Timestamp getDate(final Map<? super K, ?> map, final K key, Timestamp defaultValue) {
		return ObjectUtil.toDataType(Timestamp.class, map.get(key), defaultValue);
	}

	/**
	 * 获取日期类型的值
	 * 
	 * @param map
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static <K> java.util.Date getDate(final Map<? super K, ?> map, final K key, java.util.Date defaultValue) {
		return ObjectUtil.toDataType(java.util.Date.class, map.get(key), defaultValue);
	}

	/**
	 * 获取UUID类型的值
	 * 
	 * @param map
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static <K> UUID getUUID(final Map<? super K, ?> map, final K key, UUID defaultValue) {
		return ObjectUtil.toDataType(UUID.class, map.get(key), defaultValue);
	}

	/**
	 * 获取枚举类型的值
	 * 
	 * @param map
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static <K, E extends Enum<?>> E getEnum(final Map<? super K, ?> map, final K key, Class<E> clazz,
			E defaultValue) {
		return ObjectUtil.toDataType(clazz, map.get(key), defaultValue);
	}

	/**
	 * 获取枚举类型的值
	 * 
	 * @param map
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static <K, E extends IBaseEnumInt<?>> E getEnum(final Map<? super K, ?> map, final K key, Class<E> clazz,
			Integer defaultValue) {
		return EnumUtil.toEnum(clazz, getInteger(map, key, defaultValue));
	}

	/**
	 * 获取字符型的值
	 * <p>
	 * 默认值: ""
	 * </p>
	 * 
	 * @param map
	 * @param key
	 * @return
	 */
	public static <K> String getString(final Map<? super K, ?> map, final K key) {
		return ObjectUtil.toDataType(String.class, map.get(key), "");
	}

	/**
	 * 获取字符型的值
	 * 
	 * @param map
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static <K> String getString(final Map<? super K, ?> map, final K key, String defaultValue) {
		return ObjectUtil.toDataType(String.class, map.get(key), defaultValue);
	}

	/**
	 * 获取集合
	 * 
	 * @param map
	 * @param key
	 * @param clazz
	 * @return
	 */
	public static <K, T> List<T> getList(final Map<? super K, ?> map, final K key, Class<T> clazz) {
		final Object listObj = map.get(key);
		if (listObj != null && (listObj instanceof Collection<?>)) {
			Collection<?> items = (Collection<?>) listObj;
			List<T> list = new ArrayList<>();
			for (Object item : items) {
				list.add(ObjectUtil.toDataType(clazz, item, null));
			}
			return list;
		}
		return null;
	}

	/**
	 * Converts a map to a JavaBean.
	 * 
	 * @param type
	 *            type to convert
	 * @param map
	 *            map to convert
	 * @return JavaBean converted
	 * @throws Exception
	 */
	public static final <T> T toBean(Class<T> type, Map<Object, ? extends Object> map) throws Exception {
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		T obj = type.newInstance();
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			Class<?> clazz = descriptor.getPropertyType();
			if (map.containsKey(propertyName)) {
				Object value = map.get(propertyName);
				Object[] args = new Object[1];
				args[0] = ObjectUtil.toDataType(clazz, value, null);
				descriptor.getWriteMethod().invoke(obj, args);
			}
		}
		return obj;
	}

	/**
	 * Converts a map to a JavaBean.
	 * 
	 * @param type
	 *            type to convert
	 * @param map
	 *            map to convert
	 * @return JavaBean converted
	 * @throws Exception
	 */
	public static final Object toBean2(Class<?> type, Map<String, ? extends Object> map) throws Exception {
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		Object obj = type.newInstance();
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			Class<?> clazz = descriptor.getPropertyType();
			if (map.containsKey(propertyName)) {
				Object value = map.get(propertyName);
				Object[] args = new Object[1];
				args[0] = ObjectUtil.toDataType(clazz, value, null);
				descriptor.getWriteMethod().invoke(obj, args);
			}
		}
		return obj;
	}

	/**
	 * Converts a JavaBean to a map
	 * 
	 * @param bean
	 *            JavaBean to convert
	 * @return map converted
	 * @throws IntrospectionException
	 *             failed to get class fields
	 * @throws IllegalAccessException
	 *             failed to instant JavaBean
	 * @throws InvocationTargetException
	 *             failed to call setters
	 */
	public static final Map<String, Object> toMap(Object bean) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				if (!propertyName.equals("class")) {
					Method readMethod = descriptor.getReadMethod();
					Object result = readMethod.invoke(bean, new Object[0]);
					if (result != null) {
						returnMap.put(propertyName, result);
					} else {
						returnMap.put(propertyName, "");
					}
				}
			}
		} catch (IntrospectionException ex) {

		} catch (IllegalAccessException ex) {

		} catch (InvocationTargetException ex) {

		} catch (Exception ex) {

		}
		return returnMap;
	}
}
