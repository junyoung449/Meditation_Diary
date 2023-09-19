package com.project.mt.exception;

import lombok.Builder;

@Builder
public class ResultTemplate {
    private int status;
    private String data;
}
