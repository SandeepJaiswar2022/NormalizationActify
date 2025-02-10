package com.actify.NormalizationDemo.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.actify.NormalizationDemo.Model.Enrollment;
import com.actify.NormalizationDemo.Model.User;
import com.actify.NormalizationDemo.Response.ApiResponse;
import com.actify.NormalizationDemo.Service.Student.IStudentService;
import com.actify.NormalizationDemo.Service.User.IUserService;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/student")
@PreAuthorize("hasRole('USER')")
public class StudentController {
	private final IUserService userService;
	private final IStudentService studentService;
	
	//STUDENT : Enroll In the Course
	//STUDENT : Get All Enrolled Course
	
	@PostMapping("enroll-in-course")
	@PreAuthorize("hasAnyAuthority('admin:create','student:create','teacher:create')")
	public ResponseEntity<ApiResponse> enrollInCourse(@RequestHeader("Authorization") String authHeader, @RequestParam Long courseId)
	{
		try {
			 User user =  userService.findUserByJwtToken(authHeader);
			 Enrollment enrollmentData =  studentService.enrollInCourse(user, courseId);
		     return new ResponseEntity<>(new ApiResponse("Student Enrolled Successfully",
		    		 enrollmentData)
				,HttpStatus.OK);
		
	      } catch (Exception e) {
		    return new ResponseEntity<>(new ApiResponse(e.getMessage(), null)
				,HttpStatus.NOT_FOUND);
	     }
	}
	
	@GetMapping("/all-enrolled-courses")
	@PreAuthorize("hasAnyAuthority('admin:read','student:read','teacher:read')")
	public ResponseEntity<ApiResponse> getAllStudentCourses(@RequestHeader("Authorization") String authHeader)
	{
		try {
			 User user =  userService.findUserByJwtToken(authHeader);
			 List<?> enrolledCoursesList = studentService.studentAllEnrolledCourses(user);
		     return new ResponseEntity<>(new ApiResponse("Student Enrolled Courses"
		     		+ " Fetched Successfully",
		    		 enrolledCoursesList)
				,HttpStatus.OK);
		
	      } catch (Exception e) {
		    return new ResponseEntity<>(new ApiResponse(e.getMessage(), null)
				,HttpStatus.NOT_FOUND);
	     }
	}

}
