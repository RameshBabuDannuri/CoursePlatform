package com.course.org.service;

import com.course.org.entity.Course;
import com.course.org.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseService {

    @Autowired
    CourseRepository courseRepository;

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Course findById(Long id) {
        return courseRepository.getById(id);
    }

    public Course findByName(String name) {
        return courseRepository.findByName(name);
    }

    public Course save(Course course) {
        return courseRepository.save(course);

    }
    public Course updateCourse(Course course) {

       return courseRepository.save(course);
    }

    public void deleteCourse(Course course) {
        courseRepository.delete(course);
    }
}
