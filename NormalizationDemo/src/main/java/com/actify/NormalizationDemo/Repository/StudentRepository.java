package com.actify.NormalizationDemo.Repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.actify.NormalizationDemo.Model.Student;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	Optional<Student> findByUserId(Long appUserId);
}
	