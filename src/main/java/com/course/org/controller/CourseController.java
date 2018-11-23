package com.course.org.controller;

import com.course.org.entity.Course;
import com.course.org.entity.Review;
import com.course.org.entity.Student;
import com.course.org.error.exception.ResourseNotCreateException;
import com.course.org.error.exception.ResourseNotFoundException;
import com.course.org.service.CourseService;
import com.course.org.service.MapValidationErrorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CourseService courseService;
    @Autowired
    MapValidationErrorService mapValidationErrorService;


    @GetMapping("/all")
    public ResponseEntity<List<Course>> findAllCourses(){
        logger.info("find all course excuted");
       List<Course> courses =  courseService.findAll();
       if(courses.isEmpty()){
           throw new ResourseNotFoundException("Courses are not available now");
       }
       return  new ResponseEntity<>(courses , HttpStatus.OK);
    }

    @GetMapping("/course_id/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long courseId) {
        logger.info("get course by id method start");
        Course course = courseService.getCourseById(courseId);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @GetMapping("/course_name/{courseName}")
    public ResponseEntity<List<Course>> getCourseByName(@PathVariable String courseName){
        List<Course> courses = courseService.getCourseByName(courseName.toUpperCase());
        return new ResponseEntity<>(courses , HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createNewCourse(@Valid @RequestBody  Course course , BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap!=null){
            return errorMap;
        }
        Course course1 = courseService.saveOrUpdate(course);
        return new ResponseEntity<>(course1 , HttpStatus.CREATED);
    }
    @DeleteMapping("/{courseId}")
    ResponseEntity<Course> deleteCourse(@PathVariable Long courseId){
        courseService.deleteCourse(courseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/highestRating")
    ResponseEntity<List<Course>> getHighestRatedCourses(){
        List<Course> courses =  courseService.getHightestRatedCourses();
        return new ResponseEntity<>(courses , HttpStatus.OK);
    }

    @GetMapping("/{courseId}/review")
    public ResponseEntity<List<Review>> getCourseReviews(@PathVariable Long courseId){
        Course course = courseService.getCourseById(courseId);
        List<Review> reviews = course.getReviews();
        return new ResponseEntity<>(reviews,HttpStatus.OK);
    }

    @GetMapping("/{courseId}/review/{reviewId}")
    public ResponseEntity<Review> getCourseReviewById(@PathVariable Long courseId,@PathVariable Long reviewId){
        Review review = courseService.getCourseReviewById(courseId , reviewId);
        return new ResponseEntity<>(review,HttpStatus.OK);
    }

    @PostMapping("/{courseId}/review")
    public ResponseEntity<?> addReviewToCourse(@PathVariable Long courseId,
                                               @Valid @RequestBody Review review,BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap!=null){
            return errorMap;
        }
        Course course = courseService.addReviewToCourse(courseId , review);
        return new ResponseEntity<>(course , HttpStatus.OK);
    }

    @GetMapping("/{courseId}/student")
    public ResponseEntity<List<Student>> getCourseStudents(@PathVariable Long courseId){
        List<Student> students = courseService.getCourseStudents(courseId);
        return new ResponseEntity<>(students , HttpStatus.OK);
    }

    @PostMapping("/{courseId}/student")
    public ResponseEntity<?> addCourseToStudent( @PathVariable Long courseId,
                                                 @Valid @RequestBody Student student,BindingResult result) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap!=null){
            return errorMap;
        }
        Student student1 = courseService.addStudentToCourse(courseId  , student);
        return new ResponseEntity<>(student1,HttpStatus.CREATED);
    }
}


