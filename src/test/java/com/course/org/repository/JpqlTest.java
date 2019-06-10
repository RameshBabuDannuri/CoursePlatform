package com.course.org.repository;


import com.course.org.CoursePlatformApplication;
import com.course.org.entity.Course;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoursePlatformApplication.class)
public class JpqlTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EntityManager em;
    @Test
    public void findById(){

        Query query = em.createQuery("Select c from Course c");
        List resultlist = query.getResultList();
        logger.info("Select c from Course c {}->"+resultlist);

    }
    @Test
    public void findById_typed(){

        TypedQuery<Course> query = em.createQuery("Select c from Course c where name like '%spring'", Course.class);
        List resultlist = query.getResultList();
        logger.info("Select c from Course c {}->"+resultlist);

    }
}
