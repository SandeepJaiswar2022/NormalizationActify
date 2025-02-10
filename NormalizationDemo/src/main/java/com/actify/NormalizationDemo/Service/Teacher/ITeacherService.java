package com.actify.NormalizationDemo.Service.Teacher;

import java.util.List;

import com.actify.NormalizationDemo.Model.Course;
import com.actify.NormalizationDemo.Model.User;

public interface ITeacherService {
	List<Course> getAllCoursesTaughtByTeacher(User user);
}
