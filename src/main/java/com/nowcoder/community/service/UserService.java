package com.nowcoder.community.service;

import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.util.CommunityConstant;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.MailClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserService implements CommunityConstant {

	@Autowired
	private UserMapper userMapper;

	public User findUserById(int id){
		return userMapper.selectById(id);
	}

	//要发邮件
	@Autowired
	public MailClient mailClient;

	@Autowired
	public TemplateEngine templateEngine;

	@Value("${community.path.domain}")
	public String domain;

	@Value("${server.servlet.context-path}")
	public String contextPath;

	public Map<String,Object> register(User user){
		Map<String,Object> map = new HashMap<>();
		if(user==null){
			throw new IllegalArgumentException("参数不能为空!");
		}
		if(StringUtils.isBlank(user.getUsername())){
			map.put("usernameMsg","账户不能为空!");
			return map;
		}
		if(StringUtils.isBlank(user.getPassword())){
			map.put("passwordMsg","密码不能为空!");
			return map;
		}
		if(StringUtils.isBlank(user.getEmail())){
			map.put("emailMsg","邮箱不能为空!");
			return map;
		}
		//判断完空值之后判断是否存在该账户
		User u = userMapper.selectByUsername(user.getUsername());
		if(u!=null){
			map.put("usernameMsg","账户已存在!");
			return map;
		}
		u = userMapper.selectByEmail(user.getEmail());
		if(u!=null){
			map.put("emailMsg","邮箱已存在!");
			return map;
		}
		//不为空而且不存在账户的话  将它注册也就是插入数据库
		//添加的随机的salt
		user.setSalt(CommunityUtil.generateUUID().substring(0,5));
		//加密
		user.setPassword(CommunityUtil.md5(user.getPassword()+user.getSalt()));
		user.setType(0);
		user.setStatus(0);
		user.setActivationCode(CommunityUtil.generateUUID());
		user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000)));
		user.setCreateTime(new Date());
		int i = userMapper.insertUser(user);
		if(i>0){
			System.out.println("注册成功");
		}
		//激活邮件 不要倒错包
		Context context = new Context();
		context.setVariable("email",user.getEmail());
		// http://localhost:8080/community/activation/101/code
		String url = domain + contextPath + "/activation/" + user.getId() +"/" + user.getActivationCode();
		context.setVariable("url",url);
		//不需要带.html
		String content = templateEngine.process("/mail/activation", context);
		mailClient.sendMail(user.getEmail(),"激活邮件",content);


		return map;
	}

	public int activation(int userId, String code){
		User user = userMapper.selectById(userId);
		if(user.getStatus()==1){
			//说明重复激活
			return ACTIVATION_REPEAT;
		}else if(user.getActivationCode().equals(code)){
			//更改状态为1
			userMapper.updateStatus(userId,1);
			return ACTIVATION_SUCCESS;
		}else{
			return ACTIVATION_FAILURE;
		}
	}




}
