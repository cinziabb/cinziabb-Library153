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
	List<User> findUserByFirstName(String firstName);
	
	/**
	*
	*/
	List<User> findUserByLastName(String lastName);
	/**
	*
	*/
	List<User> findUserByFullName(String firstName, String lastName);
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
	 * @return 
	*
	*/
	boolean deleteUserById(Integer id);
	
	//gestione utenti in base ai ritardi

	User blockUser(Integer userId);
	
	User unblockUser(Integer userId);
	
	
	

	
	
	
	
}
