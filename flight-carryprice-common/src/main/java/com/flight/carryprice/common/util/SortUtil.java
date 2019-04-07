package com.flight.carryprice.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * 
 * @Description 排序
 * @see
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @date 2019年03月27日 下午5:18:14
 * @updateTime
 * @param <T>
 */
public class SortUtil<T> {

	/**
	 * @param targetList
	 *            目标排序List
	 * @param field1
	 *            排序字段(实体类属性名)
	 * @param field2
	 *            排序字段（如果field1相同，按照field2排序）
	 * @param sortMode
	 *            field1排序方式（asc or desc）
	 * @param sortMode2
	 *            field2排序方式
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sort(List<T> targetList, final String field1, final String field2, final String sortMode,
			final String sortMode2) {
		Collections.sort(targetList, new Comparator() {

			public int compare(Object arg1, Object arg2) {
				int retVal = 0;
				try {
					retVal = sortObj(field1, arg1, arg2);
					if (retVal == 0) {
						if (sortMode2 != null && "desc".equals(sortMode2)) {
							retVal = sortObj(field2, arg1, arg2);
						} else {
							retVal = sortObj(field2, arg2, arg1);
						}

					}
					if (sortMode != null && "desc".equals(sortMode)) {
						// 倒序
						retVal = -retVal;
					}
				} catch (Exception e) {
					throw new RuntimeException();
				}
				return retVal;
			}
		});
	}

	@SuppressWarnings("unchecked")
	public int sortObj(String field, Object arg1, Object arg2)
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		// 首字母转大写
		String newStr1 = field.substring(0, 1).toUpperCase() + field.replaceFirst("\\w", "");
		String newStr2 = field.substring(0, 1).toUpperCase() + field.replaceFirst("\\w", "");
		String methodStr1 = "get" + newStr1;
		String methodStr2 = "get" + newStr2;

		Method method1 = ((T) arg1).getClass().getMethod(methodStr1);
		Method method2 = ((T) arg2).getClass().getMethod(methodStr2);
		Object obj1 = method1.invoke(((T) arg1));
		Object obj2 = method2.invoke(((T) arg2));
		int result = 0;
		if (obj1 instanceof String) {
			// 字符串
			result = obj1.toString().compareTo(obj2.toString());
		} else if (obj1 instanceof Date) {
			// 日期
			long l = ((Date) obj1).getTime() - ((Date) obj2).getTime();
			if (l > 0) {
				result = 1;
			} else if (l < 0) {
				result = -1;
			} else {
				result = 0;
			}
		} else if (obj1 instanceof Integer) {
			// 整型（Method的返回参数可以是int的，因为JDK1.5之后，Integer与int可以自动转换了）
			result = (Integer) obj1 - (Integer) obj2;
		} else if (obj1 instanceof BigDecimal) {
			result = ((BigDecimal) obj1).subtract((BigDecimal) obj2).intValue();
		}
		return result;
	}
}