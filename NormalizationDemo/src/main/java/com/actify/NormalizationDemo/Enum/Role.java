package com.actify.NormalizationDemo.Enum;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.actify.NormalizationDemo.Enum.Permission.*;

@Getter
@RequiredArgsConstructor
public enum Role {
	STUDENT(
			Set.of(
					STUDENT_CREATE,
                    STUDENT_DELETE,
                    STUDENT_UPDATE,
                    STUDENT_READ
                   )
			
    ),
    TEACHER(
        Set.of(
                TEACHER_READ,
                TEACHER_UPDATE,
                TEACHER_DELETE,
                TEACHER_CREATE
              )
    ),

    USER(
    		Set.of(USER_READ)
    	),
    ADMIN(
            Set.of(
                    ADMIN_DELETE,
                    ADMIN_READ,
                    ADMIN_CREATE,
                    ADMIN_UPDATE,

                    STUDENT_CREATE,
                    STUDENT_DELETE,
                    STUDENT_UPDATE,
                    STUDENT_READ,
                    
                    TEACHER_READ,
                    TEACHER_UPDATE,
                    TEACHER_DELETE,
                    TEACHER_CREATE
                    
            )
    );
	
	private final Set<Permission> permissions;
	
	public List<SimpleGrantedAuthority> getGrantedAuthorities()
	{
		List<SimpleGrantedAuthority> authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
	}
}
