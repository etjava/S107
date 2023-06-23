package com.etjava.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.etjava.bean.Comment;
import com.etjava.mapper.CommentMapper;
import com.etjava.service.CommentService;

@Service("commentService")
public class CommentServiceImpl implements CommentService {

	@Resource
	private CommentMapper commentMapper;
	
	@Override
	public List<Comment> list(Map<String, Object> map) {
		return commentMapper.list(map);
	}

	@Override
	public Integer add(Comment comment) {
		return commentMapper.add(comment);
	}

	@Override
	public Integer total(Map<String, Object> map) {
		return commentMapper.total(map);
	}

	@Override
	public Integer update(Comment comment) {
		return commentMapper.update(comment);
	}

	@Override
	public Integer delete(Integer id) {
		return commentMapper.delete(id);
	}

}
