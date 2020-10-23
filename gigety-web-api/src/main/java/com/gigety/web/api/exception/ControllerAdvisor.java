package com.gigety.web.api.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//@ControllerAdvice
@RestControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

	@ExceptionHandler(GigetyException.class)
	public ResponseEntity<Object> handleUnknownException(GigetyException ge, WebRequest wr){
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("error", ge.getMessage());
		body.put("message", "An unexpected error occurred on server whil processing your request ... ");
		return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ge, WebRequest wr){
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("error", ge.getMessage());
		body.put("message", "An unexpected error occurred on server whil processing your request ... ");
		return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
