package com.course.org.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourseExistException extends RuntimeException {
    public ResourseExistException() {
    }
    public ResourseExistException(String s) {
        super(s);
    }
}
