package com.flight.carryprice.common.service;

import java.util.List;

public interface BaseService<T> {

	/**
	 * 查询所有
	 * 
	 * @return
	 */
	List<T> selectAll();

	/**
	 * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
	 * 
	 * @param key
	 * @return
	 */
	T selectByPrimaryKey(Object key);

	/**
	 * 保存一个实体，null的属性也会保存，不会使用数据库默认值
	 * 
	 * @param entity
	 * @return
	 */
	int insert(T entity);

	/**
	 * 保存一个实体,null属性不会保存，使用数据库默认值
	 * 
	 * @param entity
	 * @return
	 */
	int insertSelective(T entity);

	/**
	 * 根据主键删除
	 * 
	 * @param key
	 * @return
	 */
	int deleteByPrimaryKey(Object key);

	/**
	 * 根据主键更新实体全部字段，null值会被更新
	 * 
	 * @param entity
	 * @return
	 */
	int updateByPrimaryKey(T entity);

	/**
	 * 根据主键更新属性不为null的值
	 * 
	 * @param entity
	 * @return
	 */
	int updateByPrimaryKeySelective(T entity);

	List<T> select(T t);

	T selectOne(T t);

}
