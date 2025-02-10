package com.actify.NormalizationDemo.Service.Security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.actify.NormalizationDemo.Enum.Role;
import com.actify.NormalizationDemo.Exception.AlreadyExistException;
import com.actify.NormalizationDemo.Exception.ResourceNotFoundException;
import com.actify.NormalizationDemo.Model.User;
import com.actify.NormalizationDemo.Repository.UserRepo;
import com.actify.NormalizationDemo.Request.LoginRequest;
import com.actify.NormalizationDemo.Request.RegisterRequest;
import com.actify.NormalizationDemo.Response.AuthResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
	private final UserRepo userRepo;
	private final IJwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authManager;
	
	@Override
	public AuthResponse register(RegisterRequest request) {
		if(userRepo.findByEmail(request.getEmail()).isPresent())
		{
			throw new AlreadyExistException("User already Exists with Email : "+request.getEmail());
		}
		
		User user = new User();
		user.setEmail(request.getEmail());
		user.setFullName(request.getFullName());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(Role.USER);
		
		userRepo.save(user);
		
		String token = jwtService.generateToken(user);
		
		
		return new AuthResponse(token);
	}

	@Override
	public AuthResponse login(LoginRequest request) {
		authManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
				);
		User user = userRepo.findByEmail(request.getEmail())
				.orElseThrow(()-> new ResourceNotFoundException("User not Found"));
		
		String token = jwtService.generateToken(user);
		
		return new AuthResponse(token);
	}
	
}
