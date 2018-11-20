package com.course.org.controller;

import com.course.org.entity.Review;
import com.course.org.error.exception.ResourseNotFoundException;
import com.course.org.error.exception.ReviewNotFoundException;
import com.course.org.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @GetMapping("/reviews")
    List<Review> getAllReview(){
        return reviewService.getAllReviews();
    }

    @PutMapping("/reviews/{reviewId")
    ResponseEntity<Review> updateReview(@PathVariable Long reviewId , @RequestBody Review review){
        Review r = reviewService.getReview(reviewId);
        if(r == null){
            throw  new ResourseNotFoundException("review with id"+reviewId +" not found");
        }
        r.setRating(review.getRating());
        r.setDescription(review.getDescription());
        reviewService.save(r);
        return new ResponseEntity<>(r , HttpStatus.OK);
    }
    @DeleteMapping("/reviews/{reviewId")
    ResponseEntity<?> deleteReview(@PathVariable Long reviewId){
        Review review = reviewService.getReview(reviewId);
        if(review == null) {
            throw new ResourseNotFoundException("review with id" + reviewId + " not found");
        }
        reviewService.deleteReview(review);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
