package com.nowcoder.community.controller;

import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CommunityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class LoginController implements CommunityConstant {

	@Autowired
	private UserService userService;

	@RequestMapping(path="/register",method = RequestMethod.GET)
	public String getRegisterPage(){
		return "site/register";
	}

	@RequestMapping(path="/login",method = RequestMethod.GET)
	public String getLoginPage(){
		return "site/login";
	}

	@RequestMapping(path="/register",method = RequestMethod.POST)
	public String register(Model model, User user){
		//形参里面的user Spring会根据前台的name值传过来
		Map<String, Object> map = userService.register(user);
		//判断map是否是空 是的话说明注册参数都没有问题
		if(map==null||map.isEmpty()){
			//跳转到一个操作成功页面
			model.addAttribute("msg","注册成功，请前往邮箱激活");
			//跳转的页面
			model.addAttribute("target","/index");
			return "site/operate-result";
		}else{
			model.addAttribute("usernameMsg",map.get("usernameMsg"));
			model.addAttribute("passwordMsg",map.get("passwordMsg"));
			model.addAttribute("emailMsg",map.get("emailMsg"));
			return "site/register";
		}

	}
	// http://localhost:8080/community/activation/101/code
	@RequestMapping(path="/activation/{userId}/{code}",method = RequestMethod.GET)
	public String activation(Model model, @PathVariable("userId") int userId,@PathVariable("code") String code){
		int result = userService.activation(userId, code);
		if(result == ACTIVATION_SUCCESS){
			model.addAttribute("msg","激活成功，您的账号已经可以正常使用了！！！");
			model.addAttribute("target","/login");
		}else if(result == ACTIVATION_REPEAT){
			model.addAttribute("msg","无效操作，该账号已经被激活了！！！");
			model.addAttribute("target","/index");
		}else{
			model.addAttribute("msg","激活失败，您的激活码错误。");
			model.addAttribute("target","/index");
		}
		return "site/operate-result";
	}



}
