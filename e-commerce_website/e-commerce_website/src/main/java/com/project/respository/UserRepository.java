package com.project.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByEmail(String email);

}
