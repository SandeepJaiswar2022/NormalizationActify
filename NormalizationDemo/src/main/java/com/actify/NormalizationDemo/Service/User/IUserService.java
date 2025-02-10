package com.actify.NormalizationDemo.Service.User;

import java.util.List;

import com.actify.NormalizationDemo.Model.User;
import com.actify.NormalizationDemo.Response.UserDto;


public interface IUserService {
	List<User> getAllUsers();
	
	void changeUserRole(String email, String role);
	
	
	User getUserByEmail(String email);

	User findUserByJwtToken(String authHeader);
	
}
