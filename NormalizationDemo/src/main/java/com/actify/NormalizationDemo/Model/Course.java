package com.actify.NormalizationDemo.Model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Course {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false, unique = true)
	    private String courseName;

	    @JsonIgnore
	    @ManyToOne
	    @JoinColumn(name = "teacher_id", nullable = false)
	    private Teacher teacher;

	    @JsonIgnore
	    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<Enrollment> enrollments = new ArrayList<>();

}
