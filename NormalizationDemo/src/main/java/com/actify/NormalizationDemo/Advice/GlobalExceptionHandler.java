package com.actify.NormalizationDemo.Advice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.actify.NormalizationDemo.Exception.AlreadyExistException;
import com.actify.NormalizationDemo.Response.ApiResponse;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleInvalidInput( MethodArgumentNotValidException ex)
	{
		Map<String, String> errors = new HashMap<>();
		
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			errors.put(error.getField(), error.getDefaultMessage());
		});
		
		return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST); 
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = ex.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(), 
                        ConstraintViolation::getMessage
                ));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(AlreadyExistException.class)
	public ResponseEntity<ApiResponse> handleAlreadyExistException(AlreadyExistException ex) {
        return new ResponseEntity<>(new ApiResponse(ex.getMessage(), null), HttpStatus.CONFLICT);
    }


}
