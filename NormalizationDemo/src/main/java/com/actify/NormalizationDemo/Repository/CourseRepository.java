package com.actify.NormalizationDemo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.actify.NormalizationDemo.Model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
	List<Course> findByTeacherId(Long teacherId);
}
