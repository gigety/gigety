package com.gigety.web.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
@AllArgsConstructor
public class ResourceNotFoundException extends RuntimeException {


	private static final long serialVersionUID = 8882270805167553546L;
	private final String resourceName;
	private final String fieldName;
	private final Object fieldValue;

	
	
}
