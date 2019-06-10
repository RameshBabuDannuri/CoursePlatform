package com.course.org.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourseNotFoundException extends RuntimeException {

    private static final Long serialVersionUID = 1L;

    public ResourseNotFoundException(){

    }

    public ResourseNotFoundException(String s) {
        super(s);
    }
    public ResourseNotFoundException(String s , Throwable cause){
        super(s,cause);
    }
}
