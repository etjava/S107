package com.etjava.service;

import java.util.List;
import java.util.Map;

import com.etjava.bean.CrawlerBlog;

public interface CrawlerBlogService {

	List<CrawlerBlog> list(Map<String,Object> map);
	
	Integer total(Map<String,Object> map);
	
	Integer add(CrawlerBlog blog);
	
	Integer update(CrawlerBlog blog);
	
	Integer delete(Integer id);
	
	Integer insert(CrawlerBlog blog);
	
	CrawlerBlog findById(Integer id);
	
	// 上一篇博客
	CrawlerBlog lastBlog(Integer id);
	
	// 上一篇博客
	CrawlerBlog nextBlog(Integer id);
}
