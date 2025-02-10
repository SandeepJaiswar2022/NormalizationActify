package com.actify.NormalizationDemo.Service.Security;

import com.actify.NormalizationDemo.Request.LoginRequest;
import com.actify.NormalizationDemo.Request.RegisterRequest;
import com.actify.NormalizationDemo.Response.AuthResponse;

public interface IAuthService {
	AuthResponse register(RegisterRequest request);
	AuthResponse login(LoginRequest request);
}
