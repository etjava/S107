package com.etjava.mapper;

import java.util.List;
import java.util.Map;

import com.etjava.bean.Music;

public interface MusicMapper {

	List<Music> findAll(Map<String,Object> map);
}
