package com.thainh.taskmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TaskRequestException extends RuntimeException {
    public TaskRequestException(String message, String field, String value) {
        super(String.format(message, field, value));
    }
}
