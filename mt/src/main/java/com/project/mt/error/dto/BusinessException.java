package com.project.mt.error.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BusinessException extends RuntimeException {

    private ErrorCode errorCode;
    private String message;

}
