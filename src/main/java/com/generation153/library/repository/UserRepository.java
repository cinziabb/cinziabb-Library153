package com.generation153.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation153.library.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
