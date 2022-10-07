package com.galvan.chiti.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galvan.chiti.auxilliaries.NoPrivilageException;
import com.galvan.chiti.auxilliaries.UserCheck;
import com.galvan.chiti.auxilliaries.UserResponse;
import com.galvan.chiti.entity.Candidate;
import com.galvan.chiti.entity.User;
import com.galvan.chiti.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepo;
	@Autowired
	CandidateService candidateService;	

	

	@Override
	public User getUser(int id) throws NoPrivilageException,Exception {
		if(id == UserCheck.userId || UserCheck.checkPrivilage()) {
			Optional<User> user = userRepo.findById(id);
			if(user == null) {
				throw new Exception("user doesnt exist");
			}
			return user.get();
		}
		throw new NoPrivilageException();
	}

	@Override
	public UserResponse logIn(User existingUser) throws Exception {
		if(UserCheck.isLoggedIn) {
			throw new Exception("A user is already logged in ");
		}
		Optional<User> user = userRepo.findByUname(existingUser.getUname());
		if(user.get() == null) {
			throw new Exception("Username not found!!!");
		}
		if(user.get().getPassword().equals(existingUser.getPassword())) {
			return new UserResponse(true, "Login successful", user.get());
		}
		return new UserResponse(false, "Incorrect password!!!", null );
	}

	@Override
	public UserResponse removeUser(int id) throws NoPrivilageException,Exception {
		User user = getUser(id);
		
		if(UserCheck.isLoggedIn) {
			if(id>0) {
				if(UserCheck.role.equals("ADMIN") && UserCheck.userId != id) {
					UserResponse response = new UserResponse();
					userRepo.deleteById(id);
					response.setMessage("User deleted successfully");
					response.setUser(user);
					response.setLoggedIn(true);
					return response;
				}else if(UserCheck.userId == id) {
					userRepo.deleteById(id);
					return new UserResponse(false,"User deleted successfully", user);
				}else {
					throw new NoPrivilageException();
				}
			}
			throw new NoPrivilageException();
		}
		throw new Exception("User not logged in!!!");
	}

	@Override
	public List<User> getAllUsers() throws NoPrivilageException, Exception {
		UserCheck.checkPrivilage();
		List<User> users = userRepo.findAll();
		return users;		
	}

	@Override
	public UserResponse logOut() throws Exception {
		if(!UserCheck.isLoggedIn) {
			throw new Exception("User is not logged in");
		}
		return new UserResponse(false,"Logout successful", null);
	}

	@Override
	public UserResponse updateUser(User existingUser) throws NoPrivilageException,Exception {
		User oldUser = userRepo.findById(existingUser.getId()).get();
		if(oldUser == null) {
			throw new Exception("User does not exist");
		}
		if(UserCheck.isLoggedIn && (UserCheck.userId == existingUser.getId() || (UserCheck.role.equals("ADMIN") && oldUser.getRole().toString().equals("USERS")))) {
			User user = getUser(existingUser.getId());
			user.setUname(existingUser.getUname());
			user.setPassword(existingUser.getPassword());
			if(UserCheck.role.equals("ADMIN")) {
				user.setRole(existingUser.getRole());
			}
			candidateService.updateCandidate(existingUser.getCandidateId());
			userRepo.saveAndFlush(user);
			return new UserResponse("Password updated successfully",user);
		}else if(!UserCheck.isLoggedIn) {
			throw new Exception("User not logged in!!!");
		}else {
			throw new NoPrivilageException();
		}
	}
	
	@Override
	public List<String> getAllUnames() throws NoPrivilageException, Exception{
		List<User> users = getAllUsers();
		List<String> unames = users.stream().map((user)-> user.getUname()).collect(Collectors.toList());
		return unames;
	}
	
	@Override
	public User addUser(User user) throws NoPrivilageException,Exception {
		UserCheck.checkPrivilage();
		List<String> unames = getAllUnames();
		if(unames.contains(user.getUname())) {
			throw new Exception("User name already exists");
		}
//		Candidate candidate = candidateService.findCandidateById(user.getCandidateId().getId());
//		user.setCandidateId(candidate);
		Candidate candidate = candidateService.addCandidate(user.getCandidateId());
		user.setCandidateId(candidate);
		userRepo.saveAndFlush(user);
		return user;
	}
	
	@Override
	public boolean isLoggedIn() {
		return UserCheck.isLoggedIn;
	}

}
