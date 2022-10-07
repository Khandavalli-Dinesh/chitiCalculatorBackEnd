package com.galvan.chiti.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.galvan.chiti.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	/*
	 * @Query("Select u from User u where u.uname =:uname ") Optional<User>
	 * findByCredentials(@Param("uname") String uname);
	 */

	Optional<User> findByUname(String uname);

}
