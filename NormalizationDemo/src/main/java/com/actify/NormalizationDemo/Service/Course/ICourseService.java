package com.actify.NormalizationDemo.Service.Course;

import com.actify.NormalizationDemo.Model.Course;

public interface ICourseService {
	Course createCourse(String teacherEmail, String courseName);
}
