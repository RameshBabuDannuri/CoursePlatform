package com.course.org.controller;

import com.course.org.entity.Course;
import com.course.org.entity.Student;
import com.course.org.error.exception.ResourseNotFoundException;
import com.course.org.error.exception.ResourseExistException;
import com.course.org.service.CourseService;
import com.course.org.service.MapValidationErrorService;
import com.course.org.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    @Autowired
    StudentService studentService;
    @Autowired
    CourseService courseService;

    @Autowired
    MapValidationErrorService mapValidationErrorService;

    @GetMapping("/all")
    ResponseEntity<List<Student>> getAllStudents(){
        List<Student> students = studentService.getAllStudents();
        return new ResponseEntity<>(students , HttpStatus.OK);
    }

    @GetMapping("/student_id/{studentId}")
    ResponseEntity<Student> getStudentById(@PathVariable Long studentId){
        Student student = studentService.getStudentById(studentId);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping("/student_email/{email}")
    ResponseEntity<Student>  getStudentByEmail(@PathVariable String studentEmail){
        Student student =  studentService.getStudentByEmail(studentEmail);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping("/{studentId}/course")
    ResponseEntity<List<Course>> getStudentCourses(@PathVariable Long studentId){
        List<Course> courses = studentService.getStudentCourses(studentId);
        System.out.println(courses);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @PostMapping("")
    ResponseEntity<?> createStudent(@Valid @RequestBody Student student , BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap!=null){
            return errorMap;
        }
        Student student1 = studentService.saveOrUpdate(student);
        return new ResponseEntity<>(student1 , HttpStatus.OK);
    }
    @PostMapping("/{studentId}/course")
    ResponseEntity<Course> addCourseToStudent( @PathVariable Long studentId,@RequestBody Course course) {
        Course course1 = studentService.addCourseToStudent(studentId , course);
        return new ResponseEntity<>(course1 , HttpStatus.CREATED);
    }


    @DeleteMapping("/{studentId}")
    ResponseEntity<?> deleteStudent(@PathVariable Long studentId){
        studentService.deleteStudent(studentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
