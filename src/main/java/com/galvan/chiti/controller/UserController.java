package com.galvan.chiti.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.galvan.chiti.auxilliaries.NoPrivilageException;
import com.galvan.chiti.auxilliaries.UserResponse;
import com.galvan.chiti.entity.User;
import com.galvan.chiti.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/")
public class UserController {
	@Autowired
	UserService userService;
	
	@PostMapping("/user")
	public ResponseEntity<User> addUser(@Valid @RequestBody User user) throws NoPrivilageException, Exception{
		User userNew = userService.addUser(user);
		return new ResponseEntity<User>(userNew, HttpStatus.OK);
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUser(@PathVariable("id") Integer id) throws NoPrivilageException,Exception{
		User userNew = userService.getUser(id);
		return new ResponseEntity<User>(userNew, HttpStatus.OK);
	}
	
	@PutMapping("/user")
	public ResponseEntity<UserResponse> updateUser(@Valid @RequestBody User user) throws NoPrivilageException,Exception{
		UserResponse userNew = userService.updateUser(user);
		return new ResponseEntity<UserResponse>(userNew, HttpStatus.OK);
	}
	
	@DeleteMapping("/user/{id}")
	public ResponseEntity<UserResponse> deleteUser(@PathVariable("id") Integer id) throws NoPrivilageException,Exception{
		UserResponse userNew = userService.removeUser(id);
		return new ResponseEntity<UserResponse>(userNew, HttpStatus.OK);
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers() throws NoPrivilageException,Exception{
		List<User> users = userService.getAllUsers();
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	@PostMapping("/logIn")
	public ResponseEntity<UserResponse> logInUser(@Valid @RequestBody User user) throws Exception{
		UserResponse userNew = userService.logIn(user);
		return new ResponseEntity<UserResponse>(userNew, HttpStatus.OK);
	}
	
	@GetMapping("/logOut")
	public ResponseEntity<UserResponse> logOutUser() throws Exception{
		UserResponse userNew = userService.logOut();
		return new ResponseEntity<UserResponse>(userNew, HttpStatus.OK);
	}
	
	@GetMapping("/loginStatus")
	public ResponseEntity<Boolean> isLogin(){
		boolean isLoggedIn = userService.isLoggedIn();
		return new ResponseEntity<Boolean>(isLoggedIn, HttpStatus.OK);
	}
}
