package com.course.org.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourseNotCreateException extends RuntimeException {
    private static final Long serialVersionUID = 2L;

    public ResourseNotCreateException(){

    }

    public ResourseNotCreateException(String s) {
        super(s);
    }
    public ResourseNotCreateException(String s , Throwable cause){
        super(s,cause);
    }
}
