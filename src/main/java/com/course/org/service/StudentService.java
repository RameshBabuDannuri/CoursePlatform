package com.course.org.service;

import com.course.org.entity.Course;
import com.course.org.entity.Student;
import com.course.org.error.exception.ResourseExistException;
import com.course.org.error.exception.ResourseNotFoundException;
import com.course.org.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class StudentService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CourseService courseService;

    public Student verifyStudent(String email){
        return studentRepository.findByEmail(email);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long studentId)
    {
        Student student =  studentRepository.findStudent(studentId);
        if (student == null){
            throw new ResourseNotFoundException("Student with id '"+studentId+"' not exist");
        }
        return student;
    }

    public Student getStudentByEmail(String email) {
        Student student =  studentRepository.findByEmail(email);
        if (student == null){
            throw new ResourseNotFoundException("Student with email '"+email+"' not exist");
        }
        return student;
    }
    public Student saveOrUpdate(Student student) {
        try {
            student.setEmail(student.getEmail());
            return studentRepository.save(student);
        }catch (Exception ex) {
            throw new ResourseExistException("Student with email '" + student.getEmail() + "' already exist");
        }
    }
    public void deleteStudent(Long studentId) {
        Student student1 = getStudentById(studentId);
        if (student1 == null) {
            throw new ResourseNotFoundException("student with id '"+"' not exist");
        }
        studentRepository.delete(student1);
    }

    public Course addCourseToStudent(Long studentId, Course course) {

        Student student = getStudentById(studentId);
        Course c = courseService.verifyCourse(course.getId());

        if(c == null){
            student.addCourse(course);
            Course course1 = courseService.saveOrUpdate(course);
            return course1;
        }
        else{
            c.addStudent(student);
            saveOrUpdate(student);
            return c;
        }


    }
    public List<Course> getStudentCourses(Long studentId) {
        Student student = getStudentById(studentId);
        List<Course> courses = student.getCourses();
        return courses;
    }
}
