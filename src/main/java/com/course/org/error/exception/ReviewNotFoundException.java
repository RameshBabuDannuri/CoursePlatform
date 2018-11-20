package com.course.org.error.exception;

public class ReviewNotFoundException extends RuntimeException {
    private static final Long serialVersionUID = 1L;

    public ReviewNotFoundException(){

    }

    public ReviewNotFoundException(String s) {
        super(s);
    }
    public ReviewNotFoundException(String s , Throwable cause){
        super(s,cause);
    }
}
