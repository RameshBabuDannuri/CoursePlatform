package com.course.org.service;

import com.course.org.entity.Review;
import com.course.org.error.exception.ResourseNotFoundException;
import com.course.org.repository.ReviewRepository;
import net.bytebuddy.dynamic.scaffold.FieldLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }
    public Review getReview(Long reviewId) {
        Review review = reviewRepository.getOne(reviewId);
        if (review == null){
            throw new ResourseNotFoundException("Review with id '"+reviewId+"' not exist");
        }
        return review;
    }

    public Review saveOrUpdate(Review review) {
        return reviewRepository.save(review);
    }

    public void deleteReview(Long reviewId) {
        Review review = getReview(reviewId);
        reviewRepository.delete(review);
    }
}
