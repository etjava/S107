package com.etjava.mapper;

import com.etjava.bean.Users;

/**
 * 博主信息Mapper(dao层)
 * @author etjav
 *
 */
public interface UsersMapper {

	Users findByUserName(String userName);
	
	Users findById(Integer id);

	Integer update(Users user);
	
}
