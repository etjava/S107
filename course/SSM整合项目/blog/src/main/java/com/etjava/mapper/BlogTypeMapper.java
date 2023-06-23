package com.etjava.mapper;

import java.util.List;
import java.util.Map;

import com.etjava.bean.BlogType;

public interface BlogTypeMapper {

	/**
	 *   首页展示博客类别使用 
	 * @return
	 */
	List<BlogType> countList();
	
	BlogType findById(Integer id);
	
	/**
		博客类别管理 - 后台列表使用
	 * @param map
	 * @return
	 */
	List<BlogType> list(Map<String,Object> map);
	
	Integer add(BlogType blogtype);
	
	Integer delete(Integer id);
	
	Integer update(BlogType blogtype);
	
	Integer total(Map<String,Object> map);
	
	
}
