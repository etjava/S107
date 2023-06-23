package com.etjava.service;

import java.util.List;
import java.util.Map;

import com.etjava.bean.Music;

public interface MusicService {

	List<Music> findAll(Map<String,Object> map);
}
