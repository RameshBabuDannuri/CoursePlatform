package com.course.org.controller;

import com.course.org.entity.Course;
import com.course.org.entity.Review;
import com.course.org.entity.Student;
import com.course.org.error.exception.CourseNotCreateException;
import com.course.org.error.exception.ResourseNotFoundException;
import com.course.org.service.CourseService;
import com.course.org.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/api")
public class CourseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    CourseService courseService;
    @Autowired
    StudentService studentService;

    @GetMapping("/courses")
    ResponseEntity<List<Course>> findAllCourses(){
       List<Course> courses =  courseService.findAll();
       if(courses.isEmpty()){
           throw new ResourseNotFoundException("Courses are not available now");
       }
       return  new ResponseEntity<>(courses , HttpStatus.OK);
    }
    @GetMapping("/courseById/{courseId}")
    ResponseEntity<Course> findCourseById(@PathVariable Long courseId) {
        Course course = courseService.findById(courseId);

        if(course == null){
            throw new ResourseNotFoundException("Coure with id "+courseId+" not found");
        }
        return new ResponseEntity<Course>(course, HttpStatus.OK);

    }

    @GetMapping("/courseByName/{courseName}")
    ResponseEntity<?> findCourseByName(@PathVariable String courseName){
        Course course =   courseService.findByName(courseName);

        if (course == null){
            throw new ResourseNotFoundException("courses with name "+courseName+" not found");
        }
        return new ResponseEntity<>(course , HttpStatus.OK);
    }
    @GetMapping("/courses/{courseId}/reviews")
    ResponseEntity<List<Review>> getCourseReviews(@PathVariable Long courseId){
        Course course = courseService.findById(courseId);
        if (course == null){
            throw  new ResourseNotFoundException("Course with id "+courseId+" not found");
        }
        List<Review> reviews = course.getReviews();
        return new ResponseEntity<>(reviews,HttpStatus.OK);
    }

    @GetMapping("/courses/{courseId}/reviews/{reviewId}")
    ResponseEntity<Review> getCourseReviewById(@PathVariable Long courseId,@PathVariable Long reviewId){
        Course course = courseService.findById(courseId);
        if (course == null){
            throw  new ResourseNotFoundException("Course with id "+courseId+" not found");
        }
        List<Review> reviews =  course.getReviews();

        Review review = new Review();

        for (Review r : reviews) {
            if (r.getId() == reviewId){
                review = r;
                break;
            }
        }
        if(review == null){
            throw  new ResourseNotFoundException("review with id "+reviewId+" not found");
        }

        return new ResponseEntity<>(review,HttpStatus.OK);
    }
    @GetMapping("courses/{courseId}/students")
    ResponseEntity<List<Student>> getCourseStudents(@PathVariable Long courseId){
        Course course = courseService.findById(courseId);
        if (course == null){
            throw  new ResourseNotFoundException("Course with id "+courseId+" not found");
        }
        List<Student> students = course.getStudents();
        if (students == null){
            throw  new ResourseNotFoundException("this course have no students");
        }
        return new ResponseEntity<>(students , HttpStatus.OK);
    }
    @GetMapping("/courses/highestRating")
    ResponseEntity<List<Course>> getHighestRatedCourses(){
        List<Course> courses =  courseService.findAll();

        int sumOfRating = 0;
        float avgRating;
        float maxAvgRating = 0;
        Iterator iterator = courses.iterator();

        List<Course> bestCourses = new ArrayList<>();

        while (iterator.hasNext()){

            Course course = (Course) iterator.next();

            List<Review> reviews = course.getReviews();
            int n = reviews.size();

            for (Review review : reviews){
                sumOfRating = sumOfRating+ review.getRating();
            }
            avgRating = sumOfRating/n;

            if(maxAvgRating<=avgRating){
                maxAvgRating = avgRating;
                bestCourses.add(course);
            }

            sumOfRating = 0;
        }
        return new ResponseEntity<>(bestCourses , HttpStatus.OK);

    }
    @PostMapping("/courses")
     ResponseEntity<Course> createCourse( @RequestBody  Course course){

         course = courseService.save(course);

         if(course == null){
             throw new CourseNotCreateException("course not created");
         }
        // Set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();

        URI newCourseUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(course.getId())
                .toUri();

        responseHeaders.setLocation(newCourseUri);

        return new ResponseEntity<>(null , responseHeaders, HttpStatus.CREATED);
    }

    @PostMapping("/courses/{courseId}/reviews")
    ResponseEntity<Course> createCourseReview(@PathVariable Long courseId,@RequestBody Review review){
        Course course = courseService.findById(courseId);
        if (course == null){
            throw  new ResourseNotFoundException("Course with id "+courseId+" not found");
        }
        course.addReview(review);
        courseService.save(course);
        return new ResponseEntity<Course>(course , HttpStatus.OK);
    }
    @PostMapping("/courses/{courseId}/students")
    ResponseEntity<Student> addCourseToStudent( @PathVariable Long courseId,@RequestBody Student student) {
        Course course = courseService.findById(courseId);
        if (course == null){
            throw new ResourseNotFoundException("course with id "+courseId+" not found");
        }
        Student s  = studentService.getStudentByEmail(student.getEmail());

        if (s == null){
            course.addStudent(student);
            Student student1 = studentService.save(student);
            return new ResponseEntity<>(student1 , HttpStatus.OK);
        }
        else {
            s.addCourse(course);
            courseService.save(course);
            return new ResponseEntity<>(s , HttpStatus.OK);
        }
    }

    @PutMapping("/courses/{courseId}")
    ResponseEntity<Course> updateCourse(@RequestBody Course course , @PathVariable Long courseId){
        Course c = courseService.findById(courseId);
        if (c == null){
            throw new ResourseNotFoundException("course with id "+courseId+" not found");
        }
        c = courseService.updateCourse(course);
        return new ResponseEntity<>(c,HttpStatus.OK);
    }
    @DeleteMapping("/courses/{courseId}")
    ResponseEntity<Course> deleteCourse(@PathVariable Long courseId){

        Course c = courseService.findById(courseId);
        if (c == null){
            throw  new ResourseNotFoundException("course with id "+courseId+" not found");
        }
        courseService.deleteCourse(c);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
