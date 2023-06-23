package com.etjava.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.etjava.bean.Blog;
import com.etjava.mapper.BlogMapper;
import com.etjava.service.BlogService;

@Service("blogService")
public class BlogServiceimpl implements BlogService {

	@Resource
	private BlogMapper blogMapper;
	
	@Override
	public List<Blog> countList() {
		return blogMapper.countList();
	}

	@Override
	public List<Blog> list(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return blogMapper.list(map);
	}

	@Override
	public Integer total(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return blogMapper.total(map);
	}

	@Override
	public Blog findbyId(Integer id) {
		// TODO Auto-generated method stub
		return blogMapper.findbyId(id);
	}

	@Override
	public Integer update(Blog blog) {
		// TODO Auto-generated method stub
		return blogMapper.update(blog);
	}

	@Override
	public Blog lastBlog(Integer id) {
		// TODO Auto-generated method stub
		return blogMapper.lastBlog(id);
	}

	@Override
	public Blog nextBlog(Integer id) {
		// TODO Auto-generated method stub
		return blogMapper.nextBlog(id);
	}

	@Override
	public Integer add(Blog blog) {
		// TODO Auto-generated method stub
		return blogMapper.add(blog);
	}

	@Override
	public Integer delete(Integer id) {
		// TODO Auto-generated method stub
		return blogMapper.delete(id);
	}

	@Override
	public Integer countByTypeId(Integer typeId) {
		// TODO Auto-generated method stub
		return blogMapper.countByTypeId(typeId);
	}
	
	

}
