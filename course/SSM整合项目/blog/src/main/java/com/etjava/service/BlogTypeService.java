package com.etjava.service;

import java.util.List;
import java.util.Map;

import com.etjava.bean.BlogType;

public interface BlogTypeService {

	List<BlogType> countList();
	
	BlogType findById(Integer id);
	
	List<BlogType> list(Map<String,Object> map);
	
	Integer add(BlogType blogtype);
	
	Integer delete(Integer id);
	
	Integer update(BlogType blogtype);
	
	Integer total(Map<String,Object> map);
}
