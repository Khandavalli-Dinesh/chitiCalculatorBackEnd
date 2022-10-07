package com.galvan.chiti.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.galvan.chiti.auxilliaries.NoPrivilageException;
import com.galvan.chiti.auxilliaries.UserResponse;
import com.galvan.chiti.entity.User;

@Service
public interface UserService {

	public User addUser(User user) throws NoPrivilageException, Exception;

	public User getUser(int id) throws NoPrivilageException, Exception;

	UserResponse logIn(User user) throws Exception;

	UserResponse logOut() throws Exception;

	public UserResponse removeUser(int id) throws NoPrivilageException, Exception;

	public UserResponse updateUser(User existingUser) throws NoPrivilageException, Exception;

	public List<User> getAllUsers() throws NoPrivilageException, Exception;

	List<String> getAllUnames() throws NoPrivilageException, Exception;

	public boolean isLoggedIn();

}
