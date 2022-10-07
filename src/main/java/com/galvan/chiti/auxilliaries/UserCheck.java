package com.galvan.chiti.auxilliaries;

public class UserCheck {

	public static boolean isLoggedIn;
	public static String role;
	public static int userId;
	public static int candidateId;
	
	public static boolean checkPrivilage() throws Exception{
		if(!isLoggedIn) {
			throw new Exception("User not logged In!!!!");
			
		}else if(isLoggedIn && role.equals("ADMIN")) {
			return true;
		}else {
			throw new NoPrivilageException("User doesn't have privilage to perform the action");
		}
	}
}
