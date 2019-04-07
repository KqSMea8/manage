package com.flight.carryprice.common.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JacksonUtil {

	private final static Logger LOGGER = Logger.getLogger(JacksonUtil.class);

	/**
	 * 对象转json
	 * 
	 * @param obj
	 * @return
	 */
	public static String obj2json(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL); // 忽略空属性
		String json = null;
		try {
			json = mapper.writeValueAsString(obj);
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return json;
	}

	/**
	 * json转对象
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T json2Obj(String json, Class<T> clazz) {
		if (StringUtils.isBlank(json)) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		T t = null;
		try {
			t = mapper.readValue(json, clazz);
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return t;
	}

	/**
	 * json转list
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> json2List(String json, Class<T> clazz) {
		ObjectMapper mapper = new ObjectMapper();

		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		JavaType javaType = getCollectionType(mapper, ArrayList.class, clazz);
		List<T> t = new ArrayList<T>();
		try {
			t = (List<T>) mapper.readValue(json, javaType);
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return t;
	}

	/**
	 * json转map
	 * 
	 * @param json
	 * @param class1
	 *            map中key的类型
	 * @param class2
	 *            map中value的类型
	 * @return
	 */
	public static HashMap<?, ?> json2Map(String json, Class<?> class1, Class<?> class2) {
		ObjectMapper mapper = new ObjectMapper();
		HashMap<?, ?> map = null;
		try {
			JavaType javaType = mapper.getTypeFactory().constructParametricType(HashMap.class, class1, class2);
			map = mapper.readValue(json, javaType);
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return map;
	}

	public static void main(String[] args) {
		String json = "{ \"1\":\"东航B2B\", \"2\":\"南航B2B\" }";
		HashMap<Integer, String> map = (HashMap<Integer, String>) JacksonUtil
				.json2Map(json, String.class, String.class);
		System.out.println(map.get("2"));
		System.out.println(map);
	}

	/**
	 * 对象之间转换
	 * 
	 * @param obj
	 * @param clazz
	 * @return
	 */
	public static <T> T obj2obj(Object obj, Class<T> clazz) {
		String json = obj2json(obj);
		ObjectMapper mapper = new ObjectMapper();

		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		T t = null;
		try {
			t = mapper.readValue(json, clazz);
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return t;
	}

	public static <T> List<T> obj2ListObj(Object obj, Class<T> clazz) {
		String json = obj2json(obj);
		ObjectMapper mapper = new ObjectMapper();

		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		JavaType javaType = getCollectionType(mapper, ArrayList.class, clazz);
		List<T> t = new ArrayList<T>();
		try {
			t = (List<T>) mapper.readValue(json, javaType);
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return t;
	}

	/**
	 * 使用转换json方式复制属性
	 * 
	 * @param source
	 * @param targetClazz
	 * @return
	 */
	public static <T> T copyProperties(Object source, Class<T> targetClazz) {
		try {
			String tempJson = obj2json(source);
			T target = json2Obj(tempJson, targetClazz);
			return target;
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return null;
	}

	/**
	 * 获取泛型的Collection Type
	 * 
	 * @param collectionClass
	 *            泛型的Collection
	 * @param elementClasses
	 *            元素类
	 * @return JavaType Java 类型
	 */
	public static JavaType getCollectionType(ObjectMapper mapper, Class<?> collectionClass, Class<?>... elementClasses) {
		return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}
}


