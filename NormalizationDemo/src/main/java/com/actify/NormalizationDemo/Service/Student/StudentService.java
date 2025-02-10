package com.actify.NormalizationDemo.Service.Student;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.actify.NormalizationDemo.Exception.InvalidInputException;
import com.actify.NormalizationDemo.Exception.ResourceNotFoundException;
import com.actify.NormalizationDemo.Model.Course;
import com.actify.NormalizationDemo.Model.Enrollment;
import com.actify.NormalizationDemo.Model.Student;
import com.actify.NormalizationDemo.Model.User;
import com.actify.NormalizationDemo.Repository.CourseRepository;
import com.actify.NormalizationDemo.Repository.EnrollmentRepository;
import com.actify.NormalizationDemo.Repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService implements IStudentService {
	private final EnrollmentRepository enrollmentRepo;
	private final StudentRepository studentRepo;
	private final CourseRepository courseRepo;

	@Override
	public Enrollment enrollInCourse(User user, Long courseId) {
		Optional<Student> studentOptional = studentRepo.findByUserId(user.getId());
		if(studentOptional.isPresent())
		{
			Optional<Course> courseOptional = courseRepo.findById(courseId);
			
			if(courseOptional.isPresent())
			{
				Course course = courseOptional.get();
				
				if (enrollmentRepo.findByStudentAndCourse(studentOptional.get(), course).isPresent()) {
			        throw new InvalidInputException("Student is already enrolled in this course.");
			    }
				
				Enrollment enrollment = new Enrollment();
				enrollment.setStudent(studentOptional.get());
				enrollment.setCourse(course);
				return enrollmentRepo.save(enrollment);
			}
			else {
				throw new ResourceNotFoundException("Course not found with id :"+courseId);
			}
		}
		else {
			throw new ResourceNotFoundException("Student not found with id : "+user.getId());
		}
	}

	@Override
	public List<Enrollment> studentAllEnrolledCourses(User user) {
		
		Optional<Student> studentOptional = studentRepo.findByUserId(user.getId());
		
		if(studentOptional.isPresent())
		{
			Long studentId = studentOptional.get().getId();
			List<Enrollment> enrollments = enrollmentRepo.findbyStudentId(studentId);
			if (enrollments.isEmpty()) {
				throw new ResourceNotFoundException("No Course Enrollment found for student with id : "+studentId);
			}
			return enrollments;
		}
		else {
			throw new ResourceNotFoundException("Student not found with id : "+user.getId());
		}
		
	}

}
