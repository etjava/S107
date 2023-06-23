package com.etjava.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.etjava.bean.Link;
import com.etjava.mapper.LinkMapper;
import com.etjava.service.LinkService;

@Service("linkService")
public class LinkServiceImpl implements LinkService {
	
	@Resource
	private LinkMapper linkMapper;

	@Override
	public List<Link> list(Map<String, Object> map) {
		return linkMapper.list(map);
	}

	@Override
	public Integer add(Link link) {
		return linkMapper.add(link);
	}

	@Override
	public Integer update(Link link) {
		return linkMapper.update(link);
	}

	@Override
	public Integer total(Map<String, Object> map) {
		return linkMapper.total(map);
	}

	@Override
	public Integer delete(Integer id) {
		return linkMapper.delete(id);
	}

}
