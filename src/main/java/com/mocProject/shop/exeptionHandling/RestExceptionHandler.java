package com.mocProject.shop.exeptionHandling;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {



	@ExceptionHandler
	public ResponseEntity<entityNotFoundException>
	habdleException(entityNotFoundException exception){

		generalErrorResponse errorResponse = new generalErrorResponse();

		errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
		errorResponse.setMessage(exception.getMessage());
		errorResponse.setTimeStamp(System.currentTimeMillis());

		return new ResponseEntity(errorResponse,HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler
	public ResponseEntity<entityNotFoundException>
	habdleExceptions(Exception exception){

		generalErrorResponse errorResponse = new generalErrorResponse();

		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		errorResponse.setMessage(exception.getMessage());
		errorResponse.setTimeStamp(System.currentTimeMillis());

		return new ResponseEntity(errorResponse,HttpStatus.BAD_REQUEST);


	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		generalErrorResponse errorResponse = new generalErrorResponse();

		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		errorResponse.setMessage(ex.getFieldError().getDefaultMessage());
		errorResponse.setTimeStamp(System.currentTimeMillis());


		return new ResponseEntity(errorResponse,HttpStatus.BAD_REQUEST);

	}

}
