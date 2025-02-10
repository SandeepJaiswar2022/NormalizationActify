package com.actify.NormalizationDemo.Service.Teacher;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.actify.NormalizationDemo.Exception.ResourceNotFoundException;
import com.actify.NormalizationDemo.Model.Course;
import com.actify.NormalizationDemo.Model.Teacher;
import com.actify.NormalizationDemo.Model.User;
import com.actify.NormalizationDemo.Repository.CourseRepository;
import com.actify.NormalizationDemo.Repository.TeacherRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeacherService implements ITeacherService {
	private final TeacherRepository teacherRepo;
	private final CourseRepository courseRepo;
	
	@Override
	public List<Course> getAllCoursesTaughtByTeacher(User user) {
		Optional<Teacher> teacherOptional = teacherRepo.findByUserEmail(user.getEmail());
		if (teacherOptional.isPresent()) {
			Long teacherId = teacherOptional.get().getId();
			List<Course> courses = courseRepo.findByTeacherId(teacherId);
			if (courses.isEmpty()) {
				throw new ResourceNotFoundException("No Courses found for teacher with id : "+teacherId);
			}
			return courses;
		}
		else {
			throw new ResourceNotFoundException("Teacher not found with id : "+user.getId());
		}
	}

}
