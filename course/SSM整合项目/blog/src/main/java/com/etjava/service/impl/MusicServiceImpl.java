package com.etjava.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.etjava.bean.Music;
import com.etjava.mapper.MusicMapper;
import com.etjava.service.MusicService;

@Service("musicService")
public class MusicServiceImpl implements MusicService {

	@Resource
	private MusicMapper musicMapper;
	
	@Override
	public List<Music> findAll(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return musicMapper.findAll(map);
	}

}
