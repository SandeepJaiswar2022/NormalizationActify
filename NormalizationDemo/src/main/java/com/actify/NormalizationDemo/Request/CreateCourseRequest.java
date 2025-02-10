package com.actify.NormalizationDemo.Request;

import lombok.Data;

@Data
public class CreateCourseRequest {
	private String teacherEmail;
	private String courseName;
}
