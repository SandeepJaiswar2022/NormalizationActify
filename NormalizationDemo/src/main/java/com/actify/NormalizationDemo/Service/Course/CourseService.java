package com.actify.NormalizationDemo.Service.Course;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.actify.NormalizationDemo.Exception.ResourceNotFoundException;
import com.actify.NormalizationDemo.Model.Course;
import com.actify.NormalizationDemo.Repository.CourseRepository;
import com.actify.NormalizationDemo.Repository.TeacherRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService implements ICourseService {
	private final TeacherRepository teacherRepo;
	private final CourseRepository courseRepo;

	@Override
	public Course createCourse(String teacherEmail, String courseName) {
		return teacherRepo.findByUserEmail(teacherEmail)
		.map((teacher) -> 
		{
			Course course = new Course();
			course.setCourseName(courseName);
			course.setTeacher(teacher);
			return courseRepo.save(course);
		}).orElseThrow(() -> new ResourceNotFoundException("Teacher not found with email : "+teacherEmail));
	}

}
