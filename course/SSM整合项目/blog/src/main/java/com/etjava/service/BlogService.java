package com.etjava.service;

import java.util.List;
import java.util.Map;

import com.etjava.bean.Blog;

public interface BlogService {

	List<Blog> countList();
	
	/**
	 * 分页查询数据
	 * @param map
	 * @return
	 */
	List<Blog> list(Map<String,Object> map);
	
	/**
	 * 总记录数 - 分页用
	 * @param map
	 * @return
	 */
	Integer total(Map<String,Object> map);
	
	Blog findbyId(Integer id);
	
	Integer update(Blog blog);
	
	// 上一篇博客
	Blog lastBlog(Integer id);
	
	// 上一篇博客
	Blog nextBlog(Integer id);
	
	Integer add(Blog blog);
	
	Integer delete(Integer id);
	
	Integer countByTypeId(Integer typeId);
}
