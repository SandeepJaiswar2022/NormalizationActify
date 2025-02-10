package com.actify.NormalizationDemo.Exception;

public class ResourceNotFoundException extends RuntimeException{
	public ResourceNotFoundException(String message)
	{
		super(message);
	}

}
