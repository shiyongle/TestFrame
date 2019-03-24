package com.dongnao.autotest.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Json序列化工具类
 * 
 * @author easy
 *
 */
public final class JsonUtil {
	private static final ObjectMapper objectMapper = new ObjectMapper();
	static {
		// 允许键没有引号
		objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
	}

	// region ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ Jackson ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	/**
	 * 对象转成json字符串
	 * 
	 * @param object
	 * @return
	 */
	public static String toJsonUseJackson(Object object) {
		try {
			objectMapper.disable(SerializationFeature.WRITE_ENUMS_USING_INDEX);
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException ex) {
			return "";
		}
	}

	/**
	 * 对象转成json字符串
	 * 
	 * @param object
	 * @param isOrdinal
	 * @return
	 */
	public static String toJsonUseJackson(Object object, boolean isOrdinal) {
		try {
			objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, isOrdinal);
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException ex) {
			return "";
		}
	}

	/**
	 * json串转成泛型对象
	 * 
	 * @param text
	 * @param clazz
	 * @return
	 */
	public static <T> T toObjectUseJackson(String text, Class<T> clazz) {
		try {
			return objectMapper.readValue(text, clazz);
		} catch (JsonParseException e) {
		} catch (JsonMappingException e) {
		} catch (IOException e) {
		}
		return null;
	}

	/**
	 * json串转成泛型对象
	 * 
	 * @param text
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> toListUseJackson(String text, Class<T> clazz) {
		JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
		try {
			return objectMapper.readValue(text, javaType);
		} catch (JsonParseException e) {
		} catch (JsonMappingException e) {
		} catch (IOException e) {
		}
		return null;
	}

	/**
	 * json串转成泛型对象
	 * 
	 * @param text
	 * @param valueTypeRef
	 * @return
	 */
	public static <T> T toObjectUseJackson(String text, TypeReference<T> valueTypeRef) {
		try {
			return objectMapper.readValue(text, valueTypeRef);
		} catch (JsonParseException e) {
		} catch (JsonMappingException e) {
		} catch (IOException e) {
		}
		return null;
	}
	// endregion ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ Jackson ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
}
