package com.course.org.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CourseNotCreateException extends RuntimeException {
    private static final Long serialVersionUID = 2L;

    public CourseNotCreateException(){

    }

    public CourseNotCreateException(String s) {
        super(s);
    }
    public CourseNotCreateException(String s , Throwable cause){
        super(s,cause);
    }
}
