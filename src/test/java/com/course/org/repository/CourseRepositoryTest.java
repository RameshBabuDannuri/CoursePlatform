package com.course.org.repository;

import com.course.org.CoursePlatformApplication;
import com.course.org.entity.Course;
import com.course.org.service.CourseService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoursePlatformApplication.class)
public class CourseRepositoryTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    CourseRepository courseRepository;

    @Test
    public void findById(){

        Course course = courseRepository.getOne(1L);
        assertEquals("spring",course.getName());

        logger.info("Testing is running");
    }

    @Test
    @DirtiesContext
    public void deleteById(){
        Course course = courseRepository.getOne(30L);
        courseRepository.delete(course);
        assertNull(courseRepository.findById(32L));
    }

    @Test
    @DirtiesContext
    public void createCourse(){
        Course course = new Course("spring mvc","2300","web devlopment course");

      Course c =   courseRepository.save(course);

      Course c2 = courseRepository.getOne(c.getId());

        assertEquals("spring mvc",c2.getName());
    }

}
