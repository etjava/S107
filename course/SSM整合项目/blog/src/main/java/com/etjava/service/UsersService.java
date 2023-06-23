package com.etjava.service;

import com.etjava.bean.Users;

/**
 * @author etjava
 *
 */
public interface UsersService {

	Users findByUserName(String userName);
	
	Users findById(Integer id);
	
	Integer update(Users user);
}
