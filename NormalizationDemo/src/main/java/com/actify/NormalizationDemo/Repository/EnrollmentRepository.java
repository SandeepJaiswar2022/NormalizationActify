package com.actify.NormalizationDemo.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.actify.NormalizationDemo.Model.Course;
import com.actify.NormalizationDemo.Model.Enrollment;
import com.actify.NormalizationDemo.Model.Student;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
	
	@Query(value = "select * from enrollment where student_id = :studentId", nativeQuery=true)
	List<Enrollment> findbyStudentId(@Param("studentId") Long studentId);

	Optional<Enrollment> findByStudentAndCourse(Student student, Course course);
}
