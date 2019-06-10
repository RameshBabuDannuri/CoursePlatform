package com.course.org.controller;

import com.course.org.entity.Review;
import com.course.org.error.exception.ResourseNotFoundException;
import com.course.org.service.MapValidationErrorService;
import com.course.org.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;
    @Autowired
    MapValidationErrorService mapValidationErrorService;

    @GetMapping("")
    List<Review> getAllReview(){
        return reviewService.getAllReviews();
    }

    @PutMapping("")
    ResponseEntity<?> createReview(@Valid @RequestBody Review review , BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap!=null){
            return errorMap;
        }
        Review review1 = reviewService.saveOrUpdate(review);
        return new ResponseEntity<>(review1 , HttpStatus.CREATED);
    }
    @DeleteMapping("/{reviewId")
    ResponseEntity<?> deleteReview(@PathVariable Long reviewId){
        reviewService.deleteReview(reviewId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
