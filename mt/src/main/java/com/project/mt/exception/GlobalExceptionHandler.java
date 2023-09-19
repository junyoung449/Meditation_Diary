package com.project.mt.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// @Slf4j
// @RestControllerAdvice
// public class GlobalExceptionHandler {
//
// 	@ExceptionHandler( {NotFoundException.class, DuplicateException.class, NotMatchException.class, NoSuchElementException.class} )
// 	public ResultTemplate handleBadRequestExceptions(Exception e){
// 		return ResultTemplate.builder().status(HttpStatus.BAD_REQUEST.value()).data(e.getMessage()).build();
// 	}
//
// 	@ExceptionHandler(MethodArgumentNotValidException.class)
// 	public Object handleMethodArgumentNotValidException(Exception e) {
//
// 		return ResultTemplate.builder().status(HttpStatus.BAD_REQUEST.value()).data(e.getMessage()).build();
// 	}
//
//
// 	@ExceptionHandler( {InvalidAccessTokenException.class} )
// 	public ResultTemplate invalidAccessTokenException(Exception e){
//
// 		return ResultTemplate.builder().status(HttpStatus.FORBIDDEN.value()).data(e.getMessage()).build();
// 	}
//
// 	@ExceptionHandler( {InvalidRefreshTokenException.class} )
// 	public ResultTemplate invalidRefreshTokenException(Exception e){
//
// 		return ResultTemplate.builder().status(HttpStatus.FORBIDDEN.value()).data(e.getMessage()).build();
// 	}
// }
