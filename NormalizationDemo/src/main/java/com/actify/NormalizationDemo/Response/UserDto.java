package com.actify.NormalizationDemo.Response;



import com.actify.NormalizationDemo.Enum.Role;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
	
	private Long userId;
	
	private String fullName;
	
	private String email;
	
	private Role role;
}
