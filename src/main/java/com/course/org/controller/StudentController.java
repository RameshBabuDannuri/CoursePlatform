package com.course.org.controller;

import com.course.org.entity.Course;
import com.course.org.entity.Student;
import com.course.org.error.exception.ResourseNotFoundException;
import com.course.org.service.CourseService;
import com.course.org.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {
    @Autowired
    StudentService studentService;
    @Autowired
    CourseService courseService;

    @GetMapping("/students")
    List<Student> getAllStudens(){
        return studentService.getAllStudents();
    }
    @GetMapping("studentById/{studentId}")
    ResponseEntity<Student> getStudentById(@PathVariable Long studentId){
        Student student = studentService.getStudentById(studentId);

        if(student == null){
            throw  new ResourseNotFoundException("Student with id "+studentId+" not found");
        }
        return new ResponseEntity<>(student, HttpStatus.OK);
    }
    @GetMapping("/students/studentByEmail/{email}")
    Student  getStudentCourses(@PathVariable String email){
        Student student =  studentService.getStudentByEmail(email);
        if(student == null){
            throw new ResourseNotFoundException("Student with email "+email+ " not found");
        }
        return student;

    }


    @GetMapping("/students/{studentId}/courses")
    ResponseEntity<List<Course>> getStudentCourses(@PathVariable Long studentId){
        Student student = studentService.getStudentById(studentId);

        if(student == null){
            throw  new ResourseNotFoundException("Student with id "+studentId+" not found");
        }

        List<Course> courses = student.getCourses();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }


    @PostMapping("/students/{studentId}/courses")
    ResponseEntity<Course> addCourseToStudent( @PathVariable Long studentId,@RequestBody Course course) {
        Student student = studentService.getStudentById(studentId);
        Course c = courseService.findByName(course.getName());

        if (student == null) {
            throw new ResourseNotFoundException("student with id " + studentId + " not found");
        }

        if(c == null){
            student.addCourse(course);
            Course course1 = courseService.save(course);
            return new ResponseEntity<>(course1 , HttpStatus.OK);
        }
        else{
           c.addStudent(student);
           studentService.save(student);
           return new ResponseEntity<>(c , HttpStatus.OK);
        }

    }

    @PostMapping("/students")
    ResponseEntity<Student> createStudent(@RequestBody Student student){
        Student student1 = studentService.getStudentByEmail(student.getEmail());
        if (student1 != null){
            throw new ResourseNotFoundException("this email  is already exist");
        }
        studentService.save(student);
        return new ResponseEntity<>(student , HttpStatus.OK);
    }

    @PutMapping("/students")
    ResponseEntity<Student> updateStudent(@RequestBody Student student){
        Student student1 = studentService.getStudentById(student.getId());
        if (student1 == null){
            throw new ResourseNotFoundException("this student not available");
        }
        student1.setFirstName(student.getFirstName());
        student1.setLastName(student.getLastName());
        student1.setEmail(student.getEmail());
        studentService.save(student1);
        return new ResponseEntity<>(student1 , HttpStatus.OK);
    }


}
