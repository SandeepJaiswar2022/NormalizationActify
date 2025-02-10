package com.actify.NormalizationDemo.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.actify.NormalizationDemo.Model.Course;
import com.actify.NormalizationDemo.Request.ChangeRoleRequest;
import com.actify.NormalizationDemo.Request.CreateCourseRequest;
import com.actify.NormalizationDemo.Response.ApiResponse;
import com.actify.NormalizationDemo.Service.Course.ICourseService;
import com.actify.NormalizationDemo.Service.User.IUserService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
	private final IUserService userService;
	private final ICourseService courseService;
	
	//1.ADMIN : Change role 
	//2.ADMIN : Create Courses
	
	@PutMapping("/change-role")
	@PreAuthorize("hasAuthority('admin:update')")
	public ResponseEntity<ApiResponse> changeUserRole(@RequestBody ChangeRoleRequest reqest)
	{
		try {
			userService.changeUserRole(reqest.getEmail(),reqest.getRole());
			return new ResponseEntity<>(new ApiResponse("Role Changed Successfully", null)
					,HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(e.getMessage(), null)
					,HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/create-course")
	@PreAuthorize("hasAuthority('admin:create')")
	public ResponseEntity<ApiResponse> createNewCourse(@RequestBody CreateCourseRequest request)
	{
		try {
			Course course = courseService.createCourse(request.getTeacherEmail(), request.getCourseName());
			return new ResponseEntity<>(new ApiResponse("Course Created Successfully", course)
					,HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(e.getMessage(), null)
					,HttpStatus.BAD_REQUEST);
		}
	}
	
}
