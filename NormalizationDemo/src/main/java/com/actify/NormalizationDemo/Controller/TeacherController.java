package com.actify.NormalizationDemo.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.actify.NormalizationDemo.Model.Course;
import com.actify.NormalizationDemo.Model.User;
import com.actify.NormalizationDemo.Response.ApiResponse;
import com.actify.NormalizationDemo.Service.Teacher.ITeacherService;
import com.actify.NormalizationDemo.Service.User.IUserService;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/teacher")
@PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
public class TeacherController {
	private final IUserService userService;
	private final ITeacherService teacherService;
	
	//TEACHER : Can get all courses taught by the teacher
	
	@GetMapping("/get-all-courses")
	@PreAuthorize("hasAnyAuthority('admin:read','teacher:read')")
	public ResponseEntity<ApiResponse> teacherRead(@RequestHeader("Authorization") String authHeader)
	{
		try {
			User user = userService.findUserByJwtToken(authHeader);
			List<Course> courses = teacherService.getAllCoursesTaughtByTeacher(user);
			return new ResponseEntity<>(new ApiResponse("Teacher : Courses fetched Successfully", courses), HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(e.getMessage(), null), HttpStatus.NOT_FOUND);
		}
	}
	
}
