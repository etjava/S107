package com.etjava.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.etjava.bean.CrawlerBlog;
import com.etjava.mapper.CrawlerBlogMapper;
import com.etjava.service.CrawlerBlogService;

@Service("crawlerBlogService")
public class CrawlerBlogServiceImpl implements CrawlerBlogService {

	@Resource
	private CrawlerBlogMapper crawlerBlogMapper;
	
	@Override
	public List<CrawlerBlog> list(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return crawlerBlogMapper.list(map);
	}

	@Override
	public Integer total(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return crawlerBlogMapper.total(map);
	}

	@Override
	public Integer add(CrawlerBlog blog) {
		// TODO Auto-generated method stub
		return crawlerBlogMapper.add(blog);
	}

	@Override
	public Integer update(CrawlerBlog blog) {
		// TODO Auto-generated method stub
		return crawlerBlogMapper.update(blog);
	}

	@Override
	public Integer delete(Integer id) {
		// TODO Auto-generated method stub
		return crawlerBlogMapper.delete(id);
	}

	@Override
	public Integer insert(CrawlerBlog blog) {
		// TODO Auto-generated method stub
		return crawlerBlogMapper.insert(blog);
	}

	@Override
	public CrawlerBlog findById(Integer id) {
		// TODO Auto-generated method stub
		return crawlerBlogMapper.findById(id);
	}

	@Override
	public CrawlerBlog lastBlog(Integer id) {
		// TODO Auto-generated method stub
		return crawlerBlogMapper.lastBlog(id);
	}

	@Override
	public CrawlerBlog nextBlog(Integer id) {
		// TODO Auto-generated method stub
		return crawlerBlogMapper.nextBlog(id);
	}

}
