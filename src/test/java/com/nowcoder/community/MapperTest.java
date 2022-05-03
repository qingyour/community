package com.nowcoder.community;

import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.jta.WebSphereUowTransactionManager;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MapperTest {
	@Autowired
	private UserMapper userMapper;

	@Test
	public void testSelectUser(){
		User user = userMapper.selectById(101);
		System.out.println(user);
		user = userMapper.selectByUsername("liubei");
		System.out.println(user);
		user = userMapper.selectByEmail("nowcoder101@sina.com");
		System.out.println(user);

	}
	@Test
	public void testInsertUser(){
		User newUser = new User();
		newUser.setUsername("test");
		newUser.setPassword("123456");
		newUser.setEmail("test@qq.com");
		newUser.setHeaderUrl("http://www.nowcoder.com/101.png");
		newUser.setSalt("abc");
		newUser.setCreateTime(new Date());
		int i = userMapper.insertUser(newUser);
		System.out.println(i);
		System.out.println(newUser.getId());

	}


	@Test
	public void testUpdateUser(){
		User user = new User();
		user.setStatus(1);
		user.setHeaderUrl("http://www.nowcoder.com/102.png");
		user.setPassword("654321");
		int i1 = userMapper.updateStatus(150, 1);
		int i = userMapper.updateHeader(150, "http://www.nowcoder.com/102.png");
		int i2 = userMapper.updatePassword(150, "654321");
		System.out.println(i+" "+i1+" "+i2);

	}

	@Autowired
	private DiscussPostMapper discussPostMapper;

	@Test
	public void testSelectDiscussPost(){
		List<DiscussPost> list = discussPostMapper.selectDiscussPosts(0, 1, 10);
		for(DiscussPost d:list){
			System.out.println(d);
		}
		int i = discussPostMapper.selectDiscussPostRows(0);
		System.out.println(i);

	}

}
