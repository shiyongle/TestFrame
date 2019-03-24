/**
 * @title ClassUtil.java
 * @description TODO
 * @package lm.com.framework
 * @author mrluo735
 * @since JDK1.7
 * @date 2016年12月21日下午2:16:41
 * @version v1.0
 */
package com.dongnao.autotest.common;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * class工具类
 * 
 * @author mrluo735
 *
 */
public class ClassUtil {
	/**
	 * 获取同一路径下所有子类或接口实现类
	 * 
	 * @param clazz
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static List<Class<?>> getAllAssignedClass(Class<?> clazz) throws IOException, ClassNotFoundException {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		for (Class<?> c : getClasses(clazz)) {
			if (clazz.isAssignableFrom(c) && !clazz.equals(c)) {
				classes.add(c);
			}
		}
		return classes;
	}

	/**
	 * 取得当前类路径下的所有类
	 * 
	 * @param clazz
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static List<Class<?>> getClasses(Class<?> clazz) throws IOException, ClassNotFoundException {
		String pkage = clazz.getPackage().getName();
		String path = pkage.replace('.', '/');
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		URL url = classloader.getResource(path);
		return getClasses(new File(url.getFile()), pkage);
	}

	/**
	 * 迭代查找类
	 * 
	 * @param file
	 * @param pkage
	 * @return
	 * @throws ClassNotFoundException
	 */
	private static List<Class<?>> getClasses(File file, String pkage) throws ClassNotFoundException {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		if (!file.exists()) {
			return classes;
		}
		for (File f : file.listFiles()) {
			if (f.isDirectory()) {
				classes.addAll(getClasses(f, pkage + "." + f.getName()));
			}
			String name = f.getName();
			if (name.endsWith(".class")) {
				classes.add(Class.forName(pkage + "." + name.substring(0, name.length() - 6)));
			}
		}
		return classes;
	}

	/**
	 * 将对象数组转换为具体类型数组
	 *
	 * <p>
	 * 如果其中任何一个对象为空，则将插入空元素.
	 * </p>
	 *
	 * @param array
	 *            an {@code Object} array
	 * @return a {@code Class} array, {@code null} if null array input
	 * @since 2.4
	 */
	public static Class<?>[] toClass(final Object... array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return new Class[0];
		}
		final Class<?>[] classes = new Class[array.length];
		for (int i = 0; i < array.length; i++) {
			classes[i] = array[i] == null ? null : array[i].getClass();
		}
		return classes;
	}
}
