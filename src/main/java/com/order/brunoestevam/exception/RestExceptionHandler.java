package com.order.brunoestevam.exception;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private String generateUniqueId() {
        return UUID.randomUUID().toString();
    }

    private ResponseEntity<Object> buildResponseEntity(Throwable t, HttpStatus status) {
        String uid = generateUniqueId();

        String errorType = t.getClass().getSimpleName();

        Error apiError = new Error(status.value(), uid, t.getMessage(), errorType, new Date());

        return new ResponseEntity<>(apiError, status);
    }
    
    @ExceptionHandler(ListenerExecutionFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleValidationException(ListenerExecutionFailedException ex) {
    	buildResponseEntity(ex, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleValidationException(NoSuchElementException ex) {
    	buildResponseEntity(ex, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleConstraintViolation(ConstraintViolationException ex) {
    	buildResponseEntity(ex, HttpStatus.NOT_FOUND);
    }
    
	@ExceptionHandler({ InvalidDataException.class })
	protected ResponseEntity<Object> handleException(InvalidDataException ex, WebRequest request) {
		return buildResponseEntity(ex, HttpStatus.BAD_REQUEST);
	}

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
    		HttpStatusCode statusCode, WebRequest request) {
    	 return buildResponseEntity(ex, HttpStatus.valueOf(statusCode.value()));
    }
}

