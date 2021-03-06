package com.course.org.error.handler;

import com.course.org.error.ErrorDetail;
import com.course.org.error.exception.ResourseExistException;
import com.course.org.error.exception.ResourseNotCreateException;
import com.course.org.error.exception.ResourseNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourseNotFoundException.class)
    public ResponseEntity<?> handleResourseNotFoundException(ResourseNotFoundException rnfe , HttpServletRequest request){
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetail.setTitle("Resource Not Found");
        errorDetail.setDetail(rnfe.getMessage());
        errorDetail.setDeveloperMessage(rnfe.getClass().getName());

        return  new ResponseEntity<>(errorDetail , null , HttpStatus.NOT_FOUND);
    }



    @ExceptionHandler(ResourseExistException.class)
    public ResponseEntity<?> handleResourseNotFoundException(ResourseExistException rnfe , HttpServletRequest request){
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDetail.setTitle("Resource Not Found");
        errorDetail.setDetail(rnfe.getMessage());
        errorDetail.setDeveloperMessage(rnfe.getClass().getName());

        return  new ResponseEntity<>(errorDetail , null , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourseNotCreateException.class)
    public ResponseEntity<?> handleResourseNotFoundException(ResourseNotCreateException rnfe , HttpServletRequest request){
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDetail.setTitle("Resource Not Found");
        errorDetail.setDetail(rnfe.getMessage());
        errorDetail.setDeveloperMessage(rnfe.getClass().getName());

        return  new ResponseEntity<>(errorDetail , null , HttpStatus.BAD_REQUEST);
    }
}
