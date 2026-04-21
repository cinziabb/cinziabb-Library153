package com.generation153.library.service;

import java.util.List;

import com.generation153.library.entity.User;

public interface UserService {

	List<User> findAllUsers();
	User findUserById(Integer id);
	User saveUser(User user);
	User updateUserById(User user, Integer id);
	void deleteUserById(Integer id);
}
