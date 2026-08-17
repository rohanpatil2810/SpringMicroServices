package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.UserClass;

@Repository
public interface UserRepo extends JpaRepository<UserClass, Integer>{

	public UserClass findByName(String name);
}
