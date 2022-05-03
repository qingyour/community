package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {
	/**
	 * 查询所有的Discuss
	 * @param userId
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);

	/**
	 * 查询当前用户发布的所有信息
	 * @param userId
	 * @return
	 */
	int selectDiscussPostRows(@Param("userId")int userId);
}
