package com.flight.carryprice.common.enums;

public interface BaseEnum<T> {

	/**
	 * 该枚举的name
	 * @return
	 */
	public String name();
	
	/**
	 * 获取枚举的code
	 * @return
	 */
	public T getCode();
	
	/**
	 * 获取枚举的描述
	 * @return
	 */
	public String getDescription() ;
}
