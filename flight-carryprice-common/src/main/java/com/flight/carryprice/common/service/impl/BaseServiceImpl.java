package com.flight.carryprice.common.service.impl;

import com.flight.carryprice.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

public class BaseServiceImpl<T> implements BaseService<T> {

	@Autowired
	protected Mapper<T> mapper;

	@Override
	public List<T> selectAll() {
		return mapper.selectAll();
	}

	@Override
	public T selectByPrimaryKey(Object key) {
		return mapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(T entity) {
		return mapper.insert(entity);
	}

	@Override
	public int insertSelective(T entity) {
		return mapper.insertSelective(entity);
	}

	@Override
	public int deleteByPrimaryKey(Object key) {
		return mapper.deleteByPrimaryKey(key);
	}

	@Override
	public int updateByPrimaryKey(T entity) {
		return mapper.updateByPrimaryKey(entity);
	}

	@Override
	public int updateByPrimaryKeySelective(T entity) {
		return mapper.updateByPrimaryKeySelective(entity);
	}

	@Override
	public List<T> select(T t) {
		return mapper.select(t);
	}

	@Override
	public T selectOne(T t) {
		return mapper.selectOne(t);
	}

}
