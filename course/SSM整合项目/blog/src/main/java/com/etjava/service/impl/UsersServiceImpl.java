package com.etjava.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.etjava.bean.Users;
import com.etjava.mapper.UsersMapper;
import com.etjava.service.UsersService;

@Service("userService")
public class UsersServiceImpl implements UsersService{


	@Resource
	private UsersMapper usersMapper;
	
	@Override
	public Users findByUserName(String userName) {
		return usersMapper.findByUserName(userName);
	}

	@Override
	public Users findById(Integer id) {
		// TODO Auto-generated method stub
		return usersMapper.findById(id);
	}

	@Override
	public Integer update(Users user) {
		return usersMapper.update(user);
	}

}
