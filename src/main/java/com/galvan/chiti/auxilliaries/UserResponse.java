package com.galvan.chiti.auxilliaries;

import com.galvan.chiti.entity.User;

public class UserResponse {
	
	private boolean isLoggedIn;
	private String message;
	private User user;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public boolean isLoggedIn() {
		return isLoggedIn;
	}
	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public UserResponse(boolean isLoggedIn, String message, User user) {
		super();
		this.isLoggedIn = isLoggedIn;
		this.message = message;
		this.user = user;
		if(isLoggedIn) {
			UserCheck.isLoggedIn = true;
			UserCheck.role = user.getRole().name();
			UserCheck.userId = user.getId();
			if(user.getId() > 0) {
				UserCheck.candidateId = user.getCandidateId().getId();
			}else {
				UserCheck.candidateId = -2;
			}
		}else {
			UserCheck.isLoggedIn = false;
			UserCheck.role = "";
			UserCheck.userId = -1;
			UserCheck.candidateId = -1;
			
		}
	}
	
	public UserResponse(String message, User user) {
		super();
		this.isLoggedIn = true;
		this.message = message;
		this.user = user;
	}
	public UserResponse() {
		super();
	}
	
	

}
