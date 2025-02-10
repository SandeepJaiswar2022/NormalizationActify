package com.actify.NormalizationDemo.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.actify.NormalizationDemo.Request.LoginRequest;
import com.actify.NormalizationDemo.Request.RegisterRequest;
import com.actify.NormalizationDemo.Response.ApiResponse;
import com.actify.NormalizationDemo.Response.AuthResponse;
import com.actify.NormalizationDemo.Service.Security.IAuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {
private final IAuthService authService;
	
	//Only this End Point's Exceptions are getting handled by the GlobalExceptionHandler
	
	@PostMapping("/register")
	public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterRequest request)
	{
		AuthResponse authResponse = authService.register(request);
			
		return new ResponseEntity<>(new ApiResponse("Registered Successfully",authResponse)
					,HttpStatus.OK);
	}
	
	
	@PostMapping("/login")
    public ResponseEntity<ApiResponse> authenticate(@RequestBody LoginRequest request) {
		
		try {
			AuthResponse authResponse = authService.login(request);
			return new ResponseEntity<>(new ApiResponse("Logged In Successfully",authResponse)
					,HttpStatus.OK);	
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(e.getMessage(),null)
					,HttpStatus.BAD_REQUEST);
		}
		
    }

}
