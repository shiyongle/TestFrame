package com.dongnao.autotest.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 反射工具
 * 
 * @author mrluo735
 */
public final class ReflectUtil {
	/**
	 * 获取最顶层基类
	 * 
	 * @param clazz
	 * @return
	 */
	public static Class<?> getTopSuperClass(Class<?> clazz) {
		if (clazz.isInterface())
			return clazz;
		Class<?> topClass = null;
		for (Class<?> superClass = clazz.getSuperclass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			topClass = superClass;
		}
		return topClass;
	}

	/**
	 * 重载+1 获取实例
	 * 
	 * @param clazz
	 * @param parameterTypes
	 * @param initargs
	 * @return
	 */
	public static <T> T getInstance(Class<T> clazz, Class<?>... parameterTypes) {
		return getInstance(clazz, parameterTypes);
	}

	/**
	 * 重载+2 获取实例
	 * 
	 * @param clazz
	 * @param parameterTypes
	 * @param initargs
	 * @return
	 */
	public static <T> T getInstance(Class<T> clazz, Object... initargs) {
		try {
			Constructor<T> constructor = clazz.getDeclaredConstructor();
			constructor.setAccessible(true);
			return constructor.newInstance(initargs);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 重载+3 获取实例
	 * 
	 * @param clazz
	 * @param parameterTypes
	 * @param initargs
	 * @return
	 */
	public static <T> T getInstance(Class<T> clazz, Class<?>[] parameterTypes, Object... initargs) {
		try {
			Constructor<T> constructor = clazz.getDeclaredConstructor(parameterTypes);
			constructor.setAccessible(true);
			return constructor.newInstance(initargs);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 获取obj对象的字段
	 * <p>
	 * 包括父类的字段
	 * <p>
	 * 
	 * @param obj
	 * @return
	 */
	public static Field[] getDeclaredFields(Object obj) {
		List<Field> list = new ArrayList<>();
		if (obj == null) {
			return list.toArray(new Field[0]);
		}
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				list.addAll(Arrays.asList(superClass.getDeclaredFields()));
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
		return list.toArray(new Field[0]);
	}

	/**
	 * 获取obj对象fieldName的Field
	 * 
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Field getFieldByFieldName(Object obj, String fieldName) {
		if (obj == null || fieldName == null) {
			return null;
		}
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 获取obj对象fieldValue的Field
	 * 
	 * @param obj
	 * @param fieldValue
	 * @return
	 */
	public static Field getFieldByFieldValue(Object obj, Object fieldValue) {
		if (obj == null || fieldValue == null) {
			return null;
		}
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				Field[] fieldArray = superClass.getDeclaredFields();
				for (Field item : fieldArray) {
					if (fieldValue.equals(item.get(obj)))
						return item;
				}
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 获取obj对象fieldName的属性值
	 * 
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Object getValueByFieldName(Object obj, String fieldName) {
		Object value = null;
		try {
			Field field = getFieldByFieldName(obj, fieldName);
			if (field != null) {
				if (field.isAccessible()) {
					value = field.get(obj);
				} else {
					field.setAccessible(true);
					value = field.get(obj);
					field.setAccessible(false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 获取obj对象fieldType的属性值
	 * 
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getValueByFieldType(Object obj, Class<T> fieldType) {
		Object value = null;
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				Field[] fields = superClass.getDeclaredFields();
				for (Field f : fields) {
					if (f.getType() == fieldType) {
						if (f.isAccessible()) {
							value = f.get(obj);
							break;
						} else {
							f.setAccessible(true);
							value = f.get(obj);
							f.setAccessible(false);
							break;
						}
					}
				}
				if (value != null) {
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return (T) value;
	}

	/**
	 * 设置obj对象fieldName的属性值
	 * 
	 * @param obj
	 * @param fieldName
	 * @param value
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static boolean setValueByFieldName(Object obj, String fieldName, Object value) {
		try {
			// java.lang.Class.getDeclaredField()方法用法实例教程 -
			// 方法返回一个Field对象，它反映此Class对象所表示的类或接口的指定已声明字段。
			// 此方法返回这个类中的指定字段的Field对象
			Field field = getFieldByFieldName(obj, fieldName);
			/**
			 * public void setAccessible(boolean flag) throws SecurityException将此对象的 accessible 标志设置为指示的布尔值。值为 true
			 * 则指示反射的对象在使用时应该取消 Java 语言访问检查。值为 false 则指示反射的对象应该实施 Java 语言访问检查。 首先，如果存在安全管理器，则在
			 * ReflectPermission("suppressAccessChecks") 权限下调用 checkPermission 方法。 如果 flag 为
			 * true，并且不能更改此对象的可访问性（例如，如果此元素对象是 Class 类的 Constructor 对象），则会引发 SecurityException。 如果此对象是 java.lang.Class
			 * 类的 Constructor 对象，并且 flag 为 true，则会引发 SecurityException。 参数： flag - accessible 标志的新值 抛出：
			 * SecurityException - 如果请求被拒绝。
			 */
			if (field.isAccessible()) {// 获取此对象的 accessible 标志的值。
				field.set(obj, value);// 将指定对象变量上此 Field 对象表示的字段设置为指定的新值
			} else {
				field.setAccessible(true);
				field.set(obj, value);
				field.setAccessible(false);
			}
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 获取Class
	 * 
	 * @param instance
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Class<?> getUserClass(Object instance) {
		Class clazz = instance.getClass();
		if ((clazz != null) && clazz.getName().contains("$$")) {
			Class<?> superClass = clazz.getSuperclass();
			if ((superClass != null) && !Object.class.equals(superClass)) {
				return superClass;
			}
		}
		return clazz;
	}

	/**
	 * 获取属性类型
	 * 
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Class<?> getFieldClass(Object obj, String fieldName) {
		try {
			Field field = getFieldByFieldName(obj, fieldName);
			if (null == field)
				return null;
			return field.getType();
		} catch (Exception ex) {
			throw ex;
		}
	}

	/**
	 * 获取类的所有方法
	 * 
	 * @param className
	 * @return
	 */
	public static Method[] getDeclaredMethods(String className) {
		Class<?> clazz;
		try {
			clazz = Class.forName(className);
			return clazz.getDeclaredMethods();
		} catch (ClassNotFoundException e) {
		}
		return null;
	}

	/**
	 * 获取类的所有方法
	 * 
	 * @param clazz
	 * @return
	 */
	public static Method[] getDeclaredMethods(Class<?> clazz) {
		return clazz.getDeclaredMethods();
	}

	/**
	 * 根据方法名称和参数类型查找方法
	 * <p>
	 * Returns <code>null</code> if no {link Method} can be found. param clazz the class to introspect param name the
	 * name of the method param paramTypes the parameter types of the method (may be <code>null</code> to indicate any
	 * signature) return the Method object, or <code>null</code> if none found
	 */
	public static Method getMethod(Class<?> clazz, String name, Class<?>... paramTypes) {
		Class<?> searchType = clazz;
		while (searchType != null) {
			Method[] methods = (searchType.isInterface() ? searchType.getMethods() : searchType.getDeclaredMethods());
			for (Method method : methods) {
				if (name.equals(method.getName())
						&& (paramTypes == null || Arrays.equals(paramTypes, method.getParameterTypes()))) {
					return method;
				}
			}
			searchType = searchType.getSuperclass();
		}
		return null;
	}

	/**
	 * 调用方法
	 * 
	 * @param object
	 * @param methodName
	 * @param args
	 * @return
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Object invokeMethod(Object object, String methodName, Object... args)
			throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> clazz = object.getClass();
		final Class<?>[] parameterTypes = ClassUtil.toClass(args);
		Method method = getMethod(clazz, methodName, parameterTypes);
		if (method == null) {
			throw new NoSuchMethodException(
					"No such accessible method: " + methodName + "() on object: " + clazz.getName());
		}
		return method.invoke(object, args);
	}

	/**
	 * 重载+1 获取对象的所有注解
	 * 
	 * @param object
	 * @return
	 */
	public static Annotation[] getDeclaredAnnotations(Object object) {
		return object.getClass().getDeclaredAnnotations();
	}

	/**
	 * 重载+2 获取方法的所有注解
	 * 
	 * @param method
	 * @return
	 */
	public static Annotation[] getDeclaredAnnotations(Method method) {
		return method.getDeclaringClass().getDeclaredAnnotations();
	}

	/**
	 * 重载+1 根据注解类型获取对象的注解
	 * 
	 * @param clazz
	 * @return
	 */
	public static <A extends Annotation> A getDeclaredAnnotation(Class<?> clazz, Class<A> annotationClass) {
		return clazz.getDeclaredAnnotation(annotationClass);
	}

	/**
	 * 重载+2 根据注解类型获取对象的注解
	 * 
	 * @param method
	 * @return
	 */
	public static <A extends Annotation> A getDeclaredAnnotation(Method method, Class<A> annotationClass) {
		return method.getDeclaringClass().getDeclaredAnnotation(annotationClass);
	}

	/**
	 * 重载+1 根据注解类型获取对象的注解
	 * 
	 * @param clazz
	 * @param annotationClass
	 * @param annotationMethod
	 * @return
	 */
	public static <A extends Annotation> Object getDeclaredAnnotationValue(Class<?> clazz, Class<A> annotationClass,
			String annotationMethod) {
		return getDeclaredAnnotationValue(clazz, annotationClass, annotationMethod, null, null);
	}

	/**
	 * 重载+2 根据注解类型获取对象的注解
	 * 
	 * @param method
	 * @param annotationClass
	 * @param annotationMethod
	 * @return
	 */
	public static <A extends Annotation> Object getDeclaredAnnotationValue(Method method, Class<A> annotationClass,
			String annotationMethod) {
		return getDeclaredAnnotationValue(method.getDeclaringClass(), annotationClass, annotationMethod, null, null);
	}

	/**
	 * 重载+3 根据注解类型获取对象的注解
	 * 
	 * @param method
	 * @param annotationClass
	 * @param annotationMethod
	 * @param parameterTypes
	 * @param args
	 * @return
	 */
	public static <A extends Annotation> Object getDeclaredAnnotationValue(Method method, Class<A> annotationClass,
			String annotationMethod, Class<?>[] parameterTypes, Object[] args) {
		return getDeclaredAnnotationValue(method.getDeclaringClass(), annotationClass, annotationMethod, parameterTypes,
				args);
	}

	/**
	 * 重载+4 根据注解类型获取对象的注解
	 * 
	 * @param clazz
	 * @param annotationClass
	 * @param annotationMethod
	 * @param parameterTypes
	 * @param args
	 * @return
	 */
	public static <A extends Annotation> Object getDeclaredAnnotationValue(Class<?> clazz, Class<A> annotationClass,
			String annotationMethod, Class<?>[] parameterTypes, Object[] args) {
		Annotation annotation = getDeclaredAnnotation(clazz, annotationClass);
		if (annotation == null)
			return null;

		try {
			Method method = annotation.annotationType().getMethod(annotationMethod, parameterTypes);
			if (method == null)
				return null;

			return method.invoke(clazz.newInstance(), args);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 获取指定方法的注解参数名
	 * 
	 * @param method
	 *            要获取参数名的方法
	 * @return 按参数顺序排列的参数名列表
	 */
	@SuppressWarnings("all")
	public static List<String> getParameterNamesByAnnotation(Method method) {
		List<String> list = new ArrayList<String>();
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		if (null == parameterAnnotations || parameterAnnotations.length < 1)
			return list;

		for (Annotation[] parameterAnnotation : parameterAnnotations) {
			for (Annotation annotation : parameterAnnotation) {
				// TODO 方法注解参数
				// if (annotation instanceof Param) {
				// Param param = (Param) annotation;
				// list.add(param.getName());
				// }
			}
		}
		return list;
	}
}
