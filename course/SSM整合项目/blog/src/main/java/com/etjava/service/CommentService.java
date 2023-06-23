package com.etjava.service;

import java.util.List;
import java.util.Map;

import com.etjava.bean.Comment;

public interface CommentService {

	List<Comment> list(Map<String,Object> map);
	
	Integer add(Comment comment);
	
	Integer total(Map<String,Object> map);
	
	Integer update(Comment comment);
	
	Integer delete(Integer id);
}
