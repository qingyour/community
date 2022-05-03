package com.nowcoder.community.dao;

import org.springframework.stereotype.Repository;

@Repository("alphaDaoHibernate")
public class AlphaDaoHibernateImpl implements AlphaDao{
	@Override
	public String select() {
		return "这是Hibernate实现的bean";
	}
}
