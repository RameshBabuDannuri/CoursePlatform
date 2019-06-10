package com.course.org;

import com.course.org.entity.Course;
import com.course.org.entity.Review;
import com.course.org.entity.Student;
import com.course.org.repository.CourseRepository;
import com.course.org.repository.ReviewRepository;
import com.course.org.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class CoursePlatformApplication  implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CourseRepository courseRepository;
	@Autowired
	ReviewRepository reviewRepository;

	@Autowired
	StudentRepository studentRepository;

	public static void main(String[] args) {
		SpringApplication.run(CoursePlatformApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

<<<<<<< HEAD

			Course course1 = new Course( "Hibernate" , "2000","nice course");
=======
		/*
			Course course1 = new Course("Hinbernate" , "3000");
>>>>>>> a0cde94c8284bec91437a6a13735b54edcb30e99
			courseRepository.save(course1);

			Student student1 = new Student("sai","dannuri","sai.dannuri@gmail.com");
			course1.addStudent(student1);

			studentRepository.save(student1);
			courseRepository.save(course1);
<<<<<<< HEAD

=======
		*/
>>>>>>> a0cde94c8284bec91437a6a13735b54edcb30e99
	}
}
