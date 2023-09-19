package com.project.mt.exception;

import com.project.mt.exception.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NotFoundException.class, NoSuchElementException.class})
    public ResultTemplate handleBadRequestExceptions(Exception e) {
        return ResultTemplate.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .data(e.getMessage()).build();
    }
}