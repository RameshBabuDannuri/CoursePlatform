package com.course.org.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="student")
public class Student {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="first_name")
    @NotBlank(message = "please enter first name")
    @Size(min = 3 , max = 20, message = "please enter minimum 3 and max 20 characters ")
    private String firstName;

    @Column(name="last_name")
    @NotBlank(message = "please enter last name")
    @Size(min = 3 , max = 20, message = "please enter minimum 3 and max 20 characters ")
    private String lastName;

    @Column(name="email",unique = true)
    @Email(message = "please enter valid email address")
    private String email;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date registerDate_at;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date updated_at;

    @ManyToMany(fetch=FetchType.LAZY,
            cascade= {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name="course_student",
            joinColumns=@JoinColumn(name="student_id"),
            inverseJoinColumns=@JoinColumn(name="course_id")
    )
    @JsonIgnore
    private List<Course> courses;


    public Student() {

    }

    public Student(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @PrePersist
    protected  void onCreate(){
        this.registerDate_at = new Date();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updated_at = new Date();
    }
    public Date getRegisterDate_at() {
        return registerDate_at;
    }

    public void setRegisterDate_at(Date registerDate_at) {
        this.registerDate_at = registerDate_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public List<Course> getCourses() {
        return courses;
    }


    public void addCourse(Course course) {
        if(courses == null){
            courses = new ArrayList<>();
        }
        courses.add(course);
    }

    @Override
    public String toString() {
        return "Student [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
    }


}