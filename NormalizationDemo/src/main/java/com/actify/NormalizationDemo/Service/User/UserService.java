package com.actify.NormalizationDemo.Service.User;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.actify.NormalizationDemo.Exception.InvalidInputException;
import com.actify.NormalizationDemo.Exception.ResourceNotFoundException;
import com.actify.NormalizationDemo.Model.Student;
import com.actify.NormalizationDemo.Model.Teacher;
import com.actify.NormalizationDemo.Model.User;
import com.actify.NormalizationDemo.Repository.StudentRepository;
import com.actify.NormalizationDemo.Repository.TeacherRepository;
import com.actify.NormalizationDemo.Repository.UserRepo;
import com.actify.NormalizationDemo.Response.UserDto;
import com.actify.NormalizationDemo.Service.Security.IJwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

	private final UserRepo userRepo;
	private final IJwtService jwtService;
	private final StudentRepository studentRepo;
	private final TeacherRepository teacherRepo;
	
	@Override
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	@Override
	public void changeUserRole(String email, String role) {
		
		if(role.equals("ADMIN"))
		{
			throw new InvalidInputException("Role Can't be set to ADMIN");
		}
		
		if(!role.equals("STUDENT") && !role.equals("TEACHER"))
		{
			throw new InvalidInputException("Role should be from set [TEACHER, USER, STUDENT]");
		}
		
		Optional<User> userOpt = userRepo.findByEmail(email);
		
		if(userOpt.isPresent())
		{
			User user = userOpt.get();
//			user = userRepo.save(user);
			System.out.println(user);
			userRepo.updateUserRole(email, role);
			if(role.equals("STUDENT"))
			{
				Student student = new Student();
				student.setUser(user);;
				studentRepo.save(student);
			}
			else if(role.equals("TEACHER"))
			{
				Teacher teacher = new Teacher();
				teacher.setUser(user);
				teacherRepo.save(teacher);
			}
		}
		else {
			throw new ResourceNotFoundException("User not found with email : "+email);
		}
		
	}
	
	@Override
	public User getUserByEmail(String email)
	{
	   return userRepo.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User not Found with Email : "+email));
	}
	
	@Override
	public User findUserByJwtToken(String authHeader) {
        String jwtToken = authHeader.substring(7);
        String email = jwtService.extractUsername(jwtToken);
        Optional<User> user = userRepo.findByEmail(email);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User not found with email"+email);
        }
        return user.get();
    }

}
