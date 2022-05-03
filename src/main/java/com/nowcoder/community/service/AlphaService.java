package com.nowcoder.community.service;


import com.nowcoder.community.dao.AlphaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author Allen
 */

@Service
public class AlphaService {

	@Autowired
	AlphaDao alphaDao;

	public AlphaService(){
		System.out.println("这是一个构造方法。");
	}

	//在构造方法之后执行的初始化方法

	@PostConstruct
	public void init(){
		System.out.println("这是初始化方法");
	}
	//销毁这个对象
	@PreDestroy
	public void destory(){
		System.out.println("销毁该对象");
	}

	public String find(){
		return alphaDao.select();
	}

}
