package com.course.org.service;

import com.course.org.entity.Course;
import com.course.org.entity.Review;
import com.course.org.entity.Student;
import com.course.org.error.exception.ResourseNotFoundException;
import com.course.org.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    StudentService studentService;

    public Course verifyCourse(Long courseId){
        return courseRepository.getById(courseId);
    }
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        Course course = courseRepository.getById(id);
        if (course == null){
            throw  new ResourseNotFoundException("Course with id "+id+" not exist");
        }
        return course;
    }

    public List<Course> getCourseByName(String name)
    {
       List<Course> courses = courseRepository.findByName(name);
       if (courses == null){
           throw new ResourseNotFoundException("Course with name '"+name+"' not exist");
       }
       return courses;
    }

    public Course saveOrUpdate(Course course) {
         course.setName(course.getName().toUpperCase());
        return courseRepository.save(course);
    }

    public void deleteCourse(Long courseId) {
        Course course = getCourseById(courseId);
        courseRepository.delete(course);
    }

    public Review getCourseReviewById(Long courseId , Long reviewId) {
        Course course = getCourseById(reviewId);
        List<Review> reviews = course.getReviews();
        for (Review review: reviews) {
            if (review.getId() == reviewId){
                return review;
            }
        }
        throw new ResourseNotFoundException("review with id '"+reviewId+"' not exist");
    }
    public Course addReviewToCourse(Long courseId , Review review){
        Course course = getCourseById(courseId);
        course.addReview(review);
        return saveOrUpdate(course);
    }

    public Student addStudentToCourse(Long courseId, Student student){

        Course course = getCourseById(courseId);
        Student s  = studentService.verifyStudent(student.getEmail());

        if (s == null){
            course.addStudent(student);
            Student student1 = studentService.saveOrUpdate(student);
            return student1;
        }
        else {

            s.addCourse(course);
            saveOrUpdate(course);
            return s;
        }

    }
    public List<Student> getCourseStudents(Long courseId){
        Course course = getCourseById(courseId);
        List<Student> students = course.getStudents();
        if (students == null){
            throw new ResourseNotFoundException("this course don't have  students");
        }
        return students;
    }

    public List<Course> getHightestRatedCourses() {
        List<Course> courses =  findAll();

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
        return bestCourses;
    }
}
