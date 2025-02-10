package com.actify.NormalizationDemo.Service.Student;

import java.util.List;

import com.actify.NormalizationDemo.Model.Enrollment;
import com.actify.NormalizationDemo.Model.User;

public interface IStudentService {
	Enrollment enrollInCourse(User user, Long courseId);
	List<?> studentAllEnrolledCourses(User user);
}
