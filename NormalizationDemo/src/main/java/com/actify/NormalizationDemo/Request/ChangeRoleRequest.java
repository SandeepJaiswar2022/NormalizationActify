package com.actify.NormalizationDemo.Request;

import lombok.Data;

@Data
public class ChangeRoleRequest {
	private String email;
	private String role;

}
