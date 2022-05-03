package com.nowcoder.community.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/**
 * 想使用心得技术
 */
@Repository
@Primary
public class AlphaDaoMybatisImpl implements AlphaDao{

	@Override
	public String select() {
		return "这里是Mybatis实现的bean";
	}
}
