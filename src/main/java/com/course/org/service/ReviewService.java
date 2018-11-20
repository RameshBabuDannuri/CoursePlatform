package com.course.org.service;

import com.course.org.entity.Review;
import com.course.org.repository.ReviewRepository;
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
        return reviewRepository.getOne(reviewId);
    }

    public void save(Review r) {
        reviewRepository.save(r);
    }

    public void deleteReview(Review review) {
        reviewRepository.delete(review);
    }
}
