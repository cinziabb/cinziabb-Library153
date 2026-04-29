package com.generation153.library.service;

import java.util.List;

import com.generation153.library.entity.User;

public interface UserService {

	/**
	*
	*/
	List<User> findAllUsers();
	
	/**
	*
	*/
	User findUserById(Integer id);
	
	/**
	*
	*/
	List<User> findUserByName(String name);
	
	/**
	*
	*/
	User saveUser(User user);
	
	/**
	*
	*/
	User updateUserById(User user, Integer id);		//PATCH
	
	/**
	*
	*/
	User replaceUserById(User user, Integer id);	//PUT
	
	/**
	*
	*/
	void deleteUserById(Integer id);
	
	
	
}
