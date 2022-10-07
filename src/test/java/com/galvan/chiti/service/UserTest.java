package com.galvan.chiti.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.galvan.chiti.auxilliaries.NoPrivilageException;
import com.galvan.chiti.auxilliaries.UserCheck;
import com.galvan.chiti.entity.Candidate;
import com.galvan.chiti.entity.User;
import com.galvan.chiti.repository.UserRepository;

@SpringBootTest
class UserTest {
	
	@Autowired
	UserService userService;
	
	@MockBean
	UserRepository userRepo;
	
	User u = new User(0,"dummy","password",new Candidate(0,"dummy","9898989898"), "USERS");
	
	@Test
	void nullUname() {
		User user = u;
		user.setUname(null);
		UserCheck.userId = 5;
		
		List<User> users = new ArrayList<User>();
		users.add(u);
		Mockito.when(userRepo.findAll()).thenReturn(users);
		Mockito.when(userRepo.findById(user.getId())).thenReturn(Optional.of(u));
		Mockito.when(userRepo.findByUname(user.getUname())).thenReturn(Optional.of(u));
		
		//log in using no uname or null uname
		Mockito.when(userRepo.findByUname(null)).thenReturn(Optional.ofNullable(null));
		assertThrows(Exception.class,()->userService.logIn(user));
		
		UserCheck.isLoggedIn = true;
				
		//Logged in user is admin
		UserCheck.role = "ADMIN";
		assertThrows(Exception.class,()->userService.addUser(user));
		assertThrows(Exception.class,()->userService.updateUser(user));
		
		//Logged in user is user
		UserCheck.role = "USERS";
		assertThrows(NoPrivilageException.class,()->userService.addUser(user));
		assertThrows(NoPrivilageException.class,()->userService.updateUser(user));
		
		//Not logged in
		UserCheck.isLoggedIn = false;
		assertThrows(Exception.class,()->userService.addUser(user));
		assertThrows(Exception.class,()->userService.updateUser(user));
	}
	
	@Test 
	void nullPassword() {
		User user = u;
		user.setPassword(null);
		UserCheck.userId = 5;
		UserCheck.isLoggedIn = true;
		List<User> users = new ArrayList<User>();
		users.add(u);
		Mockito.when(userRepo.findAll()).thenReturn(users);
		Mockito.when(userRepo.findById(user.getId())).thenReturn(Optional.of(u));
		
		//Logged in user is admin
		UserCheck.role = "ADMIN";
		assertThrows(Exception.class,()->userService.addUser(user));
		assertThrows(Exception.class,()->userService.updateUser(user));
		
		//Logged in user is user
		UserCheck.role = "USERS";
		assertThrows(NoPrivilageException.class,()->userService.addUser(user));
		assertThrows(NoPrivilageException.class,()->userService.updateUser(user));
		
		//Not logged in
		UserCheck.isLoggedIn = false;
		assertThrows(Exception.class,()->userService.addUser(user));
		assertThrows(Exception.class,()->userService.updateUser(user));
	}
	
	@Test
	void nullCandidate() {
		User user = u;
		user.setCandidateId(null);
		UserCheck.userId = 5;
		UserCheck.isLoggedIn = true;
		List<User> users = new ArrayList<User>();
		users.add(u);
		Mockito.when(userRepo.findAll()).thenReturn(users);
		Mockito.when(userRepo.findById(user.getId())).thenReturn(Optional.of(u));
		
		//Logged in user is admin
		UserCheck.role = "ADMIN";
		assertThrows(Exception.class,()->userService.addUser(user));
		assertThrows(Exception.class,()->userService.updateUser(user));
		
		//Logged in user is user
		UserCheck.role = "USERS";
		assertThrows(NoPrivilageException.class,()->userService.addUser(user));
		assertThrows(NoPrivilageException.class,()->userService.updateUser(user));
		
		//Not logged in
		UserCheck.isLoggedIn = false;
		assertThrows(Exception.class,()->userService.addUser(user));
		assertThrows(Exception.class,()->userService.updateUser(user));
	}
	
	@Test
	void nullRole() {
		User user = u;
		user.setRole(null);
		UserCheck.userId = 5;
		UserCheck.isLoggedIn = true;
		List<User> users = new ArrayList<User>();
		users.add(u);
		Mockito.when(userRepo.findAll()).thenReturn(users);
		Mockito.when(userRepo.findById(user.getId())).thenReturn(Optional.of(u));
		
		//Logged in user is admin
		UserCheck.role = "ADMIN";
		assertThrows(Exception.class,()->userService.addUser(user));
		assertThrows(Exception.class,()->userService.updateUser(user));
		
		//Logged in user is user
		UserCheck.role = "USERS";
		assertThrows(NoPrivilageException.class,()->userService.addUser(user));
		assertThrows(NoPrivilageException.class,()->userService.updateUser(user));
		
		//Not logged in
		UserCheck.isLoggedIn = false;
		assertThrows(Exception.class,()->userService.addUser(user));
		assertThrows(Exception.class,()->userService.updateUser(user));
	}
	
	@Test
	void noUser(){
		UserCheck.isLoggedIn = true;
		UserCheck.role = "ADMIN";
		UserCheck.userId = 5;
		
		//user does not exist in repository
		Mockito.when(userRepo.findById(any(Integer.class))).thenReturn(null);
		assertThrows(Exception.class, ()->userService.getUser(0));
		assertThrows(Exception.class, ()->userService.removeUser(u.getId()));
		
		//Log in user is not ADMIN and deleting another user
		UserCheck.role = "USERS";
		Mockito.when(userRepo.findById(u.getId())).thenReturn(Optional.of(u));
		assertThrows(NoPrivilageException.class, ()->userService.getUser(0));
		assertThrows(NoPrivilageException.class, ()->userService.removeUser(0));
		
		//User is not logged in
		UserCheck.isLoggedIn = false;
		assertThrows(Exception.class, ()->userService.getUser(0));
		assertThrows(Exception.class, ()->userService.removeUser(0));
	}
	
	@Test
	void logInAndLogOut() {
		UserCheck.isLoggedIn = true;
		
		//user is logging in while another user is logged in
		assertThrows(Exception.class, ()->userService.logIn(u));
		
		UserCheck.isLoggedIn = false;
		
		//no user is logged in trying to log out
		assertThrows(Exception.class,()->userService.logIn(u));
	}
	
	@Test
	void getAllUsers() {
		
		UserCheck.isLoggedIn = false;
		//no user is logged in
		assertThrows(Exception.class,()->userService.getAllUsers());
		
		UserCheck.isLoggedIn = true;
		UserCheck.role = "USERS";
		//user logged in but role is USERS
		assertThrows(NoPrivilageException.class,()->userService.getAllUsers());
	}
}
