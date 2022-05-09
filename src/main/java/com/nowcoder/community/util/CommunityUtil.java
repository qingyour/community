package com.nowcoder.community.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.UUID;

public class CommunityUtil {
	/**
	 *	生成随机字符串
	 */
	public static String generateUUID(){
		//生成的随机ID会带有-换成”“
		return UUID.randomUUID().toString().replaceAll("-","");
	}

	public static String md5(String key){
		//如果是空的就跳出
		if(StringUtils.isBlank(key)){
			return null;
		}
		//进行加密
		return DigestUtils.md5DigestAsHex(key.getBytes());
	}
}
