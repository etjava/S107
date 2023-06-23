package com.etjava.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.etjava.bean.BlogType;
import com.etjava.mapper.BlogTypeMapper;
import com.etjava.service.BlogTypeService;

@Service("blogTypeService")
public class BlogTypeServiceImpl implements BlogTypeService {

	@Resource
	private BlogTypeMapper bligTypeMapper;
	
	@Override
	public List<BlogType> countList() {
		return bligTypeMapper.countList();
	}

	@Override
	public BlogType findById(Integer id) {
		return bligTypeMapper.findById(id);
	}

	@Override
	public List<BlogType> list(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return bligTypeMapper.list(map);
	}

	@Override
	public Integer add(BlogType blogtype) {
		// TODO Auto-generated method stub
		return bligTypeMapper.add(blogtype);
	}

	@Override
	public Integer delete(Integer id) {
		// TODO Auto-generated method stub
		return bligTypeMapper.delete(id);
	}

	@Override
	public Integer update(BlogType blogtype) {
		// TODO Auto-generated method stub
		return bligTypeMapper.update(blogtype);
	}

	@Override
	public Integer total(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return bligTypeMapper.total(map);
	}

}
